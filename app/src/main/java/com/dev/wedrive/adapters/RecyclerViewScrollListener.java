package com.dev.wedrive.adapters;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


public class RecyclerViewScrollListener<T> extends RecyclerView.OnScrollListener {

    @Setter
    @Getter
    private Map<String, T> data;

    private boolean isLoading = false;

    private RequestListAdapter adapter;

    private Consumer<RecyclerViewScrollListener> top;
    private Consumer<RecyclerViewScrollListener> bottom;

    public RecyclerViewScrollListener(Consumer<RecyclerViewScrollListener> top, Consumer<RecyclerViewScrollListener> bottom) {
        this.top = top;
        this.bottom = bottom;
        this.data = new HashMap<>();
    }


    public void reset(Map<String, T> items) {
        data = new HashMap<>();
        data.putAll(items);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    public void add(Map<String, T> items) {
        data.putAll(items);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }


    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (!isLoading) {

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            adapter = (RequestListAdapter) recyclerView.getAdapter();

            if (linearLayoutManager != null && adapter != null) {

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == 0) {
                    isLoading = true;
                    top.accept(this);
                }

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == data.size() - 1) {
                    isLoading = true;
                    bottom.accept(this);
                }

            }
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

}
