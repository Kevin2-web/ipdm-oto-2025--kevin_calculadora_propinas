package com.example.calculadora

package com.example.calculadorapropinas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.calculadorapropinas.ui.theme.CalculadoraPropinasTheme
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraPropinasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaCalculadora()
                }
            }
        }
    }
}

@Composable
fun PantallaCalculadora() {
    var montoCuenta by remember { mutableStateOf("") }
    var porcentajePropina by remember { mutableStateOf("") }
    var redondearPropina by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val monto = montoCuenta.toDoubleOrNull() ?: 0.0
    val porcentaje = porcentajePropina.toDoubleOrNull() ?: 0.0

    var propina = (porcentaje / 100) * monto
    if (redondearPropina) propina = ceil(propina)

    val propinaFormateada = NumberFormat.getCurrencyInstance().format(propina)

    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Calculadora de Propinas",
            style = MaterialTheme.typography.headlineMedium
        )
        EditNumberField(
            label = "Monto de la cuenta",
            valor = montoCuenta,
            onValorCambiado = { montoCuenta = it },
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
            onDone = { focusManager.clearFocus() }
        )
        EditNumberField(
            label = "Porcentaje de propina",
            valor = porcentajePropina,
            onValorCambiado = { porcentajePropina = it },
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            onDone = { focusManager.clearFocus() }
        )
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text("¿Redondear propina?")
            Switch(
                checked = redondearPropina,
                onCheckedChange = { redondearPropina = it },
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(
            text = "Propina: $propinaFormateada",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun EditNumberField(
    label: String,
    valor: String,
    onValorCambiado: (String) -> Unit,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    onDone: () -> Unit = {}
) {
    TextField(
        value = valor,
        onValueChange = onValorCambiado,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
