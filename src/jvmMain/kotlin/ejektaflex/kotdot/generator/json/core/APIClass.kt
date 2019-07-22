package ejektaflex.kotdot.generator.json.core

import com.squareup.kotlinpoet.*
import ejektaflex.kotdot.generator.json.NativeCommon

open class APIClass(val name: String) {

    val ktName: String
        get() = name.substringAfter("godot_").capitalize()

    val methods = mutableListOf<APIMethod>()

    fun generate(): String {
        val file = FileSpec.builder("structure", ktName).apply {
            val newClazz = TypeSpec.classBuilder(ktName).apply {

                addImport("kotlin", "Double", "Boolean")





            }.build()

            addType(newClazz)
        }.build()


        val output = StringBuilder()

        file.writeTo(output)

        return output.toString()
    }

}