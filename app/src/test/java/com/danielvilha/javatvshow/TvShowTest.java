package com.danielvilha.javatvshow;

import com.danielvilha.javatvshow.models.TopRated;
import com.danielvilha.javatvshow.service.RetrofitBuilder;
import com.danielvilha.javatvshow.service.ApiService;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import static org.junit.Assert.assertNotNull;

/**
 * Created by danielvilha on 18/10/20
 * https://github.com/danielvilha
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TvShowTest {
    @Test
    public void tvShowNotNull() {
        ApiService api = new RetrofitBuilder().retrofit().create(ApiService.class);
        Observable<TopRated> service = api.getTopRated(1);
        service.subscribeOn(Schedulers.io())
                .subscribe(new Observer<TopRated>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull TopRated topRated) {
                        assertNotNull(topRated);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) { }

                    @Override
                    public void onComplete() { }
                });
    }
}
