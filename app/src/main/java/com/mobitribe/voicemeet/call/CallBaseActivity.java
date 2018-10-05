package com.mobitribe.voicemeet.call;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.mobitribe.voicemeet.ParentActivity;
import com.mobitribe.voicemeet.R;
import com.mobitribe.voicemeet.databinding.FragmentCallBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Author: shahabrauf
 * Date: 30/08/2018
 * Descrition:
 */
public class CallBaseActivity extends ParentActivity {

    protected boolean mic = true;
    protected boolean speaker = true;
    protected boolean video = true ;
    protected boolean selfVideo = true;
    FragmentCallBinding binding;
    protected ParentActivity pActivity;
    private AudioManager mAudioMgr;
    private boolean callEnded = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pActivity = this;
        mAudioMgr = (AudioManager)pActivity.getSystemService(Context.AUDIO_SERVICE);

    }

    protected void muteSpeaker() {
        if (speaker)
        {
            binding.speaker.setBackgroundResource(R.drawable.speaker_off);
            pActivity.showMessage("Speakers off");
            mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);
            mAudioMgr.setSpeakerphoneOn(false);
            mAudioMgr.setWiredHeadsetOn(true);

        } else {
            mAudioMgr.setWiredHeadsetOn(false);
            mAudioMgr.setSpeakerphoneOn(true);
            mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);
            pActivity.showMessage("Speakers on");
            binding.speaker.setBackgroundResource(R.drawable.speaker_onn);
        }

        speaker = !speaker;
    }

    protected void recordVideo(){
        Log.d("DIALOUT","Called");
        binding.pexView.evaluateFunction("dialOut",binding.pexView.new PexCallback(){
            @Override
            public void callback(String s) {
                Log.d("DIALOUT", s);
            }
        },"s1errwtn69ly5w.cloudfront.net", "rtmp", "GUEST", getParams());
    }

    private HashMap<String, String> getParams(){
        HashMap<String,String> params = new HashMap<>();
        params.put("streaming","true");
        params.put("remote_display_name","Amazon");
        return params;
    }

    protected void endCall(){
       if (!callEnded) {
            binding.pexView.evaluateFunction("disconnect");
            super.onBackPressed();
            callEnded=true;
       }
    }

    protected void muteVideo() {
        if (video)
        {
            binding.videoCall.setBackgroundResource(R.drawable.video_recorder_off);
            binding.pexView.evaluateFunction("muteVideo", true);
            pActivity.showMessage("Video off");
            binding.selfView.setVisibility(View.GONE);


        } else {
            binding.videoCall.setBackgroundResource(R.drawable.video_recorder_onn);
            binding.pexView.evaluateFunction("muteVideo", false);
            pActivity.showMessage("Video on");
            binding.selfView.setVisibility(View.VISIBLE);

        }

        video = !video;
    }

    protected void muteMicroPhone() {
        if (mic)
        {
            binding.microPhone.setBackgroundResource(R.drawable.voice_recorder_off);
            binding.pexView.evaluateFunction("muteAudio", true);
            pActivity.showMessage("Microphone off");


        } else {
            binding.microPhone.setBackgroundResource(R.drawable.voice_recorder_on);
            binding.pexView.evaluateFunction("muteAudio", false);
            pActivity.showMessage("Microphone on");

        }

        mic = !mic;
    }

    protected void setPexipEvents() {

        binding.pexView.setEvent("onSetup", binding.pexView.new PexEvent() {
            @Override
            public void onEvent(String[] strings) {
                binding.pexView.setSelfViewVideo(strings[0]);
                    binding.pexView.evaluateFunction("connect","123456");
            }
        });

        binding.pexView.setEvent("onConnect", binding.pexView.new PexEvent() {
            @Override
            public void onEvent(String[] strings) {
                if (strings.length > 0 && strings[0] != null)
                {
                    binding.pexView.setVideo(strings[0]);
                }
            }
        });

        binding.pexView.setEvent("onError", binding.pexView.new PexEvent() {
            @Override
            public void onEvent(String[] strings) {
                pActivity.showMessage(strings[0]);
            }
        });

        binding.pexView.setEvent("onDisconnect", binding.pexView.new PexEvent() {
            @Override
            public void onEvent(String[] strings) {

            }
        });


        binding.pexView.addPageLoadedCallback(binding.pexView.new PexCallback() {
            @Override
            public void callback(String args) {

                // make a call
                    binding.pexView.evaluateFunction("makeCall", "conf.attribes.com", "Shaan.khokhar@conf.attribes.com" , "Attribe voice", "480");
            }
        });
    }

    private String parseHostFromURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
            return url.getHost();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
