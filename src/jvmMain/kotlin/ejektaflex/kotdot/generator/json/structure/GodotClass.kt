package ejektaflex.kotdot.generator.json.structure

import com.google.gson.annotations.SerializedName
import com.squareup.kotlinpoet.*
import ejektaflex.kotdot.generator.json.NativeCommon

data class GodotClass(

        val name: String = "UNDEF_NAME",

        @SerializedName("base_class")
        val baseClass: String = "GodotAny",

        val singleton: Boolean = true,

        val instantiable: Boolean = true,

        @SerializedName("is_reference")
        val isReference: Boolean = true,

        val constants: Map<String, Int> = mapOf(),

        val methods: MutableList<GodotMethod>,

        val baseClassGodot: GodotClass? = null

) {

    fun generate(): String {


        val file = FileSpec.builder("structure", name).apply {
            val newClazz = TypeSpec.classBuilder(name).apply {

                // Header / Primary Constructor

                addModifiers(KModifier.OPEN)
                superclass(ClassName("structure", baseClass))

                if (instantiable) {
                    addFunction(
                        FunSpec.constructorBuilder()
                            .callSuperConstructor("\"$name\"")
                            .build()
                    )
                } else {
                    addFunction(
                        FunSpec.constructorBuilder()
                            .addModifiers(KModifier.PRIVATE)
                            .callSuperConstructor("\"\"")
                            .build()
                    )
                }

                addFunction(
                    FunSpec.constructorBuilder()
                        .addModifiers(KModifier.INTERNAL)
                        .addParameter("memory", NativeCommon.copaque)
                        .callSuperConstructor("memory")
                        .build()
                )

                addFunction(
                    FunSpec.constructorBuilder()
                        .addModifiers(KModifier.INTERNAL)
                        .addParameter("name", String::class)
                        .callSuperConstructor("name")
                        .build()
                )

                // Body


                // Constants

                for (constant in constants) {
                    addProperty(
                        PropertySpec.builder(constant.key, Long::class, KModifier.CONST).initializer(constant.value.toString()).build()
                    )
                }




            }.build()

            addType(newClazz)
        }.build()


        val output = StringBuilder()

        file.writeTo(output)

        return output.toString()

    }

}