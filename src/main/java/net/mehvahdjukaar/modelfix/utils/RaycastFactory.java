package net.mehvahdjukaar.modelfix.utils;

import java.util.function.BiFunction;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.hit.BlockHitResult;

@FunctionalInterface
public interface RaycastFactory extends BiFunction<DamageUtils.ExposureRaycastContext, BlockPos, BlockHitResult> {}
