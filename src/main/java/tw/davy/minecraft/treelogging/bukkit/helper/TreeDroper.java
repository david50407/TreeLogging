package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

import static tw.davy.minecraft.treelogging.bukkit.helper.MaterialChecker.isJungleLeave;
import static tw.davy.minecraft.treelogging.bukkit.helper.MaterialChecker.isLeaf;
import static tw.davy.minecraft.treelogging.bukkit.helper.MaterialChecker.isOakLeave;

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
        final Material material = block.getType();
        if (!isLeaf(block.getType()))
            return;

        // Leaves which decay or are destroyed without using shears usually drop nothing,
        // but yield saplings 5% (1/20) of the time.
        // Jungle leaves drop saplings 2.5% (1/40) of the time.
        // Oak and dark oak leaves also have a 0.5% (1/200) chance of dropping an apple.
        // ref: https://minecraft.gamepedia.com/Leaves
        final int rate = sRandom.nextInt(1000);

        if (isJungleLeave(material)) {
            if (rate % 40 == 0) { // 2.5% Sapling
                dropItem(block.getWorld(), block.getLocation(),
                        new ItemStack(sLeafToSaplingMap.get(block.getType()), 1));
            }
        } else if (rate % 20 == 0) { // 5% Sapling
            dropItem(block.getWorld(), block.getLocation(),
                    new ItemStack(sLeafToSaplingMap.get(block.getType()), 1));
        } else if (isOakLeave(material)
                && rate % 200 == 1) { // 0.5% Apple
            dropItem(block.getWorld(), block.getLocation(),
                    new ItemStack(Material.APPLE, 1));
        }
    }
}
