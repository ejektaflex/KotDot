import interop.*
import kotlinx.cinterop.CPointer

object BindMap {
    private val content =  mutableMapOf<String, CPointer<godot_method_bind>>()

    private fun makeBind(key: String): CPointer<godot_method_bind> {
        val sets = key.split("::")

        // godot_method_bind_get_method does not exist at compile-time for K/N shared lib?
        /*
        content[key] = godot_method_bind_get_method(sets[0], sets[1])
                ?: throw Exception("Cannot create a method bind to '$key'!")


         */

        return content[key]!!
    }

    operator fun get(key: String): CPointer<godot_method_bind> {
        return content[key] ?: makeBind(key)
    }

}

