package net.mehvahdjukaar.modelfix.utils;

import java.util.Objects;
import java.util.stream.Stream;

import net.mehvahdjukaar.modelfix.addons.addon.client.Friends;
import net.mehvahdjukaar.modelfix.saki;

import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Items;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Box;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.Perspective;

public final class WorldUtils {

    public static boolean isDeadBodyNearby() {
        return saki.mc.world.getPlayers().parallelStream()
                .filter(e -> e != saki.mc.player)
                .filter(e -> e.distanceTo(saki.mc.player) <= 6.0D)
                .anyMatch(LivingEntity::isDead);
    }

    public static Entity findNearestEntity(PlayerEntity toPlayer, float radius, boolean seeOnly) {
        float mr = Float.MAX_VALUE;
        Entity entity = null;
        assert saki.mc.world != null;
        for (Entity e : saki.mc.world.getEntities()) {
            float d = e.distanceTo(toPlayer);
            if (e != toPlayer && d <= radius && saki.mc.player.canSee(e) == seeOnly && d < mr) {
                mr = d;
                entity = e;
            }
        }
        return entity;
    }

    public static double distance(Vec3d fromVec, Vec3d toVec) {
        return fromVec.distanceTo(toVec);
    }

    public static PlayerEntity findNearestPlayer(PlayerEntity toPlayer, float range, boolean seeOnly, boolean excludeFriends) {
        float minRange = Float.MAX_VALUE;
        PlayerEntity minPlayer = null;
        for (PlayerEntity player : saki.mc.world.getPlayers()) {
            float distance = (float) distance(toPlayer.getPos(), player.getPos());
            if (excludeFriends && ((Friends) saki.INSTANCE.getModuleManager().getModule(Friends.class)).disableAimAssist.getValue()
                    && saki.INSTANCE.getFriendManager().isFriend(player)) continue;
            if (player != toPlayer && distance <= range && player.canSee(toPlayer) == seeOnly && distance < minRange) {
                minRange = distance;
                minPlayer = player;
            }
        }
        return minPlayer;
    }

    public static Vec3d getPlayerLookVec(float yaw, float pitch) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    public static Vec3d getPlayerLookVec(PlayerEntity player) {
        return getPlayerLookVec(player.getYaw(), player.getPitch());
    }

    public static HitResult getHitResult(double radius) {
        return getHitResult(saki.mc.player, false, saki.mc.player.getYaw(), saki.mc.player.getPitch(), radius);
    }

    public static HitResult getHitResult(PlayerEntity entity, boolean ignoreInvisibles, float yaw, float pitch, double distance) {
        if (entity == null || saki.mc.world == null) return null;
        Vec3d cameraPosVec = entity.getCameraPosVec(1.0F);
        Vec3d rotationVec = getPlayerLookVec(yaw, pitch);
        Vec3d range = cameraPosVec.add(rotationVec.x * distance, rotationVec.y * distance, rotationVec.z * distance);
        BlockHitResult blockHit = saki.mc.world.raycast(new RaycastContext(cameraPosVec, range,
                RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, entity));
        double e = distance * distance;
        if (blockHit != null) e = blockHit.getPos().squaredDistanceTo(cameraPosVec);
        Vec3d vec3d3 = cameraPosVec.add(rotationVec.x * distance, rotationVec.y * distance, rotationVec.z * distance);
        Box box = entity.getBoundingBox().stretch(rotationVec.multiply(distance)).expand(1.0D, 1.0D, 1.0D);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityCollision(saki.mc.world, entity, cameraPosVec, vec3d3, box,
                entityx -> !entityx.isSpectator() && entityx.isAlive() && (!entityx.isInvisible() || !ignoreInvisibles));
        if (entityHitResult != null) {
            Vec3d vec3d4 = entityHitResult.getPos();
            double g = cameraPosVec.squaredDistanceTo(vec3d4);
            if (g < e || blockHit == null) blockHit = BlockHitResult.createMissed(vec3d4, Direction.getFacing(rotationVec.x, rotationVec.y, rotationVec.z), BlockPos.ofFloored(vec3d4));
        }
        return blockHit;
    }

    public static void placeBlock(BlockHitResult blockHit, boolean swingHand) {
        ActionResult result = saki.mc.interactionManager.interactBlock(saki.mc.player, Hand.MAIN_HAND, blockHit);
        if (result.isAccepted() && result.shouldSwingHand() && swingHand) {
            saki.mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

    public static Stream<net.minecraft.world.chunk.Chunk> getLoadedChunks() {
        int radius = Math.max(2, saki.mc.options.getViewDistance()) + 3;
        int diameter = radius * 2 + 1;
        ChunkPos center = saki.mc.player.getChunkPos();
        ChunkPos min = new ChunkPos(center.x - radius, center.z - radius);
        ChunkPos max = new ChunkPos(center.x + radius, center.z + radius);
        return Stream.iterate(min, pos -> {
                    int x = pos.x;
                    int z = pos.z;
                    if (++x > max.x) {
                        x = min.x;
                        z++;
                    }
                    if (z > max.z) throw new IllegalStateException("Stream limit didn't work.");
                    return new ChunkPos(x, z);
                }).limit(diameter * diameter)
                .filter(c -> saki.mc.world.isChunkLoaded(c.x, c.z))
                .map(c -> saki.mc.world.getChunk(c.x, c.z))
                .filter(Objects::nonNull);
    }

    public static boolean isShieldFacingAway(PlayerEntity player) {
        if (saki.mc.player != null && player != null) {
            Vec3d playerPos = saki.mc.player.getPos();
            Vec3d targetPos = player.getPos();
            Vec3d directionToPlayer = targetPos.subtract(playerPos).normalize();
            float yaw = player.getYaw();
            float pitch = player.getPitch();
            Vec3d facingDirection = new Vec3d(-Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)),
                    -Math.sin(Math.toRadians(pitch)),
                    Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))).normalize();
            double dotProduct = facingDirection.dotProduct(directionToPlayer);
            return dotProduct < 0.0D;
        }
        return false;
    }

    public static boolean isTool(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ToolItem)) return false;
        ToolMaterial material = ((ToolItem) itemStack.getItem()).getMaterial();
        return material == ToolMaterial.WOOD || material == ToolMaterial.STONE;
    }

    public static boolean isCrit(PlayerEntity player, Entity target) {
        return player.getAttackCooldownProgress(0.5F) > 0.9F && player.fallDistance > 0.0F
                && !player.isOnGround() && !player.isClimbing() && !player.isTouchingWater()
                && !player.hasStatusEffect(StatusEffects.BLINDNESS) && target instanceof LivingEntity;
    }

    public static void hitEntity(Entity entity, boolean swingHand) {
        saki.mc.interactionManager.attackEntity(saki.mc.player, entity);
        if (swingHand) saki.mc.player.swingHand(Hand.MAIN_HAND);
    }
}
