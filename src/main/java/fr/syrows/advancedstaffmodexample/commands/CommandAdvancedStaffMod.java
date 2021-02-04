package fr.syrows.advancedstaffmodexample.commands;

import fr.syrows.advancedstaffmodexample.staffmod.AdvancedStaffMod;
import fr.syrows.staffmodlib.StaffModManager;
import fr.syrows.staffmodlib.staffmod.StaffMod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandAdvancedStaffMod implements CommandExecutor {

    private final Plugin plugin;
    private final StaffModManager manager;

    public CommandAdvancedStaffMod(Plugin plugin, StaffModManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Checking that the command sender is a Player.
        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        // Checking if the player is in staff mod.
        if(!this.manager.isInStaffMod(player)) {

            AdvancedStaffMod staffMod = new AdvancedStaffMod(this.manager, this.plugin);
            staffMod.enable(player); // Enabling staff mod.

        } else {

            StaffMod staffMod = this.manager.getNullableStaffMod(player);
            staffMod.disable(player); // Disabling staff mod.
        }
        return true;
    }
}
