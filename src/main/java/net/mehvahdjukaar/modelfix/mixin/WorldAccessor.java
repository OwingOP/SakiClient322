package net.mehvahdjukaar.modelfix.mixin;

import java.util.List;

import net.minecraft.world.World;
import net.minecraft.block.entity.BlockEntityTicker;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(World.class)
public interface WorldAccessor {
    @Accessor("blockEntityTickers")
    List<BlockEntityTicker<?>> getBlockEntityTickers();
}
