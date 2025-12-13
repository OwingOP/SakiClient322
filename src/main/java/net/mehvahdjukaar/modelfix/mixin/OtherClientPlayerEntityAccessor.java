package net.mehvahdjukaar.modelfix.mixin;

import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(OtherClientPlayerEntity.class)
public interface OtherClientPlayerEntityAccessor {
    @Accessor("velocityLerpDivisor")
    int getVelocityLerpDivisor();

    @Accessor("velocityLerpDivisor")
    void setVelocityLerpDivisor(int divisor);

    @Accessor("clientVelocity")
    Vec3d getClientVelocity();

    @Accessor("clientVelocity")
    void setClientVelocity(Vec3d velocity);
}
