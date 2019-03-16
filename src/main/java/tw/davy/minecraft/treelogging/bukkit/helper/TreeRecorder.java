package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

import static tw.davy.minecraft.treelogging.bukkit.helper.MaterialChecker.isTree;

/**
 * Recorder for non-natural trees.
 *
 * @author Davy
 */
public class TreeRecorder {
    /**
     * Record non-natural tree.
     *
     * @param plugin The TreeLogging mPlugin
     * @param block  The block
     */
    public static void record(final TreeLoggingPlugin plugin, final Block block) {
        Material blockType = block.getType();
        if (isTree(blockType)) {
            plugin.getRecords().updateRecord(block);
        }
    }

    /**
     * Record non-natural tree.
     *
     * @param plugin The TreeLogging mPlugin
     * @param blocks The blocks
     */
    public static void record(final TreeLoggingPlugin plugin, final List<Block> blocks) {
        plugin.getRecords().updateRecords(blocks);
    }

    /**
     * Remove the record from the given block.
     *
     * @param plugin The TreeLogging mPlugin
     * @param block  The block
     */
    public static void remove(final TreeLoggingPlugin plugin, final Block block) {
        Material blockType = block.getType();
        if (isTree(blockType)) {
            plugin.getRecords().removeRecord(block);
        }
    }

    /**
     * Remove the record from the given blocks.
     *
     * @param plugin The TreeLogging mPlugin
     * @param blocks The blocks
     */
    public static void remove(final TreeLoggingPlugin plugin, final List<Block> blocks) {
        plugin.getRecords().removeRecords(blocks);
    }

    /**
     * Find out the given block is non-natural or not.
     *
     * @param plugin The TreeLogging mPlugin
     * @param block  The block
     *
     * @return {true} for record exists.
     */
    public static boolean contains(final TreeLoggingPlugin plugin, final Block block) {
        return plugin.getRecords().hasRecord(block);
    }
}
