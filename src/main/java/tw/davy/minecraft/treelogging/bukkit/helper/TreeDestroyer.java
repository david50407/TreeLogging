package tw.davy.minecraft.treelogging.bukkit.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

/**
 * Destroyer for trees.
 *
 * @author Davy
 */
public class TreeDestroyer
{
  public static boolean destroy(TreeLoggingPlugin plugin, ArrayList<Block> blocks)
  {
    for (Block block : blocks)
    {
      Material blockType = block.getType();
      if (blockType == Material.LOG || blockType == Material.LEAVES)
      {
        if (blockType == Material.LEAVES)
        {
          // We have a leaves block, so drop the items may from leaves.
          TreeDroper.dropLeaveItems(block);
        }
        else
        {
          // Only drop the block if it's a log.
          TreeDroper.dropBlock(block);
        }
        block.setType(Material.AIR);
      }
    }

    return true;
  }
}