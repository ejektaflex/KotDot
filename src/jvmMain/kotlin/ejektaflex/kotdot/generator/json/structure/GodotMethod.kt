package ejektaflex.kotdot.generator.json.structure

import com.google.gson.annotations.SerializedName
import com.squareup.kotlinpoet.*
import ejektaflex.kotdot.generator.json.NativeCommon
import ejektaflex.kotdot.generator.json.reg.TypeRegistry
import kotlin.reflect.KClass

data class GodotMethod(
    val name: String = "UNDEF_NAME",

    @SerializedName("return_type")
    val returnType: String = "void",

    @SerializedName("is_virtual")
    val isVirtual: Boolean = false,

    val arguments: List<GodotArgument>

)  {

    lateinit var parentClass: GodotClass

    val bindingName: String
        get() = "${parentClass.name}::$name"

    val returnTypeName: TypeName
        get() {
            return if (returnType.startsWith("enum")) {
                TypeRegistry.lookup("void")
            } else {
                TypeRegistry.lookup(returnType)
            }
        }


    private fun genPtrCall(builder: FunSpec.Builder) {
        builder.apply {

            // Return block
            if (returnType != "void") {
                addCode(
                        CodeBlock.of("%L", PropertySpec.builder(
                                "toReturn",
                                returnTypeName
                        ).apply {
                            addModifiers(KModifier.LATEINIT)
                            mutable()
                        }.build())
                ).build()
            }

            addComment(returnTypeName.toString())

            beginControlFlow("memScoped")



            if (arguments.isNotEmpty()) {
                addStatement("val args = allocArray<${NativeCommon.copaquevar.simpleName}>(${arguments.size + 1})")


                arguments.forEachIndexed { i, argument ->
                    addStatement("args[$i] = " + when (argument.type) {
                        "bool" -> "cValuesOf(${argument.name}.toByte()).getPointer(memScope)"
                        "float", "long", "int" -> "cValuesOf(${argument.name}).getPointer(memScope)"
                        else -> "${argument.name}.getMemory(memScope)"
                    })
                }

                addStatement("args[${arguments.size}] = null")

                // TODO change null if valid return value
                addStatement("godot_method_bind_ptrcall(bind_$name, getMemory(), args, null)")

            }



            endControlFlow()

        }
    }


    fun generate(): FunSpec.Builder {





        val func = FunSpec.builder(name).apply {
            returns(returnTypeName)


            // Add arguments to constructor

            for (arg in arguments) {
                addParameter(
                        ParameterSpec.builder(arg.name, TypeRegistry.lookup(arg.type)).build()
                )
            }

            genPtrCall(this)

        }


        if (!isVirtual) {

        }

        return func

    }

}