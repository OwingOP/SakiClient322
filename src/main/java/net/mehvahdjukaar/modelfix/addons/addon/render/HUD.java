package net.mehvahdjukaar.modelfix.addons.addon.render;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.events.HudListener;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.Utils;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.PlayerListEntry;

public final class HUD extends Module implements HudListener {
    private static final CharSequence argon = EncryptedString.of("saki |");

    private final BooleanSetting info = new BooleanSetting(EncryptedString.of("Info"), true);

    private final BooleanSetting modules = new BooleanSetting("Modules", true)
            .setDescription(EncryptedString.of("Renders module array list"));

    public HUD() {
        super(EncryptedString.of("HUD"), -1, Category.RENDER);
        addSettings(new Setting[]{this.info, this.modules});
    }

    @Override
    public void onEnable() {
        this.eventManager.add(HudListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventManager.remove(HudListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender3D(MatrixStack matrixStack, float partialTicks) {}

    @Override
    public void onRenderHud(HudListener.HudEvent event) {
        if (this.mc.currentScreen != saki.INSTANCE.clickGui) {
            List<Module> enabledModules = saki.INSTANCE.getModuleManager().getEnabledModules().stream()
                    .sorted((module1, module2) -> {
                        int filteredLength1 = TextRenderer.getWidth(module1.getName());
                        int filteredLength2 = TextRenderer.getWidth(module2.getName());
                        return Integer.compare(filteredLength2, filteredLength1);
                    }).toList();

            DrawContext context = event.context;
            boolean customFont = ClickGUI.customFont.getValue();

            if (!(this.mc.currentScreen instanceof net.mehvahdjukaar.modelfix.gui.ClickGui)) {
                if (this.info.getValue() && this.mc.player != null) {
                    RenderUtils.unscaledProjection();
                    int argonOffset = 10;
                    int argonOffset2 = 10 + TextRenderer.getWidth(argon);

                    String ping = "Ping: ";
                    String fps = "FPS: " + this.mc.getCurrentFps() + " |";
                    String server = (this.mc.getCurrentServerEntry() == null) ? "None" : this.mc.getCurrentServerEntry().address;

                    if (this.mc.getNetworkHandler() != null) {
                        PlayerListEntry entry = this.mc.getNetworkHandler().getPlayerListEntry(this.mc.player.getUuid());
                        if (entry != null) {
                            ping = ping + ping + " |";
                        } else {
                            ping = ping + "N/A |";
                        }
                    } else {
                        ping = ping + "N/A |";
                    }

                    RenderUtils.renderRoundedQuad(context.getMatrices(), new Color(35, 35, 35, 255),
                            5.0D, 6.0D,
                            argonOffset2 + TextRenderer.getWidth(fps) + TextRenderer.getWidth(ping) + TextRenderer.getWidth(server) + 35,
                            30.0D, 5.0D, 15.0D);

                    TextRenderer.drawString(argon, context, argonOffset, 12, Utils.getMainColor(255, 4).getRGB());
                    argonOffset += TextRenderer.getWidth(argon);
                    TextRenderer.drawString(fps, context, argonOffset + 10, 12, Utils.getMainColor(255, 3).getRGB());
                    TextRenderer.drawString(ping, context, argonOffset + 10 + TextRenderer.getWidth(fps) + 10, 12, Utils.getMainColor(255, 2).getRGB());
                    TextRenderer.drawString(server, context, argonOffset + 10 + TextRenderer.getWidth(fps) + TextRenderer.getWidth(ping) + 20, 12, Utils.getMainColor(255, 1).getRGB());

                    RenderUtils.scaledProjection();
                }

                if (this.modules.getValue()) {
                    int offset = 55;
                    for (Module module : enabledModules) {
                        RenderUtils.unscaledProjection();
                        int charOffset = 6 + TextRenderer.getWidth(module.getName());
                        Objects.requireNonNull(this.mc.textRenderer);

                        RenderUtils.renderRoundedQuad(context.getMatrices(), new Color(0, 0, 0, 175),
                                0.0D, offset - 4, charOffset + 5, offset + 18 - 1,
                                0.0D, 0.0D, 0.0D, 5.0D, 10.0D);

                        Objects.requireNonNull(this.mc.textRenderer);
                        context.fillGradient(0, offset - 4, 2, offset + 18,
                                Utils.getMainColor(255, enabledModules.indexOf(module)).getRGB(),
                                Utils.getMainColor(255, enabledModules.indexOf(module) + 1).getRGB());

                        int charOffset2 = customFont ? 5 : 8;
                        TextRenderer.drawString(module.getName(), context, charOffset2, offset + (customFont ? 1 : 0),
                                Utils.getMainColor(255, enabledModules.indexOf(module)).getRGB());

                        Objects.requireNonNull(this.mc.textRenderer);
                        offset += 18 + 3;
                        RenderUtils.scaledProjection();
                    }
                }
            }
        }
    }
}
