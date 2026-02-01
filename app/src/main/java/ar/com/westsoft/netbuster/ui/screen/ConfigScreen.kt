package ar.com.westsoft.netbuster.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConfigScreen(
    initialAuthMode: Boolean,
    onSavePushed: (authMode: Boolean, password: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        var authenticationMode by remember { mutableStateOf(initialAuthMode) }
        var password by remember { mutableStateOf("") }
        var repeatPassword by remember { mutableStateOf("") }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = authenticationMode,
                onCheckedChange = { authenticationMode = authenticationMode.not()}
            )
            Text("Authentication mode")
        }
        OutlinedTextField(
            enabled = authenticationMode,
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                enabled = authenticationMode,
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                label = { Text("Repeat") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id =
                    if (password == repeatPassword && password.isNotEmpty())
                        android.R.drawable.checkbox_on_background
                    else
                        android.R.drawable.checkbox_off_background),
                contentDescription = "Checkbox"
            )
        }
        Button(
            enabled = authenticationMode.not() || password.isNotEmpty() && password == repeatPassword,
            onClick = {
                if (password == repeatPassword) {
                    onSavePushed(authenticationMode, password)
                }
            }
        ) {
            Text("Save")
        }
    }
}

@Preview(
    widthDp = 360,
    heightDp = 600,
    showBackground = true
)
@Composable
fun ConfigScreenPreview() = ConfigScreen(
    initialAuthMode = true,
    onSavePushed = { _, _ -> }
)
