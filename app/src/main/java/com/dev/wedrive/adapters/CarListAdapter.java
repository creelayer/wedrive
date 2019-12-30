package com.dev.wedrive.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiCar;

import java.util.ArrayList;

public class CarListAdapter extends ListAdapter {

    private Drawable activeRadioShape;

    private final OnItemClickListener listener;

    public CarListAdapter(Context context, ArrayList<ApiCar> cars, OnItemClickListener listener) {
        super(context, R.layout.adapter_car_list_item, cars);
        activeRadioShape = getContext().getDrawable(R.drawable.btn_radio_shape_checked);
        this.listener = listener;
    }

    @Override
    protected View populate(int position, View convertView) {

        ApiCar car = (ApiCar) getItem(position);

        CardView item = convertView.findViewById(R.id.car_list_item);
        item.setOnClickListener((v) -> listener.onItemClick(car));

        TextView name = convertView.findViewById(R.id.car_list_name);
        name.setText(car.brand + " " + car.model);

        if (car.current) {
            convertView.findViewById(R.id.car_list_status).setBackground(activeRadioShape);
        }

        return convertView;
    }

    public interface OnItemClickListener {
        void onItemClick(ApiCar item);
    }
}
