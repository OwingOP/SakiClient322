package net.mehvahdjukaar.modelfix.imixin;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;

public interface IVec3d {
    void set(double x, double y, double z);

    default void set(Vec3d vec) {
        set(vec.x, vec.y, vec.z);
    }

    default void set(Vector3d vec) {
        set(vec.x, vec.y, vec.z);
    }

    void setXZ(double x, double z);

    void setY(double y);
}
