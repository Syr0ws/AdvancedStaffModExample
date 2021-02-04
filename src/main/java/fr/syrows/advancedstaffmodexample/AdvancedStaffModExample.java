package fr.syrows.advancedstaffmodexample;

import fr.syrows.advancedstaffmodexample.commands.CommandAdvancedStaffMod;
import fr.syrows.staffmodlib.StaffModManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedStaffModExample extends JavaPlugin {

    private StaffModManager manager;

    @Override
    public void onEnable() {

        // Loading default configuration.
        super.saveDefaultConfig();

        // Creating a new StaffModManager instance which will
        // be used by all the staff mods.
        this.manager = new StaffModManager(this);

        // Registering commands.
        this.registerCommands();
    }

    private void registerCommands() {
        super.getCommand("advancedstaffmod").setExecutor(new CommandAdvancedStaffMod(this, this.manager));
    }
}
