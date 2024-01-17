package net.lilfox.framesnextgen.gui;

import net.lilfox.framesnextgen.ModInfo;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;
import top.hendrixshen.magiclib.malilib.impl.gui.ConfigGui;

public class ConfigsGui extends ConfigGui {
    private static ConfigsGui INSTANCE;

    public ConfigsGui(String modId, String defaultTab, ConfigManager configManager) {
        super(modId, defaultTab, configManager, () -> {
            return ModInfo.translate("gui.title.configs", new Object[]{"frames_next_gen"});
        });
    }

    public static ConfigsGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigsGui("frames_next_gen", "configs", ConfigManager.get("frames_next_gen"));
        }

        return INSTANCE;
    }
}
