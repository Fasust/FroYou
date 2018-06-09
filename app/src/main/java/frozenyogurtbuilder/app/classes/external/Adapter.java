package frozenyogurtbuilder.app.classes.external;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frozenyogurtbuilder.app.R;
import frozenyogurtbuilder.app.classes.Ingredient;
import frozenyogurtbuilder.app.classes.Order;

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

    private Order order;

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

        //Set Text--------------------------------------------------------------------------
        TextView textView = view.findViewById(R.id.txt_ingredient);
        textView.setText(ingredient.getName());

        //Set Grab--------------------------------------------------------------------------
        view.findViewById(R.id.ingridient_move_imageView)
            .setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    listener.onGrab(position, row);
                    return false;
                }
            });

        //Set Icon--------------------------------------------------------------------------
        ImageView typeIcon = view.findViewById(R.id.ingridient_imageView);
        switch (ingredient.getType()){
            case Ingredient.INGREDIENT_MAIN:
                typeIcon.setImageResource(R.drawable.cupcake);
                break;
            case Ingredient.INGREDIENT_SAUCE:
                typeIcon.setImageResource(R.drawable.ketchup);
                break;
            case Ingredient.INGREDIENT_TOPPING:
                typeIcon.setImageResource(R.drawable.nuts);
                break;
        }
        //Set Change Type--------------------------------------------------------------------
        typeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Set Edit---------------------------------------------------------------------------
        ImageButton editBtn = view.findViewById(R.id.imageBtn_ingredient_edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setThroghSelectBox(ingredient.getType(),position);
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

    public void initOrder(Order order){
        this.order = order;
    }

    //add remove put Overrides-------------------------------------------------------------------------
    @Override
    public void add(Ingredient ingredient){
        super.add(ingredient);

        mIdMap.put(ingredient, midMapCounter);
        midMapCounter++;
    }
    @Override
    public void remove(Ingredient ingredient){
        mIdMap.remove(ingredient);

        super.remove(ingredient);
    }
    @Override
    public void insert(Ingredient ingredient, int index){
        if (index > 0 || index < mIdMap.size()) {
            mIdMap.remove(super.getItem(index));
        }

        mIdMap.put(ingredient, midMapCounter);
        midMapCounter++;

        Ingredient toBeRemoved = getItem(index);
        remove(toBeRemoved);

        if(index < getCount()) super.insert(ingredient,index);
        else super.add(ingredient);
    }
}
