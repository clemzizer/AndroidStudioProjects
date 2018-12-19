package fr.android.calculator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button b0;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button bplus;
    Button bminus;
    Button bmultiply;
    Button bdivide;
    Button bequals;
    Button bpoint;
    Button bc;
    TextView calc_disp;
    TextView result_disp;
    double val1=Double.NaN;
    double val2;
    char op='0';
    String res;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b0=findViewById(R.id.zero);
        b0.setOnClickListener(this);

        b1=findViewById(R.id.one);
        b1.setOnClickListener(this);

        b2=findViewById(R.id.two);
        b2.setOnClickListener(this);

        b3=findViewById(R.id.three);
        b3.setOnClickListener(this);

        b4=findViewById(R.id.four);
        b4.setOnClickListener(this);

        b5=findViewById(R.id.five);
        b5.setOnClickListener(this);

        b6=findViewById(R.id.six);
        b6.setOnClickListener(this);

        b7=findViewById(R.id.seven);
        b7.setOnClickListener(this);

        b8=findViewById(R.id.eight);
        b8.setOnClickListener(this);

        b9=findViewById(R.id.nine);
        b9.setOnClickListener(this);

        bplus=findViewById(R.id.add);
        bplus.setOnClickListener(this);

        bminus=findViewById(R.id.minus);
        bminus.setOnClickListener(this);

        bmultiply=findViewById(R.id.multiply);
        bmultiply.setOnClickListener(this);

        bdivide=findViewById(R.id.divide);
        bdivide.setOnClickListener(this);

        bequals=findViewById(R.id.equals);
        bequals.setOnClickListener(this);

        bpoint=findViewById(R.id.point);
        bpoint.setOnClickListener(this);

        calc_disp=findViewById(R.id.calc);

        result_disp=findViewById(R.id.Result);

        bc=findViewById(R.id.C);
        bc.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.zero:
                calc_disp.setText(calc_disp.getText() + "0");
                break;
            case R.id.one:
                calc_disp.setText(calc_disp.getText() + "1");
                break;
            case R.id.two:
                calc_disp.setText(calc_disp.getText() + "2");
                break;
            case R.id.three:
                calc_disp.setText(calc_disp.getText() + "3");
                break;
            case R.id.four:
                calc_disp.setText(calc_disp.getText() + "4");
                break;
            case R.id.five:
                calc_disp.setText(calc_disp.getText() + "5");
                break;
            case R.id.six:
                calc_disp.setText(calc_disp.getText() + "6");
                break;
            case R.id.seven:
                calc_disp.setText(calc_disp.getText() + "7");
                break;
            case R.id.eight:
                calc_disp.setText(calc_disp.getText() + "8");
                break;
            case R.id.nine:
                calc_disp.setText(calc_disp.getText() + "9");
                break;
            case R.id.point:
                calc_disp.setText(calc_disp.getText() + ".");
                break;
            case R.id.add:
                computeState();
                op='+';
                break;
            case R.id.minus:
                computeState();
                op='-';
                break;
            case R.id.multiply:
                computeState();
                op='*';
                break;
            case R.id.divide:
                computeState();
                op='/';
                break;
            case R.id.C:
                calc_disp.setText(null);
                result_disp.setText(null);
                val1=0;
                val2=0;
                break;
            case R.id.equals:
                ComputeCalc task = new ComputeCalc();
                task.execute();
                break;

        }
    }
    private void computeState() {
        if(!Double.isNaN(val1)) {
            val2 = Double.parseDouble(calc_disp.getText().toString());
            calc_disp.setText(null);
        }
        else {
            try {
                val1 = Double.parseDouble(calc_disp.getText().toString());
                calc_disp.setText(null);
            }
            catch (Exception e){}
        }
    }

    private class ComputeCalc extends AsyncTask<Void, Void, String> {
        DataOutputStream dos;
        DataInputStream dis;
        Socket sock;
        @Override
        protected String doInBackground(Void... arg0) {

            //Opening connection to server

            try {
                sock= new Socket("192.168.43.143", 9876);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                dos = new DataOutputStream(sock.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Write to dos
            try {
                dos.writeDouble(val1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                dos.writeChar(op);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                dos.writeDouble(val2);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Ask for dis
            try {
                dis=new DataInputStream(sock.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                res= new String(dis.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res;
        }
        protected void onPostExecute(String result) {
            result_disp.setText(res);
        }
    }
}



