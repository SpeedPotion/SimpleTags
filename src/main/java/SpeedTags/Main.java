package SpeedTags;

import SpeedTags.Commands.TagCommand;
import SpeedTags.Events.Events;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private ConsoleCommandSender console = getServer().getConsoleSender();
    static Main instance;

    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        try{
            PluginManager pm = this.getServer().getPluginManager();
            pm.registerEvents(new Events(), this);
            console.sendMessage("[SBFTags] Events have successfully been loaded!");
        } catch (Exception e){
            console.sendMessage("[SBFTags] Events have failed while loading please contact SpeedPotion!");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        try{
            this.getCommand("tags").setExecutor(new TagCommand());
            console.sendMessage("[SBFTags] Commands have successfully been loaded!");
        } catch (Exception e){
            console.sendMessage("[SBFTags] Commands have failed while loading please contact SpeedPotion!");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        console.sendMessage("[SBFTags] Plugin has loaded correctly!");
    }
    public void onDisable() {

    }
    public static Main getInstance(){
        return instance;
    }
}
