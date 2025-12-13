package net.mehvahdjukaar.modelfix.utils;

import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.rotation.Rotation;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;

public final class RotationUtils {
    public static Vec3d getEyesPos(PlayerEntity player) {
        return RenderUtils.getCameraPos();
    }

    public static BlockPos getCameraBlockPos() {
        return saki.mc.gameRenderer.getCamera().getBlockPos();
    }

    public static BlockPos getEyesBlockPos() {
        Vec3d eyes = getEyesPos(saki.mc.player);
        return new BlockPos((int) eyes.x, (int) eyes.y, (int) eyes.z);
    }

    public static Vec3d getPlayerLookVec(float yaw, float pitch) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.sin(g);
        float i = MathHelper.cos(g);
        float j = MathHelper.sin(f);
        float k = MathHelper.cos(f);
        return new Vec3d((i * j), -k, (h * j));
    }

    public static Vec3d getPlayerLookVec(PlayerEntity player) {
        return getPlayerLookVec(player.getYaw(), player.getPitch());
    }

    public static Rotation getDiff(Rotation rotation1, Rotation rotation2) {
        double yaw = Math.abs(Math.max(rotation1.yaw(), rotation2.yaw()) - Math.min(rotation1.yaw(), rotation2.yaw()));
        double pitch = Math.abs(Math.max(rotation1.pitch(), rotation2.pitch()) - Math.min(rotation1.pitch(), rotation2.pitch()));
        return new Rotation(yaw, pitch);
    }

    public static Rotation getSmoothRotation(Rotation from, Rotation to, double speed) {
        return new Rotation(
                MathHelper.lerp((float) speed, (float) from.yaw(), (float) to.yaw()),
                MathHelper.lerp((float) speed, (float) from.pitch(), (float) to.pitch()));
    }

    public static double getTotalDiff(Rotation rotation1, Rotation rotation2) {
        Rotation diff = getDiff(rotation1, rotation2);
        return diff.yaw() + diff.pitch();
    }

    public static Vec3d getClientLookVec() {
        return getPlayerLookVec(saki.mc.player);
    }

    public static Rotation getDirection(Entity entity, Vec3d vec) {
        double dx = vec.x - entity.getX();
        double dy = vec.y - entity.getY();
        double dz = vec.z - entity.getZ();
        double dist = MathHelper.sqrt((float) (dx * dx + dz * dz));
        return new Rotation(
                MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(dz, dx)) - 90.0D),
                -MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(dy, dist))));
    }

    public static double getAngleToRotation(Rotation rotation) {
        double currentYaw = MathHelper.wrapDegrees(saki.mc.player.getYaw());
        double currentPitch = MathHelper.wrapDegrees(saki.mc.player.getPitch());
        double diffYaw = MathHelper.wrapDegrees(currentYaw - rotation.yaw());
        double diffPitch = MathHelper.wrapDegrees(currentPitch - rotation.pitch());
        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }
}
