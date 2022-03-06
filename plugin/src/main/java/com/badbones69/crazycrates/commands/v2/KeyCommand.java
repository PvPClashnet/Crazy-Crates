package com.badbones69.crazycrates.commands.v2;

import com.badbones69.crazycrates.api.CrazyManager;
import com.badbones69.crazycrates.api.enums.Messages;
import com.badbones69.crazycrates.api.objects.Crate;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Command(value = "key", alias = {"keys"})
public class KeyCommand extends BaseCommand {

    @Default
    @Permission("crazycrates.keys.access")
    public void onKey(Player sender) {
        List<String> message = new ArrayList<>();
        message.add(Messages.PERSONAL_HEADER.getMessageNoPrefix());
        HashMap<Crate, Integer> keys = CrazyManager.getInstance().getVirtualKeys(sender);

        boolean hasKeys = false;

        for (Crate crate : keys.keySet()) {
            int amount = keys.get(crate);
            if (amount > 0) {
                hasKeys = true;
                HashMap<String, String> placeholders = new HashMap<>();
                placeholders.put("%Crate%", crate.getFile().getString("Crate.Name", ""));
                placeholders.put("%Keys%", amount + "");
                message.add(Messages.PER_CRATE.getMessageNoPrefix(placeholders));
            }
        }

        if (hasKeys) {
            sender.sendMessage(Messages.convertList(message));
        } else {
            sender.sendMessage(Messages.PERSONAL_NO_VIRTUAL_KEYS.getMessage());
        }
    }
}