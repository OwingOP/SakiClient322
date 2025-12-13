package net.mehvahdjukaar.modelfix.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Static friend registry for name-based checks.
 */
public final class FriendManagerImpl {
    private static final Set<String> friends = new HashSet<>();

    public static void addFriend(String name) {
        if (name != null) friends.add(name.toLowerCase());
    }

    public static void removeFriend(String name) {
        if (name != null) friends.remove(name.toLowerCase());
    }

    public static boolean isFriend(String name) {
        return name != null && friends.contains(name.toLowerCase());
    }
}
