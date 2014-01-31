package tw.davy.minecraft.treelogging.bukkit;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import tw.davy.minecraft.treelogging.bukkit.listener.BlockListener;

/**
 * The main class for TreeLogging as a Bukkit plugin.
 *
 * @author Davy
 */
public final class TreeLoggingPlugin extends JavaPlugin
{
  public static final Logger logger = Logger.getLogger("Minecraft.LyTreeHelper");

  /**
   * Called on plugin enable.
   */
  public void onEnable()
  {
    (new BlockListener(this)).registerEvents();
    logger.info("[TreeLogging] Enabled.");
  }

  /**
   * Called on plugin disable.
   */
  public void onDisable()
  {
    logger.info("[TreeLogging] Disabled.");
  }

  /**
   * Called on command trigged.
   */
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("treelogging"))
    {
      //if (!(sender instanceof Player)) {
      //} else {
      //   Player player = (Player) sender;
      //}
      sender.sendMessage("TreeLogging, Makes logging trees truely.");
      sender.sendMessage("    Version: 0.1.0");
      return true;
    }
    return false;
  }
}
