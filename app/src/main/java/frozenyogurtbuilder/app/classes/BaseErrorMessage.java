package frozenyogurtbuilder.app.classes;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class BaseErrorMessage extends AlertDialog.Builder {
    public BaseErrorMessage(String message, String titel, Context context){
        super(context);
        this.setTitle(titel);
        this.setMessage(message);
        this.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        this.setIcon(android.R.drawable.ic_dialog_alert);
    }

}
