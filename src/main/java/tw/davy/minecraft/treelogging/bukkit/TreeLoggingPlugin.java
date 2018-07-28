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
 * The main class for TreeLogging as a Bukkit Plugin.
 *
 * @author Davy
 */
public final class TreeLoggingPlugin extends JavaPlugin {
    static private final HashMap<String, List<Material>> sMaterialAliases = new HashMap<>();
    static {
        sMaterialAliases.put("FLUIDS", Arrays.asList(
                Material.WATER, Material.LAVA, Material.BUBBLE_COLUMN));
        sMaterialAliases.put("RAILS_RELATIVED", Arrays.asList(
                Material.RAIL, Material.POWERED_RAIL, Material.DETECTOR_RAIL, Material.ACTIVATOR_RAIL));
        sMaterialAliases.put("REDSTONE_RELATIVED", Arrays.asList(
                Material.PISTON, Material.PISTON_HEAD, Material.STICKY_PISTON, Material.MOVING_PISTON,
                Material.REDSTONE_WIRE, Material.REDSTONE_TORCH, Material.REDSTONE_WALL_TORCH,
                Material.ACACIA_BUTTON, Material.BIRCH_BUTTON, Material.DARK_OAK_BUTTON, Material.JUNGLE_BUTTON,
                Material.OAK_BUTTON, Material.SPRUCE_BUTTON, Material.STONE_BUTTON,
                Material.TRIPWIRE_HOOK, Material.TRIPWIRE,
                Material.LEVER));
        sMaterialAliases.put("DOORS", Arrays.asList(
                Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR, Material.JUNGLE_DOOR,
                Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.IRON_DOOR,
                Material.ACACIA_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.JUNGLE_TRAPDOOR,
                Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.IRON_TRAPDOOR));
        sMaterialAliases.put("SIGNS", Arrays.asList(
                Material.SIGN, Material.WALL_SIGN));
        sMaterialAliases.put("SAPLINGS", Arrays.asList(
                Material.OAK_SAPLING, Material.SPRUCE_SAPLING, Material.BIRCH_SAPLING,
                Material.JUNGLE_SAPLING, Material.ACACIA_SAPLING, Material.DARK_OAK_SAPLING));
        sMaterialAliases.put("PLANTS", Arrays.asList(
                Material.GRASS, Material.TALL_GRASS, Material.SEAGRASS, Material.TALL_SEAGRASS,
                Material.SUNFLOWER, Material.LILAC, Material.LARGE_FERN, Material.ROSE_BUSH, Material.PEONY,
                Material.DANDELION, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP,
                Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY,
                Material.DEAD_BUSH, Material.FERN, Material.CACTUS, Material.VINE, Material.SUGAR_CANE,
                Material.PUMPKIN_SEEDS, Material.PUMPKIN_STEM,
                Material.MELON_SEEDS, Material.MELON_STEM,
                Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.MUSHROOM_STEM,
                Material.WHEAT_SEEDS, Material.WHEAT,
                Material.BEETROOT_SEEDS, Material.BEETROOT,
                Material.LILY_PAD));
        sMaterialAliases.put("FLOWER_POTS", Arrays.asList(
                Material.FLOWER_POT,
                Material.POTTED_POPPY, Material.POTTED_DANDELION, Material.POTTED_OAK_SAPLING,
                Material.POTTED_SPRUCE_SAPLING, Material.POTTED_BIRCH_SAPLING, Material.POTTED_JUNGLE_SAPLING,
                Material.POTTED_RED_MUSHROOM, Material.POTTED_BROWN_MUSHROOM, Material.POTTED_CACTUS,
                Material.POTTED_DEAD_BUSH, Material.POTTED_FERN, Material.POTTED_ACACIA_SAPLING,
                Material.POTTED_DARK_OAK_SAPLING, Material.POTTED_BLUE_ORCHID, Material.POTTED_ALLIUM,
                Material.POTTED_AZURE_BLUET, Material.POTTED_RED_TULIP, Material.POTTED_ORANGE_TULIP,
                Material.POTTED_WHITE_TULIP, Material.POTTED_PINK_TULIP, Material.POTTED_OXEYE_DAISY));
        sMaterialAliases.put("SKULLS", Arrays.asList(
                Material.SKELETON_SKULL, Material.SKELETON_WALL_SKULL,
                Material.WITHER_SKELETON_SKULL, Material.WITHER_SKELETON_WALL_SKULL,
                Material.ZOMBIE_HEAD, Material.ZOMBIE_WALL_HEAD,
                Material.PLAYER_HEAD, Material.PLAYER_WALL_HEAD,
                Material.CREEPER_HEAD, Material.CREEPER_WALL_HEAD,
                Material.DRAGON_HEAD, Material.DRAGON_WALL_HEAD));
        sMaterialAliases.put("BANNERS", Arrays.asList(
                Material.WHITE_BANNER, Material.WHITE_WALL_BANNER,
                Material.ORANGE_BANNER, Material.ORANGE_WALL_BANNER,
                Material.MAGENTA_BANNER, Material.MAGENTA_WALL_BANNER,
                Material.LIGHT_BLUE_BANNER, Material.LIGHT_BLUE_WALL_BANNER,
                Material.YELLOW_BANNER, Material.YELLOW_WALL_BANNER,
                Material.LIME_BANNER, Material.LIME_WALL_BANNER,
                Material.PINK_BANNER, Material.PINK_WALL_BANNER,
                Material.GRAY_BANNER, Material.GRAY_WALL_BANNER,
                Material.LIGHT_GRAY_BANNER, Material.LIGHT_GRAY_WALL_BANNER,
                Material.CYAN_BANNER, Material.CYAN_WALL_BANNER,
                Material.PURPLE_BANNER, Material.PURPLE_WALL_BANNER,
                Material.BLUE_BANNER, Material.BLUE_WALL_BANNER,
                Material.BROWN_BANNER, Material.BROWN_WALL_BANNER,
                Material.GREEN_BANNER, Material.GREEN_WALL_BANNER,
                Material.RED_BANNER, Material.RED_WALL_BANNER,
                Material.BLACK_BANNER, Material.BLACK_WALL_BANNER));
        sMaterialAliases.put("CARPETS", Arrays.asList(
                Material.WHITE_CARPET, Material.ORANGE_CARPET, Material.MAGENTA_CARPET, Material.LIGHT_BLUE_CARPET,
                Material.YELLOW_CARPET, Material.LIME_CARPET, Material.PINK_CARPET, Material.GRAY_CARPET,
                Material.LIGHT_GRAY_CARPET, Material.CYAN_CARPET, Material.PURPLE_CARPET, Material.BLUE_CARPET,
                Material.BROWN_CARPET, Material.GREEN_CARPET, Material.RED_CARPET, Material.BLACK_CARPET));
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
