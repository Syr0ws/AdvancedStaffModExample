package fr.syrows.advancedstaffmodexample.commands;

import fr.syrows.advancedstaffmodexample.staffmod.AdvancedStaffMod;
import fr.syrows.staffmodlib.common.staffmod.StaffMod;
import fr.syrows.staffmodlib.common.staffmod.StaffModManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class CommandAdvancedStaffMod implements CommandExecutor {

    private final Plugin plugin;
    private final StaffModManager<Player, ItemStack> manager;

    public CommandAdvancedStaffMod(Plugin plugin, StaffModManager<Player, ItemStack>  manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Checking that the command sender is a Player.
        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        Optional<StaffMod<Player, ItemStack>> optional = this.manager.getStaffMod(player);

        if(optional.isPresent()) {

            StaffMod<Player, ItemStack> staffMod = optional.get();
            staffMod.disable(player); // Disabling staff mod.

        } else {

            StaffMod<Player, ItemStack> staffMod = new AdvancedStaffMod(this.manager, this.plugin);
            staffMod.enable(player); // Enabling staff mod.
        }
        return true;
    }
}
