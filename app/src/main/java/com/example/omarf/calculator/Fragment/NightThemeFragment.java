package com.example.omarf.calculator.Fragment;

import android.util.Log;

import com.example.omarf.calculator.R;


public class NightThemeFragment extends CalculatorParent {

    private static final String TAG = "themeTag";

    @Override
    protected int setId() {
        return R.layout.fragment_night_theme;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG," Night theme stop");
    }
}
