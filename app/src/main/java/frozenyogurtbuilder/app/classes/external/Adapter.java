package frozenyogurtbuilder.app.classes.external;

import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frozenyogurtbuilder.app.R;
import frozenyogurtbuilder.app.classes.Ingredient;

/**
 * Created by takaiwa.net on 2016/04/08.
 */
public class Adapter extends ArrayAdapter<Ingredient> {

    final int INVALID_ID = -1;
    public interface Listener {
        void onGrab(int position, ConstraintLayout row);
    }

    final Listener listener;
    final Map<Ingredient, Integer> mIdMap = new HashMap<>();

    public Adapter(Context context, List<Ingredient> list, Listener listener) {
        super(context, 0, list);
        this.listener = listener;
        for (int i = 0; i < list.size(); ++i) {
            mIdMap.put(list.get(i), i);
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Context context = getContext();
        final Ingredient ingredient = getItem(position);

        if(null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_ingredient, null);
        }

        final ConstraintLayout row = view.findViewById(R.id.fragment_ingridient);


        TextView textView = (TextView)view.findViewById(R.id.txt_ingredient);
        textView.setText(ingredient.getName());


        view.findViewById(R.id.ingridient_imageView)
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
}
