import interop.godot_vector2
import kotlinx.cinterop.*

/**
 * This is just a test class - it gets created and run in GDNative::godotNativescriptInit
 * to ensure that this is working
 */
class TestVector() {
    val value = nativeHeap.alloc<godot_vector2>()

    constructor(x: Float, y: Float) : this() {
        GDNativeAPI.godot_vector2_new!!(value.ptr, x, y)
    }

    fun getX(): Float {
        return GDNativeAPI.godot_vector2_get_x!!(value.ptr)
    }

    fun linInt(b: TestVector?, t: Float) {
        GDNativeAPI.godot_vector2_linear_interpolate!!(value.ptr, b!!.value.ptr, t)
    }

}