package mocogruppe1.frozenjoghurtbuilder.classes;

public class Ingredient {
    String name;
    char type;

    //Types
    public final static char INGREDIENT_MAIN = 'm';
    public final static char INGREDIENT_TOPPING = 't';
    public final static char INGREDIENT_SAUCE = 's';

    public Ingredient(String name, char type) {
        this.name = name;
        this.type = type;
    }


    /*
    Getters And Setters Below--------------------------
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
