package fr.syrows.advancedstaffmodexample.staffmod.items;

import fr.syrows.staffmodlib.bukkit.configuration.Configurable;
import fr.syrows.staffmodlib.bukkit.items.BukkitStaffModItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class TestItem extends BukkitStaffModItem implements Configurable {

    private ItemStack item;

    @Override
    public ItemStack getItem() {
        return this.item.clone();
    }

    @Override
    public void configure(ConfigurationSection parent) {

        ConfigurationSection section = parent.getConfigurationSection("test-item");

        this.item = section.getItemStack("item");
    }
}
