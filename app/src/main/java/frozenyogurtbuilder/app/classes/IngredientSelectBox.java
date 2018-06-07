package frozenyogurtbuilder.app.classes;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;

import frozenyogurtbuilder.app.Exceptions.OrderIsFullException;
import frozenyogurtbuilder.app.OrderProcess;
import frozenyogurtbuilder.app.R;

public class IngredientSelectBox extends AlertSelectBox<Ingredient> {

    private AlertDialog.Builder exceptionAlert;
    private Order shoppingList;
    private Context context;

    private int insertPointer = -1;

    public IngredientSelectBox(Context context, Order shoppingList) {
        super(context, "Wählen sie eine Zutat", R.drawable.logo_icon);
        this.shoppingList = shoppingList;
        this.context = context;
        buildExceptionAlertBox();
    }

    @Override
    public void afterSelect() {
        try {
            if(insertPointer == -1){
                shoppingList.add((Ingredient) super.getSelectedItem());
            }else {
                shoppingList.set(insertPointer,(Ingredient) super.getSelectedItem());
                insertPointer = -1;
            }

        } catch (OrderIsFullException e) {
            //Alert (To many main ingridtients)
            exceptionAlert.show();
        }
    }
    private void buildExceptionAlertBox(){
        exceptionAlert = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Theme_AppCompat));
        exceptionAlert.setTitle("Tut uns Leid")
                .setMessage("Leider hast du das Limet an Hauptzutaten erreicht ( "+ shoppingList.ORDER_SIZE +" )\n\nFalls du mehr Hauptzutaten haben möchteste wähle bitte eine andere Größe aus.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
    }
    public void setInsertPointer(int insertPointer){
        this.insertPointer = insertPointer;
    }
}
