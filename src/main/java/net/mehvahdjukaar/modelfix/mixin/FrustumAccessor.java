package net.mehvahdjukaar.modelfix.mixin;

import net.minecraft.client.render.Frustum;
import org.joml.FrustumIntersection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Frustum.class)
public interface FrustumAccessor {
    @Accessor("frustumIntersection")
    FrustumIntersection getFrustumIntersection();

    @Accessor("frustumIntersection")
    void setFrustumIntersection(FrustumIntersection frustumIntersection);

    @Accessor("x")
    double getX();

    @Accessor("x")
    void setX(double x);

    @Accessor("y")
    double getY();

    @Accessor("y")
    void setY(double y);

    @Accessor("z")
    double getZ();

    @Accessor("z")
    void setZ(double z);
}
