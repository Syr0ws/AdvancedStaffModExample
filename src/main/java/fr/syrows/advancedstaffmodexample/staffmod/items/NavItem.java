package fr.syrows.advancedstaffmodexample.staffmod.items;

import fr.syrows.staffmodlib.bukkit.configuration.Configurable;
import fr.syrows.staffmodlib.bukkit.items.BukkitNavigationItem;
import fr.syrows.staffmodlib.common.items.NavigationType;
import fr.syrows.staffmodlib.common.staffmod.PageableStaffMod;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class NavItem extends BukkitNavigationItem implements Configurable {

    private final NavigationType type;
    private ItemStack item;

    public NavItem(Plugin plugin, PageableStaffMod<Player, ItemStack> staffMod, NavigationType type) {
        super(plugin, staffMod);
        this.type = type;
    }

    @Override
    public NavigationType getNavigationType() {
        return this.type;
    }

    @Override
    public ItemStack getItem() {
        return this.item.clone();
    }

    @Override
    public void configure(ConfigurationSection parent) {

        // If page type is PREVIOUS, then the key is 'previous-page'.
        // Else, the key is 'next-page'.
        String key = this.type == NavigationType.OPEN_PREVIOUS_PAGE ? "previous-page" : "next-page";

        ConfigurationSection section = parent.getConfigurationSection(key);

        this.item = section.getItemStack("item").clone();
    }
}
