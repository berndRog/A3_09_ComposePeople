package de.rogallab.mobile

interface IPermissionText {
   fun getDescription(isPermanentlyDeclined: Boolean): String
}