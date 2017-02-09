package com.example.omarf.calculator.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarf.calculator.Calculation;
import com.example.omarf.calculator.CalculationPreference;
import com.example.omarf.calculator.R;

/**
 * Created by omarf on 1/2/2017.
 */

public abstract class CalculatorParent extends Fragment {
    private static final String TAG = "CalculatorTag";
    private TextView mCalculationTextView;
    private TextView mResultTextView;
    private Calculation mCalculation;
    private ValueListener mValueListener;
    private String mCalculationTextViewString;
    private String mResultTextViewString;

    protected abstract int setId();

    public void setTextViewValue(String calculation, String result) {
        Log.i(TAG, "setTextViewValue called");
        mCalculationTextViewString = calculation;
        mResultTextViewString = result;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView called");
        int layoutId = setId();
        View v = inflater.inflate(layoutId, container, false);
        mCalculationTextView = (TextView) v.findViewById(R.id.calculation_text_view);
        mResultTextView = (TextView) v.findViewById(R.id.result_text_view);
        mCalculation = new Calculation();
        mCalculationTextView.setText(mCalculationTextViewString);
        mResultTextView.setText(mResultTextViewString);


        mCalculationTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mValueListener.getCalculationTextViewValue(charSequence.toString());
                Log.i(TAG,i2+"");

                if(i2<14){
                    mCalculationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,45);
                }
                else if (i2>=14 && i2<=17){
                    mCalculationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
                }
                else {
                    mCalculationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mResultTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mValueListener.getResultTextViewValue(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        int i2=mCalculationTextView.getText().length()-1;
        if(i2<14){
            mCalculationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,45);
        }
        else if (i2>=14 && i2<=17){
            mCalculationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
        }
        else {
            mCalculationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }





        /*************************************Clear Button*************************************/

        Button clrBtn = (Button) v.findViewById(R.id.c_btn);
        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResultTextView.setText("");
                mCalculationTextView.setText("");
            }
        });


        /*************************************Equal Button*************************************/

        Button equalBtn = (Button) v.findViewById(R.id.equal_btn);
        equalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mResultTextView.getText().toString().isEmpty())
                    mCalculationTextView.setText(mResultTextView.getText());
                mResultTextView.setText("");
            }
        });

        /*************************************Dot Button*************************************/
        v.findViewById(R.id.dot_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calculationString = mCalculationTextView.getText().toString();
                if (calculationString.isEmpty())
                    return;
                int lastCharIndex = calculationString.length() - 1;

                char lastChar = calculationString.charAt(lastCharIndex);
                if (lastChar == '+' || lastChar == '-' || lastChar == 'x' || lastChar == '÷' || lastChar == '.')
                    return;

                for (int i = lastCharIndex - 1; i > 0; i--) {
                    char tempChar = calculationString.charAt(i);

                    if (tempChar == '+' || tempChar == '-' || tempChar == 'x' || tempChar == '÷') {
                        break;
                    } else if (tempChar == '.') {
                        return;

                    }
                }
                calculationString += ".";
                mCalculationTextView.setText(calculationString);

            }
        });

        /************************************************m button*************************************************************/

        v.findViewById(R.id.m_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String calculationString = mCalculationTextView.getText().toString();
                if (calculationString.isEmpty()) {
                    mCalculationTextView.setText(CalculationPreference.getMvalues(getActivity()));
                    return;
                }
                int lastCharIndex = calculationString.length() - 1;
                char lastChar = calculationString.charAt(lastCharIndex);

                if (lastChar == '.')
                    return;

                if (lastChar == '+' || lastChar == '-' || lastChar == 'x' || lastChar == '÷') {
                    String m_value = CalculationPreference.getMvalues(getActivity());
                    Log.i(TAG, "value retrieve " + m_value);
                    if (m_value != null) {
                        calculationString += m_value;
                        mCalculationTextView.setText(calculationString);
                        updateResultTextView(calculationString);
                    }
                } else {
                    String[] values = calculationString.split("[-+x÷]");
                    Log.i(TAG, "value 0 " + values[0]);
                    int length = values.length - 1;
                    String storeValue = values[length];
                    Log.i(TAG, "value store " + storeValue);
                    CalculationPreference.setMvalues(getActivity(), storeValue);
                    Toast.makeText(getActivity(), storeValue + " stored", Toast.LENGTH_SHORT).show();
                }


            }
        });

        /*************************************back button*************************************/
        v.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calculationString = mCalculationTextView.getText().toString();
                if (calculationString.isEmpty())
                    return;

                calculationString = calculationString.substring(0, calculationString.length() - 1);
                mCalculationTextView.setText(calculationString);

                if (calculationString.isEmpty())
                    return;

                int lastCharIndex = calculationString.length() - 1;
                char lastChar = calculationString.charAt(lastCharIndex);
                if (!(lastChar == '+' || lastChar == '-' || lastChar == 'x' || lastChar == '÷' || lastChar == '.'))
                    updateResultTextView(calculationString);
                else {
                    calculationString = calculationString.substring(0, lastCharIndex );
                    updateResultTextView(calculationString);
                }
            }
        });

        v.findViewById(R.id.zero_btn).setOnClickListener(new NumPadClickListener());
        v.findViewById(R.id.one_btn).setOnClickListener(new NumPadClickListener());
        v.findViewById(R.id.two_btn).setOnClickListener(new NumPadClickListener());
        v.findViewById(R.id.three_btn).setOnClickListener(new NumPadClickListener());
        v.findViewById(R.id.four_btn).setOnClickListener(new NumPadClickListener());
        v.findViewById(R.id.five_btn).setOnClickListener(new NumPadClickListener());
        v.findViewById(R.id.six_btn).setOnClickListener(new NumPadClickListener());
        v.findViewById(R.id.seven_btn).setOnClickListener(new NumPadClickListener());
        v.findViewById(R.id.eight_btn).setOnClickListener(new NumPadClickListener());
        v.findViewById(R.id.nine_btn).setOnClickListener(new NumPadClickListener());


        v.findViewById(R.id.plus_btn).setOnClickListener(new OperatorClickListener());
        v.findViewById(R.id.minus_btn).setOnClickListener(new OperatorClickListener());
        v.findViewById(R.id.multiple_btn).setOnClickListener(new OperatorClickListener());
        v.findViewById(R.id.divide_btn).setOnClickListener(new OperatorClickListener());


        return v;
    }

    /*************************************
     * Num Pad 0-9
     *************************************/

    private class NumPadClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Button numButton = (Button) view;
            String calculationString = mCalculationTextView.getText().toString();
            String numString = numButton.getText().toString();
            calculationString += numString;



            mCalculationTextView.setText(calculationString);
            updateResultTextView(calculationString);
        }
    }

    /*************************************
     * Operator Button +-x÷
     *************************************/

    private class OperatorClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String calculationString = mCalculationTextView.getText().toString();
            if (calculationString.isEmpty())
                return;
            int lastCharIndex = calculationString.length() - 1;
            char lastChar = calculationString.charAt(lastCharIndex);

            if (lastChar == '+' || lastChar == '-' || lastChar == 'x' || lastChar == '÷' || lastChar == '.')
                return;


            switch (view.getId()) {
                case R.id.plus_btn:
                case R.id.minus_btn:
                case R.id.divide_btn:
                    Button numButton = (Button) view;
                    String numString = numButton.getText().toString();
                    calculationString += numString;
                    break;
                case R.id.multiple_btn:
                    calculationString += "x";
                    break;
            }

            mCalculationTextView.setText(calculationString);


        }
    }

    private void updateResultTextView(String calculationString) {
        mResultTextView.setText(mCalculation.expressionCalculation(calculationString));
    }

    public interface ValueListener {
        void getCalculationTextViewValue(String calculation);

        void getResultTextViewValue(String result);

    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mValueListener == null)
            mValueListener = (ValueListener) context;
    }
}
