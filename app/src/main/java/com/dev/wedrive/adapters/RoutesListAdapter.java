package com.dev.wedrive.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

        convertView.findViewById(R.id.smContentView).setOnClickListener((v) -> listener.onItemClick(R.id.smContentView, route));
        convertView.findViewById(R.id.list_item_edit).setOnClickListener((v) -> listener.onItemClick(R.id.list_item_edit, route));
        convertView.findViewById(R.id.list_item_delete).setOnClickListener((v) -> listener.onItemClick(R.id.list_item_delete, route));

        TextView name = convertView.findViewById(R.id.list_name);
        name.setText(route.name);

        if (route.status != null) {
            convertView.findViewById(R.id.list_status).setBackground(activeRadioShape);
        }

        return convertView;
    }

    public interface OnItemClickListener {
        void onItemClick(int id, ApiRoute item);
    }
}
