package fr.syrows.advancedstaffmodexample.staffmod;

import fr.syrows.advancedstaffmodexample.staffmod.items.*;
import fr.syrows.staffmodlib.bukkit.configuration.Configurable;
import fr.syrows.staffmodlib.bukkit.data.*;
import fr.syrows.staffmodlib.bukkit.items.BukkitStaffModItem;
import fr.syrows.staffmodlib.bukkit.staffmod.PageableBukkitStaffMod;
import fr.syrows.staffmodlib.common.data.DataHandler;
import fr.syrows.staffmodlib.common.items.NavigationItem;
import fr.syrows.staffmodlib.common.items.NavigationType;
import fr.syrows.staffmodlib.common.staffmod.StaffModManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class AdvancedStaffMod extends PageableBukkitStaffMod implements Configurable {

    // Plugin will be needed to register item listeners.
    private final Plugin plugin;
    private PlayerDataHandler handler;

    public AdvancedStaffMod(StaffModManager<Player, ItemStack> manager, Plugin plugin) {
        super(manager);
        this.plugin = plugin;
    }

    @Override
    public void enable(Player holder) {

        this.registerItems(holder);

        // Saving player data.
        this.handler = this.createPlayerData();
        this.handler.save(holder);
        this.handler.clear(holder);

        // Must be executed at the end to set the items.
        super.enable(holder);
    }

    @Override
    public void disable(Player holder) {

        // Must be executed first to remove the items.
        super.disable(holder);

        // Restoring player data.
        this.handler.clear(holder);
        this.handler.restore(holder);
        this.handler = null; // Avoiding reuse.
    }

    @Override
    public NavigationItem<ItemStack> getPrevious() {
        return new NavItem(this.plugin, this, NavigationType.OPEN_PREVIOUS_PAGE);
    }

    @Override
    public NavigationItem<ItemStack> getNext() {
        return new NavItem(this.plugin, this, NavigationType.OPEN_NEXT_PAGE);
    }

    @Override
    public void configure(ConfigurationSection parent) {
        this.getPagination().getElements().stream()
                .filter(item -> item instanceof Configurable)
                .map(item -> (Configurable) item)
                .forEach(configurable -> configurable.configure(parent));
    }

   private void registerItems(Player player) {

        // Registering our items. The method registerItem(StaffModItem item)
        // must be performed on each item you want to register because an item
        // may need an initialization.

        // Declaring items.
        BukkitStaffModItem vanishItem = new VanishItem(player, this.plugin);
        BukkitStaffModItem freezeItem = new FreezeItem(player, this.plugin);
        BukkitStaffModItem invseeItem = new InvseeItem(player, this.plugin);

        // WARNING : Do not set slot here. They will be added automatically
        // by the pagination system.

        // Registering items.
        this.registerItem(vanishItem);
        this.registerItem(freezeItem);
        this.registerItem(invseeItem);

        // Adding test items for the example.
        for(int i = 0; i < 30; i++) this.registerItem(new TestItem());

        // Configuring the staff mod.
        this.configure(this.getConfigurationSection());
    }

    private PlayerDataHandler createPlayerData() {

        // This method has for goal to create a PlayerData object
        // which will store player state before enabling the staff mod.

        List<DataHandler<Player>> data = Arrays.asList(
                new InventoryDataHandler(), // Storing inventory information.
                new PotionDataHandler(), // Storing potion effects information.
                new GameModeDataHandler(), // Storing current game mode.
                new HealthDataHandler(), // Storing health information.
                new FoodDataHandler() // Storing food information.
        );
        return new PlayerDataHandler(data);
    }

    private ConfigurationSection getConfigurationSection() {
        return this.plugin.getConfig().getConfigurationSection("staffmod");
    }
}
