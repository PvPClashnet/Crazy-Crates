package com.badbones69.crazycrates.func.enums

import com.badbones69.crazycrates.api.objects.Crate
import com.badbones69.crazycrates.api.CrazyManager
import org.bukkit.Location

class BrokeLocation(val locationName: String, var crate: Crate, var x: Int, var y: Int, var z: Int, var world: String) {
    val location: Location get() = Location(CrazyManager.getJavaPlugin().server.getWorld(world), x.toDouble(), y.toDouble(), z.toDouble())
}