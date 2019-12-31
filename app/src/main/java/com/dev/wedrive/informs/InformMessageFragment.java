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
public class InformMessageFragment extends InformAbstract implements View.OnClickListener {


    @Setter
    private String icon;

    @Setter
    private String header;

    @Setter
    private String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inform_message, container, false);
        animationIn = AnimationUtils.loadAnimation(getContext(), R.anim.inform_slide_in);
        animationOut = AnimationUtils.loadAnimation(getContext(), R.anim.inform_slide_out);

        CardView cardView = view.findViewById(R.id.inform_message_card);
        cardView.setOnClickListener(this);

        TextView headerView = view.findViewById(R.id.inform_message_header);
        headerView.setText(header);

        TextView textView = view.findViewById(R.id.inform_message_text);
        textView.setText(text);

        view.startAnimation(animationIn);
        return view;
    }

    @Override
    public void onClick(View v) {
        getView().startAnimation(animationOut);
        getView().setVisibility(View.INVISIBLE);
    }

}
