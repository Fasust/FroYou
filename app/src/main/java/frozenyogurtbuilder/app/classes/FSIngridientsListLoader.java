package frozenyogurtbuilder.app.classes;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FSIngridientsListLoader extends FSLoader<ArrayList<Ingredient>> {

    public FSIngridientsListLoader(CollectionReference collectionReference, TaskListner listner) {
        super(collectionReference, listner);
        result = new ArrayList<>();
    }

    @Override
    void dowithEachDocument(QueryDocumentSnapshot document) {
        Ingredient ingredient =  new Ingredient(
                (String) document.get("name"),
                ((String) document.get("type")).charAt(0));

        result.add(ingredient);
    }
}