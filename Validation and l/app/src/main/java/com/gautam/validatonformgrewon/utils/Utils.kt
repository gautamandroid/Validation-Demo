package com.gautam.validatonformgrewon.utils

import android.util.Patterns
import androidx.core.text.trimmedLength
import java.util.regex.Pattern

class Utils {

    companion object {

        fun isValidPhone(phone: String): Boolean =
            phone.trimmedLength() in (10..10) && Patterns.PHONE.matcher(phone).matches()

        fun isValidEmail(email: String): Boolean {
            return Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
            ).matcher(email).matches() && email.isNotEmpty()
        }

        fun isValidpassword(passworld: String): Boolean {
            var regex = (
                    "^(?=.*[0-9])" +
                            "(?=.*[a-z])(?=.*[A-Z])" +
                            "(?=.*[@#$%^&+=])" +
                            "(?=\\S+$).{8,20}$"
                    )
            return Pattern.compile(regex).matcher(passworld).matches()
        }
    }
}
