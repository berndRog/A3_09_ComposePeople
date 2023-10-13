package de.rogallab.mobile.ui.permissions

import de.rogallab.mobile.ui.permissions.IPermissionText

class PermissionCamera : IPermissionText {
//	<uses-feature
//		android:name="android.hardware.camera"
//		android:required="false" />
//	<uses-permission android:name="android.permission.CAMERA" />
   override fun getDescription(isPermanentlyDeclined: Boolean): String {
      return if (isPermanentlyDeclined) {
         "Es scheint als hätten Sie den Zugriff auf die Kamera mehrfach abgelehnt. " +
            "Sie können diese Entscheidung nur über die App Einstellungen ändern."
      } else {
         "Die App erfordert den Zugriff auf die Kamera, um ein Foto aufzunehmen."
      }
   }
}

class PermissionPhoneCall : IPermissionText {
   override fun getDescription(isPermanentlyDeclined: Boolean): String {
      return if (isPermanentlyDeclined) {
         "Es scheint als hätten Sie den Zugriff auf Anrufen mehrfach abgelehnt. " +
            "Sie können diese Entscheidung nur über die App Einstellungen ändern."
      } else {
         "This app needs access to your camera so that your friends " +
            "can see you in a call."
      }
   }
}

class PermissionRecordAudio : IPermissionText {
   override fun getDescription(isPermanentlyDeclined: Boolean): String {
      return if (isPermanentlyDeclined) {
         "It seems you permanently declined microphone permission. " +
            "You can go to the app settings to grant it."
      } else {
         "This app needs access to your microphone so that your friends " +
            "can hear you in a call."
      }
   }
}