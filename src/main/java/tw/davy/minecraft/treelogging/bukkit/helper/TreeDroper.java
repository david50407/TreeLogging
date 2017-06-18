package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Leaves;
import org.bukkit.material.Sapling;
import org.bukkit.material.Tree;

import java.util.Random;

/**
 * Detector for trees.
 *
 * @author Davy
 */
public class TreeDroper {
    private static Random sRandom = new Random();

    public static void dropItem(final World world, final Location loc, final ItemStack stack) {
        world.dropItemNaturally(loc, stack);
    }

    public static void dropBlock(final Block block) {
        dropItem(block.getWorld(), block.getLocation(),
                block.getState().getData().toItemStack(1)
        );
    }

    public static void dropTree(final Block block) {
        if (!(block.getState().getData() instanceof Tree))
            return;
        final Tree materialData = (Tree) block.getState().getData();

        dropItem(block.getWorld(), block.getLocation(),
                new Tree(materialData.getSpecies()).toItemStack(1));
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
                if (sRandom.nextDouble() * 100 <= 1.0)
                    stack = new ItemStack(Material.APPLE, 1);
                else if (sRandom.nextDouble() * 100 <= 0.1)
                    stack = new ItemStack(Material.GOLDEN_APPLE, 1);
                else if (sRandom.nextDouble() * 100 <= 5.0)
                    stack = new Leaves(materialData.getSpecies()).toItemStack(1);
                else if (sRandom.nextDouble() * 100 <= 8.0)
                    stack = new Sapling(materialData.getSpecies()).toItemStack(1);

                if (stack != null)
                    dropItem(block.getWorld(), block.getLocation(), stack);
            }
        }
    }
}
