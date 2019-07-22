package ejektaflex.kotdot.generator

import ejektaflex.kotdot.generator.json.reg.CoreClassRegistry
import ejektaflex.kotdot.generator.json.reg.GodotClassRegistry

fun main() {

    // Generates a String for the given class and prints useful class data
    fun describeClass(named: String) {
        val nodeClazz = GodotClassRegistry[named]!!
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


    // Generates a String for the given core object and prints useful data about it
    fun describeCore(named: String) {
        val clazz = CoreClassRegistry[named]!!

        for (method in clazz.methods) {
            println(method.ktName)
        }

        println("###################\n")

        println(clazz.generate())
    }


    //describeClass("Node")
    describeCore("godot_vector2")



}