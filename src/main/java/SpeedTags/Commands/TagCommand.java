package SpeedTags.Commands;

import SpeedTags.Managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class TagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("tags")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                PlayerManager playerManager = new PlayerManager(player.getUniqueId());
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        playerManager.getHelp();
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("edit")) {
                        playerManager.openEditGUI();
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("add") && (player.hasPermission("sbftags.admin") || player.isOp()) && args.length > 2) {

                        if(Bukkit.getPlayer(args[1]) != null){
                            Player reciever = Bukkit.getPlayer(args[1]);
                            PlayerManager preciever = new PlayerManager(reciever.getUniqueId());
                            if(preciever.tagExists(args[2])) {
                                if(!(preciever.hasTag(args[2]))) {
                                    try {
                                        preciever.addTag(args[2]);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    playerManager.sendAddedTag(args[2], args[1]);
                                    return true;
                                } else {
                                    playerManager.sendPlayerAlreadyHasTag(args[2], args[1]);
                                    return true;
                                }
                            } else {
                                playerManager.sendNoExistTag(args[2]);
                                return true;
                            }
                        } else {
                            playerManager.sendPLayerError(args[1]);
                            return true;
                        }
                    }
                    if (args[0].equalsIgnoreCase("remove") && (player.hasPermission("sbftags.admin") || player.isOp()) && args.length > 2) {
                        if(Bukkit.getPlayer(args[1]) != null){
                            Player reciever = Bukkit.getPlayer(args[1]);
                            PlayerManager preciever = new PlayerManager(reciever.getUniqueId());
                            if(preciever.hasTag(args[2])){
                                if((preciever.hasTag(args[2]))) {
                                    try {
                                        preciever.removeTag(args[2]);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    playerManager.sendRemovedTag(args[2], args[1]);
                                    return true;
                                } else {
                                    playerManager.sendPlayerAlreadyHasTag(args[2], args[1]);
                                    return true;
                                }
                            } else {
                                playerManager.sendPlayerNoTag(args[2], args[1]);
                                return true;
                            }
                        } else {
                            playerManager.sendPLayerError(args[1]);
                            return true;
                        }
                    }
                    playerManager.getHelp();
                } else {
                    playerManager.openNormalGUI();
                    return true;
                }
            }
        }
        return false;
    }
}
