package mocogruppe1.frozenjoghurtbuilder.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import mocogruppe1.frozenjoghurtbuilder.R;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {
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

        //Get Eleemnt of View and Populate
        TextView txt_name = convertView.findViewById(R.id.txt_ingredient);
        txt_name.setText(ingredient.getName());

        ImageButton btn_edit =  convertView.findViewById(R.id.imageBtn_ingredient_edit);
        btn_edit.setTag(position);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                Ingredient ingredient = getItem(position);

            }
        });


        return convertView;
    }

}
