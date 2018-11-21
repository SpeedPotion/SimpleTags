package SpeedTags.Events;

import SpeedTags.Enums.ConfigTypes;
import SpeedTags.Managers.GUIManager;
import SpeedTags.Managers.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;


public class Events implements Listener {


    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {

            Player player = (Player) e.getWhoClicked();
            GUIManager guiManager = new GUIManager(player);
            PlayerManager playerManager = new PlayerManager(player.getUniqueId());

            if ((e.getInventory().getName()).equalsIgnoreCase(ConfigTypes.getMessage(ConfigTypes.NORMAL_GUI_NAME))) {
                e.setCancelled(true);
                String tag = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                if (e.getCurrentItem().getType() == Material.NAME_TAG) {
                    if (playerManager.hasTag(tag)) {
                        try {
                            playerManager.setActualTag(tag);
                            player.closeInventory();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                    if (e.getCurrentItem().getType() == GUIManager.getEditItem().getType()) {
                        player.openInventory(guiManager.getEditGUI());
                    }
                }
            } else if ((e.getInventory().getName()).equalsIgnoreCase(ConfigTypes.getMessage(ConfigTypes.EDIT_GUI_NAME))) {
                guiManager.getAnvilGUI(player);
            }
        }

    }

    /*
    @EventHandler
    public void AnvilGUI(InventoryClickEvent e) throws IOException {
        Player player = (Player) e.getWhoClicked();
        PlayerManager playerManager = new PlayerManager(player.getUniqueId());
        Inventory inv = e.getInventory();
        String otag = " ";

        if (inv instanceof AnvilInventory) {
            InventoryView view = e.getView();
            int rawSlot = e.getRawSlot();
            if (rawSlot == view.convertSlot(rawSlot)) {
                if (rawSlot == 2) {
                    ItemStack item = e.getCurrentItem();
                    if (item != null) {
                        ItemMeta meta = item.getItemMeta();
                        if (meta != null) {
                            if (meta.hasDisplayName()) {

                                String newtag = ChatColor.stripColor(meta.getDisplayName());
                                for(ItemStack itemStack : inv.getContents()){
                                    if(itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()){

                                        otag = getOTag(itemStack);
                                    }
                                }
                                if(transformationAllowed(otag, newtag)){
                                    playerManager.updateTag(otag, newtag);
                                }
                                player.closeInventory();
                                playerManager.sendNewTagdoesntMatch();
                            }
                        }
                    }
                }
            }
        }
    }
    */

    @EventHandler
    public void joincreateuser(PlayerJoinEvent e) {
        PlayerManager playerManager = new PlayerManager(e.getPlayer().getUniqueId());
        playerManager.createUser();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        PlayerManager playerManager = new PlayerManager(e.getPlayer().getUniqueId());
        e.setFormat(playerManager.getActualTag() + " %s : %s");
    }
}
