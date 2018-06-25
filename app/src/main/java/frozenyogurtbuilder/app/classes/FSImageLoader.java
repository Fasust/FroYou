package frozenyogurtbuilder.app.classes;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FSImageLoader {
    public interface onFishLoading{
        void onComplete(byte[] bytes);
        void onFail();
    }
    private onFishLoading loadingListner;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference URIreference;
    final long ONE_MEGABYTE = 1024 * 1024;

    public FSImageLoader(String uri, onFishLoading loadingListner){
        this.loadingListner = loadingListner;
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        URIreference = storageRef.child(uri);
    }

    public void load(){
        URIreference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                loadingListner.onComplete(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
                loadingListner.onFail();
            }
        });
    }
}
