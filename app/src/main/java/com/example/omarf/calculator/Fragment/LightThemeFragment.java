package com.example.omarf.calculator.Fragment;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.omarf.calculator.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LightThemeFragment extends CalculatorParent {

    private static final String TAG = "themeTag";
    public LightThemeFragment() {
        // Required empty public constructor
    }


    @Override
    protected int setId() {
        return R.layout.fragment_light_theme;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG," Light theme stop");
    }

}
