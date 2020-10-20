package com.danielvilha.javatvshow;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.danielvilha.javatvshow.object.TvShow;
import com.danielvilha.javatvshow.service.ApiService;
import com.danielvilha.javatvshow.service.RetrofitBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
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
    public void moviesList() {
        ApiService api = new RetrofitBuilder().retrofit().create(ApiService.class);
        Observable<TvShow> service = api.getMovie();
        final TvShow[] item = new TvShow[0];

        service.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TvShow>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull TvShow tvShow) {
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