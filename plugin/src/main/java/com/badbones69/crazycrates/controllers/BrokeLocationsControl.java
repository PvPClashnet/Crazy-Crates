package com.badbones69.crazycrates.controllers;

import com.badbones69.crazycrates.api.CrazyManager;
import com.badbones69.crazycrates.api.enums.BrokeLocation;
import com.badbones69.crazycrates.api.objects.CrateLocation;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Only use for this class is to check if for broken locations and to try and fix them when the server loads the world.
 */
public class BrokeLocationsControl implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e) {
        if (!CrazyManager.getInstance().getBrokeCrateLocations().isEmpty()) {
            int fixedAmount = 0;
            List<BrokeLocation> fixedWorlds = new ArrayList<>();

            for (BrokeLocation brokeLocation : CrazyManager.getInstance().getBrokeCrateLocations()) {
                Location location = brokeLocation.getLocation();
                if (location.getWorld() != null) {
                    CrazyManager.getInstance().getCrateLocations().add(new CrateLocation(brokeLocation.getLocationName(), brokeLocation.getCrate(), location));
                    if (CrazyManager.getInstance().getHologramController() != null) CrazyManager.getInstance().getHologramController().createHologram(location.getBlock(), brokeLocation.getCrate());
                    fixedWorlds.add(brokeLocation);
                    fixedAmount++;
                }
            }

            CrazyManager.getInstance().getBrokeCrateLocations().removeAll(fixedWorlds);

            CrazyManager.getJavaPlugin().getLogger().warning("Fixed " + fixedAmount + " broken crate locations.");
            if (CrazyManager.getInstance().getBrokeCrateLocations().isEmpty()) CrazyManager.getJavaPlugin().getLogger().warning("All broken crate locations have been fixed.");
        }
    }
}