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
 * Detector for trees.
 *
 * @author Davy
 */
public class TreeDetector
{
  private static List<Material> blocksToIgnore = Arrays.asList(
    Material.AIR,
    Material.SAPLING,
    Material.RED_ROSE,
    Material.YELLOW_FLOWER,
    Material.BROWN_MUSHROOM,
    Material.RED_MUSHROOM,
    Material.LONG_GRASS,
    Material.DEAD_BUSH,
    Material.VINE,
    Material.WEB,
    Material.CACTUS,
    Material.SUGAR_CANE_BLOCK
  );

  private static List<Location> locationsToDetect = Arrays.asList(
    new Location(null, 1.0, 0.0, 0.0),
    new Location(null, 0.0, 1.0, 0.0),
    new Location(null, 0.0, 0.0, 1.0),
    new Location(null, -1.0, 0.0, 0.0),
    new Location(null, 0.0, -1.0, 0.0),
    new Location(null, 0.0, 0.0, -1.0)
  );

  /**
   * Detect the tree from the given source block and return all blocks of
   * the tree.
   *
   * @see detectRecursively()
   *
   * @param plugin
   *            The TreeLogging plugin
   * @param source
   *            The source block (in normal case the destroyed block)
   * @return Blocks of the tree, null for not a tree.
   */
  public static ArrayList<Block> detect(TreeLoggingPlugin plugin, Block source)
  {
    ArrayList<Block> blocks = new ArrayList<Block>();

    boolean returned = detectRecursively(plugin, source, source, blocks);

    return returned ? blocks : null;
  }

  /**
   * Detect the tree from the given source block using recursion and return
   * all the blocks.
   *
   * @param plugin
   *            The TreeLogging plugin
   * @param source
   *            The source block
   * @param that
   *            The block need to check
   * @return
   */
  private static boolean detectRecursively(TreeLoggingPlugin plugin, Block source,
    Block that, ArrayList<Block> blocks, boolean retVal)
  {
    if (blocks.size() > 500)
    {
      return retVal;
    }
    if (!checkRadius(25, that, source))
    {
      return retVal;
    }

    for (Location location : locationsToDetect)
    {
      Block relativeBlock = that.getRelative(
        location.getBlockX(),
        location.getBlockY(),
        location.getBlockZ());
      if (relativeBlock.equals(source)) {
        continue;
      }
      if (blocks.contains(relativeBlock)) {
        continue;
      }

      Material relativeBlockType = relativeBlock.getType();
      if (relativeBlockType == Material.LEAVES
        || relativeBlockType == Material.LOG
        || relativeBlockType == Material.SNOW) {
        blocks.add(relativeBlock);
        retVal &= detectRecursively(plugin, source, relativeBlock, blocks, retVal);
      } else if (!blocksToIgnore.contains(relativeBlockType)) {
        // skip checking if it's around start point
        if (!that.equals(source)) {
          retVal &= false;
        }
      }
    }
    return retVal;
  }
  private static boolean detectRecursively(TreeLoggingPlugin plugin, Block source,
    Block that, ArrayList<Block> blocks)
  {
    return detectRecursively(plugin, source, that, blocks, true);
  }

  private static boolean checkRadius(int maxRadius, Block that, Block source) {
    if (maxRadius > 0)
    {
      if ( (that.getX() <= source.getX() + maxRadius)
        && (that.getX() >= source.getX() - maxRadius)
        && (that.getZ() <= source.getZ() + maxRadius)
        && (that.getZ() >= source.getZ() - maxRadius))
      {
        return true;
      }
      return false;
    }
    return true;
  }
}