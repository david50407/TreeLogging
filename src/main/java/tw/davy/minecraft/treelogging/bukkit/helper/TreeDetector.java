package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.davy.minecraft.treelogging.bukkit.TreeLoggingPlugin;

import static tw.davy.minecraft.treelogging.bukkit.helper.MaterialChecker.isAcaciaTree;
import static tw.davy.minecraft.treelogging.bukkit.helper.MaterialChecker.isTree;

/**
 * Detector for trees.
 *
 * @author Davy
 */
public class TreeDetector {
    private static List<Location> locationsToDetect = Arrays.asList(
            new Location(null, 1.0, 0.0, 0.0),
            new Location(null, 0.0, 1.0, 0.0),
            new Location(null, 0.0, 0.0, 1.0),
            new Location(null, -1.0, 0.0, 0.0),
            new Location(null, 0.0, -1.0, 0.0),
            new Location(null, 0.0, 0.0, -1.0)
    );
    private static List<Location> locationsToDetectForAcacia = Arrays.asList(
            new Location(null, 1.0, 1.0, 0.0),
            new Location(null, -1.0, 1.0, 0.0),
            new Location(null, 0.0, 1.0, 1.0),
            new Location(null, 0.0, 1.0, -1.0),
            new Location(null, 1.0, -1.0, 0.0),
            new Location(null, -1.0, -1.0, 0.0),
            new Location(null, 0.0, -1.0, 1.0),
            new Location(null, 0.0, -1.0, -1.0)
    );

    /**
     * Detect the tree from the given source block and return all blocks of
     * the tree.
     *
     * @param plugin The TreeLogging mPlugin
     * @param source The source block (in normal case the destroyed block)
     * @param igonre Ignore detect failed, return blocks forcely.
     * @return Blocks of the tree, null for not a tree.
     *
     * @see detectRecursively()
     */
    public static ArrayList<Block> detect(final TreeLoggingPlugin plugin, final Block source, final boolean ignore) {
        ArrayList<Block> blocks = new ArrayList<Block>();

        boolean returned = detectRecursively(plugin, source, source, blocks, ignore);

        return returned ? blocks : null;
    }

    /**
     * Detect the tree from the given source block and return all blocks of
     * the tree.
     *
     * @param plugin The TreeLogging mPlugin
     * @param source The source block (in normal case the destroyed block)
     * @return Blocks of the tree, null for not a tree.
     *
     * @see detectRecursively()
     */
    public static ArrayList<Block> detect(final TreeLoggingPlugin plugin, final Block source) {
        return detect(plugin, source, false);
    }

    /**
     * Detect the tree from the given source block using recursion and return
     * all the blocks.
     *
     * @param plugin The TreeLogging mPlugin
     * @param source The source block
     * @param that   The block need to check
     * @param ignore Ignore detect failed, return blocks forcely.
     * @return
     */
    private static boolean detectRecursively(final TreeLoggingPlugin plugin, final Block source,
                                             final Block that, final ArrayList<Block> blocks,
                                             final boolean ignore, boolean retVal) {
        if (blocks.size() > 1500) {
            return retVal;
        }
        if (!checkRadius(50, that, source)) {
            return retVal;
        }

        for (Location location : locationsToDetect) {
            Block relativeBlock = that.getRelative(
                    location.getBlockX(),
                    location.getBlockY(),
                    location.getBlockZ());
            if (relativeBlock.equals(source)) {
                continue;
            }
            if (blocks.contains(relativeBlock)) {
                continue;
            }

            Material relativeBlockType = relativeBlock.getType();
            if (isTree(relativeBlockType)) {
                if (TreeRecorder.contains(plugin, relativeBlock) && !ignore) {
                    retVal = false;
                } else {
                    blocks.add(relativeBlock);
                    retVal &= detectRecursively(plugin, source, relativeBlock, blocks, ignore, retVal);
                }
            } else if (!plugin.getIgnoredBlocks().contains(relativeBlockType)) {
                // skip checking if it's around start point
                if (!that.equals(source)) {
                    retVal = false;
                }
            }
        }
        // Locations for Acacia
        if (isAcaciaTree(that.getType())) {
            for (Location location : locationsToDetectForAcacia) {
                Block relativeBlock = that.getRelative(
                        location.getBlockX(),
                        location.getBlockY(),
                        location.getBlockZ());
                if (relativeBlock.equals(source)) {
                    continue;
                }
                if (blocks.contains(relativeBlock)) {
                    continue;
                }

                if (isAcaciaTree(relativeBlock.getType())) {
                    if (TreeRecorder.contains(plugin, relativeBlock) && !ignore) {
                        retVal = false;
                    } else {
                        blocks.add(relativeBlock);
                        retVal &= detectRecursively(plugin, source, relativeBlock, blocks, ignore, retVal);
                    }
                }
            }
        }
        return retVal || ignore;
    }

    private static boolean detectRecursively(final TreeLoggingPlugin plugin, final Block source,
                                             final Block that, final ArrayList<Block> blocks,
                                             final boolean ignore) {
        return detectRecursively(plugin, source, that, blocks, ignore, true);
    }

    private static boolean checkRadius(final int maxRadius, final Block that, final Block source) {
        if (maxRadius > 0) {
            return (that.getX() <= source.getX() + maxRadius)
                    && (that.getX() >= source.getX() - maxRadius)
                    && (that.getZ() <= source.getZ() + maxRadius)
                    && (that.getZ() >= source.getZ() - maxRadius);
        }

        return true;
    }
}
