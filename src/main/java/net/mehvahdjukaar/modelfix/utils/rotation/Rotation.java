package net.mehvahdjukaar.modelfix.utils.rotation;

/**
 * Immutable rotation holder with yaw and pitch.
 */
public final class Rotation {
    private final double yaw;
    private final double pitch;

    public Rotation(double yaw, double pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double yaw() {
        return yaw;
    }

    public double pitch() {
        return pitch;
    }

    @Override
    public String toString() {
        return "Rotation{yaw=" + yaw + ", pitch=" + pitch + '}';
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(yaw);
        int result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pitch);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rotation)) return false;
        Rotation other = (Rotation) o;
        return Double.compare(other.yaw, yaw) == 0 &&
                Double.compare(other.pitch, pitch) == 0;
    }
}
