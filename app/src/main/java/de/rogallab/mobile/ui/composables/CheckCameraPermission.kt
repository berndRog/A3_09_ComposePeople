package de.rogallab.mobile.ui.composables

import androidx.compose.runtime.Composable
import de.rogallab.mobile.domain.utilities.logDebug

/*
<uses-feature android:name="android.hardware.camera"
                android:required="false" />
  <uses-permission android:name="android.permission.CAMERA" />
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
*/


@Composable
fun CheckCameraPermission(
   tag: String = "ok>CheckCameraPermiss ."
) {

   logDebug(tag, "Start")
/*
   // Camera permission state
   val cameraPermissionState = rememberPermissionState(
      Manifest.permission.CAMERA
   )

   when (cameraPermissionState.status) {
      // If the camera permission is granted, do noting
      is PermissionStatus.Granted -> {
         LogComp(tag, "camera permission granted")

      }
      is PermissionStatus.Denied -> {
         val textToShow = if ((cameraPermissionState.status as PermissionStatus.Denied)
               .shouldShowRationale) {
            // permission has been denied, show the rationale (=Begründung)
            "Die Kamera ist wichtig für diese App. Bitte gewähren Sie den Zugriff."
         }
         else {
            // First time
            "Die App erfordert den Zugriff auf die Kamera, um ein Foto aufzunehmen."
         }
         LogComp(tag, "$textToShow")

         Column(
            modifier = Modifier
               .padding(top = 32.dp)
               .fillMaxSize(),
         ) {
            Text(
               text = textToShow,
               color = MaterialTheme.colorScheme.error,
               style = MaterialTheme.typography.bodyLarge
            )
            Button(
               modifier = Modifier
                  .padding(
                     top = 4.dp,
                     bottom = 4.dp
                  )
                  .fillMaxWidth(),
               onClick = {
                  // Note that this dialog might not appear on the screen
                  // if the user doesn't want to be asked again or has denied
                  // the permission multiple times.
                  // This behavior varies depending on the Android level API.
                  cameraPermissionState.launchPermissionRequest()
               }
            ) {
               Text(
                  //modifier = Modifier.padding(all = 4.dp),
                  // request permission
                  text = "Erlaubnis anfordern",
                  style = MaterialTheme.typography.bodyLarge
               )
            }
         }
      }
   }

 */
}