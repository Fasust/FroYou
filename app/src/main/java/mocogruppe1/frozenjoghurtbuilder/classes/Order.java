package mocogruppe1.frozenjoghurtbuilder.classes;

import java.util.ArrayList;

import mocogruppe1.frozenjoghurtbuilder.Exceptions.OrderIsFullException;

public class Order {
    private final int ORDER_SIZE;
    private int mainIngridientCount = 0;
    private ArrayList<Ingredient> ingredientList;

    public Order(int size) {
        this.ORDER_SIZE = size;
        ingredientList = new ArrayList<>();
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
}
