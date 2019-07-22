package ejektaflex.kotdot.generator

import ejektaflex.kotdot.generator.json.reg.GDNClassRegistry

fun main() {

    /*
    fun describeClass(named: String) {
        val nodeClazz = ClassRegistry[named]!!
        println("Generated Class Data:")
        println(nodeClazz.generate())
        println("(Class Name         : ${nodeClazz.name})")
        println("(# Total Methods    : ${nodeClazz.methods.size})")
        println("(# Essential Methods: ${nodeClazz.essentialMethods.size})")
        println("(Essential Methods  : ${nodeClazz.essentialMethods.map { it.name }})")
        println("(# Properties       : ${nodeClazz.properties.size})")
        println("(Properties         : ${nodeClazz.properties.map { it.name }}")
        println("(Superclasses       : ${nodeClazz.superclasses.joinToString("->") { it.name }})")
    }

    describeClass("YSort")
     */

    val clazz = GDNClassRegistry["godot_vector2"]!!

    for (method in clazz.methods) {
        println(method.ktName)
    }

    println("###################\n")

    println(clazz.generate())

    /*
    for (item in TypeRegistry) {
        println("${item.key} -> ${item.value}")
    }
     */

}