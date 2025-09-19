package com.adotransfer.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adotransfer.ui.screens.*

@Composable
fun MoneyTransferNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("dashboard") {
            DashboardScreen(
                onNavigateToTransfer = {
                    navController.navigate("transfer")
                },
                onNavigateToHistory = {
                    navController.navigate("history")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                }
            )
        }
        
        composable("transfer") {
            TransferScreen(
                onTransferSuccess = {
                    navController.popBackStack()
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("history") {
            TransactionHistoryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("profile") {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
