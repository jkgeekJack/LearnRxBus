package com.jkgeekjack.learnrxbus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonFragment extends Fragment {
    private int i;

    public ButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_button, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnTap= (Button) view.findViewById(R.id.btnTap);
        btnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                发送一个TapEvent，并传递一个数字i
                RxBus.getRxBusInstance().post(new TapEvent(i));
                i++;
            }
        });
        Button btnKillService= (Button) view.findViewById(R.id.killService);
        btnKillService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                发送KillServiceEvent
                RxBus.getRxBusInstance().post(new KillServiceEvent());
            }
        });
    }
}
