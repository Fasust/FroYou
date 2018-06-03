package mocogruppe1.frozenjoghurtbuilder.classes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import mocogruppe1.frozenjoghurtbuilder.OrderProcess;
import mocogruppe1.frozenjoghurtbuilder.R;

public abstract class AlertSelectBox<T> {

    private final Context context;
    private final ArrayAdapter<T> arrayAdapter;
    private AlertDialog.Builder dialoge;
    private T selectedItem;

    public AlertSelectBox(final Context context, String bannerTxt, int icon){
        this.context = context;
        arrayAdapter = new ArrayAdapter(context, android.R.layout.select_dialog_singlechoice);

        dialoge = new AlertDialog.Builder(context);
        if( icon != 0){
            dialoge.setIcon(icon);
        }
        dialoge.setTitle(bannerTxt);

        dialoge.setNegativeButton("abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialoge.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItem = arrayAdapter.getItem(which);
                afterSelect();
            }
        });
    }


    public void show(){
        dialoge.show();
    }
    public void add(T obj){
        arrayAdapter.add(obj);
    }
    public void add(ArrayList<T> objs){
        arrayAdapter.addAll(objs);
    }
    public T getSelectedItem(){
        return selectedItem;
    }
    public abstract void afterSelect();
}
