package frozenyogurtbuilder.app.classes;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import frozenyogurtbuilder.app.Exceptions.OrderIsFullException;
import frozenyogurtbuilder.app.OrderProcess;
import frozenyogurtbuilder.app.R;
import frozenyogurtbuilder.app.classes.external.Adapter;
import frozenyogurtbuilder.app.classes.external.CustomListView;
import frozenyogurtbuilder.app.classes.external.SwipeDismissListViewTouchListener;

public class Order {
    public final int ORDER_SIZE;
    private int mainIngridientCount = 0;
    private Adapter ingredientsAdapter;
    private AlertDialog.Builder exceptionAlert;
    private Context context;
    private ArrayList<Ingredient> ingredientList;

    public Order(int size, final CustomListView listView, Context context) {
        this.ORDER_SIZE = size;
        ingredientList = new ArrayList<>();
        this.context = context;
        buildExceptionAlertBox();
        this.ingredientsAdapter = new Adapter(context, ingredientList, new Adapter.Listener() {

            @Override
            public void onGrab(int position, RelativeLayout row) {
                listView.onGrab(position, row);
            }
        });
        listView.setAdapter(ingredientsAdapter);
        listView.setListener(new CustomListView.Listener() {

            @Override
            public void swapElements(int indexOne, int indexTwo) {
                Ingredient temp = get(indexOne);
                ingredientList.set(indexOne, get(indexTwo));
                ingredientList.set(indexTwo, temp);
            }
        });

        //Swipe Dissmiss------------------------------------------------------
        /*
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    remove(position);
                                    notifyDataSetChanged();
                                }

                            }
                        });
        listView.setOnTouchListener(touchListener);
        */
    }

    public void addThroughBox(AlertSelectBox<Ingredient> box){
        box.show();
    }
    private void buildExceptionAlertBox(){
        exceptionAlert = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Theme_AppCompat));
        exceptionAlert.setTitle("Tut uns Leid")
                .setMessage("Leider hast du das Limet an Hauptzutaten erreicht ( "+ OrderProcess.ORDER_SIZE +" )\n\nFalls du mehr Hauptzutaten haben möchteste wähle bitte eine andere Größe aus.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
    }
    public void showAlert(){
        exceptionAlert.show();
    }

    //Add set Remove
    public Ingredient get(int index){
        return ingredientsAdapter.getItem(index);
    }
    public void add(Ingredient ingredient) {
        if(ingredient.getType() == Ingredient.INGREDIENT_MAIN){
            if(mainIngridientCount < ORDER_SIZE){
                mainIngridientCount++;
            }else {
                showAlert();
                return;
            }
        }
        ingredientsAdapter.add(new Ingredient(ingredient.getName(),ingredient.getType()));
    }
    public void set(int i, Ingredient ingredient){
        ingredientsAdapter.insert(new Ingredient(ingredient.getName(),ingredient.getType()),i);
    }
    public void remove(Ingredient ingridient){
        if(ingridient.getType() == Ingredient.INGREDIENT_MAIN){
            mainIngridientCount--;
        }
        ingredientsAdapter.remove(ingridient);
    }
    public void remove(int position) {
        remove(ingredientsAdapter.getItem(position));
    }

}
