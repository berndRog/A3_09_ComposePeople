package de.rogallab.mobile.ui.composables.permissions

class PermissionPhoneCall : IPermissionText {
	override fun getDescription(isPermanentlyDeclined: Boolean): String {
		return if (isPermanentlyDeclined) {
			"Es scheint als hätten Sie den Zugriff auf Anrufen mehrfach abgelehnt. " +
				"Sie können diese Entscheidung nur über die App Einstellungen ändern."
		} else {
			"This app needs access to your camera so that your friends " +
				"can see you in a call."
		}
	}
}
