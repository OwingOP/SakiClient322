package net.mehvahdjukaar.modelfix.imixin;

import net.minecraft.util.math.Vec3d;

public interface IExplosion {
    void set(Vec3d position, float power, boolean createFire);
}
