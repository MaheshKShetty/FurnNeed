package com.example.myapplication.login

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.custom.CustomButton
import com.example.myapplication.custom.CustomTextField
import com.example.myapplication.custom.CustomToolBar
import com.example.myapplication.helper.NavigationItems
import com.example.myapplication.helper.Utils
import com.example.myapplication.helper.Utils.isValidEmail
import com.example.myapplication.helper.Utils.isValidPassword
import com.example.myapplication.helper.Utils.validatePassword
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.theme.titleColor

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }
    val modifierPadding = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)

    val context = LocalContext.current
    var emailError by remember { mutableStateOf(false) }
    var emailErrorMessage by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordErrorMessage by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CustomToolBar(
                    navHostController = navHostController,
                    modifier = modifier,
                    title = "Login"
                )
            }
        ) { innerPadding ->
            Column(modifier = modifier.fillMaxSize().padding(innerPadding), verticalArrangement = Arrangement.Top) {
                Text(
                    text = context.getString(R.string.signup_desc),
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 36.dp),
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    color = titleColor
                )

                CustomTextField(
                    modifier = modifierPadding,
                    label = "Enter your Email",
                    isError = emailError,
                    errorText = emailErrorMessage,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = {
                        email = it
                        emailError = false
                        emailErrorMessage = null
                    }
                )

                CustomTextField(
                    modifier = modifierPadding,
                    label = "Password",
                    isPassword = true,
                    isError = passwordError,
                    errorText = passwordErrorMessage,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = {
                        password = it
                        passwordError = false
                        passwordErrorMessage = null
                    }
                )

                CustomButton(
                    text = context.resources.getString(R.string.login),
                    onClick =  {
                        if (!isValidEmail(email)) {
                            emailError = true
                            emailErrorMessage = context.resources.getString(R.string.email_error)
                        }
                        if (!isValidPassword(password)) {
                            passwordError = true
                            passwordErrorMessage = validatePassword(password)
                        }
                        if (!emailError && !passwordError) {
                            navHostController.navigate(NavigationItems.HOME.route)
                        }
                    },
                    modifier = modifierPadding
                        .fillMaxWidth()
                        .wrapContentHeight(),
                )
            }
        }
    }
}