package tw.davy.minecraft.treelogging.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

/**
 * @author Davy
 */
public class PistonListener implements Listener {
    private final TreeLoggingPlugin mPlugin;

    public PistonListener(final TreeLoggingPlugin plugin) {
        mPlugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPistonExtend(final BlockPistonExtendEvent event) {
        // TODO: piston detection
    }

    @EventHandler(ignoreCancelled = true)
    public void onPistonRetract(final BlockPistonRetractEvent event) {
        if (!event.isSticky())
            return;

        // TODO: piston detection
    }
}
