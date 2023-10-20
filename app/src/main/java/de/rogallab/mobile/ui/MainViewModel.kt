package de.rogallab.mobile.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import de.rogallab.mobile.AppStart
import de.rogallab.mobile.domain.utilities.logDebug
import de.rogallab.mobile.domain.utilities.logInfo

class MainViewModel(
): ViewModel() {

   val permissionQueue: SnapshotStateList<String> = mutableStateListOf()

   override fun onCleared() {
      super.onCleared()
      logInfo(tag,"onCleared()")
      AppStart.seed.disposeImages()
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