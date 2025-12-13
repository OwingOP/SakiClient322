package net.mehvahdjukaar.modelfix.utils;

import java.util.List;
import java.util.stream.Stream;

import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.rotation.Rotation;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public final class BlockUtils {
    public static boolean isBlock(BlockPos pos, Block block) {
        return saki.mc.world.getBlockState(pos).getBlock() == block;
    }

    public static void rotateToBlock(BlockPos pos) {
        assert saki.mc.player != null;
        Rotation rotation = RotationUtils.getDirection((Entity) saki.mc.player, pos.toCenterPos());
        saki.mc.player.setPitch((float) rotation.pitch());
        saki.mc.player.setYaw((float) rotation.yaw());
    }

    public static boolean isAnchorCharged(BlockPos pos) {
        if (isBlock(pos, Blocks.RESPAWN_ANCHOR)) {
            return saki.mc.world.getBlockState(pos).get(RespawnAnchorBlock.CHARGES) != 0;
        }
        return false;
    }

    public static boolean isAnchorNotCharged(BlockPos pos) {
        if (isBlock(pos, Blocks.RESPAWN_ANCHOR)) {
            return saki.mc.world.getBlockState(pos).get(RespawnAnchorBlock.CHARGES) == 0;
        }
        return false;
    }

    public static boolean canPlaceBlockClient(BlockPos block) {
        BlockPos up = block.up();
        if (!saki.mc.world.isAir(up)) return false;

        double x = up.getX();
        double y = up.getY();
        double z = up.getZ();

        List<Entity> list = saki.mc.world.getEntities(null, new Box(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
        list.removeIf(entity -> entity instanceof ItemEntity);
        return list.isEmpty();
    }

    public static Stream<BlockPos> getAllInBoxStream(BlockPos from, BlockPos to) {
        BlockPos min = new BlockPos(
                Math.min(from.getX(), to.getX()),
                Math.min(from.getY(), to.getY()),
                Math.min(from.getZ(), to.getZ())
        );
        BlockPos max = new BlockPos(
                Math.max(from.getX(), to.getX()),
                Math.max(from.getY(), to.getY()),
                Math.max(from.getZ(), to.getZ())
        );

        Stream<BlockPos> stream = Stream.iterate(min, pos -> {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            if (++x > max.getX()) {
                x = min.getX();
                y++;
            }
            if (y > max.getY()) {
                y = min.getY();
                z++;
            }
            if (z > max.getZ()) {
                throw new IllegalStateException("Stream limit didn't work.");
            }
            return new BlockPos(x, y, z);
        });

        int limit = (max.getX() - min.getX() + 1)
                * (max.getY() - min.getY() + 1)
                * (max.getZ() - min.getZ() + 1);

        return stream.limit(limit);
    }
}
