package com.dev.wedrive.informs;

import android.os.Bundle;

import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dev.wedrive.R;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class InformMessage extends InformAbstract {


    @Setter
    private String icon;

    @Setter
    private String header;

    @Setter
    private String text;

    @Setter
    protected OnClickListener onClickListener;

    public InformMessage() {
        onClickListener = () -> hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inform_message, container, false);

        CardView cardView = view.findViewById(R.id.inform_message_card);
        cardView.setOnClickListener((v) -> onClickListener.onClick());

        TextView headerView = view.findViewById(R.id.inform_message_header);
        headerView.setText(header);

        TextView textView = view.findViewById(R.id.inform_message_text);
        textView.setText(text);

        return view;
    }

    public interface OnClickListener {
        void onClick();
    }

}
