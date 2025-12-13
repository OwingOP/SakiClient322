package net.mehvahdjukaar.modelfix.utils;

import java.util.List;

import net.mehvahdjukaar.modelfix.saki;

import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public final class CrystalUtils {
    public static boolean canPlaceCrystalClient(BlockPos block) {
        BlockState blockState = saki.mc.world.getBlockState(block);
        if (!blockState.isOf(Blocks.OBSIDIAN) && !blockState.isOf(Blocks.BEDROCK)) {
            return false;
        }
        return canPlaceCrystalClientAssumeObsidian(block);
    }

    public static boolean canPlaceCrystalClientAssumeObsidian(BlockPos block) {
        BlockPos blockPos2 = block.up();
        if (!saki.mc.world.isAir(blockPos2)) {
            return false;
        }
        double d = blockPos2.getX();
        double e = blockPos2.getY();
        double f = blockPos2.getZ();
        List<Entity> list = saki.mc.world.getEntities(null, new Box(d, e, f, d + 1.0D, e + 2.0D, f + 1.0D));
        return list.isEmpty();
    }

    public static boolean canPlaceCrystalServer(BlockPos pos) {
        BlockState blockState = saki.mc.world.getBlockState(pos);
        if (!blockState.isOf(Blocks.OBSIDIAN) && !blockState.isOf(Blocks.BEDROCK)) {
            return false;
        }
        BlockPos blockPos = pos.up();
        if (!saki.mc.world.isAir(blockPos)) {
            return false;
        }
        double d = blockPos.getX();
        double e = blockPos.getY();
        double f = blockPos.getZ();
        List<Entity> list = saki.mc.world.getEntities(null, new Box(d, e, f, d + 1.0D, e + 2.0D, f + 1.0D));
        return list.isEmpty();
    }
}
