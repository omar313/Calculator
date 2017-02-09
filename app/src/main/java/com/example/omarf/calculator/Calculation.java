package com.example.omarf.calculator;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by omarf on 12/30/2016.
 */

public class Calculation {


    private static final String TAG = "polishCalculation";

    public String expressionCalculation(String expression) {
        String[] stringNumbers = convertExpressionAsStringArray(expression);
        ArrayList<Character> operators = filterOperator(expression);
        Stack<Character> operatorStack = new Stack<>();

        ArrayList<String> polishExpression;
        polishExpression = generatePolishExpression(stringNumbers, operators, operatorStack);

        if (polishExpression != null) {
            String tempString = " ";
            for (String temp : polishExpression
                    ) {
                tempString = tempString + " " + temp;
            }
            Log.i(TAG, tempString);


            return calculateResult(polishExpression);
        }
        return null;

    }

    private String calculateResult(ArrayList<String> polishExpression) {
        Stack<BigDecimal> c_stack = new Stack<>();

        c_stack.push(new BigDecimal(polishExpression.get(0)));
        c_stack.push(new BigDecimal(polishExpression.get(1)));
        for (int i = 2; i < polishExpression.size(); i++) {
            if (polishExpression.get(i).equals("+")) {
                BigDecimal firstOperand = c_stack.pop();
                BigDecimal secondOperand = c_stack.pop();
                BigDecimal tempValue = secondOperand.add(firstOperand);

                c_stack.push(tempValue);
            } else if (polishExpression.get(i).equals("-")) {
                BigDecimal firstOperand = c_stack.pop();
                BigDecimal secondOperand = c_stack.pop();
                BigDecimal tempValue = secondOperand.subtract(firstOperand);
                c_stack.push(tempValue);
            } else if (polishExpression.get(i).equals("x")) {
                BigDecimal firstOperand = c_stack.pop();
                BigDecimal secondOperand = c_stack.pop();
                BigDecimal tempValue = secondOperand.multiply(firstOperand);

                c_stack.push(tempValue);
            } else if (polishExpression.get(i).equals("÷")) {
                BigDecimal firstOperand = c_stack.pop();
                BigDecimal secondOperand = c_stack.pop();
                BigDecimal tempValue = secondOperand.divide(firstOperand, 3, BigDecimal.ROUND_HALF_UP);
                c_stack.push(tempValue);
            } else {
                c_stack.push(new BigDecimal(polishExpression.get(i)));
            }

        }

        BigDecimal result = c_stack.pop();

        String tempResult = String.valueOf(result);
        int len = tempResult.length() - 1;
        if (len >= 4 &&
                tempResult.charAt(len) == '0'
                && tempResult.charAt(len - 1) == '0'
                && tempResult.charAt(len - 2) == '0'
                && tempResult.charAt(len - 3) == '.'
                ) {
            return tempResult.replace(".000", "");
        }
        return tempResult;
    }

    private ArrayList<String> generatePolishExpression(String[] stringNumbers, ArrayList<Character> operators, Stack<Character> operatorStack) {

        if (operators.isEmpty())
            return null;
        ArrayList<String> polishExpression = new ArrayList<>();
        HashMap<Character, Integer> precedence = new HashMap<>();
        precedence.put('-', 1);
        precedence.put('+', 1);
        precedence.put('x', 3);
        precedence.put('÷', 4);
        int i = 0;
        while (true) {

            polishExpression.add(stringNumbers[i]);
            if (i < operators.size()) {
                if (operatorStack.isEmpty()) {
                    operatorStack.push(operators.get(i));
                } else if (precedence.get(operatorStack.peek()) < precedence.get(operators.get(i))) {
                    operatorStack.push(operators.get(i));
                } else if (precedence.get(operatorStack.peek()).equals(precedence.get(operators.get(i)))) {
                    polishExpression.add(String.valueOf(operatorStack.pop()));
                    operatorStack.push(operators.get(i));
                } else {
                    while ((precedence.get(operatorStack.peek()) > precedence.get(operators.get(i)))
                            || precedence.get(operatorStack.peek()).equals(precedence.get(operators.get(i)))
                            ) {
                        polishExpression.add(String.valueOf(operatorStack.pop()));
                        if (operatorStack.isEmpty())
                            break;
                    }
                    operatorStack.push(operators.get(i));
                }
            }


            i++;
            if (i >= stringNumbers.length) {
                while (!operatorStack.isEmpty()) {
                    polishExpression.add(String.valueOf(operatorStack.pop()));
                }
                break;
            }
        }

        return polishExpression;


    }

    private ArrayList<Character> filterOperator(String expression) {
        ArrayList<Character> operators = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            switch (expression.charAt(i)) {
                case '+':
                    operators.add('+');
                    break;
                case '-':
                    operators.add('-');
                    break;
                case 'x':
                    operators.add('x');
                    break;
                case '÷':
                    operators.add('÷');
                    break;

            }
        }

        return operators;
    }

    private String[] convertExpressionAsStringArray(String expression) {
        return expression.split("[-+x÷]");
    }
}
