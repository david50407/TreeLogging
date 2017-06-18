package tw.davy.minecraft.treelogging.bukkit.listener;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;

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

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();
        TreeRecorder.remove(mPlugin, block);

        final Player crafter = event.getPlayer();
        if (crafter != null && crafter.getGameMode() == GameMode.CREATIVE && crafter.isSneaking())
            return;

        final ArrayList<Block> targetBlocks = TreeDetector.detect(mPlugin, block);
        if (targetBlocks != null)
            TreeDestroyer.destroy(mPlugin, targetBlocks);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (event.getPlayer() != null)
            TreeRecorder.record(mPlugin, event.getBlockPlaced());
    }

    @EventHandler(ignoreCancelled = true)
    public void onLeavesDecay(final LeavesDecayEvent event) {
        TreeRecorder.remove(mPlugin, event.getBlock());
    }
}
