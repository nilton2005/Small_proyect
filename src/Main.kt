// Clase abstracta que representa un repuesto de auto
abstract class AutoPart(val name: String, val price: Double, var quantity: Int) {
    abstract fun partDetails(): String
}

// Clase que representa un repuesto eléctrico
class ElectricalPart(name: String, price: Double, quantity: Int, val voltage: Double) : AutoPart(name, price, quantity) {
    override fun partDetails(): String {
        return "Repuesto Eléctrico: $name, Precio: $$price, Cantidad: $quantity, Voltaje: $voltage V"
    }
}

// Clase que representa un repuesto mecánico
class MechanicalPart(name: String, price: Double, quantity: Int, val weight: Double) : AutoPart(name, price, quantity) {
    override fun partDetails(): String {
        return "Repuesto Mecánico: $name, Precio: $$price, Cantidad: $quantity, Peso: $weight kg"
    }
}

// Interfaz que define la estructura para buscar repuestos
interface PartSearchable {
    fun searchPart(name: String): AutoPart?
}

// Clase genérica que maneja el inventario de repuestos
class Inventory<T : AutoPart> : PartSearchable {
    private val parts = mutableListOf<T>()

    // Agregar un repuesto al inventario
    fun addPart(part: T) {
        parts.add(part)
        println("${part.name} ha sido agregado al inventario.")
    }

    // Buscar un repuesto por nombre
    override fun searchPart(name: String): AutoPart? {
        return parts.find { it.name.equals(name, ignoreCase = true) }
    }

    // Eliminar un repuesto por nombre
    fun removePart(name: String) {
        val part = searchPart(name)
        if (part != null) {
            parts.remove(part)
            println("$name ha sido eliminado del inventario.")
        } else {
            println("No se encontró el repuesto con el nombre $name.")
        }
    }

    // Listar todos los repuestos
    fun listParts() {
        if (parts.isEmpty()) {
            println("El inventario está vacío.")
        } else {
            println("Repuestos en el inventario:")
            parts.forEach { println(it.partDetails()) }
        }
    }

    // Calcular el valor total del inventario
    fun calculateTotalValue(): Double {
        return parts.sumOf { it.price * it.quantity }
    }
}

// Clase que maneja la interacción con el usuario
class AutoPartsStore(private val inventory: Inventory<AutoPart>) {

    fun start() {
        var option: Int
        do {
            println("\n--- Menú de Tienda de Repuestos de Autos ---")
            println("1. Agregar repuesto eléctrico")
            println("2. Agregar repuesto mecánico")
            println("3. Buscar repuesto")
            println("4. Eliminar repuesto")
            println("5. Listar repuestos")
            println("6. Calcular valor total del inventario")
            println("7. Salir")
            print("Selecciona una opción: ")
            option = readLine()?.toIntOrNull() ?: 0

            when (option) {
                1 -> addElectricalPartMenu()
                2 -> addMechanicalPartMenu()
                3 -> searchPartMenu()
                4 -> removePartMenu()
                5 -> inventory.listParts()
                6 -> println("El valor total del inventario es: $${inventory.calculateTotalValue()}")
                7 -> println("Saliendo de la aplicación...")
                else -> println("Opción inválida. Intenta de nuevo.")
            }
        } while (option != 7)
    }

    // Menú para agregar un repuesto eléctrico
    private fun addElectricalPartMenu() {
        try {
            println("Agregar un nuevo repuesto eléctrico:")
            print("Nombre del repuesto: ")
            val name = readLine().orEmpty()
            print("Precio del repuesto: ")
            val price = readLine()?.toDoubleOrNull() ?: throw IllegalArgumentException("El precio debe ser un número.")
            print("Cantidad en stock: ")
            val quantity = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("La cantidad debe ser un número.")
            print("Voltaje del repuesto: ")
            val voltage = readLine()?.toDoubleOrNull() ?: throw IllegalArgumentException("El voltaje debe ser un número.")
            val newPart = ElectricalPart(name, price, quantity, voltage)
            inventory.addPart(newPart)
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    // Menú para agregar un repuesto mecánico
    private fun addMechanicalPartMenu() {
        try {
            println("Agregar un nuevo repuesto mecánico:")
            print("Nombre del repuesto: ")
            val name = readLine().orEmpty()
            print("Precio del repuesto: ")
            val price = readLine()?.toDoubleOrNull() ?: throw IllegalArgumentException("El precio debe ser un número.")
            print("Cantidad en stock: ")
            val quantity = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("La cantidad debe ser un número.")
            print("Peso del repuesto (en kg): ")
            val weight = readLine()?.toDoubleOrNull() ?: throw IllegalArgumentException("El peso debe ser un número.")
            val newPart = MechanicalPart(name, price, quantity, weight)
            inventory.addPart(newPart)
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    // Menú para buscar un repuesto
    private fun searchPartMenu() {
        print("Buscar repuesto por nombre: ")
        val name = readLine().orEmpty()
        val part = inventory.searchPart(name)
        if (part != null) {
            println("Repuesto encontrado: ${part.partDetails()}")
        } else {
            println("No se encontró el repuesto con el nombre $name.")
        }
    }

    // Menú para eliminar un repuesto
    private fun removePartMenu() {
        print("Eliminar repuesto por nombre: ")
        val name = readLine().orEmpty()
        inventory.removePart(name)
    }
}

// Función principal
fun main() {
    val inventory = Inventory<AutoPart>()
    val store = AutoPartsStore(inventory)
    store.start()
}
