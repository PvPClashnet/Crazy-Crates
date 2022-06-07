package me.badbones69.crazycrates.masskey;

import me.badbones69.crazycrates.api.enums.KeyType;
import me.badbones69.crazycrates.api.objects.Crate;
import org.bukkit.Location;

import java.util.UUID;

public class MassKeySession {

	private final KeyType type;
	private final UUID author;

	private final Crate crate;

	private int keyAmount;

	private final Location location;

	private final boolean checkHand;

	private final boolean virtualCrate;

	public MassKeySession(UUID author, KeyType type, Crate crate, int keyAmount, Location location, boolean checkHand, boolean virtualCrate) {
		this.author = author;
		this.type = type;
		this.crate = crate;
		this.keyAmount = keyAmount;
		this.location = location;
		this.checkHand = checkHand;
		this.virtualCrate = virtualCrate;
	}

	public boolean isCheckHand() {
		return checkHand;
	}

	public Location getLocation() {
		return location;
	}

	public void setKeys(int keyAmount) {
		this.keyAmount = keyAmount;
	}

	public Crate getCrate() {
		return crate;
	}

	public void removeOneKey() {
		keyAmount--;
	}

	public boolean isEmpty() {
		return keyAmount <= 0;
	}

	public int getKeyAmountLeft() {
		return keyAmount;
	}

	public KeyType getType() {
		return type;
	}

	public UUID getAuthor() {
		return author;
	}

	public boolean isVirtualCrate() {
		return virtualCrate;
	}
}
