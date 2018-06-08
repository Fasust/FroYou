package frozenyogurtbuilder.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import frozenyogurtbuilder.app.Exceptions.OrderIsFullException;
import frozenyogurtbuilder.app.classes.AlertSelectBox;
import frozenyogurtbuilder.app.classes.Ingredient;
import frozenyogurtbuilder.app.classes.IngredientAdapter;
import frozenyogurtbuilder.app.classes.IngredientSelectBox;
import frozenyogurtbuilder.app.classes.Order;
import frozenyogurtbuilder.app.classes.RecourceLoader;
import frozenyogurtbuilder.app.classes.SwipeDismissListViewTouchListener;
import frozenyogurtbuilder.app.classes.external.Adapter;
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
    private int size = 0;

    //View
    private ListView displayList;

    //Order
    private Order shoppingList;

    //SelectBoxes
    private IngredientSelectBox selectBox_souce;
    private IngredientSelectBox selectBox_topping;
    private IngredientSelectBox selectBox_main;

    ArrayList<Ingredient> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderprocess);

        list = new ArrayList<>();
        list.add(new Ingredient("Bana",'s'));
        list.add(new Ingredient("lana",'t'));
        list.add(new Ingredient("hana",'m'));
        list.add(new Ingredient("mana",'s'));

        final CustomListView listView = (CustomListView)findViewById(R.id.orderprocess_listview);
        Adapter adapter = new Adapter(this, list, new Adapter.Listener() {
            @Override
            public void onGrab(int position, ConstraintLayout row) {
                listView.onGrab(position, row);
            }
        });

        listView.setAdapter(adapter);
        listView.setListener(new CustomListView.Listener() {
            @Override
            public void swapElements(int indexOne, int indexTwo) {
                Ingredient temp = list.get(indexOne);
                list.set(indexOne, list.get(indexTwo));
                list.set(indexTwo, temp);
            }
        });
        adapter.add(new Ingredient("banarama",'s'));

        /*
        loadIngredients();
        loadSize();
        buildShoppingList();
        buildSelectBoxes();
        buildButtons();

        //Have to init selectboxes and order after the fact because the Selectboxes need the shoppinglist for there init
        shoppingList.getIngredientsAdapter().init(selectBox_main,selectBox_souce,selectBox_topping,shoppingList);

        try {
            shoppingList.add(new Ingredient("Schockolade",'s'));
            shoppingList.add(new Ingredient("Erdbeer",'s'));
            shoppingList.add(new Ingredient("Nüsse",'t'));
            shoppingList.add(new Ingredient("Mandeln",'t'));
            shoppingList.add(new Ingredient("Bunter Streusel",'t'));

        } catch (OrderIsFullException e) {
            e.printStackTrace();
        }
        */
    }

    private void buildSelectBoxes(){

        //souce
        selectBox_souce = new IngredientSelectBox(OrderProcess.this,shoppingList);
        selectBox_souce.add(sauce);

        //topping
        selectBox_topping = new IngredientSelectBox(OrderProcess.this,shoppingList);
        selectBox_topping.add(topings);

        //main
        selectBox_main = new IngredientSelectBox(OrderProcess.this,shoppingList);
        selectBox_main.add(mainingredients);

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
                selectBox_main.show();
            }

        });
        btn_addSouce = findViewById(R.id.btn_addSauce) ;
        btn_addSouce.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectBox_souce.show();
            }

        });
        btn_addTopping = findViewById(R.id.btn_addTopping) ;
        btn_addTopping.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectBox_topping.show();
            }

        });
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
    private void loadSize(){
        size = getIntent().getExtras().getInt(OrderChoosePricing.SIZE_KEY);
        Log.d("Size",size+"");
    }
    private void buildShoppingList(){
        //Adapter and List---------------------------------------------------

        final IngredientAdapter ingredientAdapter = new IngredientAdapter(this);
        displayList = findViewById(R.id.orderprocess_listview);
        displayList.setAdapter(ingredientAdapter);

        shoppingList = new Order(size,ingredientAdapter);

        //Swipe Dissmiss------------------------------------------------------
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        displayList,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    shoppingList.remove(position);
                                    shoppingList.getIngredientsAdapter().notifyDataSetChanged();
                                }

                            }
                        });
        displayList.setOnTouchListener(touchListener);

    }

}
