package com.dev.wedrive.sheet;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.wedrive.R;

public class Sheet extends Fragment {

    BottomSheetBehavior sheetBehavior;

    protected int height = ViewGroup.LayoutParams.WRAP_CONTENT;

    protected int state = BottomSheetBehavior.STATE_HIDDEN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().findViewById(R.id.btmControls);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);

        sheetBehavior = BottomSheetBehavior.from(view);
        sheetBehavior.setState(state);
        sheetBehavior.setHideable(true);

        return view;
    }

    public void collapse() {
        state = BottomSheetBehavior.STATE_COLLAPSED;
        if (sheetBehavior != null)
            sheetBehavior.setState(state);
    }

    public void expand() {
        state = BottomSheetBehavior.STATE_EXPANDED;
        if (sheetBehavior != null)
            sheetBehavior.setState(state);
    }

    public void hide() {
        state = BottomSheetBehavior.STATE_HIDDEN;
        if (sheetBehavior != null)
            sheetBehavior.setState(state);
    }

}
