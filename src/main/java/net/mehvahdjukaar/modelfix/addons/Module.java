package net.mehvahdjukaar.modelfix.addons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.EventManager;
import net.mehvahdjukaar.modelfix.event.events.AttackListener;
import net.mehvahdjukaar.modelfix.saki;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import org.lwjgl.glfw.GLFW;

public abstract class Module implements Serializable {
    private final List<Setting<?>> settings = new ArrayList<>();

    public EventManager eventManager = saki.INSTANCE.eventManager;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    private CharSequence name;
    private CharSequence description;
    private boolean enabled;
    private int key;
    private Category category;

    public Module(CharSequence name, int key, Category category) {
        this.name = name;
        this.description = this.description;
        this.enabled = false;
        this.key = key;
        this.category = category;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public CharSequence getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public CharSequence getDescription() {
        return this.description;
    }

    public int getKey() {
        return this.key;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<Setting<?>> getSettings() {
        return this.settings;
    }

    public void onEnable() {}

    public void onDisable() {}

    public void addSetting(Setting<?> setting) {
        this.settings.add(setting);
    }

    public void addSettings(Setting<?>... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void setEnabledStatus(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract void onRender3D(MatrixStack matrixStack, float partialTicks);

    public void onAttack(AttackListener.AttackEvent event) {
        if (GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), 0) != GLFW.GLFW_PRESS) {
            event.cancel();
        }
    }
}
