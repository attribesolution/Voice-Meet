package com.mobitribe.voicemeet

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Button
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        checkIfPermissionGrantedAlready()
        setListeners()
    }

    private fun setListeners() {
        enableCamera.setOnClickListener {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                   CAMERA_PERMISSION_REQUEST_CODE)

        }

        enableMicroPhone.setOnClickListener {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    MICROPHONE_PERMISSION_REQUEST_CODE)
        }

        getStarted.setOnClickListener {
            openActivityWithFinish(MainActivity::class.java)
        }
    }

    private fun checkIfPermissionGrantedAlready() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            enableButton(enableCamera)
        }

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            enableButton(enableMicroPhone)

        }

        if(!enableCamera.isEnabled && !enableMicroPhone.isEnabled)
            openActivityWithFinish(MainActivity::class.java)
    }

    private fun enableButton(button: Button) {
        button.isEnabled = false
        button.setTextColor(ContextCompat.getColor(this,R.color.white))
    }

    private val CAMERA_PERMISSION_REQUEST_CODE = 1231
    private val MICROPHONE_PERMISSION_REQUEST_CODE = 45324


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode)
        {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    enableButton(enableCamera)
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showMessage(getString(R.string.permission_denied))
                }
                return
            }

            MICROPHONE_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    enableButton(enableMicroPhone)
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showMessage(getString(R.string.permission_denied))
                }
                return
            }
        }
    }
}
