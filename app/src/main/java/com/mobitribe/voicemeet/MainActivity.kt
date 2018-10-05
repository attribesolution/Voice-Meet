package com.mobitribe.voicemeet

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import com.mobitribe.voicemeet.call.CallActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        join.setOnClickListener {
            openNextScreen()
        }

        promptSpeechInput()
    }


    private fun openNextScreen() {
        if(validate()) {
            startActivity(Intent(this, CallActivity::class.java))
        } else {
            pin.setText("")
            promptSpeechInput()
        }
    }

    private val REQ_CODE_SPEECH_INPUT: Int = 1234

    /**
     * Showing google speech input dialog
     */
    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt))
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(applicationContext,
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * Receiving speech input
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    val result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    pin.setText(result[0])
                    openNextScreen()
                }
            }
        }
    }

    private fun validate(): Boolean {
        if (!ValidationUtility.edittextValidator(pin))
        {
            return false
        } else if (!ValidationUtility.isPinValid(pin.text.trim().toString()))
        {
            showMessage("Invalid pin code")
            speakVoiceMessages("Invalid pin code")
            return false
        }

        return true
    }


}
