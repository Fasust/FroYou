package mocogruppe1.frozenjoghurtbuilder.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        // Lookup view for data population
        TextView txt_name = (TextView) convertView.findViewById(R.id.txt_ingredient);
        // Populate the data into the template view using the data object
        txt_name.setText(ingredient.getName());
        // Return the completed view to render on screen
        return convertView;
    }

}
