import kotlinx.cinterop.*
import interop.*
import platform.posix.strcpy
import kotlin.native.internal.ExportForCppRuntime

fun createSimpleClass(godotObject: COpaquePointer?, data: COpaquePointer?): COpaquePointer? {
    val userData = nativeHeap.alloc<user_data_struct>()
    strcpy(userData.data, "Hello World!")
    return userData.ptr
}

fun simpleDestructor(godotObject: COpaquePointer?, methodData: COpaquePointer?, userData: COpaquePointer?) {
    nativeHeap.free(userData!!.rawValue)
}


internal class GDNativeContainer(
        var apiPointer: CPointer<godot_gdnative_core_api_struct>? = null,
        var initialized: Boolean = false
)

internal class NativeScriptContainer(
        var apiPointer: CPointer<godot_gdnative_ext_nativescript_api_struct>? = null,
        var initialized: Boolean = false
)

private val GDNative = GDNativeContainer()
private val NativeScript = NativeScriptContainer()

@ExportForCppRuntime
@CName("godot_gdnative_init")
fun godotGDnativeInit(options: CPointer<godot_gdnative_init_options>?) {
    println("Godot-Kotlin GDNative Init..")

    GDNative.apiPointer = options!!.pointed.api_struct
    GDNative.initialized = true

    val api = GDNative.apiPointer!!.pointed

    for (i in 0 until api.num_extensions.toInt()) {
        val extension = api.extensions!![i]!!.pointed
        if (extension.type == GDNATIVE_EXT_NATIVESCRIPT) {
            NativeScript.apiPointer = api.extensions!![i]!!.reinterpret()
            NativeScript.initialized = true
        }
    }

    println("Godot-Kotlin GDNative API Initialized: ${GDNative.initialized}")
    println("Godot-Kotlin NativeScript API Initial: ${NativeScript.initialized}")
    println("Godot-Kotlin GDNative Init Completed")
}


@ExportForCppRuntime
@CName("godot_nativescript_init")
fun godotNativescriptInit(pHandle: COpaquePointer?) {
    println("Godot-Kotlin NativeScript Init..")
    val instanceFunc = nativeHeap.alloc<godot_instance_create_func>()
    instanceFunc.create_func = staticCFunction(::createSimpleClass)
    val destroy = nativeHeap.alloc<godot_instance_destroy_func>()
    destroy.destroy_func = staticCFunction(::simpleDestructor)

    val nativeApi = NativeScript.apiPointer?.pointed
    nativeApi?.godot_nativescript_register_class?.invoke(pHandle, CLASS_NAME, REFERENCE_VALUE, instanceFunc.readValue(), destroy.readValue())
    println("Godot-Kotlin NativeScript Init Completed")
}



@ExportForCppRuntime
@CName("godot_gdnative_terminate")
fun godotGDnativeTerminate(options: CPointer<godot_gdnative_init_options>) {
    println("Godot-Kotlin Terminate Init..")
    GDNative.apiPointer = null
    GDNative.initialized = false
    NativeScript.apiPointer = null
    NativeScript.initialized = false
    println("Godot-Kotlin Terminate Init Completed")
}


