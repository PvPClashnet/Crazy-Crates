package me.badbones69.crazycrates.masskey;

import me.badbones69.crazycrates.api.CrazyCrates;
import me.badbones69.crazycrates.api.events.PlayerPrizeEvent;
import me.badbones69.crazycrates.api.objects.Crate;
import me.badbones69.crazycrates.api.objects.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;

public class MassKeyGUIListenerHandler implements Listener {

	private final MassKeyService massKeyService;
	private final MassKeyGUI massKeyGUI;

	private final CrazyCrates plugin = CrazyCrates.getInstance();

	public MassKeyGUIListenerHandler(MassKeyService massKeyService, MassKeyGUI massKeyGUI) {
		this.massKeyService = massKeyService;
		this.massKeyGUI = massKeyGUI;
	}

	@EventHandler
	public void onFinishPrize(PlayerPrizeEvent event) {
		Player player = event.getPlayer();
		Crate crate = event.getCrate();

		MassKeySession session = massKeyService.sessionOf(player);
		session.removeOneKey();

		if (session.isEmpty()) {
			if (player.hasMetadata(MassKeyGUI.METADATA_KEY)) {
				player.closeInventory();
				massKeyService.finishSession(player);
				plugin.removePlayerFromOpeningList(player);
			} else {
				massKeyGUI.open(player, crate);
			}

			return;
		}

		plugin.openMassedCrate(
				player,
				session.getCrate(),
				session.getType(),
				session.getLocation(),
				session.isVirtualCrate(),
				session.isCheckHand()
		);
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();

		if (!massKeyService.isInInventory(player)) {
			return;
		}

		plugin.removePlayerFromOpeningList(player);
		massKeyService.closeInventory(player);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (!massKeyService.isInInventory(player)) {
			return;
		}

		event.setCancelled(true);

		int slot = event.getSlot();
		Inventory inventory = event.getClickedInventory();
		ItemStack clicked = event.getCurrentItem();

		if (Objects.isNull(clicked) || clicked.getType() == Material.AIR) {
			return;
		}

		handleToggle(player, slot, inventory);
		handleMassKey(player, clicked, slot);
	}

	private void handleMassKey(Player player, ItemStack clicked, int slot) {
		ConfigurationSection key = massKeyGUI.getSettings().getConfigurationSection("mass-keys." + clicked.getAmount());
		if (key == null || key.getInt("slot") != slot) {
			return;
		}
		MassKeySession session = massKeyService.sessionOf(player);
		session.setKeys(key.getInt("amount"));

		if (session.getKeyAmountLeft() > plugin.getTotalKeys(player, session.getCrate())) {
			return;
		}

		massKeyService.closeInventory(player);
		plugin.openMassedCrate(
				player,
				session.getCrate(),
				session.getType(),
				session.getLocation(),
				session.isVirtualCrate(),
				session.isCheckHand()
		);
	}

	private void handleToggle(Player player, int slot, Inventory inventory) {
		ItemBuilder toggleItem = massKeyGUI.provideToggleItem(player);

		if (toggleItem.getSlot() != slot) {
			return;
		}

		if (player.hasMetadata(MassKeyGUI.METADATA_KEY)) {
			player.removeMetadata(MassKeyGUI.METADATA_KEY, CrazyCrates.getInstance().getPlugin());
		} else {
			player.setMetadata(MassKeyGUI.METADATA_KEY, new FixedMetadataValue(plugin.getPlugin(), true));
		}

		massKeyGUI.setItem(inventory, massKeyGUI.provideToggleItem(player));
	}

}
