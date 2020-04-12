package com.dev.wedrive.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.wedrive.R;
import com.dev.wedrive.entity.ApiRoute;

import java.util.ArrayList;

import lombok.Setter;

public class RoutesListAdapter extends RecyclerView.Adapter<RoutesListAdapter.ViewHolder> {

    private LayoutInflater inflater;

    private Drawable activeRadioShape;

    @Setter
    private OnItemClickListener listener;

    private ArrayList<ApiRoute> routes;

    // data is passed into the constructor
    public RoutesListAdapter(Context context, ArrayList<ApiRoute> routes) {
        this.inflater = LayoutInflater.from(context);
        activeRadioShape = context.getDrawable(R.drawable.btn_radio_shape_checked);
        this.routes = routes;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return routes.size();
    }

    // convenience method for getting data at click position
    public ApiRoute getItem(int id) {
        return routes.get(id);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_route_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ApiRoute route = routes.get(position);

        holder.name.setText(route.name);

        if (route.status != null)
            holder.checkbox.setBackground(activeRadioShape);
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout content;
        TextView name;
        ImageView checkbox;
        ImageButton editBtn;
        ImageButton deleteBtn;

        ViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.smContentView);
            name = view.findViewById(R.id.list_name);
            checkbox = view.findViewById(R.id.list_status);
            editBtn = view.findViewById(R.id.list_item_edit);
            deleteBtn = view.findViewById(R.id.list_item_delete);

            content.setOnClickListener((v) -> listener.onItemClick(v, getAdapterPosition()));
            editBtn.setOnClickListener((v) -> listener.onItemClick(v, getAdapterPosition()));
            deleteBtn.setOnClickListener((v) -> listener.onItemClick(v, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
