package frozenyogurtbuilder.app.classes;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirestoreLoader extends AsyncTask<CollectionReference,Integer, ArrayList<Ingredient>> {
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    public interface TaskListner {
        void onComplete(ArrayList<Ingredient> arrayList);
    }
    private FirestoreLoader.TaskListner listner;

    public FirestoreLoader(FirestoreLoader.TaskListner listner){
        super();
        this.listner = listner;
    }

    @Override
    protected ArrayList<Ingredient> doInBackground(CollectionReference... collectionReferences) {

        collectionReferences[0].get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Ingredient ingredient =  new Ingredient(
                                        (String) document.get("name"),
                                        ((String) document.get("type")).charAt(0));

                                ingredients.add(ingredient);

                                Log.d("Firebase: Loaded", ingredient.toString());

                            }

                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }
                        listner.onComplete(ingredients);
                    }
                });
        return ingredients;
    }
}
