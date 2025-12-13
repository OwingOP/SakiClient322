package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.addons.addon.render.NoBounce;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.CrystalUtils;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;

import net.minecraft.util.ActionResult;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EndCrystalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystalItem.class)
public class EndCrystalItemMixin {
    @Unique
    private Vec3d getPlayerLookVec(PlayerEntity p) {
        return RenderUtils.getPlayerLookVec(p);
    }

    @Unique
    private Vec3d getClientLookVec() {
        assert saki.mc.player != null;
        return getPlayerLookVec(saki.mc.player);
    }

    @Unique
    private boolean isBlock(Block b, BlockPos p) {
        return getBlockState(p).getBlock() == b;
    }

    @Unique
    private BlockState getBlockState(BlockPos p) {
        return saki.mc.world.getBlockState(p);
    }

    @Unique
    private boolean canPlaceCrystalServer(BlockPos blockPos) {
        BlockState blockState = saki.mc.world.getBlockState(blockPos);
        if (!blockState.isOf(Blocks.OBSIDIAN) && !blockState.isOf(Blocks.BEDROCK)) {
            return false;
        }
        return CrystalUtils.canPlaceCrystalClientAssumeObsidian(blockPos);
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void onUse(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        NoBounce noBounce = (NoBounce) saki.INSTANCE.getModuleManager().getModule(NoBounce.class);
        if (noBounce.isEnabled() && saki.INSTANCE != null && saki.mc.player != null) {
            ItemStack mainHandStack = saki.mc.player.getMainHandStack();
            if (mainHandStack.isOf(Items.END_CRYSTAL)) {
                Vec3d e = saki.mc.player.getPos();
                BlockHitResult blockHit = saki.mc.world.raycast(new RaycastContext(
                        e,
                        e.add(getClientLookVec().multiply(4.5D)),
                        RaycastContext.ShapeType.OUTLINE,
                        RaycastContext.FluidHandling.NONE,
                        saki.mc.player
                ));
                if (isBlock(Blocks.OBSIDIAN, blockHit.getBlockPos()) || isBlock(Blocks.BEDROCK, blockHit.getBlockPos())) {
                    HitResult hitResult = saki.mc.crosshairTarget;
                    if (hitResult instanceof BlockHitResult) {
                        BlockHitResult blockHit2 = (BlockHitResult) hitResult;
                        BlockPos pos = blockHit2.getBlockPos();
                        if (canPlaceCrystalServer(pos)) {
                            context.getPlayer().addExperience(-1);
                        }
                    }
                }
            }
        }
    }
}
