package net.lilfox.framesnextgen.mixin.magiclibfix;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.malilib.impl.config.MagicConfigHotkey;

@Mixin(
        value = {WidgetConfigOption.class},
        priority = 999,
        remap = false
)
public abstract class WidgetConfigOptionMixin {

    @Shadow
    protected abstract void addHotkeyConfigElements(int var1, int var2, int var3, String var4, IHotkey var5);

    @Inject(
            method = {"addConfigOption"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetConfigOption;addHotkeyConfigElements(IIILjava/lang/String;Lfi/dy/masa/malilib/hotkeys/IHotkey;)V"
            )},
            cancellable = true
    )
    private void deleteIdioticTriggerButtonFromMagicLib(int x, int y, float zLevel, int labelWidth, int configWidth, IConfigBase config, CallbackInfo ci) {
        if (config instanceof MagicConfigHotkey) {
            this.addHotkeyConfigElements(x, y, configWidth, config.getName(), (IHotkey)config);
            ci.cancel();
        }

    }
}