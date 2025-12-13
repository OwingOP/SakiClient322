package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.imixin.IKeyBinding;
import net.mehvahdjukaar.modelfix.saki;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin implements IKeyBinding {
    @Shadow
    private InputUtil.Key boundKey;

    @Override
    public boolean isActuallyPressed() {
        long handle = saki.mc.getWindow().getHandle();
        int code = this.boundKey.getCode();
        return InputUtil.isKeyPressed(handle, code);
    }

    @Override
    public void resetPressed() {
        setPressed(isActuallyPressed());
    }

    @Shadow
    public abstract void setPressed(boolean pressed);
}
