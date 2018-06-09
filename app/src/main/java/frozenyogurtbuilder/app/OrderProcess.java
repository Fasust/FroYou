package frozenyogurtbuilder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;

import java.util.ArrayList;

import frozenyogurtbuilder.app.Exceptions.OrderIsFullException;
import frozenyogurtbuilder.app.classes.AlertSelectBox;
import frozenyogurtbuilder.app.classes.Ingredient;
import frozenyogurtbuilder.app.classes.IngredientSelectBox;
import frozenyogurtbuilder.app.classes.Order;
import frozenyogurtbuilder.app.classes.RecourceLoader;
import frozenyogurtbuilder.app.classes.external.CustomListView;

public class OrderProcess extends AppCompatActivity {

    //Buttons
    private ImageButton btn_goTo_orFi;
    private ImageButton btn_addMain;
    private ImageButton btn_addTopping;
    private ImageButton btn_addSouce;

    //Ingredient
    private Ingredient[] ingredientsArray;
    private ArrayList<Ingredient> mainingredients;
    private ArrayList<Ingredient> topings;
    private ArrayList<Ingredient> sauce;

    //Order
    private Order shoppingList;
    public static int ORDER_SIZE;
    private String mainBanner = "Wählen sie eine Ihrer " + ORDER_SIZE + " Hauptzutaten.";
    private String toppingBanner = "Bitte Wählen sie ein Topping";
    private String souceBanner = "Bitte Wählen sie eine Soße";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderprocess);

        ORDER_SIZE = getSize();

        loadIngredients();
        buildShoppingList();
        buildButtons();

    }
    private void loadIngredients(){
        /*
        LOAD All Ingredients---------------------
         */

        Gson gson = new Gson();

        //Load JSON String from Assets "ingredientsList.json"
        String ingredients_String = RecourceLoader.loadJSONFromAsset(getAssets(),"ingredientsList");

        //Turn JSON String to Ingredients (obj) Array
        ingredientsArray = gson.fromJson(ingredients_String, Ingredient[].class);

        /*
        SORT Ingredients---------------------
         */

        mainingredients = new ArrayList<>();
        sauce = new ArrayList<>();
        topings = new ArrayList<>();

        for(Ingredient ing : ingredientsArray){
            switch (ing.getType()){
                case Ingredient.INGREDIENT_MAIN:
                    mainingredients.add(ing);
                    break;
                case Ingredient.INGREDIENT_TOPPING:
                    topings.add(ing);
                    break;
                case Ingredient.INGREDIENT_SAUCE:
                    sauce.add(ing);
                    break;
                default:
                    break;
            }
        }

    }
    private void buildShoppingList(){

        shoppingList = new Order(ORDER_SIZE,(CustomListView) findViewById(R.id.orderprocess_listview),OrderProcess.this);

        try {
            shoppingList.add(new Ingredient("Iulia",'m'));
            shoppingList.add(new Ingredient("Iulia",'s'));
            shoppingList.add(new Ingredient("Iulia",'t'));
            shoppingList.add(new Ingredient("Iulia",'t'));
        } catch (OrderIsFullException e) {
            e.printStackTrace();
        }

    }
    private void buildButtons(){

        btn_goTo_orFi = findViewById(R.id.btn_goTo_orderFinal) ;
        btn_goTo_orFi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderProcess.this, OrderFinal.class));
            }

        });

        //Add
        IngredientSelectBox.AfterSelctListener<Ingredient> afterSelctListener = new IngredientSelectBox.AfterSelctListener<Ingredient>(){
            @Override
            public void afterSelect(Ingredient selectedItem){
                try {
                    shoppingList.add(selectedItem);
                } catch (OrderIsFullException e) {
                    shoppingList.showAlert();
                }
            }
        };

        final IngredientSelectBox mainBox = new IngredientSelectBox(this,mainBanner,afterSelctListener);
        final IngredientSelectBox souceBox = new IngredientSelectBox(this,souceBanner,afterSelctListener);
        final IngredientSelectBox toppingBox = new IngredientSelectBox(this,toppingBanner,afterSelctListener);

        mainBox.add(mainingredients);
        souceBox.add(sauce);
        toppingBox.add(topings);

        btn_addMain = findViewById(R.id.btn_addMain) ;
        btn_addMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mainBox.show();
            }

        });
        btn_addSouce = findViewById(R.id.btn_addSauce) ;
        btn_addSouce.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                souceBox.show();
            }

        });
        btn_addTopping = findViewById(R.id.btn_addTopping) ;
        btn_addTopping.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                toppingBox.show();
            }

        });
    }

    private int getSize(){
        return  getIntent().getExtras().getInt(OrderChoosePricing.SIZE_KEY);
    }

}
