package ejektaflex.kotdot.generator.json.structure

import com.google.gson.annotations.SerializedName
import com.squareup.kotlinpoet.FunSpec

data class GodotMethod(
    val name: String = "UNDEF_NAME",

    @SerializedName("return_type")
    val returnType: String = "void",

    @SerializedName("is_virtual")
    val isVirtual: Boolean = false

)  {


    fun generate(godotClass: GodotClass): String {
        return buildString {

            val func = FunSpec.constructorBuilder()


            if (!isVirtual) {
                appendln(

                )
            }


        }
    }

}