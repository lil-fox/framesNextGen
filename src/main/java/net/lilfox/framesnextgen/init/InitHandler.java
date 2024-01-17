package net.lilfox.framesnextgen.init;

import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.lilfox.framesnextgen.ModInfo;
import net.lilfox.framesnextgen.configs.Configs;
import top.hendrixshen.magiclib.malilib.impl.ConfigHandler;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;

public class InitHandler implements IInitializationHandler {
    public InitHandler() {
    }

    public void registerModHandlers() {
        ConfigManager cm = ConfigManager.get("betterframes");
        cm.parseConfigClass(Configs.class);
        ModInfo.configHandler = new ConfigHandler("betterframes", cm, 1);
        ModInfo.configHandler.preDeserializeCallback = Configs::preDeserialize;
        ModInfo.configHandler.postSerializeCallback = Configs::postSerialize;
        ConfigHandler.register(ModInfo.configHandler);
        Configs.init(cm);
    }
}
