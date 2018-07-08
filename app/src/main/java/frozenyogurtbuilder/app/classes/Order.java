package frozenyogurtbuilder.app.classes;

import android.content.Context;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Locale;

import frozenyogurtbuilder.app.OrderChoosePricing;
import frozenyogurtbuilder.app.OrderProcess;
import frozenyogurtbuilder.app.R;
import frozenyogurtbuilder.app.classes.external.Adapter;
import frozenyogurtbuilder.app.classes.external.CustomListView;
import frozenyogurtbuilder.app.classes.external.SwipeDismissListViewTouchListener;

public class Order {
    public interface OnListChangeEventListner{
        void listChange();
    }
    private OnListChangeEventListner onListChangeEventListner;

    private final int ORDER_SIZE;
    private int mainIngridientCount = 0;
    private Adapter ingredientsAdapter;
    private BaseErrorMessage exceptionAlert;
    private Context context;
    private ArrayList<Ingredient> ingredientList;

    private IngredientSelectBox mainBoxAdd;
    private IngredientSelectBox souceBoxAdd;
    private IngredientSelectBox toppingBoxAdd;

    private IngredientSelectBox mainBoxSet;
    private IngredientSelectBox souceBoxSet;
    private IngredientSelectBox toppingBoxSet;

    private int selectionIndex;

    public Order(int size, final CustomListView listView, Context context) {
        this.ORDER_SIZE = size;
        this.context = context;
        ingredientList = new ArrayList<>();

        buildExceptionAlertBox();
        buildSelectBoxes();


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
                                    ingredientsAdapter.notifyDataSetChanged();
                                }

                            }
                        });
        listView.setOnTouchListener(touchListener);

        //--------------------------------------------------------------------

        ingredientsAdapter.initOrder(this);
    }

    private void buildExceptionAlertBox(){
        exceptionAlert = new BaseErrorMessage(
                context.getString(R.string.toMannyMain),
                "Sorry",
                context);
    }
    private void buildSelectBoxes(){
        //Add-----------------------
        IngredientSelectBox.AfterSelctListener<Ingredient> addAfterSelctListener = new IngredientSelectBox.AfterSelctListener<Ingredient>(){
            @Override
            public void afterSelect(Ingredient selectedItem){
                add(selectedItem);
            }
        };

        mainBoxAdd = new IngredientSelectBox(context,context.getString(R.string.bannerChooseMain),addAfterSelctListener);
        souceBoxAdd = new IngredientSelectBox(context,context.getString(R.string.bannerChooseSouce),addAfterSelctListener);
        toppingBoxAdd = new IngredientSelectBox(context,context.getString(R.string.bannerChooseTopping),addAfterSelctListener);

        mainBoxAdd.add(OrderProcess.mainingredients);
        souceBoxAdd.add(OrderProcess.sauce);
        toppingBoxAdd.add(OrderProcess.topings);

        //Set------------------------
        IngredientSelectBox.AfterSelctListener<Ingredient> setAfterSelctListener = new IngredientSelectBox.AfterSelctListener<Ingredient>(){
            @Override
            public void afterSelect(Ingredient selectedItem){
                set(selectionIndex,selectedItem);
            }
        };

        mainBoxSet = new IngredientSelectBox(context,context.getString(R.string.bannerChooseMain),setAfterSelctListener);
        souceBoxSet = new IngredientSelectBox(context,context.getString(R.string.bannerChooseMain),setAfterSelctListener);
        toppingBoxSet = new IngredientSelectBox(context,context.getString(R.string.bannerChooseMain),setAfterSelctListener);

        mainBoxSet.add(OrderProcess.mainingredients);
        souceBoxSet.add(OrderProcess.sauce);
        toppingBoxSet.add(OrderProcess.topings);

    }
    private void showAlert(){
        exceptionAlert.show();
    }

    public void addThroghSelectBox(char type){
        switch (type){
            case Ingredient.INGREDIENT_MAIN:
                mainBoxAdd.show();
                break;
            case Ingredient.INGREDIENT_SAUCE:
                souceBoxAdd.show();
                break;
            case Ingredient.INGREDIENT_TOPPING:
                toppingBoxAdd.show();
                break;
        }
    }
    public void setThroghSelectBox(char type, int index){
        selectionIndex = index;

        switch (type){
            case Ingredient.INGREDIENT_MAIN:
                mainBoxSet.show();
                break;
            case Ingredient.INGREDIENT_SAUCE:
                souceBoxSet.show();
                break;
            case Ingredient.INGREDIENT_TOPPING:
                toppingBoxSet.show();
                break;
        }
    }

    public void setOnListChangeEventListner(OnListChangeEventListner listChangeEventListner){
        this.onListChangeEventListner = listChangeEventListner;
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

        if(onListChangeEventListner != null){onListChangeEventListner.listChange();}
    }

    public void set(int i, Ingredient ingredient){
        ingredientsAdapter.insert(new Ingredient(ingredient.getName(),ingredient.getType()),i);

        if(onListChangeEventListner != null){onListChangeEventListner.listChange();}
    }
    public void remove(Ingredient ingridient){
        if(ingridient.getType() == Ingredient.INGREDIENT_MAIN){
            mainIngridientCount--;
        }
        ingredientsAdapter.remove(ingridient);

        if(onListChangeEventListner != null){onListChangeEventListner.listChange();}

    }
    public void remove(int position) {
        remove(ingredientsAdapter.getItem(position));
    }

    public int getMainIngridientCount() {
        return mainIngridientCount;
    }

    @Override
    public String toString(){
        String string = "";

        if(ingredientsAdapter.getCount() == 0){
            return "ERROR";
        }

        for (int i = 0; i < ingredientsAdapter.getCount(); i++) {

            Ingredient ingredient = ingredientsAdapter.getItem(i);
            if (ingredient.getType() == Ingredient.INGREDIENT_MAIN) {
                string += "*";
            }

            string += ingredientsAdapter.getItem(i).toString();

            if (ingredient.getType() == Ingredient.INGREDIENT_MAIN) {
                string += "*\n";
            }else {
                string += "\n";
            }
        }

        string += "\n---\n" + String.format(Locale.GERMAN,"%.2f", getPrice()) +" Euro";
        return string;
    }
    public boolean isEmpty(){
        return ingredientsAdapter.isEmpty();
    }
    public double getPrice(){
        double  price = 0;

        switch (mainIngridientCount) {
            case 1:
                price = OrderChoosePricing.PRICE_SMALL;
                break;
            case 2:
                price = OrderChoosePricing.PRICE_MEDIUM;
                break;
            case 3:
                price = OrderChoosePricing.PRICE_LARG;
                break;
        }
        return price;
    }
}
