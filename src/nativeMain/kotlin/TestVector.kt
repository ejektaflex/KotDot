import interop.godot_vector2
import kotlinx.cinterop.*

/**
 * This is just a test class - it gets created and run in GDNative::godotNativescriptInit
 * to ensure that this is working
 */
class TestVector() {
    lateinit var value: godot_vector2

    constructor(data: godot_vector2) : this() {
        value = data
    }

    constructor(x: Float, y: Float) : this(nativeHeap.alloc<godot_vector2>()) {
        GDNativeAPI.godot_vector2_new!!(value.ptr, x, y)
    }

    fun getX(): Float {
        return GDNativeAPI.godot_vector2_get_x!!(value.ptr)
    }

    fun getY(): Float {
        return GDNativeAPI.godot_vector2_get_y!!(value.ptr)
    }

    fun linInt(b: TestVector?, t: Float) {
        GDNativeAPI.godot_vector2_linear_interpolate!!(value.ptr, b!!.value.ptr, t)
    }

    override fun toString(): String {
        return "TestVector[x=${getX()}, y=${getY()}]"
    }


    fun slide(n: TestVector): TestVector {
        memScoped {
            val a = GDNativeAPI.godot_vector2_slide!!(value.ptr, n.value.ptr)
            var b: CPointer<godot_vector2>
            b = a.getPointer(this)
            return TestVector(b.pointed)
        }
    }

    fun normalized(): TestVector {
        memScoped {
            val a = GDNativeAPI.godot_vector2_normalized!!(value.ptr)
            val b = a.getPointer(this)
            return TestVector(b.pointed)
        }
    }


    fun reflect(n: TestVector): TestVector {
        memScoped {
            val a = GDNativeAPI.godot_vector2_reflect!!(value.ptr, n.value.ptr)
            val b = a.getPointer(this)
            return TestVector(b.pointed)
        }
    }

    operator fun plus(o: TestVector): TestVector {
        memScoped {
            val a = GDNativeAPI.godot_vector2_operator_add!!(value.ptr, o.value.ptr)
            val b = a.getPointer(this).pointed
            return TestVector(b)
        }
    }



}