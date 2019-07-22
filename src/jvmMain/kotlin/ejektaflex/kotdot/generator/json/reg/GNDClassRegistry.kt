package ejektaflex.kotdot.generator.json.reg

import com.google.gson.Gson
import ejektaflex.kotdot.generator.json.core.APIClass
import ejektaflex.kotdot.generator.json.core.APIGlobal
import ejektaflex.kotdot.generator.json.core.APIMethod
import ejektaflex.kotdot.generator.json.core.GDNativeAPIObject
import ejektaflex.kotdot.generator.json.structure.GodotClass
import java.io.File

private val apifile = File("godot_headers/gdnative_api.json")

private val content = Gson().fromJson(apifile.readText(), GDNativeAPIObject::class.java)

object GDNClassRegistry : SimpleRegistry<String, APIClass>() {
    init {
        //content.allMethods.associateBy { it.name }.toMutableMap()

        //(godot_[a-z0-9]+)_new.*

        val regex = Regex("(godot_[a-z_0-9]+)_new.*")

        val newFuncs = content.allMethods.groupBy { regex.matchEntire(it.name)?.groupValues?.get(1) }

        val clNames = newFuncs.keys.filterNotNull()


        val groupedFuncs = content.allMethods.groupBy {method ->
            clNames.filter { it in method.name }.maxBy { it.length }
        }

        for (funcGroup in groupedFuncs) {
            if (funcGroup.key == null) {
                APIGlobal.methods.addAll(funcGroup.value)
            } else {
                insert(funcGroup.key!!, APIClass(funcGroup.key!!).apply {
                    methods.addAll(funcGroup.value)
                    methods.forEach { it.parentClass = this }
                })
            }
        }

        //val funcMapping = content.allMethods.groupBy { Regex("") }



    }
}