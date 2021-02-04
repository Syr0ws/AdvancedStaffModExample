package fr.syrows.advancedstaffmodexample.staffmod.items;

import fr.syrows.staffmodlib.staffmod.items.AbstractStaffModItem;
import fr.syrows.staffmodlib.util.Configurable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestItem extends AbstractStaffModItem implements Configurable {

    private ItemStack item;

    public TestItem(Player holder) {
        super(holder);
    }

    @Override
    public ItemStack getItemStack() {
        return this.item.clone();
    }

    @Override
    public void configure(ConfigurationSection parent) {

        ConfigurationSection section = parent.getConfigurationSection("test-item");

        this.item = section.getItemStack("item");
    }
}
