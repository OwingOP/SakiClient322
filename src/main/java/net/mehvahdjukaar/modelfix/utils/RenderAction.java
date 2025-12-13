package net.mehvahdjukaar.modelfix.utils;

import net.minecraft.client.render.BufferBuilder;
import org.joml.Matrix4f;

interface RenderAction {
    void run(BufferBuilder bufferBuilder,
             float x1, float y1, float z1,
             float x2, float y2, float z2,
             float x3, float y3, float z3,
             float x4, float y4, float z4,
             Matrix4f matrix);
}