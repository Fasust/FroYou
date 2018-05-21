package mocogruppe1.frozenjoghurtbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;

import mocogruppe1.frozenjoghurtbuilder.classes.Ingredient;

public class OrderProcess extends AppCompatActivity {

    //Buttons
    private FloatingActionButton btn_goTo_orFi;

    //Ingredient
    private Ingredient[] ingredientsArray;
    private ArrayList<Ingredient> mainingredients;
    private ArrayList<Ingredient> topings;
    private ArrayList<Ingredient> sauce;
    private int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildLayout();
        loadIngredients();
        loadSize();

        writeToDebugText();

    }

    private void buildLayout(){
        setContentView(R.layout.activity_orderprocess);

        //Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bestellung Prozess");


        //Buttons
        btn_goTo_orFi = findViewById(R.id.btn_goTo_orFi) ;
        btn_goTo_orFi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderProcess.this, OrderFinal.class));
            }

        });
    }
    private void loadIngredients(){
        /*
        LOAD All Ingredients---------------------
         */

        Gson gson = new Gson();

        //Load JSON String from Assets "ingredientsList.json"
        String ingredients_String = loadJSONFromAsset("ingredientsList");

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
                case 'm':
                    mainingredients.add(ing);
                    break;
                case 't':
                    topings.add(ing);
                    break;
                case 's':
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

    /**
     * Load a Json String from Assets
     * @param filename name of the JSON file in Assets (end with .json)
     * @return Json String
     */
    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
