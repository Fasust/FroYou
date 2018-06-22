package frozenyogurtbuilder.app.classes;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class FSRecepieLoader extends FSLoader<ArrayList<Recipe>> {

    public FSRecepieLoader(CollectionReference collectionReference, TaskListner listner) {
        super(collectionReference, listner);
        result = new ArrayList<>();
    }

    @Override
    void dowithEachDocument(QueryDocumentSnapshot document) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        ArrayList<HashMap> ingredientsHashList = (ArrayList<HashMap>) document.get("ingridients");
        for(HashMap ingridientHash : ingredientsHashList){
            try {
                Ingredient ingredient = new Ingredient(
                        (String) ingridientHash.get("name"),
                        ((String) ingridientHash.get("typ")).charAt(0));

                ingredients.add(ingredient);

            } catch (Exception e) {
                ingredients.add(new Ingredient("ERROR",'s'));
            }
        }


        Recipe recipe = new Recipe(
                (String)document.get("name"),
                (String)document.get( "desription"),
                ingredients
        );

        result.add(recipe);
    }
}
