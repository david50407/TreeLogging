package tw.davy.minecraft.treelogging.bukkit.helper;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;

public class MaterialChecker {
    static private final HashSet<Material> sLogMaterials = new HashSet<>(Arrays.asList(
            Material.ACACIA_LOG, Material.STRIPPED_ACACIA_LOG,
            Material.BIRCH_LOG, Material.STRIPPED_BIRCH_LOG,
            Material.DARK_OAK_LOG, Material.STRIPPED_DARK_OAK_LOG,
            Material.JUNGLE_LOG, Material.STRIPPED_JUNGLE_LOG,
            Material.OAK_LOG, Material.STRIPPED_OAK_LOG,
            Material.SPRUCE_LOG, Material.STRIPPED_SPRUCE_LOG
    ));
    static private final HashSet<Material> sLeafMaterials = new HashSet<>(Arrays.asList(
            Material.ACACIA_LEAVES,
            Material.BIRCH_LEAVES,
            Material.DARK_OAK_LEAVES,
            Material.JUNGLE_LEAVES,
            Material.OAK_LEAVES,
            Material.SPRUCE_LEAVES
    ));

    static public boolean isAcaciaTree(final Material material) {
        return material == Material.ACACIA_LOG || material == Material.STRIPPED_ACACIA_LOG;
    }

    static public boolean isOakLeave(final Material material) {
        return material == Material.OAK_LEAVES || material == Material.DARK_OAK_LEAVES;
    }

    static public boolean isJungleLeave(final Material material) {
        return material == Material.JUNGLE_LEAVES;
    }

    static public boolean isLog(final Material material) {
        return sLogMaterials.contains(material);
    }

    static public boolean isLeaf(final Material material) {
        return sLeafMaterials.contains(material);
    }

    static public boolean isTree(final Material material) {
        return isLog(material) || isLeaf(material);
    }
}
