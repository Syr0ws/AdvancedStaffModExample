package fr.syrows.advancedstaffmodexample.staffmod.items;

import fr.syrows.staffmodlib.staffmod.PageableStaffMod;
import fr.syrows.staffmodlib.staffmod.items.AbstractPageItem;
import fr.syrows.staffmodlib.util.Configurable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PageItem extends AbstractPageItem implements Configurable {

    private final PageItemType type;
    private ItemStack item;

    public PageItem(Player holder, Plugin plugin, PageableStaffMod staffMod, PageItemType type) {
        super(holder, plugin, staffMod);
        this.type = type;
    }

    @Override
    protected PageItemType getPageItemType() {
        return this.type;
    }

    @Override
    public ItemStack getItemStack() {
        return this.item.clone();
    }

    @Override
    public void configure(ConfigurationSection parent) {

        // If page type is PREVIOUS, then the key is 'previous-page'.
        // Else, the key is 'next-page'.
        String key = this.type == PageItemType.PREVIOUS ? "previous-page" : "next-page";

        ConfigurationSection section = parent.getConfigurationSection(key);

        this.item = section.getItemStack("item").clone();
    }
}
