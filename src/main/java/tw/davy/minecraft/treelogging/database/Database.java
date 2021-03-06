package tw.davy.minecraft.treelogging.database;

import org.bukkit.block.Block;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.davy.minecraft.treelogging.Disposable;
import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

public class Database implements Disposable {
    private final TreeLoggingPlugin mPlugin;
    private Connection mConnection;
    private static final List<String> sSqlStatements = Arrays.asList(
            "REPLACE INTO record (world, x, y, z) VALUES (?, ?, ?, ?)",
            "DELETE FROM record WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
            "SELECT * FROM record WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?"
    );
    private List<PreparedStatement> mPreparedStatements = new ArrayList<PreparedStatement>();

    public Database(TreeLoggingPlugin plugin) {
        this.mPlugin = plugin;

        try {
            String database = "jdbc:sqlite:" + plugin.getDataFolder().getCanonicalPath() + "/treelogging.sqlite3";
            Class.forName("org.sqlite.JDBC");
            this.mConnection = DriverManager.getConnection(database);

            initTable();
            for (String sql : sSqlStatements) {
                mPreparedStatements.add(this.mConnection.prepareStatement(sql));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        plugin.getLogger().info("Using SQlite as data storage.");
    }

    public void dispose() {
        if (!isDisposed() && mConnection != null) {
            try {
                mConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isDisposed() {
        if (mConnection == null)
            return false;

        try {
            return mConnection.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void initTable() {
        try {
            Statement statement = mConnection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS record (" +
                    "world STRING NOT NULL, " +
                    "x INTEGER NOT NULL, " +
                    "y INTEGER NOT NULL, " +
                    "z INTEGER NOT NULL" +
                    ")"
            );
            statement.executeUpdate("CREATE UNIQUE INDEX IF NOT EXISTS unique_record ON record (world, x, y, z)");
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateRecord(Block block) {
        try {
            PreparedStatement statement = mPreparedStatements.get(0);
            statement.setString(1, block.getWorld().getUID().toString());
            statement.setInt(2, block.getX());
            statement.setInt(3, block.getY());
            statement.setInt(4, block.getZ());
            statement.executeUpdate();
            statement.clearParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateRecords(List<Block> blocks) {
        try {
            PreparedStatement statement = mPreparedStatements.get(0);
            for (Block block : blocks) {
                statement.setString(1, block.getWorld().getUID().toString());
                statement.setInt(2, block.getX());
                statement.setInt(3, block.getY());
                statement.setInt(4, block.getZ());
                statement.addBatch();
            }
            mConnection.setAutoCommit(false);
            statement.executeBatch();
            mConnection.setAutoCommit(true);
            statement.clearParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeRecord(Block block) {
        try {
            PreparedStatement statement = mPreparedStatements.get(1);
            statement.setString(1, block.getWorld().getUID().toString());
            statement.setInt(2, block.getX());
            statement.setInt(3, block.getY());
            statement.setInt(4, block.getZ());
            statement.executeUpdate();
            statement.clearParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeRecords(List<Block> blocks) {
        try {
            PreparedStatement statement = mPreparedStatements.get(1);
            for (Block block : blocks) {
                statement.setString(1, block.getWorld().getUID().toString());
                statement.setInt(2, block.getX());
                statement.setInt(3, block.getY());
                statement.setInt(4, block.getZ());
                statement.addBatch();
            }
            mConnection.setAutoCommit(false);
            statement.executeBatch();
            mConnection.setAutoCommit(true);
            statement.clearParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean hasRecord(Block block) {
        try {
            PreparedStatement statement = mPreparedStatements.get(2);
            statement.setString(1, block.getWorld().getUID().toString());
            statement.setInt(2, block.getX());
            statement.setInt(3, block.getY());
            statement.setInt(4, block.getZ());
            ResultSet results = statement.executeQuery();
            statement.clearParameters();
            if (results.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
