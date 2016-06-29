package com.jkgeekjack.learnrxbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import rx.Subscriber;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {
    private Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //启动service
        Intent i=new Intent(this,MyService.class);
        startService(i);
        ButtonFragment buttonFragment=new ButtonFragment();
        TapFragment tapFragment=new TapFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frameLayout1, buttonFragment).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout2, tapFragment).commit();
        subscription=RxBus.getRxBusInstance().toObserverable(KillServiceEvent.class)
                .subscribe(new Subscriber<KillServiceEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(KillServiceEvent killServiceEvent) {
//                        关闭sevice
                        Intent i=new Intent(MainActivity.this,MyService.class);
                        Toast.makeText(MainActivity.this,"kill",Toast.LENGTH_SHORT).show();
                        stopService(i);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        Intent i=new Intent(this,MyService.class);
        stopService(i);
    }
}
