package com.dev.wedrive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public abstract class ListAdapter<T> extends ArrayAdapter {

    protected int mResource;
    protected LayoutInflater inflater;

    public ListAdapter(Context context, int resource, ArrayList<T> items) {
        super(context, resource, items);
        mResource = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inflater.inflate(mResource, parent, false);

        populate(position, convertView);

        return convertView;
    }

    protected abstract View populate(int position, View convertView);
}
