package com.example.omarf.calculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.omarf.calculator.Fragment.CalculatorParent;
import com.example.omarf.calculator.Fragment.ClassicThemeFragment;
import com.example.omarf.calculator.Fragment.LightThemeFragment;
import com.example.omarf.calculator.Fragment.NightThemeFragment;

public class CalculatorActivity extends AppCompatActivity implements CalculatorParent.ValueListener {
    private static final String TAG = "CalculatorActivityTag";
    FragmentManager mFragmentManager;
    private  String mCalculationTextViewString;
    private String mResultTextViewString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        mFragmentManager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Fragment[] mFragmentArray = new Fragment[]{
                new LightThemeFragment(),
                new NightThemeFragment(),
                new ClassicThemeFragment()
        };


        Fragment fragment = mFragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            int index=CalculationPreference.getIndexValue(this);
            fragment = mFragmentArray[index];

            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.light_theme_menu_id:

                CalculationPreference.setFragmentIndex(CalculatorActivity.this,0);
                LightThemeFragment lightThemeFragment = new LightThemeFragment();
                lightThemeFragment.setTextViewValue(mCalculationTextViewString,mResultTextViewString);
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, lightThemeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();



                return true;

            case R.id.night_theme_menu_id:
                CalculationPreference.setFragmentIndex(CalculatorActivity.this,1);
               NightThemeFragment nightThemeFragment = new NightThemeFragment();
                nightThemeFragment.setTextViewValue(mCalculationTextViewString,mResultTextViewString);
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, nightThemeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

                return true;
            case R.id.classical_theme_menu_id:
                CalculationPreference.setFragmentIndex(CalculatorActivity.this,2);
                ClassicThemeFragment classicThemeFragment = new ClassicThemeFragment();
                classicThemeFragment.setTextViewValue(mCalculationTextViewString,mResultTextViewString);
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, classicThemeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getCalculationTextViewValue(String calculation) {
        mCalculationTextViewString=calculation;

        Log.i(TAG," calculationTextView "+calculation);
    }

    @Override
    public void getResultTextViewValue(String result) {
        mResultTextViewString=result;
        Log.i(TAG,"resultTextView"+result);
    }


}
