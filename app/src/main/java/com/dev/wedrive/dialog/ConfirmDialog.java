package com.dev.wedrive.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class ConfirmDialog implements DialogInterface {

    private Activity mActivity;

    @Setter
    private String headerText;

    @Setter
    private String messageText;

    @Setter
    private OnClickListener okListener;

    public ConfirmDialog(Activity activity) {
        mActivity = activity;
    }

    public AlertDialog create() {

        AlertDialog dialogBuilder = new AlertDialog.Builder(mActivity).create();

        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm, null);


        TextView header = dialogView.findViewById(R.id.dialog_header);
        TextView message = dialogView.findViewById(R.id.dialog_message);
        header.setText(headerText);
        message.setText(messageText);

        Button ok = dialogView.findViewById(R.id.ok_btn);
        Button cancel = dialogView.findViewById(R.id.cancel_btn);

        ok.setOnClickListener((v) -> okListener.onClick(dialogBuilder));
        cancel.setOnClickListener((v) -> dialogBuilder.cancel());

        dialogBuilder.setView(dialogView);
        return dialogBuilder;
    }


    public interface OnClickListener{

        public void onClick(AlertDialog dialog);
    }
}
