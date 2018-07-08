package frozenyogurtbuilder.app.classes.Firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public abstract class FSLoader<T> {
    public interface TaskListner<T> {
        void onComplete(T result);
        void onFail();
    }
    protected T result;
    private FSIngridientsListLoader.TaskListner listner;
    private CollectionReference collectionReference;

    public FSLoader(CollectionReference collectionReference, FSIngridientsListLoader.TaskListner listner){
        this.listner = listner;
        this.collectionReference = collectionReference;
    }

    public void execute() {
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                dowithEachDocument(document);
                            }
                            listner.onComplete(result);
                        } else {
                            listner.onFail();
                        }

                    }
                });
    }
    abstract void dowithEachDocument(QueryDocumentSnapshot document);
}
