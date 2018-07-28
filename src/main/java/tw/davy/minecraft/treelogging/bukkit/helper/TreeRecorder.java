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
     * @return
     */
    public static boolean record(final TreeLoggingPlugin plugin, final Block block) {
        Material blockType = block.getType();
        if (isTree(blockType)) {
            return plugin.getRecords().updateRecord(block);
        }
        return true;
    }

    /**
     * Record non-natural tree.
     *
     * @param plugin The TreeLogging mPlugin
     * @param blocks The blocks
     * @return
     */
    public static boolean record(final TreeLoggingPlugin plugin, final List<Block> blocks) {
        return plugin.getRecords().updateRecords(blocks);
    }

    /**
     * Remove the record from the given block.
     *
     * @param plugin The TreeLogging mPlugin
     * @param block  The block
     * @return
     */
    public static boolean remove(final TreeLoggingPlugin plugin, final Block block) {
        Material blockType = block.getType();
        if (isTree(blockType)) {
            return plugin.getRecords().removeRecord(block);
        }
        return true;
    }

    /**
     * Remove the record from the given blocks.
     *
     * @param plugin The TreeLogging mPlugin
     * @param blocks The blocks
     * @return
     */
    public static boolean remove(final TreeLoggingPlugin plugin, final List<Block> blocks) {
        return plugin.getRecords().removeRecords(blocks);
    }

    /**
     * Find out the given block is non-natural or not.
     *
     * @param plugin The TreeLogging mPlugin
     * @param block  The block
     * @return
     */
    public static boolean contains(final TreeLoggingPlugin plugin, final Block block) {
        return plugin.getRecords().hasRecord(block);
    }
}
