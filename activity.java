package com.example.standarexp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import android.appcompat.app.Activity;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity {

    private EditText display;
    private double firstOperand = 0;
    private double secondOperand = 0;
    private String operator = "";
    private boolean isOperatorPressed = false;
    private double memory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (EditText)findViewById(R.id.editText);

        // Register click listeners for all buttons
        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonPlus, R.id.buttonMinus,
                R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonEquals,
                R.id.buttonC, R.id.buttonCE, R.id.buttonBackspace,
                R.id.buttonPercent, R.id.buttonSqrt, R.id.buttonInvers,
                R.id.buttonPlusMinus, R.id.buttonComa,
                R.id.buttonMC, R.id.buttonMR, R.id.buttonMS, R.id.buttonMplus, R.id.buttonMminus
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }
    }

	@SuppressLint("NewApi")
	private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@SuppressLint("NewApi")
		@Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();

            if (buttonText.matches("[0-9]")) { // If a number is pressed
                if (isOperatorPressed) {
                    display.setText("");
                    isOperatorPressed = false;
                }
                display.append(buttonText);
            } else if (buttonText.equals(".")) { // If decimal point is pressed
                if (!display.getText().toString().contains(".")) {
                    display.append(".");
                }
            } else if (buttonText.equals("C")) { // Clear all
                display.setText("");
                firstOperand = secondOperand = 0;
                operator = "";
            } else if (buttonText.equals("CE")) { // Clear entry
                display.setText("");
            } else if (buttonText.equals("←")) { // Backspace
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (buttonText.equals("±")) { // Negate
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    double value = Double.parseDouble(currentText);
                    display.setText(String.valueOf(-value));
                }
            } else if (buttonText.equals("√")) { // Square root
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    double value = Double.parseDouble(currentText);
                    if (value >= 0) {
                        display.setText(String.valueOf(Math.sqrt(value)));
                    } else {
                        display.setText("Error");
                    }
                }
            } else if (buttonText.equals("1/x")) { // Reciprocal
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    double value = Double.parseDouble(currentText);
                    if (value != 0) {
                        display.setText(String.valueOf(1 / value));
                    } else {
                        display.setText("Error");
                    }
                }
            } else if (buttonText.equals("%")) { // Percent
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    double value = Double.parseDouble(currentText);
                    display.setText(String.valueOf(value / 100));
                }
            } else if (buttonText.equals("=")) { // Calculate result
                secondOperand = Double.parseDouble(display.getText().toString());
                double result = calculateResult(firstOperand, secondOperand, operator);
                display.setText(String.valueOf(result));
                operator = "";
            } else if (buttonText.equals("MC")) { // Memory Clear
                memory = 0;
                Toast.makeText(MainActivity.this, "Memory Cleared", Toast.LENGTH_SHORT).show();
            } else if (buttonText.equals("MR")) { // Memory Recall
                display.setText(String.valueOf(memory));
            } else if (buttonText.equals("MS")) { // Memory Store
                try {
                    memory = Double.parseDouble(display.getText().toString());
                    Toast.makeText(MainActivity.this, "Memory Stored", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            } else if (buttonText.equals("M+")) { // Memory Plus
                try {
                    double currentValue = Double.parseDouble(display.getText().toString());
                    memory += currentValue;
                    Toast.makeText(MainActivity.this, "Memory Added", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            } else if (buttonText.equals("M-")) { // Memory Minus
                try {
                    double currentValue = Double.parseDouble(display.getText().toString());
                    memory -= currentValue;
                    Toast.makeText(MainActivity.this, "Memory Subtracted", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            } else { // Operator is pressed
                firstOperand = Double.parseDouble(display.getText().toString());
                operator = buttonText;
                isOperatorPressed = true;
            }
        }
    };

    private double calculateResult(double num1, double num2, String operator) {
    	if (operator.equals("+")) {
            return num1 + num2;
        } else if (operator.equals("-")) {
            return num1 - num2;
        } else if (operator.equals("*")) {
            return num1 * num2;
        } else if (operator.equals("/")) {
            if (num2 != 0) {
                return num1 / num2;
            } else {
                return Double.NaN; // Handle division by zero
            }
        } else {
            return 0;
        }
    }
}
