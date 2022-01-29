package com.badbones69.crazycrates.func.enums

enum class CrateType(val crateName: String) {

    MENU("Menu"),
    COSMIC("Cosmic"),
    CRATE_ON_THE_GO("CrateOnTheGo"),
    CSGO("CSGO"),
    FIRE_CRACKER("FireCracker"),
    QUAD_CRATE("QuadCrate"),
    QUICK_CRATE("QuickCrate"),
    ROULETTE("Roulette"),
    WHEEL("Wheel"),
    WONDER("Wonder"),
    WAR("War");

    companion object {
        @JvmStatic
        fun getFromName(name: String?): CrateType? {
            values().forEach { if (it.name.equals(name, ignoreCase = true)) return it }
            return null
        }
    }
}