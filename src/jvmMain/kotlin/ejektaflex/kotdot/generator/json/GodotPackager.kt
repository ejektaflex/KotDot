package ejektaflex.kotdot.generator.json

import ejektaflex.kotdot.generator.json.reg.CoreClassRegistry
import ejektaflex.kotdot.generator.json.reg.GodotClassRegistry
import java.io.File

object GodotPackager {

    fun generateGodotClasses(loc: File) {


        // Godot classes
        val gdclassLoc = File(loc, "godot").apply { mkdirs() }
        for (godotClass in GodotClassRegistry.values) {
            val classFile = File(gdclassLoc, "${godotClass.name}.kt").apply { createNewFile() }
            classFile.writeText(godotClass.generate())
            println("Wrote Godot Class: ${godotClass.name}")
        }

        println("Finished writing Godot classes!")

        // Core classes
        val coreLoc = File(loc, "core").apply { mkdirs() }
        for (coreClass in CoreClassRegistry.values) {
            val classFile = File(coreLoc, "${coreClass.ktName}.kt").apply { createNewFile() }
            classFile.writeText(coreClass.generate())
            println("Wrote Core Class: ${coreClass.ktName}")
        }

        println("Finished writing Core classes!")

    }

}