package ejektaflex.kotdot.generator.json.reg

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.jvm.jvmSuppressWildcards
import com.squareup.kotlinpoet.jvm.jvmWildcard
import kotlin.reflect.KType
import kotlin.reflect.full.createType

object TypeRegistry : SimpleRegistry<String, KType>() {

    val primitives = mutableListOf<String>()

    init {
        mapOf(
                "void" to Unit::class.createType(),
                "bool" to Boolean::class.createType(),
                "float" to Double::class.createType(),
                "int" to Long::class.createType()
        ).forEach { t, u ->
            primitives.add(t)
            delegate[t] = u
        }
    }

    fun lookup(name: String): TypeName {
        return if (name in delegate) {
            delegate[name]!!.asTypeName()
        } else {
            ClassName("godot", name)
        }
    }

}