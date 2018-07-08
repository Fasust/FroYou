package frozenyogurtbuilder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import frozenyogurtbuilder.app.classes.RecepieViewHolder;
import frozenyogurtbuilder.app.classes.Recipe;

public class RecipeGallery extends AppCompatActivity {

    //View
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipegallery);

        buildFirestoreListView();

    }

    private void buildFirestoreListView(){
        Query query = FirebaseFirestore.getInstance()
                .collection("recipes")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(50);

        FirestoreRecyclerOptions<Recipe> options = new FirestoreRecyclerOptions.Builder<Recipe>()
                .setQuery(query, Recipe.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Recipe, RecepieViewHolder>(options) {

            @NonNull
            @Override
            public RecepieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_recipegallery, parent, false);

                return new RecepieViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RecepieViewHolder holder, int position, @NonNull Recipe model) {
                holder.setData(model,RecipeGallery.this);
            }
        };
        RecyclerView listview = findViewById(R.id.gallery_listView);
        listview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listview.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable( R.drawable.devider ));
        listview.addItemDecoration(dividerItemDecoration);

        listview.setLayoutManager(layoutManager);
        listview.setHasFixedSize(true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RecipeGallery.this, MainActivity.class));
        finish();

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
