package ejektaflex.kotdot.generator.json.reg

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import ejektaflex.kotdot.generator.json.structure.GodotMethod

object PtrCallRegistry : SimpleRegistry<String, () -> FunSpec.Builder>() {

    fun generate(method: GodotMethod, baseMethod: FunSpec.Builder) {

        if (method.bindingName in delegate) {
            delegate[method.bindingName]!!()
        } else {

        }







    }



}