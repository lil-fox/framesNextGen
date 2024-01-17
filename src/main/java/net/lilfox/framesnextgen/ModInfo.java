package net.lilfox.framesnextgen;

import top.hendrixshen.magiclib.language.api.I18n;
import top.hendrixshen.magiclib.malilib.impl.ConfigHandler;

public class ModInfo {
    public static final String MOD_ID = "frames_next_gen";
    public static final String MOD_NAME = "Frames Next Gen";
    public static ConfigHandler configHandler;

    public static String translate(String key, Object... objects) {
        return I18n.get("frames_next_gen." + key, objects);
    }
}
