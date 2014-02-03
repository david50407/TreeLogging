package tw.davy.minecraft.treelogging.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Block;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

public class Database
{
  private final TreeLoggingPlugin plugin;
  private Connection connection;
  private final List<String> SqlStatements = Arrays.asList(
    "REPLACE INTO record (world, x, y, z) VALUES (?, ?, ?, ?)",
    "DELETE FROM record WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
    "SELECT * FROM record WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?"
  );
  private List<PreparedStatement> preStatements = new ArrayList<PreparedStatement>();

  public Database(TreeLoggingPlugin plugin)
  {
    this.plugin = plugin;

    try
    {
      String database = "jdbc:sqlite:" + plugin.getDataFolder().getCanonicalPath() + "/treelogging.sqlite3";
      Class.forName("org.sqlite.JDBC");
      this.connection = DriverManager.getConnection(database);

      initTable();
      for (String sql : SqlStatements)
      {
        preStatements.add(this.connection.prepareStatement(sql));
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    plugin.getLogger().info("Using SQlite as data storage.");
  }

  private void initTable()
  {
    try
    {
      Statement statement = connection.createStatement();
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS record (" +
        "world STRING NOT NULL, " +
        "x INTEGER NOT NULL, " +
        "y INTEGER NOT NULL, " +
        "z INTEGER NOT NULL" +
        ")"
      );
      statement.executeUpdate("CREATE UNIQUE INDEX IF NOT EXISTS unique_record ON record (world, x, y, z)");
      statement.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public boolean updateRecord(Block block)
  {
    try
    {
      PreparedStatement statement = preStatements.get(0);
      statement.setString(1, block.getWorld().getUID().toString());
      statement.setInt(2, block.getX());
      statement.setInt(3, block.getY());
      statement.setInt(4, block.getZ());
      statement.executeUpdate();
      statement.clearParameters();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean removeRecord(Block block)
  {
    try
    {
      PreparedStatement statement = preStatements.get(1);
      statement.setString(1, block.getWorld().getUID().toString());
      statement.setInt(2, block.getX());
      statement.setInt(3, block.getY());
      statement.setInt(4, block.getZ());
      statement.executeUpdate();
      statement.clearParameters();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean hasRecord(Block block)
  {
    try
    {
      PreparedStatement statement = preStatements.get(2);
      statement.setString(1, block.getWorld().getUID().toString());
      statement.setInt(2, block.getX());
      statement.setInt(3, block.getY());
      statement.setInt(4, block.getZ());
      ResultSet results = statement.executeQuery();
      statement.clearParameters();
      if (results.next())
      {
        return true;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
    return false;
  }
}
