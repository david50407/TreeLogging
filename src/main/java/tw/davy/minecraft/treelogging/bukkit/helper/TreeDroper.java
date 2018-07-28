package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

/**
 * Detector for trees.
 *
 * @author Davy
 */
public class TreeDroper {
    private static HashMap<Material, Material> sLeafToSaplingMap = new HashMap<>();

    static {
        sLeafToSaplingMap.put(Material.ACACIA_LEAVES, Material.ACACIA_SAPLING);
        sLeafToSaplingMap.put(Material.BIRCH_LEAVES, Material.BIRCH_SAPLING);
        sLeafToSaplingMap.put(Material.DARK_OAK_LEAVES, Material.DARK_OAK_SAPLING);
        sLeafToSaplingMap.put(Material.JUNGLE_LEAVES, Material.JUNGLE_SAPLING);
        sLeafToSaplingMap.put(Material.OAK_LEAVES, Material.OAK_SAPLING);
        sLeafToSaplingMap.put(Material.SPRUCE_LEAVES, Material.SPRUCE_SAPLING);
    }

    public static boolean dropItem(final World world, final Location loc, final ItemStack stack) {
        world.dropItemNaturally(loc, stack);
        return true;
    }

    public static boolean dropLog(final Block block) {
        return dropItem(block.getWorld(), block.getLocation(),
                new ItemStack(block.getType(), 1)
        );
    }

    public static boolean dropLeaf(final Block block) {
        Random gen = new Random();
        final int maxItemsPerBlock = 3;

        for (int i = 0; i < maxItemsPerBlock; i++) {
            int item = gen.nextInt(5);

            // drop item rate
            if (item < 3) {
                ItemStack stack = null;
                if (gen.nextDouble() * 100 <= 1.0) {
                    stack = new ItemStack(Material.APPLE, 1);
                } else if (gen.nextDouble() * 100 <= 0.1) {
                    stack = new ItemStack(Material.GOLDEN_APPLE, 1);
                } else if (gen.nextDouble() * 100 <= 5.0) {
                    stack = new ItemStack(block.getType(), 1);
                } else if (gen.nextDouble() * 100 <= 8.0) {
                    stack = new ItemStack(sLeafToSaplingMap.get(block.getType()), 1);
                }

                // If we have a stack, drop it
                if (stack != null) {
                    dropItem(block.getWorld(), block.getLocation(), stack);
                }
                continue;
            }
        }
        return true;
    }
}
