package me.tatocaster.revoluttest.entity

data class Rate(val name: String, val rate: Double) {
    override fun equals(other: Any?): Boolean {
        return other is Rate && other.name == name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + rate.hashCode()
        return result
    }
}