package de.rogallab.mobile.ui.people

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import de.rogallab.mobile.R
import de.rogallab.mobile.domain.model.Person
import de.rogallab.mobile.ui.navigation.NavScreen
import de.rogallab.mobile.domain.utilities.logDebug
import de.rogallab.mobile.ui.composables.ShowErrorMessage
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PeopleListScreen(
   navController: NavController,
   viewModel: PeopleViewModel
) {

   val tag = "ok>PeopleListScreen   ."

   val snackbarHostState = remember { SnackbarHostState() }

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text(stringResource(R.string.people_list)) },
            navigationIcon = {
               val activity = LocalContext.current as Activity
               IconButton(
                  onClick = {
                     logDebug(tag, "Lateral Navigation: finish app")
                     // Finish the app
                     activity.finish()
                  }) {
                  Icon(imageVector = Icons.Default.Menu,
                     contentDescription = stringResource(R.string.back))
               }
            }
         )
      },
      floatingActionButton = {
         FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.tertiary,
            onClick = {
               logDebug(tag, "Forward Navigation: FAB clicked")
               // Navigate to 'PersonDetail' destination and put 'PeopleList' on the back stack
               navController.navigate(
                  route = NavScreen.PersonInput.route
               )
            }
         ) {
            Icon(Icons.Default.Add, "Add a contact")
         }
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

         LazyColumn(
            modifier = Modifier
               .padding(top = innerPadding.calculateTopPadding())
               .padding(bottom = innerPadding.calculateBottomPadding())
               .padding(horizontal = 8.dp),
            state = rememberLazyListState()
         ) {
            items(
               items = viewModel.people.toList(),
               key = { person: Person -> person.id }
            ) { person ->
               PersonListItem(
                  id = person.id,
                  firstName = person.firstName,
                  lastName = person.lastName,
                  email = person.email,
                  phone = person.phone,
                  imagePath = person.imagePath ?: "",
                  onClick = { id -> // Event ↑  Row(task.id)
                     logDebug(tag, "Forward Navigation: Item clicked")
                     // Navigate to 'PersonDetail' destination and put 'PeopleList' on the back stack
                     navController.navigate(
                        route = NavScreen.PersonDetail.route + "/$id"
                     )
                  },
               ) // end personListItem
            }
            //viewModel.onErrorMessage("Fehler in LazyColumn", "PeopleListScreen")
         }
      }
   ) // end Scaffold

   // testing the snackbar
   // viewModel.onErrorMessage("Test SnackBar: Fehlermeldung ...","PeopleListScreen")

   viewModel.errorMessage?.let{
      if(viewModel.errorFrom == "PeopleListScreen" ) {
         LaunchedEffect(it) {
            ShowErrorMessage(
               snackbarHostState = snackbarHostState,
               errorMessage = it,
               actionLabel = "Abbrechen",
               onErrorAction = { viewModel.onErrorAction() }
            )
         }
         viewModel.onErrorMessage(null, null)
      }
   }
}

@Composable
fun PersonListItem(
   id: UUID,
   firstName: String,
   lastName: String,
   email: String?,
   phone: String?,
   imagePath: String?,
   onClick: (UUID) -> Unit    // Event ↑  Person
) {
   //12345678901234567890123
   val tag = "ok>PersonListItem     ."
   logDebug(tag, "Person: $firstName $lastName")

   var checked: Boolean by rememberSaveable { mutableStateOf(false) }

   Column {

      Row(
         verticalAlignment = Alignment.CenterVertically,
         modifier = Modifier
            .clickable {
               logDebug(tag, "Row onClick()")
               onClick(id)  // Event ↑
            }
      ) {
         Row {
            Column(modifier = Modifier.weight(0.85f)) {
               Text(
                  text = "$firstName $lastName",
                  style = MaterialTheme.typography.bodyLarge,
               )
               email?.let {
                  Text(
                     modifier = Modifier.padding(top = 4.dp),
                     text = it,
                     style = MaterialTheme.typography.bodyMedium
                  )
               }
               phone?.let {
                  Text(
                     text = phone,
                     style = MaterialTheme.typography.bodyMedium,
                     modifier = Modifier
                  )
               }
            }
            Column(modifier = Modifier.weight(0.15f)) {
               imagePath?.let { path: String ->                  // State ↓
                  AsyncImage(
                     model = path,
                     contentDescription = "Bild der Person",
                     modifier = Modifier
                        .size(width = 60.dp, height = 75.dp)
                        .clip(RoundedCornerShape(percent = 5)),
                     alignment = Alignment.Center,
                     contentScale = ContentScale.Crop
                  )
               }
            }
         }
      }
      Divider(modifier = Modifier.padding(vertical = 8.dp))
   }
}