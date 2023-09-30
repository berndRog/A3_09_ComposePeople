package de.rogallab.mobile.ui.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import de.rogallab.mobile.domain.utilities.logDebug
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ShowErrorMessage(
   snackbarHostState: SnackbarHostState, // State ↓
   coroutineScope: CoroutineScope,       // State ↓
   errorMessage: String?,                // State ↓
   actionLabel: String?,                 // State ↓
   onErrorDismiss: () -> Unit,           // Event ↑
   onErrorAction: () -> Unit             // Event ↑
) {

   val tag = "ok>ShowErrorMessage   ."
   logDebug(tag, "Start")

   errorMessage?.let { message ->
      coroutineScope.launch {
         logDebug(tag,"launch Snackbar")
         val snackbarResult = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Long
         )
         when (snackbarResult) {
            SnackbarResult.Dismissed       -> {
               logDebug(tag,"SnackbarResult.Dismissed")
               onErrorDismiss()
            }
            SnackbarResult.ActionPerformed -> {
               logDebug(tag,"SnackbarResult.ActionPerformed")
               onErrorAction()
            }
         }
      }
   }
}