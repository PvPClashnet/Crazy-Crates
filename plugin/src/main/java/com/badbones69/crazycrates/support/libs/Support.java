package com.badbones69.crazycrates.support.libs;

import com.badbones69.crazycrates.api.CrazyManager;

public enum Support {
    
    PLACEHOLDERAPI("PlaceholderAPI"),
    MVDWPLACEHOLDERAPI("MVdWPlaceholderAPI"),
    HOLOGRAPHIC_DISPLAYS("HolographicDisplays");
    
    private final String name;
    
    Support(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isPluginLoaded() {
        return CrazyManager.getJavaPlugin().getServer().getPluginManager().getPlugin(name) != null;
    }
}