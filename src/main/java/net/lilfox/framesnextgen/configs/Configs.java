package net.lilfox.framesnextgen.configs;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import net.lilfox.framesnextgen.gui.ConfigsGui;
import net.minecraft.client.MinecraftClient;
import top.hendrixshen.magiclib.malilib.api.annotation.Config;
import top.hendrixshen.magiclib.malilib.api.annotation.Hotkey;
import top.hendrixshen.magiclib.malilib.api.annotation.Numeric;
import top.hendrixshen.magiclib.malilib.impl.ConfigHandler;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;

public class Configs {
    private static final String CONFIG_FILE_NAME = "frames_next_gen.json";
    public static final int CONFIG_VERSION = 1;

    @Config(
            category = "configs"
    )
    public static boolean invisibleItemFrames;

    @Config(category="configs")
    public static boolean hangableEntitiesBypass;

    @Config(
            category = "configs"
    )
    public static boolean noInvisibleFramesOffset;

    @Config(
            category = "configs"
    )
    public static boolean customInvisibleFramesOffset;

    @Config(
            category = "configs"
    )
    @Numeric(
            maxValue = 1.375,
            minValue = -1.375,
            useSlider = true
    )
    public static double invisibleFramesXOffset = -0.5f;

    @Config(
            category = "configs"
    )
    @Numeric(
            maxValue = 1.375,
            minValue = -1.375,
            useSlider = true
    )
    public static double invisibleFramesYOffset = -0.5f;
    @Config(
            category = "configs"
    )
    @Numeric(
            maxValue = 1.375,
            minValue = -1.376,
            useSlider = true
    )
    public static double invisibleFramesZOffset = 0.4375f;

    @Hotkey(
            hotkey = "B,F"
    )
    @Config(
            category = "configs"
    )
    public static ConfigHotkey openConfigGui;

    public Configs() {
    }

    public static void init(ConfigManager cm) {
        openConfigGui.getKeybind().setCallback((keyAction, iKeybind) -> {
            ConfigsGui screen = ConfigsGui.getInstance();
            screen.setParent( MinecraftClient.getInstance().currentScreen);
            MinecraftClient.getInstance().setScreen(screen);
            return true;
        });
    }

    public static void preDeserialize(ConfigHandler configHandler) {
    }

    public static void postSerialize(ConfigHandler configHandler) {
    }

    public static class ConfigCategory {

        public static final String CONFIGS = "configs";


        public ConfigCategory() {
        }
    }
}