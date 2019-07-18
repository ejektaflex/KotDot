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

        val properties: MutableList<GodotProperty>,

        var baseClassGodot: GodotClass? = null

) {

    /*
    Returns a list of all superclasses, including the current class
     */
    val superclasses: List<GodotClass>
        get() {
            return listOf(this) + if (baseClassGodot == null) {
                listOf()
            } else {
                baseClassGodot!!.superclasses
            }
        }

    val essentialMethods: List<GodotMethod>
        get() {
            return methods.filter { method ->
                method.name !in properties.map { it.getter }
                        && method.name !in properties.map { it.setter }
            }
        }

    fun generate(): String {


        val file = FileSpec.builder("structure", name).apply {
            val newClazz = TypeSpec.classBuilder(name).apply {

                // Header / Primary Constructor

                addModifiers(KModifier.OPEN)

                baseClassGodot?.let {
                    superclass(ClassName("structure", it.name))
                }

                /*
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
                 */

                addFunction(
                    FunSpec.constructorBuilder()
                        .addModifiers(KModifier.INTERNAL)
                        .addParameter("memory", NativeCommon.copaque)
                        .callSuperConstructor("memory")
                        .build()
                )

                /*
                addFunction(
                    FunSpec.constructorBuilder()
                        .addModifiers(KModifier.INTERNAL)
                        .addParameter("name", String::class)
                        .callSuperConstructor("name")
                        .build()
                )
                 */

                // Companion Object

                val companion = TypeSpec.companionObjectBuilder()



                // Body

                for (essMethod in essentialMethods) {
                    addFunction(
                            essMethod.generate().build()
                    )

                    // Add binding properties
                    companion.addProperty(
                            PropertySpec.builder(
                                    "bind_${essMethod.name}",
                                    NativeCommon.godotBind
                            ).apply {
                                addModifiers(KModifier.PRIVATE)
                                initializer("BindMap[\"${essMethod.bindingName}\"]")
                            }.build()
                    )

                }

                // Companion Object



                addType(companion.build())


                // Constants (should be done but get in the way a lot, so are commented out for now)

                /*
                for (constant in constants) {
                    addProperty(
                        PropertySpec.builder(constant.key, Long::class, KModifier.CONST).initializer(constant.value.toString()).build()
                    )
                }
                 */




            }.build()

            addType(newClazz)
        }.build()


        val output = StringBuilder()

        file.writeTo(output)

        return output.toString()

    }

}