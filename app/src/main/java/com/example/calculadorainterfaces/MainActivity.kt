package com.example.calculadorainterfaces

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadorainterfaces.ui.theme.CalculadoraInterfacesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraInterfacesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Principal()
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun Principal() {
    var resultadoTexto by rememberSaveable {
        mutableStateOf("0")
    }
    
    var cadena by rememberSaveable {
        mutableStateOf("")
    }


    var operadorTexto by rememberSaveable {
        mutableStateOf("")
    }

    fun EscribirOperando(caracter: String){
        cadena = cadena + caracter
    }

    fun EscribirOperador(caracter: String){
        if(cadena.isNotEmpty()){
            if(!" ".contains(cadena.last())){
                cadena = cadena + caracter
            }
        }
    }

    fun tienePrioridad(operador1: Char, operador2: Char): Boolean{
        return (operador2 == '+' || operador2 == '-') && (operador1 == '*' || operador1 == '/')
    }

    fun hacerOperacion(operando1: Double, operando2: Double, operador: Char): Double {
        return when (operador) {
            '+' -> operando1 + operando2
            '-' -> operando1 - operando2
            '*' -> operando1 * operando2
            '/' -> operando1 / operando2
            else -> throw IllegalArgumentException("Operador no válido: $operador")
        }
    }

    fun calcular(cadena: String){
        val tokens = cadena.split(" ")
        val listaNums = mutableListOf<Double>()
        val listaOperadores = mutableListOf<Char>()

        for(token in tokens){
            if(!token.isEmpty()){
                if (token.matches(Regex("\\d+(\\.\\d+)?"))) {
                    listaNums.add(token.toDouble())
                } else if(token.length == 1 && "+-*/".contains(token)){
                    while(listaOperadores.isNotEmpty() &&  tienePrioridad(token[0], listaOperadores.last())){
                        val operador = listaOperadores.removeAt(listaOperadores.size - 1)
                        val operando2 = listaNums.removeAt(listaNums.size -1)
                        val operando1 = listaNums.removeAt(listaNums.size -1)
                        val resultado =  hacerOperacion(operando1, operando2, operador)
                        listaNums.add(resultado)
                    }
                    listaOperadores.add(token[0])
                }
            }
        }

        while(listaOperadores.isNotEmpty()){
            val operator = listaOperadores.removeAt(listaOperadores.size - 1)
            val operand2 = listaNums.removeAt(listaNums.size - 1)
            val operand1 = listaNums.removeAt(listaNums.size - 1)
            val result = hacerOperacion(operand1, operand2, operator)
            listaNums.add(result)
        }

        if(listaNums.size != 1 || listaOperadores.isNotEmpty()){
            Toast.makeText(null, "Expresion no valida", Toast.LENGTH_SHORT).show()
        }

        resultadoTexto = listaNums[0].toString()


    }




    Column(Modifier.fillMaxSize()){
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(2f)
            .background(Color.White)){

            Column(Modifier.fillMaxWidth()) {
                Operaciones(cadena = cadena)
                Resultado(numero = resultadoTexto)
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .weight(4f)
                .background(Color.LightGray)){
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "1", {
                            EscribirOperando("1")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "2",  {
                            EscribirOperando("2")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "3",  {
                            EscribirOperando("3")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "CE", {
                            cadena = ""
                            operadorTexto = ""
                            resultadoTexto = "0"
                        })
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "4",  {
                            EscribirOperando("4")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "5", {
                            EscribirOperando("5")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "6", {
                            EscribirOperando("6")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "C", {
                            if(cadena != ""){
                                if(" ".contains(cadena.last())){
                                    cadena = cadena.substring(0, cadena.length - 1)
                                    cadena = cadena.substring(0, cadena.length - 1)
                                }
                                cadena = cadena.substring(0, cadena.length - 1)
                            }
                        })
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "7",  {
                            EscribirOperando("7")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "8", {
                            EscribirOperando("8")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "9",  {
                            EscribirOperando("9")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "0",  {
                            EscribirOperando("0")
                        })
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "+", {
                            EscribirOperador(" + ")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "-", {
                            EscribirOperador(" - ")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "*", {
                            EscribirOperador(" * ")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "/", {
                            EscribirOperador(" / ")
                        })
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)){
                        Boton(textoBoton = "=", {
                            if(cadena.isNotEmpty()){
                                if(!" ".contains(cadena.last())){
                                    calcular(cadena)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun Operaciones(cadena: String){
    Text(text = cadena, Modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize = 35.sp,
            color = Color.Black),
        textAlign = TextAlign.Start
    )
}

@Composable
fun Resultado(numero: String){
    Text(text = numero,
        Modifier
            .fillMaxWidth()
            .padding(top = 80.dp)
        ,
        style = TextStyle(
            fontSize = 45.sp),
        textAlign = TextAlign.End
    )

}

@Composable
fun Boton(textoBoton: String, onKeyPressed: () -> Unit){
    Button(modifier = Modifier
        .size(150.dp)
        .clip(CircleShape)
        .padding(5.dp)
        .shadow(11.dp, shape = CircleShape),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black ),
        onClick = { onKeyPressed()}) {
        Text(text = textoBoton, style = TextStyle(fontSize = 30.sp))
    }
}




