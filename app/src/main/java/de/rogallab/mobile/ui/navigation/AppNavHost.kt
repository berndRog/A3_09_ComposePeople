package de.rogallab.mobile.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
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
   peopleViewModel: PeopleViewModel,
) {
   val tag ="ok>AppNavHost         ."

   val navHostController: NavHostController = rememberNavController()

   // https://developer.android.com/jetpack/compose/animation/quick-guide
   val duration  = 1000

   NavHost(
      navController = navHostController,
      startDestination = NavScreen.PeopleList.route,
      enterTransition = { EnterTransition.None },
      exitTransition = { ExitTransition.None }
   ) {
      composable(
         route = NavScreen.PeopleList.route,
         enterTransition = { enterTransition(duration) },
         exitTransition  = { exitTransition(duration)  }
      ) {
         logDebug(tag, "PeopleListScreen()")
         PeopleListScreen(
            navController = navHostController,
            viewModel = peopleViewModel
         )
      }
      composable(
         route = NavScreen.PersonInput.route,
         enterTransition = { enterTransition(duration) },
         exitTransition  = { exitTransition(duration)  }
      ) {
         logDebug(tag, "PersonInputScreen()")
         PersonInputScreen(
            navController = navHostController,
            viewModel = peopleViewModel
         )
      }
      composable(
         route = NavScreen.PersonDetail.route + "/{personId}",
         arguments = listOf(navArgument("personId") { type = NavType.StringType}),
         enterTransition = { enterTransition(duration) },
         exitTransition  = { exitTransition(duration)  }
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

private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(
   duration: Int
) =
   fadeIn(animationSpec = tween(duration)) +
      slideIntoContainer(
         animationSpec = tween(duration),
         towards = AnimatedContentTransitionScope.SlideDirection.Left
      )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(
   duration: Int
) =
   fadeOut(animationSpec = tween(duration)) +
      slideOutOfContainer(
         animationSpec = tween(duration),
         towards = AnimatedContentTransitionScope.SlideDirection.Left
      )