package net.mehvahdjukaar.modelfix.managers;

import java.util.HashSet;
import java.util.Set;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.EntityHitResult;

/**
 * Entity-based friend manager, supports checking if the player is aiming at a friend.
 */
public final class FriendManager {
    private final Set<String> friends = new HashSet<>();

    public void addFriend(PlayerEntity player) {
        this.friends.add(player.getName().getString());
    }

    public void removeFriend(PlayerEntity player) {
        this.friends.remove(player.getName().getString());
    }

    public boolean isFriend(PlayerEntity player) {
        return this.friends.contains(player.getName().getString());
    }

    public boolean isAimingOverFriend() {
        HitResult hitResult = saki.mc.crosshairTarget;
        if (hitResult instanceof EntityHitResult) {
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            if (entity instanceof PlayerEntity player) {
                return isFriend(player);
            }
        }
        return false;
    }
}
