package ejektaflex.kotdot.generator.json.core

import com.google.gson.annotations.SerializedName
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import ejektaflex.kotdot.generator.json.NativeCommon
import ejektaflex.kotdot.generator.json.reg.CTypeRegistry
import ejektaflex.kotdot.generator.json.reg.GDNClassRegistry
import ejektaflex.kotdot.generator.json.reg.TypeRegistry
import ejektaflex.kotdot.generator.toCamelCase

data class APIMethod(
        val name: String,
        @SerializedName("return_type")
        val returnType: String,
        val arguments: List<List<String>>
) {

    lateinit var parentClass: APIClass

    val ktName: String
        get() = name.substringAfter(parentClass.name + "_").toCamelCase()

    val apiArguments: List<APIArgument>
        get() = arguments.map { APIArgument(it[0], it[1]) }


    fun generate(initFunc: Boolean = false, dropFirst: Boolean = true): FunSpec.Builder {

        val builder = if (initFunc) {
            FunSpec.constructorBuilder()
        } else {
            FunSpec.builder(name)
        }

        return builder.apply {

            if (!initFunc) {
                returns(CTypeRegistry.lookup(returnType))
            }

            // Class methods can drop first method, as it's self referential
            val iterArgs = if (dropFirst) {
                apiArguments.drop(1)
            } else {
                apiArguments
            }

            for (arg in iterArgs) {
                addParameter(arg.trueName, arg.resolveType())
            }

            var proto = "GDNativeAPI.$name!!("


            iterArgs.forEachIndexed { i, arg ->

                proto += arg.trueName

                if (i != iterArgs.size - 1) {
                    proto += ", "
                }
            }

            proto += ")"
            addStatement(proto)


            // Add arguments to constructor

            /*
            for (arg in arguments) {
                addParameter(
                        ParameterSpec.builder(arg.name, TypeRegistry.lookup(arg.type)).build()
                )
            }

             */

        }
    }

}