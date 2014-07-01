package tw.davy.minecraft.treelogging.bukkit;

import java.util.logging.Logger;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

/**
 * The state manager for TreeLogging.
 *
 * @author Davy
 */
public final class StateManager
{
  public TreeLoggingPlugin plugin = null;
  public ArrayList<String> recordingList;

  public StateManager(TreeLoggingPlugin plugin)
  {
    this.plugin = plugin;
    this.recordingList = new ArrayList<String>();
  }

  public void enableRecording(Player player)
  {
    String uuid = player.getName();
    recordingList.add(uuid);
    player.sendMessage(ChatColor.GREEN + "You are recording.");
    plugin.getLogger().info("Enable recording for player: " + uuid);
  }

  public void disableRecording(Player player)
  {
    String uuid = player.getName();
    recordingList.remove(uuid);
    player.sendMessage(ChatColor.GREEN + "You are " + ChatColor.RED + "NOT" + ChatColor.GREEN + " recording.");
    plugin.getLogger().info("Disable recording for player: " + uuid);
  }

  public boolean isRecording(Player player)
  {
    String uuid = player.getName();
    return recordingList.contains(uuid);
  }

  public void toggleRecording(Player player)
  {
    if (isRecording(player))
      disableRecording(player);
    else
      enableRecording(player);
  }
}
