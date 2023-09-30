package de.rogallab.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.rogallab.mobile.ui.people.PeopleListScreen
import de.rogallab.mobile.ui.people.PeopleViewModel
import de.rogallab.mobile.ui.people.PersonDetailScreen
import de.rogallab.mobile.ui.people.PersonInputScreen
import de.rogallab.mobile.domain.utilities.logDebug
import java.util.UUID

@Composable
fun AppNavHost(
   peopleViewModel: PeopleViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
   val tag ="ok>AppNavHost         ."

   val navHostController: NavHostController = rememberNavController()

// https://medium.com/androiddevelopers/animations-in-navigation-compose-36d48870776b

   NavHost(
      navController = navHostController,
      startDestination = NavScreen.PeopleList.route
   ) {
      composable(
         route = NavScreen.PeopleList.route,
      ) {
         logDebug(tag, "PeopleListScreen()")
         PeopleListScreen(
            navController = navHostController,
            viewModel = PeopleViewModel()
         )
      }

      composable(
         route = NavScreen.PersonInput.route,
      ) {
         logDebug(tag, "PersonInputScreen()")
         PersonInputScreen(
            navController = navHostController,
            viewModel = PeopleViewModel()
         )
      }

      composable(
         route = NavScreen.PersonDetail.route + "/{personId}",
         arguments = listOf(navArgument("personId") { type = NavType.StringType})
      ) { backStackEntry ->
         val id = backStackEntry.arguments?.getString("personId")?.let{
            UUID.fromString(it)
         }
         logDebug(tag, "PersonDetailScreen() id=$id")
         PersonDetailScreen(
            id = id,
            navController = navHostController,
            viewModel = peopleViewModel
         )
      }
   }
}