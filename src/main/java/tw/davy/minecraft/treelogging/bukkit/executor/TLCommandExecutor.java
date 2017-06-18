package tw.davy.minecraft.treelogging.bukkit.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

/**
 * Executor for `tl` commands.
 *
 * @author Davy
 */
public class TLCommandExecutor implements CommandExecutor {
    private TreeLoggingPlugin mPlugin;

    public TLCommandExecutor(final TreeLoggingPlugin plugin) {
        mPlugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("tl")) {
            if (args.length == 0) {
                sender.sendMessage("Not enough arguments!");
                return false;
            }

            if (args[0].equalsIgnoreCase("recording")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This command can only be run by a player.");
                    return false;
                } else {
                    Player player = (Player) sender;
                    if (!player.hasPermission("tw.davy.treelogging.execute.recording")) {
                        sender.sendMessage("You don't have permission");
                        return false;
                    }
                    mPlugin.getStateManager().toggleRecording(player);
                    return true;
                }
            }
        }
        return false;
    }
}
