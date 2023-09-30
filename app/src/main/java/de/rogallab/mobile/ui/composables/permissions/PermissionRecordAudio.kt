package de.rogallab.mobile.ui.composables.permissions

class PermissionRecordAudio : IPermissionText {
	override fun getDescription(isPermanentlyDeclined: Boolean): String {
		return if (isPermanentlyDeclined) {
			"It seems you permanently declined microphone permission. " +
				"You can go to the app settings to grant it."
		} else {
			"This app needs access to your microphone so that your friends " +
				"can hear you in a call."
		}
	}
}
