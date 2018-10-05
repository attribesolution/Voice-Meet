package com.mobitribe.voicemeet

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat

class SplashActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        openNextScreenInDelay()
    }

    private fun openNextScreenInDelay() {
        Handler().postDelayed({
                openActivityWithFinish(PermissionActivity::class.java)
            }
        , 2000)
    }
}
