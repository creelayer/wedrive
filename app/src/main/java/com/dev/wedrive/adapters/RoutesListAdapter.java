package com.dev.wedrive.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiRoute;

import java.util.ArrayList;

public class RoutesListAdapter extends ListAdapter {

    private Drawable activeRadioShape;

    private final OnItemClickListener listener;

    public RoutesListAdapter(Context context, ArrayList<ApiRoute> routes, OnItemClickListener listener) {
        super(context, R.layout.adapter_route_list_item, routes);
        activeRadioShape = getContext().getDrawable(R.drawable.btn_radio_shape_checked);
        this.listener = listener;
    }

    @Override
    protected View populate(int position, View convertView) {

        ApiRoute route = (ApiRoute) getItem(position);

        CardView item = convertView.findViewById(R.id.route_list_item);
        item.setOnClickListener((v) -> listener.onItemClick(route));

        TextView name = convertView.findViewById(R.id.route_list_name);
        name.setText(route.name);

        if (route.status != null) {
            convertView.findViewById(R.id.route_list_status).setBackground(activeRadioShape);
        }

        return convertView;
    }

    public interface OnItemClickListener {
        void onItemClick(ApiRoute item);
    }
}
