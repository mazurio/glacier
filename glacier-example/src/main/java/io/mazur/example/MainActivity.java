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
import rx.Subscription;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends Activity {
    @InjectView(R.id.first_text_view)
    TextView mFirstTextView;

    @InjectView(R.id.second_text_view)
    TextView mSecondTextView;

    private static final String CACHE_KEY = "cacheKey;";

    Observable<String> cacheObservable;

    CompositeSubscription compositeSubscription = new CompositeSubscription();

    @OnClick(R.id.first_button)
    public void subscribe(View view) {
        Log.d("RxJava", "Subscribed new TextView");

        compositeSubscription.add(cacheObservable.subscribe(s -> {
            Log.d("RxJava", "Set to: " + s);

            mFirstTextView.setText(s);
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        cacheObservable = Glacier.getObservable(CACHE_KEY, String.class);

        AndroidObservable.bindActivity(this, cacheObservable);

        compositeSubscription.add(cacheObservable.subscribe(s -> mSecondTextView.setText(s)));
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

        compositeSubscription.unsubscribe();
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
