package me.badbones69.crazycrates.masskey;

import me.badbones69.crazycrates.Methods;
import me.badbones69.crazycrates.Sections;
import me.badbones69.crazycrates.api.CrazyCrates;
import me.badbones69.crazycrates.api.FileManager;
import me.badbones69.crazycrates.api.objects.Crate;
import me.badbones69.crazycrates.api.objects.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class MassKeyGUI {

	public static final String METADATA_KEY = "cc-autoclose";
	private final MassKeyService massKeyService;

	public MassKeyGUI(MassKeyService massKeyService) {
		this.massKeyService = massKeyService;
	}

	public void open(Player player, Crate crate) {
		FileConfiguration config = FileManager.Files.CONFIG.getFile();
		ConfigurationSection settings = config.getConfigurationSection("mass-key-gui");

		Inventory inventory = Bukkit.createInventory(
				null,
				settings.getInt("rows") * 9,
				Methods.color(settings.getString("title"))
		);

		setItem(inventory, provideToggleItem(player));
		setItem(inventory, provideInformationItem());

		provideMassKeys(player, crate)
				.forEach(itemBuilder -> setItem(inventory, itemBuilder));

		player.openInventory(inventory);
		massKeyService.openInventory(player);
	}

	protected void setItem(Inventory inventory, ItemBuilder builder) {
		inventory.setItem(builder.getSlot(), builder.build());
	}

	private List<ItemBuilder> provideMassKeys(Player player, Crate crate) {
		FileConfiguration config = FileManager.Files.CONFIG.getFile();
		List<ItemBuilder> items = new ArrayList<>();

		int keys = CrazyCrates.getInstance().getTotalKeys(player, crate);
		ConfigurationSection template = config.getConfigurationSection("mass-key-gui.mass-key-template");

		String available = template.getString("available-material");
		String unavailable = template.getString("unavailable-material");

		ItemBuilder templateItem = ItemBuilder.fromConfiguration(template);

		Sections.forEach(
				config.getConfigurationSection("mass-key-gui.mass-keys"),
				section -> {
					int amount = section.getInt("amount");

					items.add(
							new ItemBuilder(templateItem)
									.setSlot(section.getInt("slot"))
									.setAmount(amount)
									.setMaterial(keys >= amount ? available : unavailable)
									.addNamePlaceholder("%amount%", Integer.toString(amount))
					);
				});

		return items;
	}

	protected ItemBuilder provideInformationItem() {
		FileConfiguration config = FileManager.Files.CONFIG.getFile();
		return ItemBuilder.fromConfiguration(config.getConfigurationSection("mass-key-gui.information"));
	}

	protected ItemBuilder provideToggleItem(Player player) {
		FileConfiguration config = FileManager.Files.CONFIG.getFile();
		ConfigurationSection section = config.getConfigurationSection("mass-key-gui.toggle-close");
		String material = player.hasMetadata(METADATA_KEY) ?
				section.getString("enabled-material") :
				section.getString("disabled-material");

		return ItemBuilder.fromConfiguration(section)
				.setMaterial(material);
	}

	public ConfigurationSection getSettings() {
		FileConfiguration config = FileManager.Files.CONFIG.getFile();
		return config.getConfigurationSection("mass-key-gui");
	}
}
