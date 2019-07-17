package ejektaflex.kotdot.generator.json.reg

import com.google.gson.Gson
import ejektaflex.kotdot.generator.json.structure.GodotClass
import java.io.File

val apifile = File("godot_headers/api.json")

val content = Gson().fromJson(apifile.readText(), Array<GodotClass>::class.java)

object ClassRegistry : SimpleRegistry<String, GodotClass>(content.associateBy { it.name }.toMutableMap()) {
    init {

        for ((name, clazz) in delegate) {

            // Link class heirarchy
            if (clazz.baseClass in delegate) {
                clazz.baseClassGodot = delegate[clazz.baseClass]
                //println("$name->${clazz.baseClassGodot?.name}")
            }

            // Link methods to classes
            for (method in clazz.methods) {
                method.parentClass = clazz
            }



        }

    }
}