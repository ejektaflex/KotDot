package ejektaflex.kotdot.generator.json.core

import com.squareup.kotlinpoet.ClassName
import ejektaflex.kotdot.generator.json.reg.CTypeRegistry
import ejektaflex.kotdot.generator.json.reg.CoreClassRegistry

data class CoreArgument(val rawType: String, val name: String) {

    val typeMatcher = Regex("(const )*([a-z_0-9]+)( \\*)*")

    val trueName: String
        get() = name.substringAfter("p_")

    fun getRealTypeStr(): String {
        return typeMatcher.matchEntire(rawType)?.groupValues?.get(2)!!
    }

    fun resolveType(): ClassName {
        val realType = getRealTypeStr()
        return ClassName("godot", if (realType in CTypeRegistry.keys) {
            CTypeRegistry[realType].toString()
        } else {
            CoreClassRegistry[realType]!!.ktName
        })
    }

}