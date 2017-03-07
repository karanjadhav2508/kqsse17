package com.farecat.farecat.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertDialogHelper {

    public static AlertDialog buildAlertDialog(Context ctx, String title, String button) {
        AlertDialog ad = new AlertDialog.Builder(ctx).create();
        ad.setTitle(title);
        ad.setButton(AlertDialog.BUTTON_NEUTRAL, button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        return ad;
    }
}
