package com.dev.wedrive.adapters;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.RouteService;

import java.util.ArrayList;

public class RoutesListAdapter extends ListAdapter {

    private MapActivity activity;
    private RouteService routeService;

    public RoutesListAdapter(MapActivity context, int resource, ArrayList<ApiRoute> routes) {
        super(context, resource, routes);
        activity = context;
        inflater = LayoutInflater.from(context);
        routeService = new RouteService();
    }

    @Override
    protected View populate(int position, View convertView) {

        ApiRoute route = (ApiRoute) getItem(position);

        CardView item = convertView.findViewById(R.id.route_list_item);
        item.setOnClickListener(new OnClickListener(route));

        TextView name = convertView.findViewById(R.id.route_list_name);
        name.setText(route.name);

        if (route.status != null) {
            Button status = convertView.findViewById(R.id.route_list_status);
            status.setBackground(mContext.getDrawable(R.drawable.btn_radio_shape_checked));
        }

        return convertView;
    }


    public class OnClickListener implements View.OnClickListener {

        ApiRoute mRoute;

        public OnClickListener(ApiRoute route) {
            mRoute = route;
        }

        @Override
        public void onClick(View v) {

            routeService.setStatus(mRoute, ApiRoute.STATUS_CURRENT, (route) -> {
                activity.getBottomInform().setState(BottomSheetBehavior.STATE_COLLAPSED);
                return null;
            });

        }
    }
}
