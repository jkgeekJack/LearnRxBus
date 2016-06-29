package com.jkgeekjack.learnrxbus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import rx.Subscriber;
import rx.Subscription;

public class MyService extends Service {
    private Subscription subscription;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_SHORT).show();
        subscription=RxBus.getRxBusInstance().toObserverable(TapEvent.class)
                .subscribe(new Subscriber<TapEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TapEvent tapEvent) {
                        int n=tapEvent.n;
                        Log.e("Service",n+"");
                        Toast.makeText(getApplicationContext(),"service",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
