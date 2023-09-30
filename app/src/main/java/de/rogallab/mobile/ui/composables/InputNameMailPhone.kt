package de.rogallab.mobile.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import de.rogallab.mobile.R
import de.rogallab.mobile.domain.utilities.logDebug

@Composable
fun InputNameMailPhone(
   firstName: String,                    // State ↓
   onFirstNameChange: (String) -> Unit,  // Event ↑
   lastName: String,                     // State ↓
   onLastNameChange: (String) -> Unit,   // Event ↑
   email: String?,                       // State ↓
   onEmailChange: (String) -> Unit,      // Event ↑
   phone: String?,                       // State ↓
   onPhoneChange: (String) -> Unit       // Event ↑
) {

   val tag = "ok>InputNameMailPhone ."
   logDebug(tag, "Start")

   OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = firstName,
      onValueChange = { onFirstNameChange(it) },
      label = { Text(text = stringResource(R.string.firstName)) },
      textStyle = MaterialTheme.typography.bodyMedium,
      leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "First Name") },
      singleLine = true,
   )

   OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = lastName,
      onValueChange = { onLastNameChange(it) },
      label = { Text(text = stringResource(R.string.lastName)) },
      textStyle = MaterialTheme.typography.bodyMedium,
      leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "Last Name") },
      singleLine = true,
   )

   OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = email ?: "",
      onValueChange = { onEmailChange(it) },
      label = { Text(text = stringResource(R.string.email)) },
      textStyle = MaterialTheme.typography.bodyMedium,
      keyboardOptions = KeyboardOptions(
         keyboardType = KeyboardType.Email
      ),
      leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "Email") },
      singleLine = true
   )

   OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = phone ?: "",
      onValueChange = { onPhoneChange(it) },
      label = { Text(text = stringResource(R.string.phone)) },
      textStyle = MaterialTheme.typography.bodyMedium,
      leadingIcon = { Icon(imageVector = Icons.Outlined.Phone, contentDescription = "Phone") },
      keyboardOptions = KeyboardOptions(
         keyboardType = KeyboardType.Phone
      ),
      singleLine = true
   )
}