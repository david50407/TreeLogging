package tw.davy.minecraft.treelogging.bukkit.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeDestroyer;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeDetector;
import tw.davy.minecraft.treelogging.bukkit.helper.TreeRecorder;

/**
 * Listener for logging tree blocks.
 *
 * @author Davy
 */
public final class BlockListener implements Listener {
    private final TreeLoggingPlugin mPlugin;

    public BlockListener(final TreeLoggingPlugin plugin) {
        mPlugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Block block = event.getBlock();
        TreeRecorder.remove(mPlugin, block);
        ArrayList<Block> targetBlocks = TreeDetector.detect(mPlugin, block);
        if (targetBlocks != null) {
            TreeDestroyer.destroy(mPlugin, targetBlocks);
        }
    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getPlayer() != null) {
            TreeRecorder.record(mPlugin, event.getBlockPlaced());
        }
    }
}
