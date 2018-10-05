package com.mobitribe.voicemeet

import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.util.*

/**
 * Author: Muhammad Shahab.
 * Organization: Mobitribe
 * Date: 2/13/18
 * Description:
 */

class ValidationUtility{

    companion object {
        fun isValidEmail(mEditText: EditText): Boolean {
            val text = mEditText.text.trim().toString()
            if (text.isEmpty()) {
                return false
            } else {
                val correct = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
                if (!correct) {
                    return false
                } else {
                    if (text.contains("example")) {
                        return false
                    }
                }


            }
            return true
        }

        fun isValidEmailForNext(mEditText: EditText): Boolean {
                val text = mEditText.text.trim().toString()
            if(!mEditText.text.isEmpty()){
                val correct = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
                if (!correct) {
                    return false
                }
            }
            else {
                    if (text.contains("example")) {
                        return false
                    }
                }

            return true
        }

        fun edittextValidator(vararg mEditTexts: EditText): Boolean {
            for (mEditText in mEditTexts) {
                if (mEditText.text.toString().isEmpty()) {
                    mEditText.error = "Required"
                    return false
                }
            }
            return true
        }

        fun matchesPassword(password : EditText, confirmPassword : EditText) : Boolean{
            return (password.text.toString().equals(confirmPassword.text.toString()))
        }

        fun isValidPassword(edPassword: EditText): Boolean {
            return edPassword.text.toString().matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}\$"))
        }

        fun isValidLoginPassword(edPassword: EditText): Boolean {
            return edPassword.text.toString().length >=8
        }
        fun isValidUsername(userName: EditText): Boolean {
            return userName.text.toString().length >= 5
        }

        fun isValidDomain(mEditText: EditText) : Boolean{
            val domain =  mEditText.text.indexOf('@').let { if (it == -1) null else mEditText.text.substring(it) }
            if(domain?.equals("@gmail.com") as Boolean || domain?.equals("@hotmail.com") || domain?.equals("@msn.com")
                    || domain?.equals("@yahoo.com") || domain?.equals("@live.com") || domain?.equals("@outlook.com"))
            {
                return false
            }
            return true
        }


        fun removeErrorEditText(mEditText: AppCompatAutoCompleteTextView) {
            mEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    mEditText.setError(null)
                }

                override fun afterTextChanged(s: Editable) {
                }
            })
        }

        fun isValidDate(startTimeCalendar: Calendar?, endTimeCalendar: Calendar?): Boolean {
            Log.d("DateComparison", "" + startTimeCalendar?.timeInMillis + " " + endTimeCalendar?.timeInMillis)
            if (startTimeCalendar?.after(endTimeCalendar)!!)
            {
                return false
            }
            return true
        }

        fun isPinValid(trim: String): Boolean {
            return trim.length == 6
        }

    }
}