package de.rogallab.mobile.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import de.rogallab.mobile.AppStart
import de.rogallab.mobile.ui.navigation.AppNavHost
import de.rogallab.mobile.ui.people.PeopleViewModel
import de.rogallab.mobile.ui.permissions.RequestPermissions
import de.rogallab.mobile.ui.theme.AppTheme

class MainActivity : BaseActivity(tag) {

   private val _mainViewModel by viewModels<MainViewModel>()
   private val _peopleViewModel by viewModels<PeopleViewModel>()

   // required permissions
   private val permissionsToRequest = arrayOf(
      Manifest.permission.CAMERA
//    Manifest.permission.RECORD_AUDIO
   )

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      // initialize peoples from seed
      _peopleViewModel.initialize(AppStart.seed)

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
      private const val tag = "ok>MainActivity       ."
   }
}

// static extension function for Activity
fun Activity.openAppSettings() {
   Intent(
      Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
      Uri.fromParts("package", packageName, null)
   ).also(::startActivity)
}