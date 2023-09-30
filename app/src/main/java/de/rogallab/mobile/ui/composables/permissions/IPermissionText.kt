package de.rogallab.mobile.ui.composables.permissions

interface IPermissionText {
   fun getDescription(isPermanentlyDeclined: Boolean): String
}