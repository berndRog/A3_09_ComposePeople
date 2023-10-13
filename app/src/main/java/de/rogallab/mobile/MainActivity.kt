package de.rogallab.mobile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import de.rogallab.mobile.domain.utilities.Seed
import de.rogallab.mobile.domain.utilities.logDebug
import de.rogallab.mobile.ui.navigation.AppNavHost
import de.rogallab.mobile.ui.people.PeopleViewModel
import de.rogallab.mobile.ui.permissions.IPermissionText
import de.rogallab.mobile.ui.permissions.PermissionCamera
import de.rogallab.mobile.ui.permissions.PermissionRecordAudio
import de.rogallab.mobile.ui.theme.AppTheme

class MainActivity : BaseActivity(tag) {

   private val _seed = Seed()
   private val _mainViewModel by viewModels<MainViewModel>()
   private val _peopleViewModel by viewModels<PeopleViewModel>()

   // required permissions
   private val permissionsToRequest = arrayOf(
      Manifest.permission.CAMERA
//    Manifest.permission.RECORD_AUDIO
   )

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      // initialize images from resources
      _mainViewModel.initializeImages(resources)

      // initialize peoples from seed
      _peopleViewModel.initialize(_mainViewModel.seed)

      setContent {
         AppTheme {
            // A surface container using the 'background' color from the theme
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               RequestPermissions(permissionsToRequest, _mainViewModel)
               AppNavHost(_peopleViewModel)
            }
         }
      }
   }



   // static members
   companion object {
      const val isInfo = true
      const val isDebug = true
      //12345678901234567890123
      private const val tag = "ok>MainActivity       ."
   }
}

@Composable
fun RequestPermissions(
   permissionsToRequest: Array<String>,
   mainViewModel: MainViewModel
) {

   val tag = "ok>RequestPermissions ."

   val activity = (LocalContext.current as? Activity)



   // Setup multiple permission request launcher
   val multiplePermissionRequestLauncher = rememberLauncherForActivityResult(
      contract = ActivityResultContracts.RequestMultiplePermissions(),
      // callback: handle permissions results, i.e store them into the MainViewModel
      onResult = { permissionMap: Map<String, @JvmSuppressWildcards Boolean> ->
         logDebug(tag, "PermissionRequestLauncher onResult: ")
         permissionsToRequest.forEach { permission ->
            logDebug(tag,"$permission isGranted ${permissionMap[permission]}")
            mainViewModel.addPermission(
               permission = permission,
               isGranted = permissionMap[permission] == true  // key = true
            )
         }
      }
   )

   if (!arePermissionsAlreadyGranted(permissionsToRequest, tag)) {
      // Request permissions, i.e. launch dialog
      LaunchedEffect(true) {
         val cameraPermission = Manifest.permission.CAMERA
         logDebug(tag, "PermissionRequestLauncher launched")
         multiplePermissionRequestLauncher.launch(
            permissionsToRequest
         )
      }
   }

   // if a requested permission is not granted -> ask again or goto appsettings
   mainViewModel.permissionQueue
      .reversed()
      .forEach { permission ->
         logDebug(tag, "permissionQueue $permission")

         var dialogOpen by remember {  mutableStateOf(false) }

         val isPermanentlyDeclined =
            activity!!.shouldShowRequestPermissionRationale(permission)
         logDebug(tag, "permissionsQueue isPermanentlyDeclined $isPermanentlyDeclined")

         // get the text for requested permission
         val permissionText: IPermissionText? = when (permission) {
            Manifest.permission.CAMERA -> PermissionCamera()
            Manifest.permission.RECORD_AUDIO -> PermissionRecordAudio()
            else -> null
         }
         logDebug(tag, "permissionsQueue " +
            "${permissionText?.getDescription(isPermanentlyDeclined)}")

         AlertDialog(
            modifier = Modifier,
            onDismissRequest = {
               dialogOpen = false
            },
            // permission is granted, perform the confirm actions
            confirmButton = {
               TextButton(
                  onClick = {
                     logDebug(tag, "confirmButton() $permission")
                     // remove granted permission from the permissionQueue
                     mainViewModel.removePermission()
                     // launch the dialog again if further permissions are required
                     multiplePermissionRequestLauncher.launch(arrayOf(permission))
                     // close the dialog
                     dialogOpen = false
                  }
               ) {
                  Text(text = "Zustimmen")
               }
            },
            // permission is declined, perform the decline actions
            dismissButton = {
               TextButton(
                  onClick = {
                     logDebug(tag, "dismissButton() $permission")
                     if (! isPermanentlyDeclined) {
                        // remove permanently declined permissions from the permissionQueue
                        mainViewModel.removePermission()
                        // launch the dialog again if further permissions are required
                        multiplePermissionRequestLauncher.launch(arrayOf(permission))
                     } else {
                        logDebug(tag, "openAppSettings() $permission and exit the app")
                        // as a last resort, go to the app settings and close the app
                        activity?.openAppSettings()
                        activity?.finish()
                     }
                     // close the dialog
                     dialogOpen = false
                  }
               ) {
                  Text(text = "Ablehnen")
               }
            },
            icon = {},
            title = {
               Text(text = "Zustimmung erforderlich (Permission)")
            },
            text = {
               Text(
                  text = permissionText?.getDescription(
                     isPermanentlyDeclined = isPermanentlyDeclined
                  ) ?: ""
               )
            }
         )
      }
}

@Composable
fun arePermissionsAlreadyGranted(
   permissionsToRequest: Array<String>,
   tag: String
): Boolean {
   permissionsToRequest.forEach { permissionToRequest ->
      if (ContextCompat.checkSelfPermission(
            LocalContext.current,
            permissionToRequest
         ) == PackageManager.PERMISSION_GRANTED) {
         logDebug(tag, "requestPermission() $permissionToRequest already granted")
      } else {
         // permission must be requested
         return false
      }
   }
   // all permission are already granted
   return true
}

// static extension function for Activity
fun Activity.openAppSettings() {
   Intent(
      Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
      Uri.fromParts("package", packageName, null)
   ).also(::startActivity)
}


@Preview(showBackground = true)
@Composable
fun Preview() {

   AppTheme {
   }
}
