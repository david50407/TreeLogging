package tw.davy.minecraft.treelogging.bukkit.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeDetector;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeRecorder;

/**
 * Listener for player's event.
 *
 * @author Davy
 */
public final class PlayerListener implements Listener {
    private final TreeLoggingPlugin mPlugin;

    public PlayerListener(final TreeLoggingPlugin plugin) {
        mPlugin = plugin;
    }

    @EventHandler
    public void playerInteract(final PlayerInteractEvent event) {
        if (event.isCancelled() || !mPlugin.getStateManager().isRecording(event.getPlayer())) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            ArrayList<Block> targetBlocks = TreeDetector.detect(mPlugin, clickedBlock, true);
            if (targetBlocks == null)
                return;
            TreeRecorder.record(mPlugin, targetBlocks);
            mPlugin.getLogger().info("Logged " + targetBlocks.size() + " block(s).");
            event.getPlayer().sendMessage(ChatColor.GREEN + "Logged " + targetBlocks.size() + " block(s).");
            event.setCancelled(true);
        }
    }
}
