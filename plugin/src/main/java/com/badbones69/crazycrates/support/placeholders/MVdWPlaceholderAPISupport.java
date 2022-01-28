package com.badbones69.crazycrates.support.placeholders;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.badbones69.crazycrates.api.CrazyManager;
import com.badbones69.crazycrates.api.enums.CrateType;
import com.badbones69.crazycrates.api.objects.Crate;
import java.text.NumberFormat;

public class MVdWPlaceholderAPISupport {
    
    public static void registerPlaceholders() {
        for (final Crate crate : CrazyManager.getInstance().getCrates()) {
            if (crate.getCrateType() != CrateType.MENU) {
                PlaceholderAPI.registerPlaceholder(CrazyManager.getJavaPlugin(), "crazycrates_" + crate.getName(), e -> NumberFormat.getNumberInstance().format(CrazyManager.getInstance().getVirtualKeys(e.getPlayer(), crate)));
                PlaceholderAPI.registerPlaceholder(CrazyManager.getJavaPlugin(), "crazycrates_" + crate.getName() + "_physical", e -> NumberFormat.getNumberInstance().format(CrazyManager.getInstance().getPhysicalKeys(e.getPlayer(), crate)));
                PlaceholderAPI.registerPlaceholder(CrazyManager.getJavaPlugin(), "crazycrates_" + crate.getName() + "_total", e -> NumberFormat.getNumberInstance().format(CrazyManager.getInstance().getTotalKeys(e.getPlayer(), crate)));
            }
        }
    }
}