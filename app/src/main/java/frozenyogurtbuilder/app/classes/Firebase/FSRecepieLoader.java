package frozenyogurtbuilder.app.classes.Firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import frozenyogurtbuilder.app.classes.Firebase.FSLoader;
import frozenyogurtbuilder.app.classes.Recipe;

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
        try {
            String image = (String)document.get("image");
            recipe.setImagePath(image);

        }catch (Exception e){

        }

        result.add(recipe);
    }
}
