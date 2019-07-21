package ejektaflex.kotdot.generator

import com.squareup.kotlinpoet.TypeName

val TypeName.simpleName: String
    get() = this.toString().split('.').last()