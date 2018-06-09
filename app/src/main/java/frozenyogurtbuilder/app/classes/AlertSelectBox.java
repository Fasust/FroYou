package frozenyogurtbuilder.app.classes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import frozenyogurtbuilder.app.OrderProcess;
import frozenyogurtbuilder.app.R;

public class AlertSelectBox<T> {

    private final Context context;
    private final ArrayAdapter<T> arrayAdapter;
    private AlertDialog.Builder dialoge;
    public interface AfterSelctListener<T>{
        void afterSelect(T selectedItem);
    }
    private AfterSelctListener afterSelctListener;

    public AlertSelectBox(final Context context, String bannerTxt, int icon, AfterSelctListener listener){
        this.context = context;
        this.afterSelctListener = listener;
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
                T selectedItem = arrayAdapter.getItem(which);
                afterSelctListener.afterSelect(selectedItem);
                dialog.dismiss();
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
    public void afterSelect(T selectedItem){
        afterSelctListener.afterSelect(selectedItem);
    }
}
