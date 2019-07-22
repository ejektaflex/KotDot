package ejektaflex.kotdot.generator.json.core

data class APIContainer(
        val type: String,
        val version: APIVersion,
        val next: APIContainer? = null,
        val api: List<APIMethod>
) {
    val allMethods: List<APIMethod>
        get() {
            return mutableListOf<APIMethod>() + api + (next?.api ?: listOf())
        }
}