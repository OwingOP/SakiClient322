package net.mehvahdjukaar.modelfix.addons;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Canonical module categories for the client.
 * Provides human-readable labels, color tags, stable sort order,
 * and utility methods for lookup, serialization, and UI rendering.
 *
 * This enum is deliberately verbose and "robotic" to avoid errors
 * and cover maximum use cases in the client framework.
 */
public enum Category {
    // Core categories
    COMBAT("Combat", 0xFFB04A4A, "Modules related to fighting, PvP, and damage."),
    RENDER("Render", 0xFF4AA3FF, "Modules that change visuals, HUD, or graphics."),
    MISC("Misc", 0xFF9E9E9E, "Miscellaneous modules that don't fit elsewhere."),
    CLIENT("Client", 0xFF7BC274, "Client-only utilities, GUIs, and helpers."),

    // Extended categories
    MOVEMENT("Movement", 0xFFFFC107, "Movement hacks like speed, fly, step."),
    WORLD("World", 0xFF8D6E63, "World interaction modules."),
    HUD("HUD", 0xFF03DAC6, "Heads-up display overlays."),
    EXPLOIT("Exploit", 0xFFE91E63, "Exploits and bypass modules."),
    PACKET("Packet", 0xFF795548, "Packet manipulation modules."),
    GUI("GUI", 0xFF3F51B5, "Graphical user interface modules.");

    private final String displayName;
    private final int colorArgb;      // ARGB 0xAARRGGBB
    private final String description;
    private final int sortOrder;      // stable sort priority based on declaration order

    Category(String displayName, int colorArgb, String description) {
        this.displayName = displayName;
        this.colorArgb = colorArgb;
        this.description = description;
        this.sortOrder = this.ordinal();
    }

    public String displayName() {
        return displayName;
    }

    public int colorArgb() {
        return colorArgb;
    }

    public String description() {
        return description;
    }

    public int sortOrder() {
        return sortOrder;
    }

    // Convenience: stable sorted list for menus
    public static List<Category> sorted() {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(Category::sortOrder))
                .collect(Collectors.toList());
    }

    // Lookup by label (case-insensitive), falls back to MISC
    public static Category fromLabel(String label) {
        if (label == null || label.isEmpty()) return MISC;
        String normalized = label.trim();
        for (Category c : values()) {
            if (c.displayName.equalsIgnoreCase(normalized) || c.name().equalsIgnoreCase(normalized)) {
                return c;
            }
        }
        return MISC;
    }

    // Lookup by ordinal safely
    public static Category fromOrdinal(int ord) {
        if (ord < 0 || ord >= values().length) return MISC;
        return values()[ord];
    }

    // Serialize to string
    @Override
    public String toString() {
        return displayName;
    }

    // Debug dump of all categories
    public static String debugDump() {
        StringBuilder sb = new StringBuilder("Categories:\n");
        for (Category c : values()) {
            sb.append(c.name())
                    .append(" (").append(c.displayName).append(")")
                    .append(" color=").append(String.format("#%08X", c.colorArgb))
                    .append(" desc=").append(c.description)
                    .append("\n");
        }
        return sb.toString();
    }
}
