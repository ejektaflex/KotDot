package kotdot.generator.json.core

import com.squareup.kotlinpoet.ClassName
import kotdot.generator.json.reg.CTypeRegistry
import kotdot.generator.json.reg.CoreClassRegistry

data class CoreArgument(val rawType: String, val name: String) {

    val typeMatcher = Regex("(const )*([a-z_0-9]+)( \\*)*")

    val trueName: String
        get() {
            return if (name == "p_self" || name == "r_dest") {
                "value.ptr"
            } else {
                name.substringAfter("p_")
            }
        }

    fun getRealTypeStr(): String {
        return typeMatcher.matchEntire(rawType)?.groupValues?.get(2)!!
    }

    val isCoreType: Boolean
        get() {
            return getRealTypeStr() in CoreClassRegistry
        }

    fun resolveType(): ClassName {
        val realType = getRealTypeStr()
        return ClassName("", if (realType in CTypeRegistry.keys) {
            CTypeRegistry[realType].toString()
        } else {
            CoreClassRegistry[realType]!!.ktName
        })
    }

}