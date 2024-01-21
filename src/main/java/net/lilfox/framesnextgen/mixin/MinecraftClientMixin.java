package net.lilfox.framesnextgen.mixin;

import net.lilfox.framesnextgen.configs.Configs;
import net.lilfox.framesnextgen.utils.Flags;
import net.lilfox.framesnextgen.utils.Raycast;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.SignChangingItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(net.minecraft.client.MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    public HitResult crosshairTarget;
    @Shadow
    public ClientPlayerEntity player;
    @Shadow
    public ClientWorld world;


    @Inject(method = "doItemUse", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void switchCrosshairTarget(CallbackInfo ci, Hand[] var1, int var2, int var3, Hand hand) {


        if (hand == Hand.MAIN_HAND) {
            if (Configs.hangableEntitiesBypass) {
                if (crosshairTarget != null) {
                    if (crosshairTarget.getType() == HitResult.Type.BLOCK) {
                        BlockPos blockPos = ((BlockHitResult) crosshairTarget).getBlockPos();
                        BlockState state = world.getBlockState(blockPos);
                        Block block = state.getBlock();

                        if (block instanceof WallSignBlock || block instanceof SignBlock) {

                            BlockHitResult newTarget = Raycast.raycastIgnoreNonCollisionBlocks(player, world);

                            BlockPos newPos = newTarget.getBlockPos();

                            if (player.isSneaking()) {
                                if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof SignChangingItem
                                || player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                                    Flags.shouldReSneak = true;
                                    Flags.preventInteractionCanceling = true;
                                    player.networkHandler.sendPacket(new ClientCommandC2SPacket(player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
                                }
                            } else {
                                this.crosshairTarget = new BlockHitResult(newTarget.getPos(), (newTarget).getSide(), newPos, false);
                            }
                        }

                    }
                }


            }
        }
    }

    @Inject(method = "doItemUse", at = @At("RETURN"))
    public void reSneakIfNecessary(CallbackInfo ci) {
        if (Flags.shouldReSneak) {
            Flags.shouldReSneak = false;
            player.networkHandler.sendPacket(new ClientCommandC2SPacket(player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
        }
    }
}
