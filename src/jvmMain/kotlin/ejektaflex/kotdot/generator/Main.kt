package ejektaflex.kotdot.generator

import com.squareup.kotlinpoet.asTypeName
import ejektaflex.kotdot.generator.json.reg.ClassRegistry
import ejektaflex.kotdot.generator.json.reg.TypeRegistry
import kotlin.reflect.jvm.jvmErasure

fun main() {

    fun describeClass(named: String) {
        val nodeClazz = ClassRegistry[named]!!
        println("Generated Class Data:")
        println(nodeClazz.generate())
        println("(Total Methods      : ${nodeClazz.methods.size})")
        println("(Essential Methods  : ${nodeClazz.essentialMethods.size})")
        println("(Methods            : ${nodeClazz.essentialMethods.map { it.name }})")
        println("(Num Properties     : ${nodeClazz.properties.size})")
        println("(Properties         : ${nodeClazz.properties.map { it.name }}")
        println("(Class Name         : ${nodeClazz.name})")
        println("(Superclasses       : ${nodeClazz.superclasses.joinToString("->") { it.name }})")
    }

    describeClass("GIProbe")

    for (item in TypeRegistry) {
        println("${item.key} -> ${item.value}")
    }

}