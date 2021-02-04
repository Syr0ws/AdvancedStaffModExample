package fr.syrows.advancedstaffmodexample.staffmod;

import fr.syrows.advancedstaffmodexample.staffmod.items.*;
import fr.syrows.staffmodlib.StaffModManager;
import fr.syrows.staffmodlib.data.*;
import fr.syrows.staffmodlib.staffmod.PageableStaffMod;
import fr.syrows.staffmodlib.staffmod.items.AbstractPageItem;
import fr.syrows.staffmodlib.staffmod.items.StaffModItem;
import fr.syrows.staffmodlib.util.Configurable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class AdvancedStaffMod extends PageableStaffMod implements Configurable {

    // Plugin will be needed to register item listeners.
    private final Plugin plugin;

    // By default, items are not stored even if you call the registerItem(StaffModItem item) method.
    // So, you have to create this list in this implementation.

    public AdvancedStaffMod(StaffModManager manager, Plugin plugin) {
        super(manager);
        this.plugin = plugin;
    }

    @Override
    public AbstractPageItem getNextPageItem() {
        return new PageItem(this.getPlayer(), this.plugin, this, AbstractPageItem.PageItemType.NEXT);
    }

    @Override
    public AbstractPageItem getPreviousPageItem() {
        return new PageItem(this.getPlayer(), this.plugin, this, AbstractPageItem.PageItemType.PREVIOUS);
    }

    @Override
    public void registerItems(Player player) {

        // Registering our items. The method registerItem(StaffModItem item)
        // must be performed on each item you want to register because an item
        // may need an initialization.

        // Declaring items.
        StaffModItem vanishItem = new VanishItem(player, this.plugin);
        StaffModItem freezeItem = new FreezeItem(player, this.plugin);
        StaffModItem invseeItem = new InvseeItem(player, this.plugin);

        // WARNING : Do not set slot here. They will be added automatically
        // by the pagination system.

        // Registering items.
        this.registerItem(vanishItem);
        this.registerItem(freezeItem);
        this.registerItem(invseeItem);

        // Adding test items for the example.
        for(int i = 0; i < 30; i++) this.registerItem(new TestItem(player));

        // Configuring the staff mod.
        this.configure(this.getConfigurationSection());
    }

    @Override
    public PlayerData createPlayerData() {

        // This method has for goal to create a PlayerData object
        // which will store player state before enabling the staff mod.

        List<Data> data = Arrays.asList(
                new InventoryData(), // Storing inventory information.
                new PotionData(), // Storing potion effects information.
                new GameModeData(), // Storing current game mode.
                new HealthData(), // Storing health information.
                new FoodData() // Storing food information.
        );
        return new PlayerData(data);
    }

    @Override
    public void configure(ConfigurationSection parent) {

        // Configuring each item that implements Configurable.
        // You can notice that the getAllItems() method is used
        // instead of the getItems() which will only return the
        // items of the current page.
        this.getAllItems().stream()
                .filter(item -> item instanceof Configurable)
                .map(item -> (Configurable) item)
                .forEach(configurable -> configurable.configure(parent));
    }

    private ConfigurationSection getConfigurationSection() {
        return this.plugin.getConfig().getConfigurationSection("staffmod");
    }
}
