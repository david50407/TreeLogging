package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

import static tw.davy.minecraft.treelogging.bukkit.helper.MaterialChecker.isLog;
import static tw.davy.minecraft.treelogging.bukkit.helper.MaterialChecker.isLeaf;

/**
 * Destroyer for trees.
 *
 * @author Davy
 */
public class TreeDestroyer {
    public static void destroy(final TreeLoggingPlugin plugin, final ArrayList<Block> blocks) {
        for (Block block : blocks) {
            final Material blockType = block.getType();
            if (isLeaf(blockType)) {
                // We have a leaves block, so drop the items may from leaves.
                TreeDroper.dropLeaf(block);
                block.setType(Material.AIR);
            } else if (isLog(blockType)) {
                // Only drop the block if it's a log.
                TreeDroper.dropLog(block);
                block.setType(Material.AIR);
            }
        }

    }
}