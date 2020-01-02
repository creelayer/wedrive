package com.dev.wedrive.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.wedrive.Constants;
import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.DriverLocationData;
import com.dev.wedrive.helpers.DownloadImageTask;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.service.RequestService;
import com.google.gson.internal.LinkedTreeMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RequestListAdapter extends ListAdapter {


    private final OnItemClickListener listener;

    public Button deniedBtn;
    public Button acceptBtn;

    public RequestListAdapter(Context context, ArrayList<ApiRequest> request, OnItemClickListener listener) {
        super(context, R.layout.adapter_request_list_item, request);
        this.listener = listener;
    }

    @Override
    protected View populate(int position, View convertView) {

        ApiRequest request = (ApiRequest) getItem(position);

        ImageView userImage = convertView.findViewById(R.id.user_image);
        TextView userName = convertView.findViewById(R.id.user_name);
        TextView requestTime = convertView.findViewById(R.id.request_time);
        TextView requestMessage = convertView.findViewById(R.id.request_message);
        TextView routeName = convertView.findViewById(R.id.route_name);
        TextView locationTime = convertView.findViewById(R.id.location_time);
        deniedBtn = convertView.findViewById(R.id.denied_btn);
        acceptBtn = convertView.findViewById(R.id.accept_btn);


        ApiProfile profile = request.user.profile;
        ApiLocation location = request.location;
        DriverLocationData locationData = new DriverLocationData((LinkedTreeMap<String, String>) location.data);

        if (profile.image != null)
            new DownloadImageTask(userImage).execute(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(profile.image, "sm"));

        userName.setText(profile.name + " " + profile.lastName);
        requestTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(request.createdAt).toString());
        requestMessage.setText(request.message == null ? "Hello! How about new passenger?" : request.message.message);
        routeName.setText(location.route.name);
        locationTime.setText(locationData.hour + ":" + locationData.min);
        deniedBtn.setOnClickListener((v) -> listener.onItemClick(R.id.denied_btn, request, this));
        acceptBtn.setOnClickListener((v) -> listener.onItemClick(R.id.accept_btn, request, this));

        return convertView;
    }

    public interface OnItemClickListener {
        void onItemClick(int id, ApiRequest item, RequestListAdapter requestListAdapter);
    }

}
