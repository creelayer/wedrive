package com.dev.wedrive.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.service.RouteService;

import lombok.Setter;

public class InformDialog extends DialogAbstract {

    @Setter
    private String text;

    public InformDialog(MapActivity activity, String text) {
        super(activity);
        this.text = text;
    }

    @Override
    public void show() {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.inform_dialog, null);


        TextView textView = (TextView) view.findViewById(R.id.inform_message);
        textView.setText(text);

        Button okBtn = view.findViewById(R.id.dialog_ok_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//TODO: hide
            }
        });


        informLayout.removeAllViews();
        informLayout.addView(view);
        informLayout.setMinimumHeight(100);

        super.show();
    }
}
