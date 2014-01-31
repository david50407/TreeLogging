package tw.davy.minecraft.treelogging.bukkit;

import java.util.logging.Logger;

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
}
