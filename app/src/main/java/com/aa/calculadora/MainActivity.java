package com.aa.calculadora;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView textOperation, textANS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        new AppUpdater(this)
                .setDisplay(Display.DIALOG)
                .setDisplay(Display.NOTIFICATION)
                //.setUpdateFrom(UpdateFrom.GITHUB)
                //.setGitHubUserAndRepo("AlexisYadir", "CalculatorTestUpdate")
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON("https://raw.githubusercontent.com/AlexisYadir/CalculatorTestUpdate/master/app/update-changelog.json")
                .start();
        textOperation = findViewById(R.id.textOperation);
        textANS = findViewById(R.id.textANS);
        findViewById(R.id.btnPOINT).setOnClickListener(this);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnDEL).setOnClickListener(this);
        findViewById(R.id.btnCE).setOnClickListener(this);
        findViewById(R.id.btnC).setOnClickListener(this);
        findViewById(R.id.btnADD).setOnClickListener(this);
        findViewById(R.id.btnSUB).setOnClickListener(this);
        findViewById(R.id.btnX).setOnClickListener(this);
        findViewById(R.id.btnDIV).setOnClickListener(this);
        findViewById(R.id.btnRESULT).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnPOINT: addPoint(); break;
            case R.id.btn0: addNum(0); break;
            case R.id.btn1: addNum(1); break;
            case R.id.btn2: addNum(2); break;
            case R.id.btn3: addNum(3); break;
            case R.id.btn4: addNum(4); break;
            case R.id.btn5: addNum(5); break;
            case R.id.btn6: addNum(6); break;
            case R.id.btn7: addNum(7); break;
            case R.id.btn8: addNum(8); break;
            case R.id.btn9: addNum(9); break;
            case R.id.btnDEL: delNum(); break;
            case R.id.btnCE: delBase(); break;
            case R.id.btnC: delAll(); break;
            case R.id.btnADD: setOperation("+"); break;
            case R.id.btnSUB: setOperation("-"); break;
            case R.id.btnX: setOperation("x"); break;
            case R.id.btnDIV: setOperation("/"); break;
            case R.id.btnRESULT: resultTotal(); break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void resultTotal() {
        String operation = textOperation.getText().toString();
        String aux = textANS.getText().toString();
        if (aux.isEmpty()) return;
        StringBuilder auxBuilder = new StringBuilder(aux);
        String op = auxBuilder.substring(auxBuilder.length() - 1);
        auxBuilder.deleteCharAt(auxBuilder.length() - 1);
        double num = Double.parseDouble(auxBuilder.toString());
        double num2 = Double.parseDouble(operation);
        textOperation.setText(operators(num, num2, op).toString());
        //String result = String.valueOf(operators(num, num2, op));
    }

    private String resultPreview(String textOperation) {
        String aux = textANS.getText().toString();
        if (!aux.isEmpty()) {
            StringBuilder auxBuilder = new StringBuilder(aux);
            String op = auxBuilder.substring(auxBuilder.length() - 1);
            auxBuilder.deleteCharAt(auxBuilder.length() - 1);
            double num = Double.parseDouble(auxBuilder.toString());
            double num2 = Double.parseDouble(textOperation);
            return String.valueOf(operators(num, num2, op));
        } return textOperation;
    }

    private Double operators(double num, double num2, String op) {
        switch (op) {
            case "+": return num+num2;
            case "-": return num-num2;
            case "/": return num/num2;
            case "x": return num*num2;
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    private void addPoint() {
        String aux = textOperation.getText().toString();
        if (!aux.contains(".")) {
            if (aux.isEmpty()) aux = "0";
            textOperation.setText(aux + ".");
        }
    }

    @SuppressLint("SetTextI18n")
    private void setOperation(String op) {
        String base = textOperation.getText().toString();
        if (base.isEmpty()) return;
        String aux = resultPreview(base);
        textANS.setText(aux+op);
        textOperation.setText("");
    }

    private void delAll() {
        textANS.setText("");
        delBase();
    }

    private void delBase() { textOperation.setText(""); }

    private void delNum() {
        String aux = textOperation.getText().toString();
        if (aux.isEmpty()) return;
        StringBuilder auxBuilder = new StringBuilder(aux);
        auxBuilder.deleteCharAt(auxBuilder.length()-1);
        textOperation.setText(auxBuilder.toString());
    }

    private void addNum(int num) {
        Log.i("op", textOperation.getText().toString());
        if (textOperation.getText() == null) textOperation.setText("");
        String aux = textOperation.getText().toString();
        Log.i("op", aux);
        aux += String.valueOf(num);
        Log.i("op", aux);
        textOperation.setText(aux);
    }
}
