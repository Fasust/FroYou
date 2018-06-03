package mocogruppe1.frozenjoghurtbuilder.classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mocogruppe1.frozenjoghurtbuilder.R;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    private IngredientSelectBox selectBox_main;
    private IngredientSelectBox selectBox_topping;
    private IngredientSelectBox selectBox_souce;

    private Order order;

    public IngredientAdapter(Context context) {
        super(context, 0, new ArrayList<Ingredient>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Ingredient ingredient = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_ingredient, parent, false);
        }

        //Build Text------------------------------------------------------------------------------

        TextView txt_name = convertView.findViewById(R.id.txt_ingredient);
        txt_name.setText(ingredient.getName());

        //Build Icon------------------------------------------------------------------------------

        ImageView icon = convertView.findViewById(R.id.ingridient_imageView);
        switch (ingredient.getType()){
            case Ingredient.INGREDIENT_MAIN:
                icon.setImageResource(R.drawable.cupcake);
                break;
            case Ingredient.INGREDIENT_SAUCE:
                icon.setImageResource(R.drawable.ketchup);
                break;
            case Ingredient.INGREDIENT_TOPPING:
                icon.setImageResource(R.drawable.nuts);
                break;
        }

        //Build Buttons------------------------------------------------------------------------------

        ImageButton btn_edit =  convertView.findViewById(R.id.imageBtn_ingredient_edit);
        btn_edit.setTag(R.string.KEY_POSITION,position);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = (Integer) view.getTag(R.string.KEY_POSITION);
                Ingredient ingredient = getItem(position);
                switch (ingredient.getType()){
                    case Ingredient.INGREDIENT_MAIN:
                            selectBox_main.setInsertPointer(position);
                            selectBox_main.show();
                        break;
                    case Ingredient.INGREDIENT_SAUCE:
                            selectBox_souce.setInsertPointer(position);
                            selectBox_souce.show();
                        break;
                    case Ingredient.INGREDIENT_TOPPING:
                            selectBox_topping.setInsertPointer(position);
                            selectBox_topping.show();
                        break;
                }

            }
        });

        //Animation------------------------------------------------------------------------------

        if(convertView.getTag(R.string.KEY_ANIM) == null) {
            convertView.setTag(R.string.KEY_ANIM,1);

            Animation animation = AnimationUtils
                    .loadAnimation(getContext(), android.R.anim.slide_in_left);
            convertView.startAnimation(animation);
        }
        return convertView;
    }

    public void init(IngredientSelectBox selectBox_main, IngredientSelectBox selectBox_souce, IngredientSelectBox selectBox_topping,Order order){
        this.selectBox_main = selectBox_main;
        this.selectBox_topping = selectBox_topping;
        this.selectBox_souce = selectBox_souce;

        this.order = order;
    }

}
