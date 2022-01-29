package com.badbones69.crazycrates.func.enums

enum class KeyType(val keyName: String) {

    PHYSICAL_KEY("Physical_Key"),
    VIRTUAL_KEY("Virtual_Key"),
    FREE_KEY("Free_Key");

    companion object {
        @JvmStatic
        fun getFromName(type: String): KeyType? {
            if (type.equals("virtual", ignoreCase = true) || type.equals("v", ignoreCase = true)) {
                return VIRTUAL_KEY
            } else if (type.equals("physical", ignoreCase = true) || type.equals("p", ignoreCase = true)) {
                return PHYSICAL_KEY
            } else if (type.equals("free", ignoreCase = true) || type.equals("f", ignoreCase = true)) {
                return FREE_KEY
            }
            return null
        }
    }
}