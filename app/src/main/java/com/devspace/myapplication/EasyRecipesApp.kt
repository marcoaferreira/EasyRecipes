package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspace.myapplication.list.presentation.RecipesViewModel
import com.devspace.myapplication.list.presentation.ui.MainScreen
import com.devspace.myapplication.list.presentation.ui.OnboardingScreen
import com.devspace.myapplication.list.presentation.ui.SearchRecipesScreen


@Composable
fun EasyRecipiesApp(
    listViewModel: RecipesViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboarding_screen" ){
        composable(route = "onboarding_screen"){
            OnboardingScreen(navController)
        }
        composable(route = "main_screen"){
            MainScreen(navController, listViewModel)
        }

        composable(
            route = "recipe_detail" + "/{itemId}",
            arguments = listOf(navArgument("itemId"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            RecipeDetailScreen(id, navController)
        }
        composable(
            route = "search_recipes" + "/{query}",
            arguments = listOf(navArgument("query"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(id, navController)
        }
    }
}