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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import de.rogallab.mobile.ui.composables.permissions.IPermissionText
import de.rogallab.mobile.ui.composables.permissions.PermissionCamera
import de.rogallab.mobile.ui.composables.permissions.PermissionRecordAudio
import de.rogallab.mobile.ui.navigation.AppNavHost
import de.rogallab.mobile.ui.theme.AppTheme
import de.rogallab.mobile.domain.utilities.logDebug

class MainActivity : BaseActivity(tag) {

   // required permissions
   private val permissionsToRequest = arrayOf(
      Manifest.permission.CAMERA
//    Manifest.permission.RECORD_AUDIO
   )

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      setContent {
         AppTheme {
            // A surface container using the 'background' color from the theme
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {

               val mainViewModel = viewModel<MainViewModel>()
               CheckPermissions(permissionsToRequest, mainViewModel)

               AppNavHost()
            }
         }
      }
   }

   companion object {
      const val isInfo = true
      const val isDebug = true
      //12345678901234567890123
      private const val tag = "ok>MainActivity       ."

   }

   @Composable
   private fun CheckPermissions(
      permissionsToRequest: Array<String>,
      mainViewModel: MainViewModel
   ) {

      val activity = (LocalContext.current as? Activity)

      val multiplePermissionRequestLauncher = rememberLauncherForActivityResult(
         contract = ActivityResultContracts.RequestMultiplePermissions(),
         // callback: user
         onResult = { permissionMap: Map<String, @JvmSuppressWildcards Boolean> ->
            permissionsToRequest.forEach { permission ->
               logDebug(tag, "PermissionRequestLauncher onResult: $permission isGranted ${permissionMap[permission]}")
               mainViewModel.addPermission(
                  permission = permission,
                  isGranted = permissionMap[permission] == true  // key = true
               )
            }
         }
      )

      if (!arePermissionsAlreadyGranted(permissionsToRequest)) {
         // Request permissions, i.e. launch dialog
//         LaunchedEffect(true) {
//            LogFun(tag,"PermissionRequestLauncher launched")
//            multiplePermissionRequestLauncher.launch(
//               permissionsToRequest
//            )
//         }
         DisposableEffect(key1 = Unit) {
            val cameraPermission = Manifest.permission.CAMERA
            logDebug(tag, "PermissionRequestLauncher launched")
            multiplePermissionRequestLauncher.launch(
               permissionsToRequest
            )
            onDispose {
               // Clean up the disposable effect

            }
         }
      }

      // Any requested permission not granted
      // -> ask again or goto appsettings
      mainViewModel.permissionQueue
         .reversed()
         .forEach { permission ->
            logDebug(tag, "permissionQueue $permission")

            var dialogOpen by remember {  mutableStateOf(false) }

            val isPermanentlyDeclined =
               !shouldShowRequestPermissionRationale(permission)
            logDebug(tag, "permissionsQueue isPermanentlyDeclined $isPermanentlyDeclined")

            val permissionText: IPermissionText? = when (permission) {
               Manifest.permission.CAMERA -> PermissionCamera()
               Manifest.permission.RECORD_AUDIO -> PermissionRecordAudio()
               else -> null
            }
            logDebug(tag, "permissionsQueue ${permissionText?.getDescription(isPermanentlyDeclined)}")

            // https://semicolonspace.com/jetpack-compose-dialog/

            AlertDialog(
               modifier = Modifier,
               onDismissRequest = {
                  dialogOpen = false
               },
               // permission granted
               confirmButton = {
                  TextButton(
                     onClick = {
                        // perform the confirm action
                        logDebug(tag, "confirmButton() $permission")
                        mainViewModel.removePermission() // or viewModel::removeDialogFromQueue
                        // launched dialog again
                        multiplePermissionRequestLauncher.launch(
                           arrayOf(permission)
                        )
                        // close the dialog
                        dialogOpen = false
                     }
                  ) {
                     Text(text = "Zustimmen")
                  }
               },
               dismissButton = {
                  TextButton(
                     onClick = {
                        // perform the confirm action
                        logDebug(tag, "dismissButton() $permission")
                        if (! isPermanentlyDeclined) {
                           mainViewModel.removePermission()
                           // relaunch dialog again
                           multiplePermissionRequestLauncher.launch(
                              arrayOf(permission)
                           )
                        } else {
                           logDebug(tag, "openAppSettings() $permission and exit the app")
                           openAppSettings()
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
   private fun arePermissionsAlreadyGranted(
      permissionsToRequest: Array<String>
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



/*
PermissionDialog(
   permissionText = when (permission) {
      Manifest.permission.CAMERA -> PermissionCamera()
      Manifest.permission.RECORD_AUDIO -> PermissionRecordAudio()
      else -> return@forEach
   },
   onConfirm = {
      LogFun(tag, "onConfirm() $permission")
      viewModel.removePermission() // or viewModel::removeDialogFromQueue
      multiplePermissionRequestLauncher.launch(
         arrayOf(permission)
      )
   },
   onDismiss = {  // viewModel::removeDialogFromQueue,
      LogFun(tag, "onDismiss() $permission")
      viewModel.removePermission()
   },
   isPermanentlyDeclined =
      !shouldShowRequestPermissionRationale(permission),
   onGoToAppSettingsClick = {
      LogFun(tag, "openAppSettings() $permission")
      openAppSettings()
   } //::openAppSettings
)
*/
