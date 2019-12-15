package com.dev.wedrive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiRoute;

import java.util.ArrayList;

public class RoutesListAdapter extends ListAdapter {

    public RoutesListAdapter(Context context, int resource, ArrayList<ApiRoute> routes) {
        super(context, resource, routes);
        mResource = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected View populate(int position, View convertView) {

        ApiRoute route = (ApiRoute) getItem(position);

        TextView name = convertView.findViewById(R.id.route_list_name);
        name.setText(route.name);

        if(route.status != null){
            Button status = convertView.findViewById(R.id.route_list_status);
            status.setBackground(mContext.getDrawable(R.drawable.btn_radio_shape_checked));
        }

        return convertView;
    }
}
