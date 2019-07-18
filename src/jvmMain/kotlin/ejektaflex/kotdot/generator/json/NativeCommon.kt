package ejektaflex.kotdot.generator.json

import com.squareup.kotlinpoet.ClassName

object NativeCommon {
    private val godot = "structure"
    private val cinterop = "kotlinx.cinterop"

    val variant = ClassName(godot, "Variant")
    val copaque = ClassName(cinterop, "COpaquePointer")
    val cpointer = ClassName(cinterop, "CPointer")
    val copaquevar = ClassName(cinterop, "COpaquePointerVar")
    val godotBind = ClassName(godot, "CPointer<godot_method_bind>")
    val memscope = ClassName(cinterop, "MemScope")

    fun cPointerArray(contentClass: String): ClassName {
        return ClassName(cinterop, "CArrayPointer<$contentClass>")
    }

    fun cPointerArray(className: ClassName): ClassName {
        return ClassName(cinterop, "CArrayPointer<${className.simpleName}>")
    }


    val bindMap = ClassName(godot, "BindMap")
}