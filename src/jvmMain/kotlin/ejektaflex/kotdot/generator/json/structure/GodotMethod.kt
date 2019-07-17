package ejektaflex.kotdot.generator.json.structure

import com.google.gson.annotations.SerializedName
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.jvm.jvmWildcard
import ejektaflex.kotdot.generator.json.reg.TypeRegistry

data class GodotMethod(
    val name: String = "UNDEF_NAME",

    @SerializedName("return_type")
    val returnType: String = "void",

    @SerializedName("is_virtual")
    val isVirtual: Boolean = false

)  {

    lateinit var parentClass: GodotClass

    val bindingName: String
        get() = "${parentClass.name}::$name"


    fun generate(): FunSpec.Builder {


        val toRet = if (returnType.startsWith("enum")) {
            TypeRegistry.lookup("void")
        } else {
            TypeRegistry.lookup(returnType)
        }


        val func = FunSpec.builder(name).apply {
            returns(toRet)

            addComment("${parentClass.name}::$name")

        }


        if (!isVirtual) {

        }

        return func

    }

}