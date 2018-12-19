package fr.android.conversioneurodollar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class ConversionMonnaie extends AppCompatActivity {

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Attention doit être après le OnCreate
        text= (EditText) findViewById(R.id.mavaleur);
    }

    public void myClickHandler(View view) {
        switch (view.getId()) {
            case R.id.calculer:
                RadioButton euroButton = (RadioButton) findViewById(R.id.Euro);
                RadioButton dollarButton = (RadioButton) findViewById(R.id.Dollar);
                if (text.getText().length() == 0) {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
                    return;
                }
                float inputValue = Float.parseFloat(text.getText().toString());
                if (euroButton.isChecked()) {
                    text.setText(String.valueOf(convertDollarToEuro(inputValue)));
                    euroButton.setChecked(false);
                    dollarButton.setChecked(true);
                } else {
                    text.setText(String.valueOf(convertEuroToDollar(inputValue)));
                    dollarButton.setChecked(false);
                    euroButton.setChecked(true);
                }
                break;
        }
    }

    // Convertir Dollar à Euro
    private float convertDollarToEuro(float dollar) {
        float x=0.82f;
        return dollar*x;
    }

    // Convertir Euro à Dollar
    private float convertEuroToDollar(float euro) {
        float x=1.22f;
        return euro*x;
    }
}
