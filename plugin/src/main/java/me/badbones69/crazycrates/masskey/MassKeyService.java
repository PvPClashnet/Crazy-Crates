package me.badbones69.crazycrates.masskey;

import me.badbones69.crazycrates.api.enums.KeyType;
import me.badbones69.crazycrates.api.objects.Crate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MassKeyService {

	private final Map<UUID, MassKeySession> sessions = new HashMap<>();
	private final Set<UUID> view = new HashSet<>();

	public void openInventory(Player player) {
		view.add(player.getUniqueId());
	}

	public boolean isInInventory(Player player) {
		return view.contains(player.getUniqueId());
	}

	public void closeInventory(Player player) {
		view.remove(player.getUniqueId());
	}

	public MassKeySession createSession(Player player, Crate crate, KeyType keyType, Location location, boolean checkHand, boolean virtualCrate) {
		MassKeySession session = new MassKeySession(player.getUniqueId(), keyType, crate, 0, location, checkHand, virtualCrate);
		sessions.put(session.getAuthor(), session);

		return session;
	}

	public void finishSession(Player player) {
		sessions.remove(player.getUniqueId());
	}

	public MassKeySession sessionOf(Player player) {
		return sessions.get(player.getUniqueId());
	}

}
