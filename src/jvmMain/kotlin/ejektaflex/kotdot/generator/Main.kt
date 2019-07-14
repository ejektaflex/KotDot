package ejektaflex.kotdot.generator

import ejektaflex.kotdot.generator.json.structure.ClassRegistry

fun main() {

    val simpleNode = ClassRegistry["Node"]

    println(simpleNode!!.baseClassGodot)

    val nodeClazz = ClassRegistry.entries.first { it.value.name == "Node" }.value

    println(nodeClazz.generate())

    println("(Methods: ${nodeClazz.methods})")

}