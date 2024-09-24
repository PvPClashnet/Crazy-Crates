package com.badbones69.crazycrates.commands.crates.types.admin;

import com.badbones69.crazycrates.api.enums.Messages;
import com.badbones69.crazycrates.api.enums.misc.Files;
import com.badbones69.crazycrates.utils.MiscUtils;
import com.badbones69.crazycrates.commands.crates.types.BaseCommand;
import com.badbones69.crazycrates.managers.config.impl.ConfigKeys;
import com.ryderbelserion.vital.common.utils.FileUtil;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import java.io.File;

public class CommandReload extends BaseCommand {

    @Command("reload")
    @Permission(value = "crazycrates.reload", def = PermissionDefault.OP)
    public void reload(CommandSender sender) {
        final File logsFolder = new File(this.plugin.getDataFolder(), "logs");

        if (logsFolder.exists() && this.config.getProperty(ConfigKeys.log_to_file)) {
            FileUtil.zip(Files.crate_log.getFile(), logsFolder, "", true);
            FileUtil.zip(Files.key_log.getFile(), logsFolder, "", true);
        }

        this.plugin.getInstance().reload();

        this.fileManager.reloadFiles().init();

        MiscUtils.save();

        if (this.config.getProperty(ConfigKeys.take_out_of_preview)) {
            this.inventoryManager.closePreview();
        }

        this.crateManager.loadHolograms();

        this.crateManager.loadCrates();

        Messages.reloaded_plugin.sendMessage(sender);
    }
}