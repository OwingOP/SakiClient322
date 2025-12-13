package net.mehvahdjukaar.modelfix.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import net.mehvahdjukaar.modelfix.mixin.ClientPlayerInteractionManagerAccessor;
import net.mehvahdjukaar.modelfix.saki;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;

public final class InventoryUtils {
    public static void setInvSlot(int slot) {
        saki.mc.player.getInventory().selectedSlot = slot;
        ((ClientPlayerInteractionManagerAccessor) saki.mc.interactionManager).syncSlot();
    }

    public static boolean selectItemFromHotbar(Predicate<Item> item) {
        PlayerInventory inv = saki.mc.player.getInventory();
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = inv.getStack(i);
            if (!item.test(itemStack.getItem())) continue;
            inv.selectedSlot = i;
            return true;
        }
        return false;
    }

    public static boolean selectItemFromHotbar(Item item) {
        return selectItemFromHotbar(i -> i == item);
    }

    public static boolean hasItemInHotbar(Predicate<Item> item) {
        PlayerInventory inv = saki.mc.player.getInventory();
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = inv.getStack(i);
            if (item.test(itemStack.getItem())) return true;
        }
        return false;
    }

    public static int countItem(Predicate<Item> item) {
        PlayerInventory inv = saki.mc.player.getInventory();
        int count = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack itemStack = inv.getStack(i);
            if (item.test(itemStack.getItem())) count += itemStack.getCount();
        }
        return count;
    }

    public static int countItemExceptHotbar(Predicate<Item> item) {
        PlayerInventory inv = saki.mc.player.getInventory();
        int count = 0;
        for (int i = 9; i < 36; i++) {
            ItemStack itemStack = inv.getStack(i);
            if (item.test(itemStack.getItem())) count += itemStack.getCount();
        }
        return count;
    }

    public static int getSwordSlot() {
        PlayerInventory inv = saki.mc.player.getInventory();
        for (int itemIndex = 0; itemIndex < 9; itemIndex++) {
            if (inv.getStack(itemIndex).getItem() instanceof SwordItem) return itemIndex;
        }
        return -1;
    }

    public static boolean selectSword() {
        int itemIndex = getSwordSlot();
        if (itemIndex != -1) {
            setInvSlot(itemIndex);
            return true;
        }
        return false;
    }

    public static int findSplash(StatusEffect type, int duration, int amplifier) {
        PlayerInventory inv = saki.mc.player.getInventory();
        StatusEffectInstance potion = new StatusEffectInstance(type, duration, amplifier);
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = inv.getStack(i);
            if (itemStack.getItem() instanceof PotionItem) {
                NbtCompound nbt = itemStack.getNbt();
                if (nbt != null && nbt.toString().contains(potion.toString())) return i;
            }
        }
        return -1;
    }

    public static boolean isThatSplash(StatusEffect type, int duration, int amplifier, ItemStack itemStack) {
        StatusEffectInstance potion = new StatusEffectInstance(type, duration, amplifier);
        return (itemStack.getItem() instanceof PotionItem
                && itemStack.getNbt() != null
                && itemStack.getNbt().toString().contains(potion.toString()));
    }

    public static int findTotemSlot() {
        PlayerInventory inv = saki.mc.player.getInventory();
        for (int index = 9; index < 36; index++) {
            if (inv.main.get(index).getItem() == Items.TOTEM_OF_UNDYING) return index;
        }
        return -1;
    }

    public static boolean selectAxe() {
        int itemIndex = getAxeSlot();
        if (itemIndex != -1) {
            saki.mc.player.getInventory().selectedSlot = itemIndex;
            return true;
        }
        return false;
    }

    public static int findRandomTotemSlot() {
        PlayerInventory inventory = saki.mc.player.getInventory();
        Random random = new Random();
        List<Integer> totemIndexes = new ArrayList<>();
        for (int i = 9; i < 36; i++) {
            if (inventory.main.get(i).getItem() == Items.TOTEM_OF_UNDYING) totemIndexes.add(i);
        }
        if (!totemIndexes.isEmpty()) return totemIndexes.get(random.nextInt(totemIndexes.size()));
        return -1;
    }

    public static int findRandomPot(String potion) {
        PlayerInventory inventory = saki.mc.player.getInventory();
        Random random = new Random();
        int slotIndex = random.nextInt(27) + 9;
        for (int i = 0; i < 27; i++) {
            int index = (slotIndex + i) % 36;
            ItemStack itemStack = inventory.main.get(index);
            if (itemStack.getItem() instanceof PotionItem) {
                if (itemStack.getNbt() != null && itemStack.getNbt().toString().contains(potion)) return index;
            }
        }
        return -1;
    }

    public static int findPot(StatusEffect effect, int duration, int amplifier) {
        PlayerInventory inv = saki.mc.player.getInventory();
        StatusEffectInstance instance = new StatusEffectInstance(effect, duration, amplifier);
        for (int index = 9; index < 34; index++) {
            ItemStack stack = inv.main.get(index);
            if (stack.getItem() instanceof PotionItem && stack.getNbt() != null && stack.getNbt().toString().contains(instance.toString())) {
                return index;
            }
        }
        return -1;
    }

    public static List<Integer> getEmptyHotbarSlots() {
        PlayerInventory inventory = saki.mc.player.getInventory();
        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (inventory.main.get(i).isEmpty()) {
                slots.add(i);
            } else if (slots.contains(i) && !inventory.main.get(i).isEmpty()) {
                slots.remove((Integer) i);
            }
        }
        return slots;
    }

    public static int getAxeSlot() {
        PlayerInventory inv = saki.mc.player.getInventory();
        for (int itemIndex = 0; itemIndex < 9; itemIndex++) {
            if (inv.getStack(itemIndex).getItem() instanceof AxeItem) return itemIndex;
        }
        return -1;
    }

    public static int countItem(Item item) {
        return countItem(i -> i == item);
    }
}
