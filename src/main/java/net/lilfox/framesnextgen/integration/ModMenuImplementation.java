package net.lilfox.framesnextgen.integration;

import net.lilfox.framesnextgen.ModInfo;
import net.lilfox.framesnextgen.gui.ConfigsGui;
import top.hendrixshen.magiclib.compat.modmenu.ModMenuCompatApi;

public class ModMenuImplementation implements ModMenuCompatApi {
    @Override
    public ModMenuCompatApi.ConfigScreenFactoryCompat<?> getConfigScreenFactoryCompat() {
        return (screen) -> {
            ConfigsGui gui = ConfigsGui.getInstance();
            gui.setParent(screen);
            return gui;
        };
    }

    @Override
    public String getModIdCompat() {
        return ModInfo.MOD_ID;
    }
}
