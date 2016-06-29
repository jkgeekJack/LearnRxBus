package com.jkgeekjack.learnrxbus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rx.Subscriber;
import rx.Subscription;


/**
 * A simple {@link Fragment} subclass.
 */
public class TapFragment extends Fragment {
    private Subscription subscription;

    public TapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tap, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView tvTap= (TextView) view.findViewById(R.id.tvTap);
        //subscription是类型为Subscription全局，通常在onDestroy里unsubscribe
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
                        tvTap.setText(n+"");
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
