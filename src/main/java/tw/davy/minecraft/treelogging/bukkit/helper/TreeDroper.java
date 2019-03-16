package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Leaves;
import org.bukkit.material.Sapling;
import org.bukkit.material.Tree;

import java.util.HashMap;
import java.util.Random;

/**
 * Detector for trees.
 *
 * @author Davy
 */
public class TreeDroper {
    private static Random sRandom = new Random();
    private static HashMap<Material, Material> sLeafToSaplingMap = new HashMap<>();

    static {
        sLeafToSaplingMap.put(Material.ACACIA_LEAVES, Material.ACACIA_SAPLING);
        sLeafToSaplingMap.put(Material.BIRCH_LEAVES, Material.BIRCH_SAPLING);
        sLeafToSaplingMap.put(Material.DARK_OAK_LEAVES, Material.DARK_OAK_SAPLING);
        sLeafToSaplingMap.put(Material.JUNGLE_LEAVES, Material.JUNGLE_SAPLING);
        sLeafToSaplingMap.put(Material.OAK_LEAVES, Material.OAK_SAPLING);
        sLeafToSaplingMap.put(Material.SPRUCE_LEAVES, Material.SPRUCE_SAPLING);
    }

    public static void dropItem(final World world, final Location loc, final ItemStack stack) {
        world.dropItemNaturally(loc, stack);
    }

    public static void dropLog(final Block block) {
        dropItem(block.getWorld(), block.getLocation(),
                new ItemStack(block.getType(), 1)
        );
    }

    public static void dropLeaf(final Block block) {
        final int maxItemsPerBlock = 3;

        if (!(block.getState().getData() instanceof Leaves))
            return;

        final Leaves materialData = (Leaves) block.getState().getData();

        for (int i = 0; i < maxItemsPerBlock; i++) {
            final int item = sRandom.nextInt(5);

            // drop item rate
            if (item < 3) {
                ItemStack stack = null;
                if (sRandom.nextDouble() * 100 <= 1.0) {
                    stack = new ItemStack(Material.APPLE, 1);
                } else if (sRandom.nextDouble() * 100 <= 0.1) {
                    stack = new ItemStack(Material.GOLDEN_APPLE, 1);
                } else if (sRandom.nextDouble() * 100 <= 5.0) {
                    stack = new ItemStack(block.getType(), 1);
                } else if (sRandom.nextDouble() * 100 <= 8.0) {
                    stack = new ItemStack(sLeafToSaplingMap.get(block.getType()), 1);
                }

                if (stack != null)
                    dropItem(block.getWorld(), block.getLocation(), stack);
            }
        }
    }
}
