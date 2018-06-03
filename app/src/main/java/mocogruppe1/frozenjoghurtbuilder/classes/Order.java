package mocogruppe1.frozenjoghurtbuilder.classes;

import java.util.ArrayList;

import mocogruppe1.frozenjoghurtbuilder.Exceptions.OrderIsFullException;

public class Order {
    private final int ORDER_SIZE;
    private int mainIngridientCount = 0;
    private IngredientAdapter ingredientList;

    public Order(int size,IngredientAdapter ingredientList) {
        this.ORDER_SIZE = size;
        this.ingredientList = ingredientList;
    }

    public void add(Ingredient ingredient) throws OrderIsFullException {
        if(ingredient.getType() == Ingredient.INGREDIENT_MAIN){
            if(mainIngridientCount < ORDER_SIZE){
                mainIngridientCount++;
            }else {
                throw new OrderIsFullException();
            }
        }
        ingredientList.add(ingredient);
    }
    public void remove(Ingredient ingridient){
        if(ingridient.getType() == Ingredient.INGREDIENT_MAIN){
            mainIngridientCount--;
        }
        ingredientList.remove(ingridient);
    }
    public void remove(int position) {
        remove(ingredientList.getItem(position));
    }

    //Getter and Setter
    public IngredientAdapter getIngredientsAdapter() {
        return ingredientList;
    }
}
