package de.rogallab.mobile

import android.app.Application
import android.content.res.Resources
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import de.rogallab.mobile.domain.utilities.Seed
import de.rogallab.mobile.domain.utilities.logDebug
import de.rogallab.mobile.domain.utilities.logInfo
import java.io.Closeable

class MainViewModel(
   private val _application: Application
): AndroidViewModel(_application) {

   private lateinit var _resources: Resources

   val seed = Seed()

   val permissionQueue: SnapshotStateList<String> = mutableStateListOf()

   fun initializeImages(
      resources: Resources
   ) {
      _resources = resources
      val context = _application.applicationContext
      seed.initializeImages(context, _resources)
   }

   override fun onCleared() {
      super.onCleared()
      logInfo(tag,"onCleared()")
      seed.disposeImages()
   }

    fun addPermission(
        permission: String,
        isGranted: Boolean
    ) {
        logDebug(tag,"addPermission $permission $isGranted")
        if (isGranted || permissionQueue.contains(permission)) return
        permissionQueue.add(permission)
    }

    fun removePermission() {
        logDebug(tag,"removePermission ${permissionQueue.size}")
        permissionQueue.removeFirst()
    }

    companion object {
        private const val tag:String = "ok>MainViewModel      ."
    }
}