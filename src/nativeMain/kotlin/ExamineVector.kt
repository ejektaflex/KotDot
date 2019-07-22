
import GDNativeAPI
import String
import godot_vector2
import interop.godot_vector2
import kotlin.Boolean
import kotlin.Double
import kotlin.Float
import kotlin.Int
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr

class Vector2 {
    val value: godot_vector2 = nativeHeap.alloc()

    constructor(x: kotlin.Float, y: kotlin.Float) {
        GDNativeAPI.godot_vector2_new!!.invoke(value.ptr, x, y)
    }

    fun asString(): String = GDNativeAPI.godot_vector2_as_string!!.invoke(value.ptr)

    fun normalized(): Vector2 = GDNativeAPI.godot_vector2_normalized!!.invoke(value.ptr)

    fun length(): Float = GDNativeAPI.godot_vector2_length!!.invoke(value.ptr)

    fun angle(): Float = GDNativeAPI.godot_vector2_angle!!.invoke(value.ptr)

    fun lengthSquared(): Float = GDNativeAPI.godot_vector2_length_squared!!.invoke(value.ptr)

    fun isNormalized(): Boolean = GDNativeAPI.godot_vector2_is_normalized!!.invoke(value.ptr)

    fun distanceTo(to: Vector2): Float = GDNativeAPI.godot_vector2_distance_to!!.invoke(value.ptr, to)

    fun distanceSquaredTo(to: Vector2): Float =
            GDNativeAPI.godot_vector2_distance_squared_to!!.invoke(value.ptr, to)

    fun angleTo(to: Vector2): Float = GDNativeAPI.godot_vector2_angle_to!!.invoke(value.ptr, to)

    fun angleToPoint(to: Vector2): Float =
            GDNativeAPI.godot_vector2_angle_to_point!!.invoke(value.ptr, to)

    fun linearInterpolate(b: Vector2, t: kotlin.Float): Vector2 =
            GDNativeAPI.godot_vector2_linear_interpolate!!.invoke(value.ptr, b, t)

    fun cubicInterpolate(
            b: Vector2,
            pre_a: Vector2,
            post_b: Vector2,
            t: kotlin.Float
    ): Vector2 = GDNativeAPI.godot_vector2_cubic_interpolate!!.invoke(value.ptr, b, pre_a, post_b, t)

    fun rotated(phi: kotlin.Float): Vector2 = GDNativeAPI.godot_vector2_rotated!!.invoke(value.ptr,
            phi)

    fun tangent(): Vector2 = GDNativeAPI.godot_vector2_tangent!!.invoke(value.ptr)

    fun floor(): Vector2 = GDNativeAPI.godot_vector2_floor!!.invoke(value.ptr)

    fun snapped(by: Vector2): Vector2 = GDNativeAPI.godot_vector2_snapped!!.invoke(value.ptr, by)

    fun aspect(): Float = GDNativeAPI.godot_vector2_aspect!!.invoke(value.ptr)

    fun dot(with: Vector2): Float = GDNativeAPI.godot_vector2_dot!!.invoke(value.ptr, with)

    fun slide(n: Vector2): Vector2 = GDNativeAPI.godot_vector2_slide!!.invoke(value.ptr, n)

    fun bounce(n: Vector2): Vector2 = GDNativeAPI.godot_vector2_bounce!!.invoke(value.ptr, n)

    fun reflect(n: Vector2): Vector2 = GDNativeAPI.godot_vector2_reflect!!.invoke(value.ptr, n)

    fun abs(): Vector2 = GDNativeAPI.godot_vector2_abs!!.invoke(value.ptr)

    fun clamped(length: kotlin.Float): Vector2 = GDNativeAPI.godot_vector2_clamped!!.invoke(value.ptr,
            length)

    operator fun plus(b: Vector2): Vector2 =
            GDNativeAPI.godot_vector2_operator_add!!.invoke(value.ptr, b)

    operator fun minus(b: Vector2): Vector2 =
            GDNativeAPI.godot_vector2_operator_subtract!!.invoke(value.ptr, b)

    operator fun times(b: Vector2): Vector2 =
            GDNativeAPI.godot_vector2_operator_multiply_vector!!.invoke(value.ptr, b)

    operator fun times(b: kotlin.Float): Vector2 =
            GDNativeAPI.godot_vector2_operator_multiply_scalar!!.invoke(value.ptr, b)

    operator fun div(b: Vector2): Vector2 =
            GDNativeAPI.godot_vector2_operator_divide_vector!!.invoke(value.ptr, b)

    operator fun div(b: kotlin.Float): Vector2 =
            GDNativeAPI.godot_vector2_operator_divide_scalar!!.invoke(value.ptr, b)

    operator fun equals(b: Vector2): Boolean =
            GDNativeAPI.godot_vector2_operator_equal!!.invoke(value.ptr, b)

    operator fun compareTo(b: Vector2): Int =
            GDNativeAPI.godot_vector2_operator_less!!.invoke(value.ptr, b)

    operator fun unaryMinus(): Vector2 = GDNativeAPI.godot_vector2_operator_neg!!.invoke(value.ptr)

    fun setX(x: kotlin.Float) {
        GDNativeAPI.godot_vector2_set_x!!.invoke(value.ptr, x)
    }

    fun setY(y: kotlin.Float) {
        GDNativeAPI.godot_vector2_set_y!!.invoke(value.ptr, y)
    }

    fun getX(): Float = GDNativeAPI.godot_vector2_get_x!!.invoke(value.ptr)

    fun getY(): Float = GDNativeAPI.godot_vector2_get_y!!.invoke(value.ptr)
}
