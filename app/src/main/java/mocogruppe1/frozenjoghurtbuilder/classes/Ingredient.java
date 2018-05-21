package mocogruppe1.frozenjoghurtbuilder.classes;

public class Ingredient {
    String name;
    char type;

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
