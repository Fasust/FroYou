package mocogruppe1.frozenjoghurtbuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;

import mocogruppe1.frozenjoghurtbuilder.Exceptions.OrderIsFullException;
import mocogruppe1.frozenjoghurtbuilder.classes.AlertSelectBox;
import mocogruppe1.frozenjoghurtbuilder.classes.Ingredient;
import mocogruppe1.frozenjoghurtbuilder.classes.IngredientAdapter;
import mocogruppe1.frozenjoghurtbuilder.classes.Order;
import mocogruppe1.frozenjoghurtbuilder.classes.RecourceLoader;
import mocogruppe1.frozenjoghurtbuilder.classes.SwipeDismissListViewTouchListener;

public class OrderProcess extends AppCompatActivity {

    //Buttons
    private FloatingActionButton btn_goTo_orFi;

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

    //Tools
    private AlertSelectBox<Ingredient> selectBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderprocess);

        loadIngredients();
        loadSize();
        buildShoppingList();
        buildLayout();

        try {
            shoppingList.add(new Ingredient("Iulia",'s'));
            shoppingList.add(new Ingredient("Brot",'t'));
            shoppingList.add(new Ingredient("Butter",'s'));
            shoppingList.add(new Ingredient("Mett",'t'));
        } catch (OrderIsFullException e) {
            e.printStackTrace();
        }
        //writeToDebugText();

    }

    private void buildLayout(){

        //selectbox
        selectBox = new AlertSelectBox<Ingredient>(
                displayList.getContext(),
                "Wählen sie eine Zutat",
                R.drawable.logo_icon) {

            @Override
            public void afterSelect() {
                try {
                    shoppingList.add(selectBox.getSelectedItem());
                } catch (OrderIsFullException e) {
                    e.printStackTrace();
                }
            }
        };
        selectBox.add(sauce);

        //Buttons
        btn_goTo_orFi = findViewById(R.id.btn_goTo_orFi) ;
        btn_goTo_orFi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectBox.show();
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
        final IngredientAdapter ingredientAdapter = new IngredientAdapter(this);
        displayList = findViewById(R.id.orderprocess_listview);
        displayList.setAdapter(ingredientAdapter);

        shoppingList = new Order(size,ingredientAdapter);

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

    private void writeToDebugText(){
        TextView debugtxt = findViewById(R.id.txt_order_debug);
        debugtxt.setText("\nSize:   " +size);

        debugtxt.append("\n------------------------------\n");

        for (Ingredient ing : mainingredients){
            debugtxt.append("\nType:   " + ing.getType());
            debugtxt.append(" | Name:   " + ing.getName());

        }
        debugtxt.append("\n------------------------------\n");
        for (Ingredient ing : sauce){
            debugtxt.append("\nType:   " + ing.getType());
            debugtxt.append(" | Name:   " + ing.getName());

        }
        debugtxt.append("\n------------------------------\n");
        for (Ingredient ing : topings){
            debugtxt.append("\nType:   " + ing.getType());
            debugtxt.append(" | Name:   " + ing.getName());

        }
    }
}
