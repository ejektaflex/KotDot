package ejektaflex.kotdot.generator.json.structure

data class GodotProperty(
        val name: String,
        val type: String,
        val getter: String,
        val setter: String,
        val index: Int
) {
}