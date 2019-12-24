package com.dev.wedrive.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import java.util.function.Function;

public class RoutesListAdapter extends ListAdapter  {


    private RouteService routeService;
    private Drawable activeRadioShape;

    public RoutesListAdapter(Context context, ArrayList<ApiRoute> routes) {
        super(context, R.layout.adapter_route_list_item, routes);
        routeService = new RouteService();
        activeRadioShape = getContext().getDrawable(R.drawable.btn_radio_shape_checked);
    }

    @Override
    protected View populate(int position, View convertView) {

        ApiRoute route = (ApiRoute) getItem(position);

        CardView item = convertView.findViewById(R.id.route_list_item);
       // item.setOnClickListener();

        TextView name = convertView.findViewById(R.id.route_list_name);
        name.setText(route.name);

        if (route.status != null) {
            convertView.findViewById(R.id.route_list_status).setBackground(activeRadioShape);
        }

        return convertView;
    }



    //    public class OnClickListener implements View.OnClickListener {
//
//        ApiRoute mRoute;
//
//        public OnClickListener(ApiRoute route) {
//            mRoute = route;
//        }
//
//        @Override
//        public void onClick(View v) {
//
//            routeService.setStatus(mRoute, ApiRoute.STATUS_CURRENT, (route) -> {
//                //TODO: collapse bottom context
//                return null;
//            });
//
//        }
//    }
}
