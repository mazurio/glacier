package io.mazur.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import io.mazur.glacier.Duration;
import io.mazur.glacier.Glacier;
import io.mazur.example.R;

public class MainActivity extends Activity {
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);

        String cache = Glacier.getOrElse("cacheKey", String.class, Duration.FIVE_MINUTES, new Glacier.Callback<String>() {
            @Override
            public String onCacheNotFound() {
                return "String returned from Cache";
            }
        });

        mTextView.setText(cache);
    }
}
