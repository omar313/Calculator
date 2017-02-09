package com.example.omarf.calculator;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by omarf on 1/1/2017.
 */

public class CalculationPreference {
    private static final String M_TAG ="tag_m" ;
    private static final String I_TAG = "tag_i";

    public static String getMvalues(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(M_TAG,null);
    }

    public static void setMvalues(Context context, String value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(M_TAG,value )
                .apply();

    }

    public static int getIndexValue(Context context){
       return   PreferenceManager.getDefaultSharedPreferences(context).getInt(I_TAG,0);
    }

    public static void setFragmentIndex(Context context, int index){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(I_TAG,index)
                .apply();

    }
}
