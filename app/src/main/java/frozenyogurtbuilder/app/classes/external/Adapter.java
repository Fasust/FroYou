package frozenyogurtbuilder.app.classes.external;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frozenyogurtbuilder.app.R;
import frozenyogurtbuilder.app.classes.Ingredient;

/**
 * Created by takaiwa.net on 2016/04/08.
 */
public class Adapter extends ArrayAdapter<Ingredient> {

    private final int INVALID_ID = -1;
    public interface Listener {
        void onGrab(int position, RelativeLayout row);
    }

    private final Listener listener;
    private Map<Ingredient, Integer> mIdMap = new HashMap<>();
    private int midMapCounter = 0;

    public Adapter(Context context, ArrayList<Ingredient> list, Listener listener) {
        super(context, 0, list);
        this.listener = listener;
        for (; midMapCounter< list.size(); midMapCounter++){
            mIdMap.put(list.get(midMapCounter),midMapCounter);
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Context context = getContext();
        final Ingredient ingredient = getItem(position);

        if(null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_ingredient, null);
        }

        final RelativeLayout row = view.findViewById(R.id.fragment_ingridient);


        TextView textView = view.findViewById(R.id.txt_ingredient);
        textView.setText(ingredient.getName());


        view.findViewById(R.id.ingridient_move_imageView)
            .setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    listener.onGrab(position, row);
                    return false;
                }
            });

        return view;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        Ingredient item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public void add(Ingredient ingredient){
        super.add(ingredient);
        mIdMap.put(ingredient, midMapCounter);
        midMapCounter++;
    }
}
