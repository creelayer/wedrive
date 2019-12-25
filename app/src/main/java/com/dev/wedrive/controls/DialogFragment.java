package com.dev.wedrive.controls;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.wedrive.R;

public class DialogFragment extends Fragment {

    BottomSheetBehavior sheetBehavior;

    protected int height = ViewGroup.LayoutParams.WRAP_CONTENT;

    protected int state = BottomSheetBehavior.STATE_COLLAPSED;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().findViewById(R.id.btmControls);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);

        sheetBehavior = BottomSheetBehavior.from(view);
        sheetBehavior.setState(state);

        return view;
    }

    public void collapse(){
        state = BottomSheetBehavior.STATE_COLLAPSED;
        sheetBehavior.setState(state);
    }

}
