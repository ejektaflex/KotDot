package ejektaflex.kotdot.generator.json

import com.squareup.kotlinpoet.ClassName

object NativeCommon {
    private val godot = "structure"
    val variant = ClassName(godot, "Variant")
    val copaque = ClassName("kotlinx.cinterop", "COpaquePointer")
    val cpointer = ClassName("kotlinx.cinterop", "CPointer")
    val godotBind = ClassName(godot, "CPointer<godot_method_bind>")

    val bindMap = ClassName(godot, "BindMap")
}