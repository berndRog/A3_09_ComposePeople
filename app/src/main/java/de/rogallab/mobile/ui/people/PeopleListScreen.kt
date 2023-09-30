package de.rogallab.mobile.ui.people

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.rogallab.mobile.ui.navigation.NavScreen
import de.rogallab.mobile.R
import de.rogallab.mobile.domain.model.Person
import de.rogallab.mobile.ui.composables.ShowErrorMessage
import de.rogallab.mobile.domain.utilities.logDebug
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PeopleListScreen(
   navController: NavController,
   viewModel: PeopleViewModel,
) {

   val tag: String = "ok>ContactsHomeScreen ."
// viewModel.onErrorMessage("Test SnackBar: Fehlermeldung ...")

   val snackbarHostState = remember { SnackbarHostState() }
   val coroutineScope = rememberCoroutineScope()

   ShowErrorMessage(
      snackbarHostState = snackbarHostState,           // State ↓
      coroutineScope = coroutineScope,                 // State ↓
      errorMessage = viewModel.errorMessage,           // State ↓
      actionLabel = "Abbrechen",                       // State ↓
      onErrorDismiss = { viewModel.onErrorDismiss() }, // Event ↑
      onErrorAction = { viewModel.onErrorAction() },   // Event ↑
   )

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            navigationIcon = {
               val activity = (LocalContext.current as? Activity)
               IconButton(
                  onClick = {
                     logDebug(tag, "MENU clicked: finish app")
                     // Show navigation menu or navigation drawer
                     activity?.finish()
                  }
               ) {
                  Icon(imageVector = Icons.Default.Menu,
                     contentDescription = stringResource(R.string.back))
               }
            }
         )
      },

      floatingActionButton = {
         FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.secondary,
            onClick = {
               logDebug(tag, "FAB clicked: Add a contact")
               navController.navigate(NavScreen.PersonInput.route)
            }
         ) {
            Icon(Icons.Default.Add, "Add a contact")
         }
      },
      floatingActionButtonPosition = FabPosition.End,

      snackbarHost = {
         SnackbarHost(hostState = snackbarHostState) { data ->
            Snackbar(
               //modifier =  Modifier.border(2.dp, MaterialTheme.colors.secondary),
               snackbarData = data,
               actionOnNewLine = true
            )
         }
      },

      content = { innerPadding ->
         logDebug(tag, "Scaffold")

         Column(
            modifier = Modifier
               .fillMaxSize()
               .padding(horizontal = 8.dp)
               .padding(bottom = innerPadding.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
         ) {
            Text(
               text = "EMPTY HOMESCREEN",
               style = MaterialTheme.typography.titleLarge
            )

            LazyColumn(
               modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
               state = rememberLazyListState()
            ) {
               items(
                  items = viewModel.people.toList(),
                  key = { person: Person -> person.id }
               ) { person ->

                  PersonListItem(
                     id = person.id,
                     name = "${person.firstName} {person.lastName} ",
                     email = person.email ?: "",
                     phone = person.phone ?: "",
                     imagePath = person.imagePath ?: "",
                     onClick = { id -> // Event ↑  Row(task.id)
                        navController.navigate(
                           route = NavScreen.PersonDetail.route + "/$id"
                        )
                     },
                  )
               }
            }
         }
      }
   )
}

@Composable
fun PersonListItem(
   id: UUID,
   name: String,
   email: String,
   phone: String,
   imagePath: String,
   onClick: (UUID) -> Unit    // Event ↑  Person
) {
   //12345678901234567890123
   val tag = "ok>PersonListItem     ."
   //LogComp(tag, "Task: ${id.as8()}")

   // import androidx.compose.runtime.getValue
   // import androidx.compose.runtime.setValue
   var checked: Boolean by rememberSaveable { mutableStateOf(false) }

   Column() {

      Row(
         verticalAlignment = Alignment.CenterVertically,
         modifier = Modifier
            .clickable {
               logDebug(tag, "Row onClick()")
               onClick(id)  // Event ↑
            }
      ) {

         Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier

         )

      }

      Divider()
   }
}
