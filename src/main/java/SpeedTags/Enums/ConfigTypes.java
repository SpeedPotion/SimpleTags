package SpeedTags.Enums;


import SpeedTags.Main;
import org.bukkit.ChatColor;

public enum ConfigTypes {

    NORMAL_GUI_NAME,
    EDIT_GUI_NAME,
    NO_PLAYER_FOUND,
    TAG_DOESNT_MATCH,
    PLAYER_DOESNT_HAVE_THAT_TAG,
    TAG_DOESNT_EXIST,
    ADDED_TAG,
    REMOVED_TAG,
    PLAYER_ALREADY_HAS_TAG,
    ANVIL_MESSAGE,
    EDIT_MODE_ITEM_DISPLAYNAME,
    PREFIX;


    public static String getMessage(ConfigTypes types){
        return (ChatColor.translateAlternateColorCodes('&',(Main.getInstance().getConfig().getString("Messages." + types.name()))));
    }
}
