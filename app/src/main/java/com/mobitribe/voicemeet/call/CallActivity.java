package com.mobitribe.voicemeet.call;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mobitribe.voicemeet.R;


/**
 * Author: shahabrauf
 * Date: 29/08/2018
 * Descrition:
 */
public class CallActivity extends CallBaseActivity {


    private int count = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_call);
        binding.pexView.setSelfView(binding.selfView);
//
        getIntentData();
        setPexipEvents();
        setListeners();

        // Load the page which will then trigger the callbacks
        binding.pexView.load();
    }

    private void getIntentData(){

    }

    private void setListeners() {
        binding.microPhone.setOnClickListener(clickListener);
        binding.videoCall.setOnClickListener(clickListener);
        binding.speaker.setOnClickListener(clickListener);
        binding.end.setOnClickListener(clickListener);
        binding.people.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.speaker:
                    muteSpeaker();
                    break;
                case R.id.microPhone:
                    muteMicroPhone();
                    break;
                case R.id.videoCall:
                    muteVideo();
                    break;
                case R.id.end:
                    endCall();
                    break;
                case R.id.people:
                    break;
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        endCall();
    }

    @Override
    public void onBackPressed() {

        if (count < 1){
            showMessage("Please tap again to exit");
            count ++;
        } else {
            this.endCall();
        }
    }
}
