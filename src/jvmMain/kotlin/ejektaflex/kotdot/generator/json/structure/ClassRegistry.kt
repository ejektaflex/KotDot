package ejektaflex.kotdot.generator.json.structure

import com.google.gson.Gson
import kotdot.registry.SimpleRegistry
import java.io.File

val apifile = File("godot_headers/api.json")

val content = Gson().fromJson(apifile.readText(), Array<GodotClass>::class.java)

object ClassRegistry : SimpleRegistry<String, GodotClass>(content.associateBy { it.name }.toMutableMap()) {
    init {
        // Link class heirarchy
        for ((name, clazz) in delegate) {
            if (clazz.baseClass in delegate) {
                clazz.baseClassGodot = delegate[clazz.baseClass]
                //println("$name->${clazz.baseClassGodot?.name}")
            }
        }
    }
}