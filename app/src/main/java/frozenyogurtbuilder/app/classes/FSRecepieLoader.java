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

        Recipe recipe = new Recipe(
                (String)document.get("name"),
                (String)document.get( "description"),
                (String)document.get("ingredients")
        );

        result.add(recipe);
    }
}
