package net.mehvahdjukaar.modelfix.utils;

import net.minecraft.util.math.Vec3d;
import java.util.Objects;

public final class ExposureRaycastContext {
    private final Vec3d start;
    private final Vec3d end;

    public ExposureRaycastContext(Vec3d start, Vec3d end) {
        this.start = start;
        this.end = end;
    }

    public Vec3d start() {
        return this.start;
    }

    public Vec3d end() {
        return this.end;
    }

    @Override
    public String toString() {
        return "ExposureRaycastContext{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExposureRaycastContext)) return false;
        ExposureRaycastContext that = (ExposureRaycastContext) o;
        return Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }
}
