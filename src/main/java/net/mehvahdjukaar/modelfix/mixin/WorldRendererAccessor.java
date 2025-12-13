package net.mehvahdjukaar.modelfix.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.Frustum;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {
    @Accessor("chunks")
    ChunkBuilder getChunks();

    @Accessor("frustum")
    Frustum getFrustum();

    @Accessor("frustum")
    void setFrustum(Frustum frustum);
}
