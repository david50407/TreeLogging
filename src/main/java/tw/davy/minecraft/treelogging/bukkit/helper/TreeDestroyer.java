package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

/**
 * Destroyer for trees.
 *
 * @author Davy
 */
public class TreeDestroyer {
    public static boolean destroy(TreeLoggingPlugin plugin, ArrayList<Block> blocks) {
        for (Block block : blocks) {
            Material blockType = block.getType();
            if (blockType == Material.LEAVES || blockType == Material.LEAVES_2) {
                // We have a leaves block, so drop the items may from leaves.
                TreeDroper.dropLeaf(block);
                block.setType(Material.AIR);
            } else if (blockType == Material.LOG || blockType == Material.LOG_2) {
                // Only drop the block if it's a log.
                TreeDroper.dropTree(block);
                block.setType(Material.AIR);
            }
        }

        return true;
    }
}