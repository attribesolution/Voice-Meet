package com.mobitribe.voicemeet

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager


import java.io.IOException

import android.widget.Toast
import android.speech.tts.TextToSpeech
import java.util.*
import android.speech.RecognizerIntent
import android.content.ActivityNotFoundException




/**
 * Author: Muhammad Shahab
 * Date: 5/2/17.
 * Description:It is the parent class of all activities
 */

abstract class ParentActivity : AppCompatActivity() {
    private var animationNeeded: Boolean = false
    private var forwardTransition: Boolean = false
    private val view: View? = null


    /**
     * @usage onCreate method that will be called by all child class instances to initialize some useful objects
     * @param savedInstanceState
     */

    private lateinit var t1: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animationNeeded = true
        forwardTransition = true
        initTTS()
    }

    private fun initTTS() {
        t1 = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                t1.setLanguage(Locale.UK)
            }
        })
    }



    /**
     * @purpose  Setter for animation needed parameter
     * @param value
     */
    fun setAnimationNeeded(value: Boolean) {
        animationNeeded = value
    }

    /**
     * @usage it opens the activity receives in parameter
     * @param activity
     */
    fun openActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }

    /**
     * @usage it opens the activity receives in parameter and finish  the current activity running
     * @param activity
     */
    fun openActivityWithFinish(activity: Class<*>) {
        startActivity(Intent(this, activity))
        finish()
    }

    /**
     * @usage it opens the activity with provide intent and finish  the current activity running
     * @param intent
     */
    fun openActivityWithFinish(intent: Intent) {
        startActivity(intent)
        finish()
    }

    /**
     * @usage it opens the activity with provide intent
     * @param intent
     */
    fun openActivity(intent: Intent) {
        startActivity(intent)
    }

    /**
     * @usage It opens the activity with the provided bundle as a parameter
     * @param activity
     * @param bundle
     */
    fun openActivity(activity: Class<*>, bundle: Bundle) {
        startActivity(Intent(this, activity).putExtras(bundle))
    }

    /**
     * @usage It opens the activity for result with the provided bundle as a parameter
     * @param activity
     * @param bundle
     */
    fun openActivityForResults(activity: Class<*>, bundle: Bundle, requestCode: Int) {
        startActivityForResult(Intent(this, activity).putExtras(bundle), requestCode)
    }


    /**
     * @usage It opens the activity for result
     * @param activity
     */
    fun openActivityForResults(activity: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(this, activity), requestCode)
    }


    /**
     * @usage Making notification bar transparent
     */
    fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }



    /**
     * @usage It use to show any message provided by the caller
     * @param message
     */
    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * @usage it handles onFailure Response of retrofit
     * @param throwable
     * @param view
     */
    fun onFailureResponse(throwable: Throwable) {
        if (throwable is IOException) {
            showMessage(getString(R.string.internet_connectivity))
        } else {
            showMessage(getString(R.string.some_thing_went_wrong))
        }
    }



    fun onBackPressed(v: View) {
        super.onBackPressed()
    }

    override fun onPause() {
        if (animationNeeded) {
            if (forwardTransition)
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity)
            else
                overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity)
        }
        super.onPause()

    }


    override fun onBackPressed() {
        forwardTransition = false
        super.onBackPressed()
    }

    companion object {

        var SIGN_UP_STATUS = 0
        private val TAG = "ParentActivity"
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    protected fun speakVoiceMessages(s: String) {
        t1.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }


}
