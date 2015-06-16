package io.mazur.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import io.mazur.glacier.Duration;
import io.mazur.glacier.Glacier;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends Activity {
    @InjectView(R.id.text_view) TextView mTextView;

    private static final String CACHE_KEY = "cacheKey;";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        Observable<String> o = Glacier.getObservable(CACHE_KEY, String.class)
                .subscribeOn(AndroidSchedulers.mainThread());

        o.subscribe(s -> mTextView.setText(s));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHandler.post(new MyRunnable());
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHandler.removeCallbacksAndMessages(null);
    }

    private static final Handler mHandler = new Handler();
    private static final Random mRandom = new Random();
    private static final class MyRunnable implements Runnable {
        @Override
        public void run() {
            Glacier.put(CACHE_KEY, "Random Number: " + (mRandom.nextInt(90) + 90));

            mHandler.postDelayed(this, Duration.ONE_SECOND);
        }
    }
}
