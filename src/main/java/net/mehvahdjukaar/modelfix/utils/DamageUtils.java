package net.mehvahdjukaar.modelfix.utils;

import java.util.function.BiFunction;

import net.mehvahdjukaar.modelfix.saki;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Box;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.RaycastContext;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import net.minecraft.world.GameMode;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.attribute.EntityAttributes;

public class DamageUtils {
    public static final RaycastFactory HIT_FACTORY;

    static {
        HIT_FACTORY = (context, blockPos) -> {
            BlockState blockState = saki.mc.world.getBlockState(blockPos);
            if (blockState.getBlock().getBlastResistance() < 600.0F) return null;
            return blockState.getCollisionShape(saki.mc.world, blockPos)
                    .raycast(context.start(), context.end(), blockPos);
        };
    }

    public static float crystalDamage(LivingEntity target, Vec3d targetPos, Box targetBox,
                                      Vec3d explosionPos, RaycastFactory raycastFactory) {
        return explosionDamage(target, targetPos, targetBox, explosionPos, 12.0F, raycastFactory);
    }

    public static float bedDamage(LivingEntity target, Vec3d targetPos, Box targetBox,
                                  Vec3d explosionPos, RaycastFactory raycastFactory) {
        return explosionDamage(target, targetPos, targetBox, explosionPos, 10.0F, raycastFactory);
    }

    public static float anchorDamage(LivingEntity target, Vec3d targetPos, Box targetBox,
                                     Vec3d explosionPos, RaycastFactory raycastFactory) {
        return explosionDamage(target, targetPos, targetBox, explosionPos, 10.0F, raycastFactory);
    }

    public static float explosionDamage(LivingEntity target, Vec3d targetPos, Box targetBox,
                                        Vec3d explosionPos, float power, RaycastFactory raycastFactory) {
        double modDistance = targetPos.distanceTo(explosionPos);
        if (modDistance > power) return 0.0F;
        double exposure = getExposure(explosionPos, targetBox, raycastFactory);
        double impact = (1.0D - modDistance / power) * exposure;
        float damage = (float) ((impact * impact + impact) / 2.0D * 7.0D * power + 1.0D);
        return calculateReductions(damage, target, saki.mc.world.getDamageSources().generic());
    }

    public static double distanceTo(Entity entity) {
        return saki.mc.player.getPos().distanceTo(entity.getPos());
    }

    public static double distanceTo(BlockPos blockPos) {
        return saki.mc.player.getPos().distanceTo(Vec3d.of(blockPos));
    }

    public static double distanceTo(Vec3d vec3d) {
        return saki.mc.player.getPos().distanceTo(vec3d);
    }

    public static float crystalDamage(LivingEntity target, Vec3d crystal, boolean predictMovement, BlockPos obsidianPos) {
        return overridingExplosionDamage(target, crystal, 12.0F, predictMovement, obsidianPos, Blocks.OBSIDIAN.getDefaultState());
    }

    public static float crystalDamage(LivingEntity target, Vec3d crystal) {
        return explosionDamage(target, crystal, 12.0F, false);
    }

    public static float bedDamage(LivingEntity target, Vec3d bed) {
        return explosionDamage(target, bed, 10.0F, false);
    }

    public static float anchorDamage(LivingEntity target, Vec3d anchor) {
        return overridingExplosionDamage(target, anchor, 10.0F, false, BlockPos.ofFloored(anchor), Blocks.RESPAWN_ANCHOR.getDefaultState());
    }

    private static float overridingExplosionDamage(LivingEntity target, Vec3d explosionPos, float power,
                                                   boolean predictMovement, BlockPos overridePos, BlockState overrideState) {
        return explosionDamage(target, explosionPos, power, predictMovement, getOverridingHitFactory(overridePos, overrideState));
    }

    private static float explosionDamage(LivingEntity target, Vec3d explosionPos, float power,
                                         boolean predictMovement) {
        return explosionDamage(target, explosionPos, power, predictMovement, HIT_FACTORY);
    }

    public static GameMode getGameMode(PlayerEntity player) {
        PlayerListEntry entry = saki.mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        if (entry == null) return GameMode.SURVIVAL;
        return entry.getGameMode();
    }

    private static float explosionDamage(LivingEntity target, Vec3d explosionPos, float power,
                                         boolean predictMovement, RaycastFactory raycastFactory) {
        if (target == null) return 0.0F;
        if (target instanceof PlayerEntity player) {
            if (getGameMode(player) == GameMode.CREATIVE) return 0.0F;
        }
        Vec3d position = predictMovement ? target.getPos().add(target.getVelocity()) : target.getPos();
        Box box = target.getBoundingBox();
        if (predictMovement) box = box.offset(target.getVelocity());
        return explosionDamage(target, position, box, explosionPos, power, raycastFactory);
    }

    public static RaycastFactory getOverridingHitFactory(BlockPos overridePos, BlockState overrideState) {
        return (context, blockPos) -> {
            BlockState blockState;
            if (blockPos.equals(overridePos)) {
                blockState = overrideState;
            } else {
                blockState = saki.mc.world.getBlockState(blockPos);
                if (blockState.getBlock().getBlastResistance() < 600.0F) return null;
            }
            return blockState.getCollisionShape(saki.mc.world, blockPos)
                    .raycast(context.start(), context.end(), blockPos);
        };
    }

    public static float getAttackDamage(LivingEntity attacker, LivingEntity target) {
        float itemDamage = (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        ItemStack stack = attacker.getMainHandStack();
        float enchantDamage = 0.0F;
        if (attacker instanceof PlayerEntity playerEntity) {
            float charge = playerEntity.getAttackCooldownProgress(0.5F);
            itemDamage *= 0.2F + charge * charge * 0.8F;
            enchantDamage *= charge;
            if (charge > 0.9F && attacker.fallDistance > 0.0F && !attacker.isOnGround() && !attacker.isClimbing()
                    && !attacker.hasStatusEffect(StatusEffects.BLINDNESS) && !attacker.isSwimming()) {
                itemDamage *= 1.5F;
            }
        }
        float damage = itemDamage + enchantDamage;
        return calculateReductions(damage, target, saki.mc.world.getDamageSources().playerAttack((PlayerEntity) attacker));
    }

    public static float fallDamage(LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            if (player.getAbilities().flying) return 0.0F;
        }
        if (entity.hasStatusEffect(StatusEffects.SLOW_FALLING) || entity.hasStatusEffect(StatusEffects.LEVITATION)) return 0.0F;
        return calculateReductions(entity.fallDistance, entity, saki.mc.world.getDamageSources().fall());
    }

    public static float calculateReductions(float damage, LivingEntity entity, DamageSource source) {
        damage = MathHelper.clamp(damage, 0.0F, Float.MAX_VALUE);
        if (entity.hasStatusEffect(StatusEffects.RESISTANCE)) {
            StatusEffectInstance resistance = entity.getStatusEffect(StatusEffects.RESISTANCE);
            int lvl = resistance.getAmplifier() + 1;
            damage *= 1.0F - lvl * 0.2F;
        }
        return Math.max(damage, 0.0F);
    }

    @FunctionalInterface
    public interface RaycastFactory extends BiFunction<ExposureRaycastContext, BlockPos, BlockHitResult> {}

    public static final class ExposureRaycastContext {
        private final Vec3d start;
        private final Vec3d end;

        public ExposureRaycastContext(Vec3d start, Vec3d end) {
            this.start = start;
            this.end = end;
        }

        public Vec3d start() { return start; }
        public Vec3d end() { return end; }
    }

    private static double getExposure(Vec3d source, Box box, RaycastFactory raycastFactory) {
        // Simplified exposure calculation
        return 1.0D;
    }
}
