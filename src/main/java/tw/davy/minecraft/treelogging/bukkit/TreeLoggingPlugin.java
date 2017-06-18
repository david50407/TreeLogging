package tw.davy.minecraft.treelogging.bukkit;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    static final HashMap<String, List<Material>> sMaterialAliases = new HashMap<>();
    static {
        sMaterialAliases.put("FLUIDS", Arrays.asList(
                Material.WATER, Material.STATIONARY_WATER,
                Material.LAVA, Material.STATIONARY_LAVA));
        sMaterialAliases.put("RAILS_RELATIVED", Arrays.asList(
                Material.RAILS, Material.POWERED_RAIL, Material.DETECTOR_RAIL, Material.ACTIVATOR_RAIL));
        sMaterialAliases.put("REDSTONE_RELATIVED", Arrays.asList(
                Material.PISTON_EXTENSION, Material.PISTON_MOVING_PIECE,
                Material.REDSTONE_WIRE, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON,
                Material.STONE_BUTTON, Material.WOOD_BUTTON,
                Material.TRIPWIRE_HOOK, Material.TRIPWIRE,
                Material.LEVER));
        sMaterialAliases.put("DOORS", Arrays.asList(
                Material.WOODEN_DOOR, Material.IRON_DOOR_BLOCK,
                Material.TRAP_DOOR, Material.IRON_TRAPDOOR,
                Material.SPRUCE_DOOR, Material.BIRCH_DOOR, Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR));
        sMaterialAliases.put("SIGNS", Arrays.asList(
                Material.SIGN_POST, Material.WALL_SIGN));
        sMaterialAliases.put("PLANTS", Arrays.asList(
                Material.LONG_GRASS, Material.DOUBLE_PLANT,
                Material.DEAD_BUSH, Material.CROPS, Material.CACTUS, Material.VINE, Material.SUGAR_CANE_BLOCK,
                Material.YELLOW_FLOWER, Material.RED_ROSE,
                Material.BROWN_MUSHROOM, Material.RED_MUSHROOM));
    }

    private Database mDatabase;
    private StateManager mStateManager;
    private ArrayList<Material> mIgnoredBlocks = new ArrayList<>();

    /**
     * Called on mPlugin enable.
     */
    public void onEnable() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        saveDefaultConfig();
        final List<String> ignoredBlocks = getConfig().getStringList("ignoredBlocks");
        loadIgnoredBlocks(ignoredBlocks);

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

    public List<Material> getIgnoredBlocks() {
        return mIgnoredBlocks;
    }

    private void loadIgnoredBlocks(final List<String> ignores) {
        for (final String name : ignores) {
            final List<Material> materials = sMaterialAliases.getOrDefault(name.toUpperCase(), null);
            if (materials != null) {
                mIgnoredBlocks.addAll(materials);
            } else {
                final Material material = Material.getMaterial(name.toUpperCase());
                if (material != null)
                    mIgnoredBlocks.add(material);
            }
        }
    }
}
