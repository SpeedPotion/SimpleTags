package SpeedTags.Managers;


import SpeedTags.Enums.ConfigTypes;
import SpeedTags.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager {

    private UUID uuid;
    private static File userFile;
    private static YamlConfiguration UserConfig;


    public PlayerManager(UUID u) {

        this.uuid = u;
        userFile = new File(Main.getInstance().getDataFolder() + "/Users", u + ".yml");
        UserConfig = YamlConfiguration.loadConfiguration(userFile);

    }

    public void createUser() { //Create user file if not created!
        if (!(userFile.exists())) {
            try {
                YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(userFile);

                UserConfig.set("Name", getPlayer().getName());
                UserConfig.set("UUID", uuid.toString());
                UserConfig.set("Tags", new ArrayList<>());
                UserConfig.set("Actual", " ");
                UserConfig.save(userFile);


            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void getHelp() { //Sends player help menu
        getPlayer().sendMessage("§b§lSKYBLOCK.FUN TAGS");
        getPlayer().sendMessage(" ");
        getPlayer().sendMessage("§b* §f/tags - Opens the tags GUI");
        getPlayer().sendMessage("§b* §f/tags help - Opens this menu");
        getPlayer().sendMessage("§b* §f/tags edit <TagName> - Opens the edit tag GUI");
        if (getPlayer().hasPermission("sbftags.admin")) {
            getPlayer().sendMessage("§b* §f/tags add <Playername> <TagName> - Opens the edit tag GUI");
            getPlayer().sendMessage("§b* §f/tags remove <Playername> <TagName> - Opens the edit tag GUI");
        }
        getPlayer().sendMessage(" ");
    }

    public void sendPLayerError(String player) { //Sends admin that the player doesn't exists
        getPlayer().sendMessage(ConfigTypes.getMessage(ConfigTypes.PREFIX) + " " + ConfigTypes.getMessage(ConfigTypes.NO_PLAYER_FOUND).replace("{player}", player));
    }

    public void sendNoExistTag(String tagname) { //Sends admin that the tag he tried to add to the player doesn't exist
        getPlayer().sendMessage(ConfigTypes.getMessage(ConfigTypes.PREFIX) + " " + ConfigTypes.getMessage(ConfigTypes.TAG_DOESNT_EXIST).replace("{tag}", tagname));
    }

    public void sendPlayerNoTag(String tagname, String player) { //When admin removing tag and user doesnt contain the tag
        getPlayer().sendMessage(ConfigTypes.getMessage(ConfigTypes.PREFIX) + " " + ConfigTypes.getMessage(ConfigTypes.PLAYER_DOESNT_HAVE_THAT_TAG).replace("{tag}", tagname).replace("{player}", player));
    }

    public void sendPlayerAlreadyHasTag(String tagname, String player) { //When admin adding a tag, player already has that tag
        getPlayer().sendMessage(ConfigTypes.getMessage(ConfigTypes.PREFIX) + " " + ConfigTypes.getMessage(ConfigTypes.PLAYER_ALREADY_HAS_TAG).replace("{tag}", tagname).replace("{player}", player));
    }

    public void sendAddedTag(String tagname, String player) { //When admin adds a tag to a user.
        getPlayer().sendMessage(ConfigTypes.getMessage(ConfigTypes.PREFIX) + " " + ConfigTypes.getMessage(ConfigTypes.ADDED_TAG).replace("{tag}", tagname).replace("{player}", player));
    }

    public void sendRemovedTag(String tagname, String player) { //When admin removed a tag to the user.
        getPlayer().sendMessage(ConfigTypes.getMessage(ConfigTypes.PREFIX) + " " + ConfigTypes.getMessage(ConfigTypes.REMOVED_TAG).replace("{tag}", tagname).replace("{player}", player));
    }
    public void sendNewTagdoesntMatch() { //When editing tag and new tag doesn't match teh old one
        getPlayer().sendMessage(ConfigTypes.getMessage(ConfigTypes.PREFIX) + " " + ConfigTypes.getMessage(ConfigTypes.REMOVED_TAG));
    }

    public String getPlayerTag(String tagname) { //Gets player custom tag
        if (UserConfig.contains("Tags." + tagname)) {
            return ChatColor.translateAlternateColorCodes('&', UserConfig.getString("Tags." + tagname));
        } else {
            return ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Tags." + tagname));
        }
    }

    public boolean tagExists(String tagname) { //check if tag exists in the main config file
        return Main.getInstance().getConfig().contains("Tags." + tagname);
    }

    public String getActualTag() {
        return UserConfig.getString("Actual");
    }
    public void setActualTag(String tag) throws IOException {
        UserConfig.set("Actual", tag);
        UserConfig.save(userFile);
    }

    public void addTag(String tagname) throws IOException { //Adds tag to the user
        UserConfig.set("Tags." + tagname, tagname);
        UserConfig.save(userFile);

    }

    public void updateTag(String tagname, String colortag) throws IOException { //Adds tag to the user
        UserConfig.set("Tags." + tagname, colortag);
        UserConfig.save(userFile);
    }

    public void removeTag(String tagname) throws IOException { //Removes tag from the user
        UserConfig.set("Tags." + tagname, null);
        UserConfig.save(userFile);

    }

    public boolean hasTag(String tagname) { //Check if the user has access to a tag
        return (UserConfig.contains("Tags." + tagname));
    }

    public void openNormalGUI() { //Opens the normal GUI for a player.
        GUIManager manager = new GUIManager(getPlayer());
        getPlayer().openInventory(manager.getNormalGUI());
    }
    public void openEditGUI() { //Opens the edit GUI for a player.
        GUIManager manager = new GUIManager(getPlayer());
        getPlayer().openInventory(manager.getEditGUI());
    }

}
