package ejektaflex.kotdot.generator.json.reg

import kotdot.registry.SimpleRegistry
import kotlin.reflect.KType
import kotlin.reflect.full.createType

object TypeRegistry : SimpleRegistry<String, KType>() {
    init {
        mapOf(
                "void" to Unit::class.createType(),
                "bool" to Boolean::class.createType(),
                "float" to Float::class.createType()
        ).forEach { t, u ->
            delegate[t] = u
        }
    }
}