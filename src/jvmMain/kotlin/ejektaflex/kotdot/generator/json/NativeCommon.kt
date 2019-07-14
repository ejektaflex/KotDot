package ejektaflex.kotdot.generator.json

import com.squareup.kotlinpoet.ClassName

object NativeCommon {
    private val godot = "structure"
    val variant = ClassName(godot, "Variant")
    val copaque = ClassName(godot, "COpaquePointer")
}