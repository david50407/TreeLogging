package tw.davy.minecraft.treelogging.bukkit.listener;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginManager;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeDestroyer;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeDetector;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeRecorder;
import tw.davy.minecraft.treelogging.database.Database;

/**
 * Listener for logging tree blocks.
 *
 * @author Davy
 */
public final class BlockListener implements Listener
{
  private final TreeLoggingPlugin plugin;

  public BlockListener(TreeLoggingPlugin plugin)
  {
    this.plugin = plugin;
  }

  public void registerEvents()
  {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event)
  {
    if (event.isCancelled())
    {
      return;
    }

    Block block = event.getBlock();
    TreeRecorder.remove(plugin, block);
    ArrayList<Block> targetBlocks = TreeDetector.detect(plugin, block);
    if (targetBlocks != null)
    {
      TreeDestroyer.destroy(plugin, targetBlocks);
    }
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event)
  {
    if (event.isCancelled())
    {
      return;
    }

    if (event.getPlayer() != null)
    {
      TreeRecorder.record(plugin, event.getBlockPlaced());
    }
  }
}
