package net.mehvahdjukaar.modelfix.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.mehvahdjukaar.modelfix.mixin.ClientPlayerInteractionManagerAccessor;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1661;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1844;
import net.minecraft.class_7923;
import net.minecraft.class_9334;

public final class InventoryUtils {
  public static void setInvSlot(int slot) {
    (saki.mc.field_1724.method_31548()).field_7545 = slot;
    ((ClientPlayerInteractionManagerAccessor)saki.mc.field_1761).syncSlot();
  }
  
  public static boolean selectItemFromHotbar(Predicate<class_1792> item) {
    class_1661 inv = saki.mc.field_1724.method_31548();
    for (int i = 0; i < 9; ) {
      class_1799 itemStack = inv.method_5438(i);
      if (!item.test(itemStack.method_7909())) {
        i++;
        continue;
      } 
      inv.field_7545 = i;
      return true;
    } 
    return false;
  }
  
  public static boolean selectItemFromHotbar(class_1792 item) {
    return selectItemFromHotbar(i -> (i == item));
  }
  
  public static boolean hasItemInHotbar(Predicate<class_1792> item) {
    class_1661 inv = saki.mc.field_1724.method_31548();
    for (int i = 0; i < 9; i++) {
      class_1799 itemStack = inv.method_5438(i);
      if (item.test(itemStack.method_7909()))
        return true; 
    } 
    return false;
  }
  
  public static int countItem(Predicate<class_1792> item) {
    class_1661 inv = saki.mc.field_1724.method_31548();
    int count = 0;
    for (int i = 0; i < 36; i++) {
      class_1799 itemStack = inv.method_5438(i);
      if (item.test(itemStack.method_7909()))
        count += itemStack.method_7947(); 
    } 
    return count;
  }
  
  public static int countItemExceptHotbar(Predicate<class_1792> item) {
    class_1661 inv = saki.mc.field_1724.method_31548();
    int count = 0;
    for (int i = 9; i < 36; i++) {
      class_1799 itemStack = inv.method_5438(i);
      if (item.test(itemStack.method_7909()))
        count += itemStack.method_7947(); 
    } 
    return count;
  }
  
  public static int getSwordSlot() {
    class_1661 class_1661 = saki.mc.field_1724.method_31548();
    for (int itemIndex = 0; itemIndex < 9; itemIndex++) {
      if (class_1661.method_5438(itemIndex).method_7909() instanceof net.minecraft.class_1829)
        return itemIndex; 
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
  
  public static int findSplash(class_1291 type, int duration, int amplifier) {
    class_1661 inv = saki.mc.field_1724.method_31548();
    class_1293 potion = new class_1293(class_7923.field_41174.method_47983(type), duration, amplifier);
    for (int i = 0; i < 9; i++) {
      class_1799 itemStack = inv.method_5438(i);
      if (itemStack.method_7909() instanceof net.minecraft.class_1828) {
        String s = ((class_1844)itemStack.method_57824(class_9334.field_49651)).method_57397().toString();
        if (s.contains(potion.toString()))
          return i; 
      } 
    } 
    return -1;
  }
  
  public static boolean isThatSplash(class_1291 type, int duration, int amplifier, class_1799 itemStack) {
    class_1293 potion = new class_1293(class_7923.field_41174.method_47983(type), duration, amplifier);
    return (itemStack.method_7909() instanceof net.minecraft.class_1828 && ((class_1844)itemStack
      .method_57824(class_9334.field_49651)).method_57397().toString().contains(potion.toString()));
  }
  
  public static int findTotemSlot() {
    assert saki.mc.field_1724 != null;
    class_1661 inv = saki.mc.field_1724.method_31548();
    for (int index = 9; index < 36; index++) {
      if (((class_1799)inv.field_7547.get(index)).method_7909() == class_1802.field_8288)
        return index; 
    } 
    return -1;
  }
  
  public static boolean selectAxe() {
    int itemIndex = getAxeSlot();
    if (itemIndex != -1) {
      (saki.mc.field_1724.method_31548()).field_7545 = itemIndex;
      return true;
    } 
    return false;
  }
  
  public static int findRandomTotemSlot() {
    class_1661 inventory = saki.mc.field_1724.method_31548();
    Random random = new Random();
    List<Integer> totemIndexes = new ArrayList<>();
    for (int i = 9; i < 36; i++) {
      if (((class_1799)inventory.field_7547.get(i)).method_7909() == class_1802.field_8288)
        totemIndexes.add(Integer.valueOf(i)); 
    } 
    if (!totemIndexes.isEmpty())
      return ((Integer)totemIndexes.get(random.nextInt(totemIndexes.size()))).intValue(); 
    return -1;
  }
  
  public static int findRandomPot(String potion) {
    class_1661 inventory = saki.mc.field_1724.method_31548();
    Random random = new Random();
    int slotIndex = random.nextInt(27) + 9;
    for (int i = 0; i < 27; i++) {
      int index = (slotIndex + i) % 36;
      class_1799 itemStack = (class_1799)inventory.field_7547.get(index);
      if (itemStack.method_7909() instanceof net.minecraft.class_1828 && (index != 36 || index != 37 || index != 38 || index != 39)) {
        if (!((class_1844)itemStack.method_57824(class_9334.field_49651)).method_57397().toString().contains(potion.toString()))
          return -1; 
        return index;
      } 
    } 
    return -1;
  }
  
  public static int findPot(class_1291 effect, int duration, int amplifier) {
    assert saki.mc.field_1724 != null;
    class_1661 inv = saki.mc.field_1724.method_31548();
    class_1293 instance = new class_1293(class_7923.field_41174.method_47983(effect), duration, amplifier);
    for (int index = 9; index < 34; index++) {
      if (((class_1799)inv.field_7547.get(index)).method_7909() instanceof net.minecraft.class_1828 && (
        (class_1844)((class_1799)inv.field_7547.get(index)).method_57824(class_9334.field_49651)).method_57397().toString().contains(instance.toString()))
        return index; 
    } 
    return -1;
  }
  
  public static List<Integer> getEmptyHotbarSlots() {
    class_1661 inventory = saki.mc.field_1724.method_31548();
    List<Integer> slots = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      if (((class_1799)inventory.field_7547.get(i)).method_7960()) {
        slots.add(Integer.valueOf(i));
      } else if (slots.contains(Integer.valueOf(i)) && !((class_1799)inventory.field_7547.get(i)).method_7960()) {
        slots.remove(i);
      } 
    } 
    return slots;
  }
  
  public static int getAxeSlot() {
    class_1661 class_1661 = saki.mc.field_1724.method_31548();
    for (int itemIndex = 0; itemIndex < 9; itemIndex++) {
      if (class_1661.method_5438(itemIndex).method_7909() instanceof net.minecraft.class_1743)
        return itemIndex; 
    } 
    return -1;
  }
  
  public static int countItem(class_1792 item) {
    return countItem(i -> (i == item));
  }
}
