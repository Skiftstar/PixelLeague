package Yukami.PixelLeague.Saver_Loader;

import Yukami.PixelLeague.Main;
import Yukami.PixelLeague.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveCommand implements CommandExecutor {

    private Main plugin;

    public SaveCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("savepart").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Util.getMess("playerOnlyCommand"));
            return false;
        }
        Player p = (Player) commandSender;

        if (!p.hasPermission("PixelLeague.savePart")) {
            p.sendMessage(Util.getMess("NEPermissions"));
            return false;
        }

        if (strings.length < 2) {
            p.sendMessage(Util.getMess("savePartUsage"));
            return false;
        }
        if (!(strings[0].equalsIgnoreCase("easy") || strings[0].equalsIgnoreCase("medium") || strings[0].equalsIgnoreCase("hard") || strings[0].equalsIgnoreCase("hardcore"))) {
            p.sendMessage(Util.getMess("savePartUsage"));
            return false;
        }
        if (!plugin.pos.hasBothPos(p)) {
            p.sendMessage(Util.getMess("noPositionsSet"));
            return false;
        }
        final Location[] cords = plugin.pos.getPos(p);
        if (plugin.partsConfig.get(strings[0].toLowerCase() + "." + strings[1].toLowerCase()) != null) {
            p.sendMessage(Util.getMess("partNameTaken"));
            return false;
        }
        if (Saver.saveArena(cords, strings[0].toLowerCase(), strings[1].toLowerCase())) {
            p.sendMessage(Util.getMess("partSaved"));
            return true;
        }
        p.sendMessage(Util.getMess("invalidBlocksInPart"));


        return false;
    }
}
