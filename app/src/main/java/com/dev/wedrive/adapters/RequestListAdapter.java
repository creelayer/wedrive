package com.dev.wedrive.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.wedrive.Constants;
import com.dev.wedrive.R;
import com.dev.wedrive.dialog.ConfirmDialog;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.entity.ApiRequest;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.ApiUser;
import com.dev.wedrive.entity.DriverLocationData;
import com.dev.wedrive.service.UserService;
import com.dev.wedrive.util.DownloadImageTask;
import com.dev.wedrive.helpers.FileHelper;
import com.google.gson.internal.LinkedTreeMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lombok.Setter;

public class RequestListAdapter extends ListAdapter {


    private ApiUser user;

    @Setter
    private OnItemClickListener listener;

    private Button acceptBtn;
    private Button deniedBtn;
    private Button cancelBtn;

    public RequestListAdapter(Context context, ApiUser user, ArrayList<ApiRequest> request) {
        super(context, R.layout.adapter_request_list_item, request);
        this.user = user;
    }

    public RequestListAdapter(Context context, ApiUser user, ArrayList<ApiRequest> request, OnItemClickListener listener) {
        super(context, R.layout.adapter_request_list_item, request);
        this.listener = listener;
        this.user = user;
    }

    @Override
    protected View populate(int position, View convertView) {

        ApiRequest request = (ApiRequest) getItem(position);

        ImageView userImage = convertView.findViewById(R.id.user_image);
        TextView userName = convertView.findViewById(R.id.user_name);
        TextView requestTime = convertView.findViewById(R.id.request_time);
        TextView requestMessage = convertView.findViewById(R.id.request_message);
        TextView requestStatus = convertView.findViewById(R.id.request_status);
        TextView routeName = convertView.findViewById(R.id.route_name);
        TextView locationTime = convertView.findViewById(R.id.location_time);
        acceptBtn = convertView.findViewById(R.id.accept_btn);
        deniedBtn = convertView.findViewById(R.id.denied_btn);
        cancelBtn = convertView.findViewById(R.id.cancel_btn);
        Button messageBtn = convertView.findViewById(R.id.message_btn);

        ApiProfile profile = request.user.profile;
        ApiLocation location = request.location;
        DriverLocationData locationData = new DriverLocationData((LinkedTreeMap<String, String>) location.data);

        if (profile.image != null)
            new DownloadImageTask(userImage).execute(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(profile.image, "sm"));

        userName.setText(profile.name + " " + profile.lastName);
        requestTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(request.createdAt).toString());
        requestMessage.setText(request.message == null ? "Hello! How about new passenger?" : request.message.message);
        requestStatus.setText(request.status);
        routeName.setText(location.route.name);
        locationTime.setText(locationData.hour + ":" + locationData.min);
        acceptBtn.setOnClickListener((v) -> listener.onItemClick(R.id.accept_btn, request));
        deniedBtn.setOnClickListener((v) -> listener.onItemClick(R.id.denied_btn, request));
        cancelBtn.setOnClickListener((v) -> listener.onItemClick(R.id.cancel_btn, request));
        messageBtn.setOnClickListener((v) -> listener.onItemClick(R.id.message_btn, request));

        updateControlsState(request);

        return convertView;
    }

    public void updateControlsState(ApiRequest request) {

        cancelBtn.setVisibility(View.GONE);
        acceptBtn.setVisibility(View.GONE);
        deniedBtn.setVisibility(View.GONE);

        if (request.status.equals(ApiRequest.STATUS_NEW)) {
            if (user.id == request.userId)
                cancelBtn.setVisibility(View.VISIBLE);
            else {
                acceptBtn.setVisibility(View.VISIBLE);
                deniedBtn.setVisibility(View.VISIBLE);
            }
        }

        if (request.status.equals(ApiRequest.STATUS_ACCEPTED)) {
            if (user.id == request.userId)
                cancelBtn.setVisibility(View.VISIBLE);
            else {
                deniedBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int id, ApiRequest item);
    }

}
