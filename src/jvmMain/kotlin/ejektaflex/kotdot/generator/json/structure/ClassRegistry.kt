package ejektaflex.kotdot.generator.json.structure

import com.google.gson.Gson
import kotdot.registry.SimpleRegistry
import java.io.File

val apifile = File("godot_headers/api.json")

val content = Gson().fromJson(apifile.readText(), Array<GodotClass>::class.java)

object ClassRegistry : SimpleRegistry<String, GodotClass>(content.associateBy { it.name }.toMutableMap()) {
    init {
        for (clazz in delegate) {

        }
    }
}