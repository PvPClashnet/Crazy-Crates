package me.badbones69.crazycrates;

import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Consumer;

public class Sections {

	public static void forEach(ConfigurationSection parent, Consumer<ConfigurationSection> childAction) {
		forEach(parent, childAction, false);
	}

	public static void forEach(ConfigurationSection parent, Consumer<ConfigurationSection> childAction, boolean deep) {
		for (String childNode : parent.getKeys(deep)) {
			ConfigurationSection childSection = parent.getConfigurationSection(childNode);

			childAction.accept(childSection);
		}
	}

}
