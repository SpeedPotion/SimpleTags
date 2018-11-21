package SpeedTags.Managers;

import SpeedTags.Enums.ConfigTypes;
import SpeedTags.Main;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUIManager {

    PlayerManager playerManager;

    public GUIManager(Player player) {
        this.playerManager = new PlayerManager(player.getUniqueId());
    }

    public Inventory getNormalGUI() { //Function opens GUI for player, supports editor mode
        int pos = 0;
        Inventory inv = Bukkit.createInventory(null, 54, ConfigTypes.getMessage(ConfigTypes.NORMAL_GUI_NAME));
        for (String key : Main.getInstance().getConfig().getConfigurationSection("Tags").getKeys(false)) {
            if (pos <= 45)
                inv.setItem(pos, getItemFromTag(key));
            pos++;
        }
        inv.setItem(49, getEditItem());
        return inv;

    }

    public Inventory getEditGUI() { //Function opens editor GUI
        int pos = 0;
        Inventory inv = Bukkit.createInventory(null, 54, ConfigTypes.getMessage(ConfigTypes.EDIT_GUI_NAME));
        for (String key : Main.getInstance().getConfig().getConfigurationSection("Tags").getKeys(false)) {
            if (pos <= 45)
                if (playerManager.hasTag(key)) {
                    inv.setItem(pos, getItemFromTag(key));
                    pos++;
                }
        }
        return inv;
    }

    public void getAnvilGUI(Player myplayer) { //Function opens editor GUI
        new AnvilGUI(Main.getInstance(), myplayer, ConfigTypes.getMessage(ConfigTypes.ANVIL_MESSAGE), (player, reply) -> {
            if (reply.equalsIgnoreCase("you")) {
                player.sendMessage("You have magical powers!");
                return null;
            }
            return "Incorrect.";
        });

    }

    boolean transformationAllowed(String otag, String newtag){
        return (ChatColor.stripColor(otag).equalsIgnoreCase(ChatColor.stripColor(newtag)));
    }
    String getOTag(ItemStack itemStack){
        return itemStack.getItemMeta().getLore().get(0);
    }

    public ItemStack getItemFromTag(String tag) { //Check if player has access to tag, if so return green pane, if not, red pane
        if (playerManager.hasTag(tag)) {
            ItemStack yes = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta yesmeta = yes.getItemMeta();
            yesmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', playerManager.getPlayerTag(tag)));
            yes.setItemMeta(yesmeta);
            return yes;
        } else {
            ItemStack no = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            ItemMeta nometa = no.getItemMeta();
            nometa.setDisplayName(ChatColor.translateAlternateColorCodes('&', playerManager.getPlayerTag(tag)));
            no.setItemMeta(nometa);
            return no;
        }
    }

    public static ItemStack getEditItem() {
        ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ConfigTypes.getMessage(ConfigTypes.EDIT_MODE_ITEM_DISPLAYNAME));
        item.setItemMeta(meta);
        return item;
    }
}
