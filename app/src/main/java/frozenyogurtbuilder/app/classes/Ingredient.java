package frozenyogurtbuilder.app.classes;

public class Ingredient {
    private String name;
    private char type;

    //Types
    public final static char INGREDIENT_MAIN = 'm';
    public final static char INGREDIENT_TOPPING = 't';
    public final static char INGREDIENT_SAUCE = 's';

    public Ingredient(String name, char type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(){
        return getName();
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
