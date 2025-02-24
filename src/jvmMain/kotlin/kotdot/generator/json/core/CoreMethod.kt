package kotdot.generator.json.core

import com.google.gson.annotations.SerializedName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import kotdot.generator.json.reg.CTypeRegistry
import kotdot.generator.json.reg.CoreClassRegistry
import kotdot.generator.toCamelCase
import kotlin.reflect.KClass

data class CoreMethod(
        val name: String,
        @SerializedName("return_type")
        val returnType: String,
        val arguments: List<List<String>>
) {

    lateinit var parentClass: CoreClass

    val returnsAnything: Boolean
        get() = returnType != "void"

    val trimmedParentName: String
        get() = name.substringAfter(parentClass.name + "_")

    val ktName: String
        get() = trimmedParentName.toCamelCase()

    val coreArguments: List<CoreArgument>
        get() = arguments.map { CoreArgument(it[0], it[1]) }

    val isOperator: Boolean
        get() = trimmedParentName.startsWith("operator")

    val operatorType: OperatorType?
        get() {
            return if (isOperator) {
                val opString = trimmedParentName.substringAfter("operator_").split("_").first()
                OperatorType.valueOf(opString.toUpperCase()) ?: null
            } else {
                null
            }
        }



    enum class OperatorType(val jsonPrefix: String, val kotlinName: String, val requiredReturn: KClass<*>? = null) {
        DIVIDE("divide", "div"),
        EQUAL("equal", "equals"),
        ADD("add", "plus"),
        SUBTRACT("subtract", "minus"),
        MULTIPLY("multiply", "times"),
        LESS("less", "compareTo", requiredReturn = Int::class),
        NEG("neg", "unaryMinus")
    }




    fun generate(initFunc: Boolean = false, dropFirst: Boolean = true): FunSpec.Builder {

        val finalFuncName = if (isOperator) {
            operatorType!!.kotlinName
        } else {
            ktName
        }

        val builder = if (initFunc) {
            FunSpec.constructorBuilder()
        } else {
            FunSpec.builder(finalFuncName)
        }

        return builder.apply {

            if (isOperator) {
                addModifiers(KModifier.OPERATOR)
            }

            // compareTo returns Int, not Boolean
            if (!initFunc) {
                if (isOperator && operatorType!!.kotlinName == OperatorType.LESS.kotlinName) {
                    returns(Int::class)
                } else {
                    returns(CTypeRegistry.lookup(returnType))
                }
            }

            // Class methods can drop first method, as it's self referential
            val iterArgs = if (dropFirst) {
                coreArguments.drop(1)
            } else {
                coreArguments
            }

            for (arg in iterArgs) {
                addParameter(arg.trueName, arg.resolveType())
            }

            beginControlFlow("memScoped")

            // Generate API call
            var proto = ""//if (returnType != "void") "return " else ""

            if (returnsAnything) {
                proto += "val apiCall = "
            }

            proto += "GDNativeAPI.$name!!.invoke("


            proto += coreArguments.joinToString(", ") {
                it.trueName + when {
                    it.isCoreType && it.trueName != "value.ptr" -> ".value.ptr"
                    else -> ""
                }
            }

            proto += ")"

            addStatement(proto)

            if (returnsAnything) {
                addStatement("val retPtr = a.getPointer(this).pointed")

                when (returnType) {
                    "void" -> {}
                    in CoreClassRegistry -> addStatement("return ${CoreClassRegistry[returnType]!!.ktName}(retPtr)")
                    else -> addStatement("return retPtr")
                }
            }


            endControlFlow()


        }
    }

}