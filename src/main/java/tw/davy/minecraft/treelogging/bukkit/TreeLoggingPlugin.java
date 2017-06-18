package tw.davy.minecraft.treelogging.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tw.davy.minecraft.treelogging.bukkit.executor.TLCommandExecutor;
import tw.davy.minecraft.treelogging.bukkit.listener.BlockListener;
import tw.davy.minecraft.treelogging.bukkit.listener.PlayerListener;
import tw.davy.minecraft.treelogging.database.Database;

/**
 * The main class for TreeLogging as a Bukkit mPlugin.
 *
 * @author Davy
 */
public final class TreeLoggingPlugin extends JavaPlugin {
    private Database mDatabase;
    private StateManager mStateManager;

    /**
     * Called on mPlugin enable.
     */
    public void onEnable() {
        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mDatabase = new Database(this);
        mStateManager = new StateManager(this);

        final PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new BlockListener(this), this);
        pluginManager.registerEvents(new PlayerListener(this), this);
        getCommand("tl").setExecutor(new TLCommandExecutor(this));
        getLogger().info("Enabled.");
    }

    /**
     * Called on mPlugin disable.
     */
    public void onDisable() {
        getLogger().info("Disabled.");
    }

    /**
     * Called on command trigged.
     */
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
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

    public Database getRecords() {
        return mDatabase;
    }

    public StateManager getStateManager() {
        return mStateManager;
    }
}
