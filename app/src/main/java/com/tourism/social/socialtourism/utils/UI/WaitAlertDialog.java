package com.tourism.social.socialtourism.utils.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.tourism.social.socialtourism.R;

/**
 * Created by emanuelteixeira on 05/03/15.
 */
public class WaitAlertDialog {
    private final Context context;
    private final Activity activity;

    public WaitAlertDialog(Context context) {
        this.context = context;
        this.activity = null;
    }

    public WaitAlertDialog(Activity activity) {
        this.activity = activity;
        this.context = null;
    }

    public AlertDialog getAlertDialog(){
        AlertDialog.Builder dialogBuilder;
        if (this.context != null)
            dialogBuilder = new AlertDialog.Builder(this.context);
        else
            dialogBuilder = new AlertDialog.Builder(this.activity);
        LayoutInflater inflater = null;
        if (this.activity == null) {
            inflater = ((Activity) context).getLayoutInflater();
        }
        else {
            inflater = this.activity.getLayoutInflater();
        }
        View dialogView = inflater.inflate(R.layout.wait_dialog_layout, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        return alertDialog;
    }

}
