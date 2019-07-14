package ejektaflex.kotdot.generator

import ejektaflex.kotdot.generator.json.structure.ClassRegistry

fun main() {

    fun describeClass(named: String) {
        val nodeClazz = ClassRegistry[named]!!
        println("Generated Class Data:")
        println(nodeClazz.generate())
        println("(Essential Methods  : ${nodeClazz.essentialMethods.size})")
        println("(Total Methods      : ${nodeClazz.methods.size})")
        println("(Properties         : ${nodeClazz.properties.size})")
        println("(Base Class         : ${nodeClazz.name})")
        println("(Superclasses       : ${nodeClazz.superclasses.joinToString("->") { it.name }})")
    }

    describeClass("YSort")

}