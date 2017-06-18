package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.Leaves;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Tree;

import java.util.ArrayList;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

/**
 * Destroyer for trees.
 *
 * @author Davy
 */
public class TreeDestroyer {
    public static void destroy(final TreeLoggingPlugin plugin, final ArrayList<Block> blocks) {
        for (Block block : blocks) {
            final MaterialData materialData = block.getState().getData();
            if (materialData instanceof Leaves) {
                // We have a leaves block, so drop the items may from leaves.
                TreeDroper.dropLeaf(block);
                block.setType(Material.AIR);
            } else if (materialData instanceof Tree) {
                // Only drop the block if it's a log.
                TreeDroper.dropTree(block);
                block.setType(Material.AIR);
            }
        }

    }
}