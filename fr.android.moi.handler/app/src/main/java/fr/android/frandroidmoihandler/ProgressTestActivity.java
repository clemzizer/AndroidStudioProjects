package fr.android.frandroidmoihandler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class ProgressTestActivity extends AppCompatActivity {
    private Handler handler;
    private ProgressBar progress;
    /** Called everytime the activity is created */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_test);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        handler = new Handler();
    }

    public void startProgress(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    final int value = i; // simulate a slow network !
                    try { Thread.sleep(2000); }
                    catch (InterruptedException e) { e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() { progress.setProgress(value);}
                    });
                }
            }
        };
        new Thread(runnable).start();
    }
}
