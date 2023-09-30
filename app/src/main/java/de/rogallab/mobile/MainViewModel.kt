package de.rogallab.mobile

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import de.rogallab.mobile.domain.utilities.logDebug

class MainViewModel: ViewModel() {

    val permissionQueue: SnapshotStateList<String> = mutableStateListOf()

    fun addPermission(
        permission: String,
        isGranted: Boolean
    ) {
        logDebug(_tag,"addPermission $permission $isGranted")
        if (isGranted || permissionQueue.contains(permission)) return
        permissionQueue.add(permission)
    }

    fun removePermission() {
        logDebug(_tag,"removePermission ${permissionQueue.size}")
        permissionQueue.removeFirst()
    }

    companion object {
        private val _tag:String = "ok>MainViewModel      ."
    }

}