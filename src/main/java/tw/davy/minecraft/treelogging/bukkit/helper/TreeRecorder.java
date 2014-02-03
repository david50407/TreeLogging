package tw.davy.minecraft.treelogging.bukkit.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

/**
 * Recorder for non-natural trees.
 *
 * @author Davy
 */
public class TreeRecorder
{
  /**
   * Record non-natural tree.
   *
   * @param plugin
   *            The TreeLogging plugin
   * @param block
   *            The block
   * @return
   */
  public static boolean record(TreeLoggingPlugin plugin, Block block)
  {
    Material blockType = block.getType();
    if (blockType == Material.LEAVES
      || blockType == Material.LEAVES_2
      || blockType == Material.LOG
      || blockType == Material.LOG_2)
    {
      return plugin.database.updateRecord(block);
    }
    return true;
  }

  /**
   * Remove the record from the given block.
   *
   * @param plugin
   *            The TreeLogging plugin
   * @param block
   *            The block
   * @return
   */
  public static boolean remove(TreeLoggingPlugin plugin, Block block)
  {
    Material blockType = block.getType();
    if (blockType == Material.LEAVES
      || blockType == Material.LEAVES_2
      || blockType == Material.LOG
      || blockType == Material.LOG_2)
    {
      return plugin.database.removeRecord(block);
    }
    return true;
  }

  /**
   * Find out the given block is non-natural or not.
   *
   * @param plugin
   *            The TreeLogging plugin
   * @param block
   *            The block
   * @return
   */
  public static boolean contains(TreeLoggingPlugin plugin, Block block)
  {
    return plugin.database.hasRecord(block);
  }
}