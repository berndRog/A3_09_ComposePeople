package de.rogallab.mobile.ui.composables.permissions;

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
