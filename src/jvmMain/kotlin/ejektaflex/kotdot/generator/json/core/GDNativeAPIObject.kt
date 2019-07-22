package ejektaflex.kotdot.generator.json.core

data class GDNativeAPIObject(val core: APIContainer) {
    val allMethods: List<APIMethod>
        get() = core.allMethods
}