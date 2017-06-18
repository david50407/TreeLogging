package tw.davy.minecraft.treelogging.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import tw.davy.minecraft.treelogging.bukkit.executor.TLCommandExecutor;
import tw.davy.minecraft.treelogging.bukkit.listener.BlockListener;
import tw.davy.minecraft.treelogging.bukkit.listener.PlayerListener;
import tw.davy.minecraft.treelogging.database.Database;

/**
 * The main class for TreeLogging as a Bukkit plugin.
 *
 * @author Davy
 */
public final class TreeLoggingPlugin extends JavaPlugin {
    public Database database = null;
    public StateManager states;

    /**
     * Called on plugin enable.
     */
    public void onEnable() {
        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.database = new Database(this);
        this.states = new StateManager(this);
        (new BlockListener(this)).registerEvents();
        (new PlayerListener(this)).registerEvents();
        getCommand("tl").setExecutor(new TLCommandExecutor(this));
        getLogger().info("Enabled.");
    }

    /**
     * Called on plugin disable.
     */
    public void onDisable() {
        getLogger().info("Disabled.");
    }

    /**
     * Called on command trigged.
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("treelogging")) {
            //if (!(sender instanceof Player)) {
            //} else {
            //   Player player = (Player) sender;
            //}
            sender.sendMessage("TreeLogging, Makes logging trees truely.");
            sender.sendMessage("    Version: " + getDescription().getVersion());
            return true;
        }
        return false;
    }
}
