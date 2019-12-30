package com.dev.wedrive.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiCar;
import com.tubb.smrv.SwipeHorizontalMenuLayout;

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

        convertView.findViewById(R.id.smContentView).setOnClickListener((v) -> listener.onItemClick(R.id.smContentView, car));
        convertView.findViewById(R.id.list_item_edit).setOnClickListener((v) -> listener.onItemClick(R.id.list_item_edit, car));
        convertView.findViewById(R.id.list_item_delete).setOnClickListener((v) -> listener.onItemClick(R.id.list_item_delete, car));

        TextView name = convertView.findViewById(R.id.list_name);
        name.setText(car.brand + " " + car.model);

        if (car.current) {
            convertView.findViewById(R.id.list_status).setBackground(activeRadioShape);
        }

        return convertView;
    }

    public interface OnItemClickListener {
        void onItemClick(int id, ApiCar item);
    }
}
