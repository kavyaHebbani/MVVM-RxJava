package upday.mvvm.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import upday.mvvm.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentById(R.id.main_fragment) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, new MainFragment(), "MainFragment")
                    .commit();
        }
    }

}
