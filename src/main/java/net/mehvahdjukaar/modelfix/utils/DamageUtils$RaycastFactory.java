package net.mehvahdjukaar.modelfix.utils;

import java.util.function.BiFunction;
import net.minecraft.class_2338;
import net.minecraft.class_3965;

@FunctionalInterface
public interface RaycastFactory extends BiFunction<DamageUtils.ExposureRaycastContext, class_2338, class_3965> {}
