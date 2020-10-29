package com.danielvilha.javatvshow;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.danielvilha.javatvshow.models.TopRated;
import com.danielvilha.javatvshow.service.ApiService;
import com.danielvilha.javatvshow.service.RetrofitBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Created by danielvilha on 18/10/20
 * https://github.com/danielvilha
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.danielvilha.javatvshow", appContext.getPackageName());
    }

    @Test
    public void topRatedList() {
        ApiService api = new RetrofitBuilder().retrofit().create(ApiService.class);
        Observable<TopRated> service = api.getTopRated(1);
        final TopRated[] item = new TopRated[0];

        service.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopRated>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull TopRated tvShow) {
                        item[0] = tvShow;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) { }

                    @Override
                    public void onComplete() { }
                });

        assertNotNull(item);

    }
}