package net.lilfox.framesnextgen.mixin.magiclibfix;

import fi.dy.masa.malilib.gui.GuiBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
        value = {GuiBase.class},
        remap = false
)
public class GuiBaseMixin {
    @Shadow
    private int keyInputCount;
    @Unique
    private long openTime;

    @Inject(
            method = {"init"},
            at = {@At("HEAD")},
            remap = true
    )
    private void setOpenTimeToCloseFuckingSearchBarOnNextGuiOpening(CallbackInfo ci) {
        this.openTime = System.nanoTime();
        this.keyInputCount = 0;
    }

    @Inject(
            method = {"charTyped"},
            at = {@At("HEAD")},
            cancellable = true,
            remap = true
    )
    private void closeFuckingSearchBarOnNextGuiOpening(char charIn, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this.keyInputCount <= 0 && System.nanoTime() - this.openTime <= 100000000L) {
            ++this.keyInputCount;
            cir.setReturnValue(true);
        }

    }
}