package frozenyogurtbuilder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.Locale;

import frozenyogurtbuilder.app.classes.BaseErrorMessage;
import frozenyogurtbuilder.app.classes.Firebase.FSIngridientsListLoader;
import frozenyogurtbuilder.app.classes.Firebase.FSLoader;
import frozenyogurtbuilder.app.classes.Ingredient;
import frozenyogurtbuilder.app.classes.Order;
import frozenyogurtbuilder.app.classes.external.CustomListView;

public class OrderProcess extends AppCompatActivity {

    //View
    private ProgressBar progressBar;

    //Ingredient
    public static ArrayList<Ingredient> mainingredients;
    public static ArrayList<Ingredient> topings;
    public static ArrayList<Ingredient> sauce;

    //Order
    private Order shoppingList;
    public static int ORDER_SIZE;
    public static final String  ORDER_KEY = "order";

    //Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ingredientsCollection = db.collection("ingredients");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderprocess);

        ORDER_SIZE = getSize();
        buildProgressbar();

        FSIngridientsListLoader loader = new FSIngridientsListLoader(ingredientsCollection,new FSLoader.TaskListner<ArrayList<Ingredient>>() {
            @Override
            public void onComplete(ArrayList<Ingredient> arrayList) {

                //Functions that Require Successfull DB loading
                sortIngridients(arrayList);
                buildShoppingList();
                buildButtons();
                buildMainCounter();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFail() {
                showLoadingError();
            }
        });
        loader.execute();

    }

    private void sortIngridients(ArrayList<Ingredient> ingredients){
        mainingredients = new ArrayList<>();
        sauce = new ArrayList<>();
        topings = new ArrayList<>();

        for(Ingredient ing : ingredients){
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
    private void buildProgressbar(){
        progressBar = findViewById(R.id.progressBar);
    }
    private void buildShoppingList(){
        shoppingList = new Order(ORDER_SIZE,(CustomListView) findViewById(R.id.orderprocess_listview),OrderProcess.this);
    }
    private void buildButtons(){
        ImageButton btn_goTo_orFi;
        ImageButton btn_addMain;
        ImageButton btn_addTopping;
        ImageButton btn_addSouce;

        btn_goTo_orFi = findViewById(R.id.btn_goTo_orderFinal) ;
        btn_goTo_orFi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(shoppingList.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.emtyOrder), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0 , 220);
                    toast.show();
                    return;
                }
                if(shoppingList.getMainIngridientCount() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.noMain), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0 , 220);
                    toast.show();
                    return;
                }

                Intent intent = new Intent(OrderProcess.this, OrderFinal.class);
                intent.putExtra(ORDER_KEY,shoppingList.toString());
                startActivity(intent);
            }

        });

        //Add
        btn_addMain = findViewById(R.id.btn_addMain) ;
        btn_addMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shoppingList.addThroghSelectBox(Ingredient.INGREDIENT_MAIN);
            }

        });
        btn_addSouce = findViewById(R.id.btn_addSauce) ;
        btn_addSouce.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shoppingList.addThroghSelectBox(Ingredient.INGREDIENT_SAUCE);
            }

        });
        btn_addTopping = findViewById(R.id.btn_addTopping) ;
        btn_addTopping.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shoppingList.addThroghSelectBox(Ingredient.INGREDIENT_TOPPING);
            }

        });


    }
    private void buildMainCounter(){
        TextView textView_mainCounterSize = findViewById(R.id.textView_mainCounterSize);
        textView_mainCounterSize.setText(String.valueOf(ORDER_SIZE));

        // order price in top left
        final TextView showOrderPrice = findViewById(R.id.showOrderPrice);

        //Explain Text
        final TextView explainText = findViewById(R.id.textView_howToInfo);

        final TextView textView_mainCounter = findViewById(R.id.textView_mainCounter);

        shoppingList.setOnListChangeEventListner(new Order.OnListChangeEventListner() {
            @Override
            public void listChange() {

                textView_mainCounter.setText(shoppingList.getMainIngridientCount() + "");

                if(!shoppingList.isEmpty()){
                    explainText.setVisibility(View.INVISIBLE);
                    showOrderPrice.setVisibility(View.VISIBLE);
                }else {
                    explainText.setVisibility(View.VISIBLE);
                    showOrderPrice.setVisibility(View.INVISIBLE);
                }

                showOrderPrice.setText(String.format(Locale.GERMAN,"%.2f", shoppingList.getPrice()) + " Euro");

            }
        });
    }
    private int getSize(){
        return  getIntent().getExtras().getInt(OrderChoosePricing.SIZE_KEY);
    }
    private void showLoadingError(){
        BaseErrorMessage loadingError = new BaseErrorMessage(this.getString(R.string.loadingERR),"Sorry",OrderProcess.this);
        loadingError.show();
    }

}
