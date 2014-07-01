package tw.davy.minecraft.treelogging.bukkit.listener;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;
import tw.davy.minecraft.treelogging.bukkit.StateManager;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeDestroyer;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeDetector;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeRecorder;
import tw.davy.minecraft.treelogging.database.Database;

/**
 * Listener for player's event.
 *
 * @author Davy
 */
public final class PlayerListener implements Listener
{
  private final TreeLoggingPlugin plugin;

  public PlayerListener(TreeLoggingPlugin plugin)
  {
    this.plugin = plugin;
  }

  public void registerEvents()
  {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void playerInteract(PlayerInteractEvent event)
  {
    if (event.isCancelled() || !plugin.states.isRecording(event.getPlayer()))
    {
      return;
    }

    if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
    {
      Block clickedBlock = event.getClickedBlock();
      ArrayList<Block> targetBlocks = TreeDetector.detect(plugin, clickedBlock, true);
      if (targetBlocks == null)
        return;
      TreeRecorder.record(plugin, targetBlocks);
      plugin.getLogger().info("Logged " + targetBlocks.size() + " block(s).");
      event.getPlayer().sendMessage(ChatColor.GREEN + "Logged " + targetBlocks.size() + " block(s).");
      event.setCancelled(true);
    }
  }
}
