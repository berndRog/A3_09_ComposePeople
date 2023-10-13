package de.rogallab.mobile.domain.utilities

fun isValidEmail(email: String): Boolean {

   // Email Validation Permitted by RFC 5322
   // val emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$".toRegex()

   // Pure Kotlin
   // val emailRegex =
   //   "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
   //   .toRegex()
   // return email.matches(emailRegex)
   return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPhone(phone: String): Boolean {
   return android.util.Patterns.PHONE.matcher(phone).matches()
}




