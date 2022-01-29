package com.badbones69.crazycrates.func.guis

import com.badbones69.crazycrates.api.FileManager
import com.badbones69.crazycrates.func.color
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import net.kyori.adventure.text.Component
import org.bukkit.Material

class PreviewMenu {

    val previewMenu = Gui.paginated()
        .title(Component.text(color("")))
        .rows(3)
        .pageSize(45)
        .create()

    private val config = FileManager.Files.CONFIG.file
    private val path = "Settings.Preview.Buttons."

    val menuButton = config.getString("$path.Menu.Item", "COMPASS")
    val menuName = config.getString("$path.Menu.Name", "")
    val menuLore = if (config.contains("$path.Menu.Lore")) config.getStringList("$path.Menu.Lore") else listOf("&7Return to the menu.")

    val nextButton = config.getString("$path.Next.Item", "FEATHER")
    val nextName = config.getString("$path.Next.Name", "&6&lNext >>")
    val nextLore = if (config.contains("$path.Next.Lore")) config.getStringList("$path.Next.Lore") else listOf("&7&lPage: &b%page%")

    val backButton = config.getString("$path.Back.Item", "FEATHER")
    val backName = config.getString("$path.Back.Name", "&6&l<< Back")
    val backLore = if (config.contains("$path.Back.Lore")) config.getStringList("$path.Back.Lore") else listOf("&7&lPage: &b%page%")
    val backBuilder = ItemBuilder.from(Material.ACACIA_BOAT).name(Component.text(""))
}