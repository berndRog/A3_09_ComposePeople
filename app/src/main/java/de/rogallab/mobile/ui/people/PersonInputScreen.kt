package de.rogallab.mobile.ui.people

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.rogallab.mobile.R
import de.rogallab.mobile.ui.composables.InputNameMailPhone
import de.rogallab.mobile.ui.composables.SelectAndShowImage
import de.rogallab.mobile.ui.composables.ShowErrorMessage
import de.rogallab.mobile.ui.navigation.NavScreen
import de.rogallab.mobile.ui.theme.AppTheme
import de.rogallab.mobile.domain.utilities.logDebug

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonInputScreen(
   navController: NavController,
   viewModel: PeopleViewModel,

) {

   val tag: String = "ok>PersonInputScreen  ."

   // testing the snackbar
   // viewModel.onErrorMessage("Test SnackBar: Fehlermeldung ...")

   val snackbarHostState = remember { SnackbarHostState() }
   val coroutineScope = rememberCoroutineScope()

   AppTheme {

      // https://stackoverflow.com/questions/72926359/show-snackbar-in-material-design-3-using-scaffold

      Scaffold(
         topBar = {
            TopAppBar(
               title = { Text(stringResource(R.string.person_input)) },
               navigationIcon = {
                  IconButton(onClick = {
                     navController.navigate(route = NavScreen.PeopleList.route) {
                        popUpTo(route = NavScreen.PeopleList.route) { inclusive = true }
                     }
                  }) {
                     Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back))
                  }
               }
            )
         },
         snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
               Snackbar(
                  snackbarData = data,
                  actionOnNewLine = true
               )
            }
         },
         content = { innerPadding ->

            Column(
               modifier = Modifier
                  .padding(top = innerPadding.calculateTopPadding())
                  .padding(horizontal = 8.dp)
                  .fillMaxWidth()
                  .verticalScroll(
                     state = rememberScrollState(),
                     enabled = true,
                     reverseScrolling = true
                  )
            ) {

               InputNameMailPhone(
                  firstName = viewModel.firstName,                          // State ↓
                  onFirstNameChange = { viewModel.onFirstNameChange(it) },  // Event ↑
                  lastName = viewModel.lastName,                            // State ↓
                  onLastNameChange = { viewModel.onLastNameChange(it) },    // Event ↑
                  email = viewModel.email,                                  // State ↓
                  onEmailChange = { viewModel.onEmailChange(it) },          // Event ↑
                  phone = viewModel.phone,                                  // State ↓
                  onPhoneChange = { viewModel.onPhoneChange(it) }           // Event ↑
               )

               SelectAndShowImage(
                  imagePath = viewModel.imagePath,                          // State ↓
                  onImagePathChanged = { viewModel.onImagePathChange(it) }  // Event ↑
               )

               Button(
                  modifier = Modifier
                     .fillMaxWidth(),
                  onClick = {
                     logDebug(tag, "onClickHandler()")
                     val id = viewModel.add()
                     navController.navigate(route = NavScreen.PeopleList.route) {
                        popUpTo(route = NavScreen.PeopleList.route) { inclusive = true }
                     }
                  }
               ) {
                  Text(
                     style = MaterialTheme.typography.bodyLarge,
                     text = stringResource(R.string.save)
                  )
               }
            }

            ShowErrorMessage(
               snackbarHostState = snackbarHostState,           // State ↓
               coroutineScope = coroutineScope,                 // State ↓
               errorMessage = viewModel.errorMessage,           // State ↓
               actionLabel = "Abbrechen",                       // State ↓
               onErrorDismiss = { viewModel.onErrorDismiss() }, // Event ↑
               onErrorAction = { viewModel.onErrorAction() },   // Event ↑
            )

         }
      )
   }
}