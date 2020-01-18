package com.dev.wedrive.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder> {


    private LayoutInflater inflater;

    private ApiUser user;
    private ArrayList<ApiRequest> requests;

    @Setter
    private OnItemClickListener listener;


    // data is passed into the constructor
    public RequestListAdapter(Context context, ApiUser user, ArrayList<ApiRequest> requests) {
        this.inflater = LayoutInflater.from(context);
        this.user = user;
        this.requests = requests;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return requests.size();
    }

    // convenience method for getting data at click position
    public ApiRequest getItem(int id) {
        return requests.get(id);
    }

    public void updateItem(int id, ApiRequest request) {
        requests.set(id, request);
        notifyItemChanged(id);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_request_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiRequest request = requests.get(position);
        ApiProfile profile = request.user.profile;
        ApiLocation location = request.location;


        if (profile.image != null)
            new DownloadImageTask(holder.userImage).execute(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(profile.image, "sm"));

        holder.userName.setText(profile.name + " " + profile.lastName);
        holder.requestTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(request.createdAt));
        holder.requestMessage.setText(request.message == null ? "Hello! How about new passenger?" : request.message.message);
        holder.requestStatus.setText(request.status);
        holder.locationTime.setText(location.hour + ":" + location.min);


        if (location.route != null) {
            holder.routeName.setText(location.route.name);
            holder.routeInfo.setVisibility(View.VISIBLE);
        } else
            holder.routeInfo.setVisibility(View.GONE);

        createRouteLine(holder, location.route);

        updateControlsState(holder, request);
    }

    public void createRouteLine(ViewHolder holder, ApiRoute route) {
        if (route != null) {
            holder.routeName.setText(route.name);
            holder.routeName.setVisibility(View.VISIBLE);
        } else
            holder.routeName.setVisibility(View.GONE);
    }

    public void updateControlsState(ViewHolder holder, ApiRequest request) {

        holder.cancelBtn.setVisibility(View.GONE);
        holder.acceptBtn.setVisibility(View.GONE);
        holder.deniedBtn.setVisibility(View.GONE);

        if (request.status.equals(ApiRequest.STATUS_NEW)) {
            if (user.id == request.userId)
                holder.cancelBtn.setVisibility(View.VISIBLE);
            else {
                holder.acceptBtn.setVisibility(View.VISIBLE);
                holder.deniedBtn.setVisibility(View.VISIBLE);
            }
        }

        if (request.status.equals(ApiRequest.STATUS_ACCEPTED)) {
            if (user.id == request.userId)
                holder.cancelBtn.setVisibility(View.VISIBLE);
            else {
                holder.deniedBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView requestTime;
        TextView requestMessage;
        TextView requestStatus;
        LinearLayout routeInfo;
        TextView routeName;
        TextView locationTime;
        Button acceptBtn;
        Button deniedBtn;
        Button cancelBtn;
        Button messageBtn;

        ViewHolder(View view) {
            super(view);
            userImage = view.findViewById(R.id.user_image);
            userName = view.findViewById(R.id.user_name);
            requestTime = view.findViewById(R.id.request_time);
            requestMessage = view.findViewById(R.id.request_message);
            requestStatus = view.findViewById(R.id.request_status);
            routeInfo = view.findViewById(R.id.route_info);
            routeName = view.findViewById(R.id.route_name);
            locationTime = view.findViewById(R.id.location_time);
            acceptBtn = view.findViewById(R.id.accept_btn);
            deniedBtn = view.findViewById(R.id.denied_btn);
            cancelBtn = view.findViewById(R.id.cancel_btn);
            messageBtn = view.findViewById(R.id.message_btn);

            acceptBtn.setOnClickListener((v) -> listener.onItemClick(v, getAdapterPosition()));
            deniedBtn.setOnClickListener((v) -> listener.onItemClick(v, getAdapterPosition()));
            cancelBtn.setOnClickListener((v) -> listener.onItemClick(v, getAdapterPosition()));
            messageBtn.setOnClickListener((v) -> listener.onItemClick(v, getAdapterPosition()));

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
