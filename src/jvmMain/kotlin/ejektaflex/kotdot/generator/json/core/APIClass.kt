package ejektaflex.kotdot.generator.json.core

import com.squareup.kotlinpoet.*

open class APIClass(val name: String) {

    val ktName: String
        get() = name.substringAfter("godot_").capitalize()

    val methods = mutableListOf<APIMethod>()

    val normalMethods: List<APIMethod>
        get() = methods - newClassMethod

    val newClassMethod: APIMethod
        get() {
            return methods.first { it.name == "${name}_new" }
        }

    fun generate(): String {
        val file = FileSpec.builder("structure", ktName).apply {
            val newClazz = TypeSpec.classBuilder(ktName).apply {

                addImport("kotlin", "Double", "Boolean")


                addFunction(newClassMethod.generate(initFunc = true).build())



            }.build()

            addType(newClazz)
        }.build()


        val output = StringBuilder()

        file.writeTo(output)

        return output.toString()
    }

}