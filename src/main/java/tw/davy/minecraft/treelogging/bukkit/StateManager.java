package tw.davy.minecraft.treelogging.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * The state manager for TreeLogging.
 *
 * @author Davy
 */
public final class StateManager {
    private TreeLoggingPlugin mPlugin = null;
    private ArrayList<String> mRecordingList;

    public StateManager(final TreeLoggingPlugin plugin) {
        this.mPlugin = plugin;
        this.mRecordingList = new ArrayList<>();
    }

    public void enableRecording(final Player player) {
        String uuid = player.getName();
        mRecordingList.add(uuid);
        player.sendMessage(ChatColor.GREEN + "You are recording.");
        mPlugin.getLogger().info("Enable recording for player: " + uuid);
    }

    public void disableRecording(final Player player) {
        String uuid = player.getName();
        mRecordingList.remove(uuid);
        player.sendMessage(ChatColor.GREEN + "You are " + ChatColor.RED + "NOT" + ChatColor.GREEN + " recording.");
        mPlugin.getLogger().info("Disable recording for player: " + uuid);
    }

    public boolean isRecording(final Player player) {
        String uuid = player.getName();
        return mRecordingList.contains(uuid);
    }

    public void toggleRecording(final Player player) {
        if (isRecording(player))
            disableRecording(player);
        else
            enableRecording(player);
    }
}
