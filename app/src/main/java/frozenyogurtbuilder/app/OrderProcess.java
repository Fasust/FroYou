package frozenyogurtbuilder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import frozenyogurtbuilder.app.Exceptions.OrderIsFullException;
import frozenyogurtbuilder.app.classes.AlertSelectBox;
import frozenyogurtbuilder.app.classes.Ingredient;
import frozenyogurtbuilder.app.classes.IngredientSelectBox;
import frozenyogurtbuilder.app.classes.Order;
import frozenyogurtbuilder.app.classes.RecourceLoader;
import frozenyogurtbuilder.app.classes.external.CustomListView;
import frozenyogurtbuilder.app.classes.maineventlistener;

public class OrderProcess extends AppCompatActivity implements maineventlistener {

    //Buttons
    private ImageButton btn_goTo_orFi;
    private ImageButton btn_addMain;
    private ImageButton btn_addTopping;
    private ImageButton btn_addSouce;

    // globally
    private TextView textView_mainCounterSize;
    private TextView textView_mainCounter;

    //Ingredient
    private Ingredient[] ingredientsArray;
    public static ArrayList<Ingredient> mainingredients;
    public static ArrayList<Ingredient> topings;
    public static ArrayList<Ingredient> sauce;

    //Order
    private Order shoppingList;
    public static int ORDER_SIZE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderprocess);

        ORDER_SIZE = getSize();

        loadIngredients();
        buildShoppingList();
        buildMainCounter();
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
        CustomListView listView = (CustomListView) findViewById(R.id.orderprocess_listview);

        //textView_mainCounterSize.setText(String.valueOf(shoppingList.getMainIngridientCount()));


        shoppingList = new Order(ORDER_SIZE,listView,OrderProcess.this);
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
        btn_addMain = findViewById(R.id.btn_addMain) ;
        btn_addMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shoppingList.addThroghSelectBox('m');
            }

        });
        btn_addSouce = findViewById(R.id.btn_addSauce) ;
        btn_addSouce.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shoppingList.addThroghSelectBox('s');
            }

        });
        btn_addTopping = findViewById(R.id.btn_addTopping) ;
        btn_addTopping.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shoppingList.addThroghSelectBox('t');
            }

        });

    }

    private void buildMainCounter() {
        textView_mainCounterSize = findViewById(R.id.textView_mainCounterSize);
        textView_mainCounterSize.setText(String.valueOf(ORDER_SIZE));

        textView_mainCounter = findViewById(R.id.textView_mainCounter);
    }

    private int getSize(){
        return  getIntent().getExtras().getInt(OrderChoosePricing.SIZE_KEY);
    }

    @Override
    public void mainIngredientChanged(int mainCounter) {
        textView_mainCounterSize.setText(String.valueOf(mainCounter));
    }

}