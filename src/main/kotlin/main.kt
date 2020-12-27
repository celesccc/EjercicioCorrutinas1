import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

var cubosActuales = 0
var lenaActual = 0
var ramasActuales = 0
var comidaActual = 0

const val CUBOS_NECESARIOS = 4
const val LENA_NECESARIA  = 2
const val RAMA_NECESARIA  = 1
const val COMIDA_NECESARIA  = 1

val mutex = Mutex()

fun main(){
    comenzar()
    Thread.sleep(80000)
}

fun comenzar() {
    println("***** LLEGAMOS A UNA ISLA DESIERTA *****")
    amigoA()
    amigoB()
    amigoC()
}

fun hamaca(nombre: String, tiempo: Long) {
    runBlocking {
        mutex.withLock {
            println("El amigo $nombre, quiere descansar")
            println("El amigo $nombre, se tumba en la hamaca")
            delay(tiempo)
            println("El amigo $nombre, se levanta de la hamaca")
            println("El amigo $nombre, deja de descansar")
        }
    }
}

fun hacha(nombre: String, tiempo: Long) {
    runBlocking {
        mutex.withLock {
            println("El amigo $nombre coge el hacha")
            delay(tiempo)
            println("El amigo $nombre deja el hacha")
        }
    }
}

fun amigoA() {
    GlobalScope.launch {
        while(cubosActuales != CUBOS_NECESARIOS) {
            println("El amigo A va a por un cubo de agua")
            delay(4000)
            println("El amigo A vuelve con un cubo de agua")
            cubosActuales++
            hamaca("A", 1000)
        }
        final()
    }
}

fun amigoB() {
    GlobalScope.launch {
        while(lenaActual != LENA_NECESARIA) {
            println("El amigo B va a por leña")
            hacha("B", 5000)
            delay(5000)
            lenaActual++
            hamaca("B", 3000)
        }
        final()
    }
}

fun amigoC() {
    GlobalScope.launch {
        while (ramasActuales != RAMA_NECESARIA && comidaActual != COMIDA_NECESARIA) {
            println("El amigo C va a por ramas")
            delay(3000)
            println("El amigo C vuelve con ramas")
            ramasActuales++
            println("El amigo C va a cazar")
            hacha("C", 4000)
            comidaActual++
        }
        final()
    }
}

fun final() {
    if (cubosActuales == CUBOS_NECESARIOS && lenaActual == LENA_NECESARIA && ramasActuales == RAMA_NECESARIA && comidaActual == COMIDA_NECESARIA)
        println("Barca construida y aprovisionada con éxito :)")
}
