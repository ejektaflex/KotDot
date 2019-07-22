package ejektaflex.kotdot.generator.json.core

import com.google.gson.annotations.SerializedName
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

}