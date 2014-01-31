package tw.davy.minecraft.treelogging.bukkit.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

/**
 * Detector for trees.
 *
 * @author Davy
 */
public class TreeDroper
{
  public static boolean dropItem(World world, Location loc, ItemStack stack)
  {
    world.dropItemNaturally(loc, stack);
    return true;
  }

  public static boolean dropBlock(Block block)
  {
    return dropItem(block.getWorld(), block.getLocation(),
      new ItemStack(block.getType(), 1, (short) 0, block.getData())
    );
  }

  public static boolean dropTree(Block block)
  {
    return dropItem(block.getWorld(), block.getLocation(),
      new ItemStack(block.getType(), 1, (short) 0, (byte)(block.getData() & 0x3))
    );
  }

  public static boolean dropLeaf(Block block)
  {
    Random gen = new Random();
    final int maxItemsPerBlock = 3;

    for (int i = 0; i < maxItemsPerBlock; i++)
    {
      int item = gen.nextInt(5);

      // drop item rate
      if (item < 3)
      {
        ItemStack stack = null;
        if (gen.nextDouble() * 100 <= 1.0)
        {
          stack = new ItemStack(Material.APPLE, 1);
        }
        else if (gen.nextDouble() * 100 <= 0.1)
        {
          stack = new ItemStack(Material.GOLDEN_APPLE, 1);
        }
        else if (gen.nextDouble() * 100 <= 5.0)
        {
          if (block.getType() == Material.LEAVES)
          {
            stack = new ItemStack(Material.LEAVES, 1, (short) 0,
              (byte) (block.getData() & ~0x8)
              );
          }
          else // if (block.getType() == Material.LEAVES_2)
          {
            stack = new ItemStack(Material.LEAVES_2, 1, (short) 0,
              (byte) (block.getData() & ~0x8)
              );
          }
        }
        else if (gen.nextDouble() * 100 <= 8.0)
        {
          if (block.getType() == Material.LEAVES)
          {
            stack = new ItemStack(Material.SAPLING, 1, (short) 0,
              (byte) (block.getData() & ~0x8)
              );
          }
          else // if (block.getType() == Material.LEAVES_2)
          {
            stack = new ItemStack(Material.SAPLING, 1, (short) 0,
              (byte) ((block.getData() & ~0x8) + 4)
              );
          }
        }

        // If we have a stack, drop it
        if (stack != null)
        {
          dropItem(block.getWorld(), block.getLocation(), stack);
        }
        continue;
      }
    }
    return true;
  }
}
