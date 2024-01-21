package net.lilfox.framesnextgen.mixin;

import net.lilfox.framesnextgen.utils.Flags;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method="shouldCancelInteraction", at=@At("HEAD"), cancellable = true)
    private void noCancelInteraction(CallbackInfoReturnable<Boolean> cir) {
        if (((Object) this) instanceof ClientPlayerEntity) {
            if (Flags.preventInteractionCanceling) {
                cir.setReturnValue(false);
                cir.cancel();
                Flags.preventInteractionCanceling = false;
            }
        }
    }
}
