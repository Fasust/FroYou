package frozenyogurtbuilder.app.classes.Firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Locale;

import frozenyogurtbuilder.app.classes.Ingredient;


public class FSIngridientsListLoader extends FSLoader<ArrayList<Ingredient>> {

    public FSIngridientsListLoader(CollectionReference collectionReference, TaskListner listner) {
        super(collectionReference, listner);
        result = new ArrayList<>();
    }

    @Override
    void dowithEachDocument(QueryDocumentSnapshot document) {

        if( Locale.getDefault().getDisplayLanguage().equals( Locale.ENGLISH.getDisplayLanguage())){
            Ingredient ingredient =  new Ingredient(
                    (String) document.get("en_name"),
                    ((String) document.get("type")).charAt(0));

            result.add(ingredient);

        }else {
            Ingredient ingredient =  new Ingredient(
                    (String) document.get("name"),
                    ((String) document.get("type")).charAt(0));

            result.add(ingredient);
        }


    }
}