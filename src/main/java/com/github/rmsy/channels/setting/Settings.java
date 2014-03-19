package com.github.rmsy.channels.setting;

import me.anxuiz.settings.Setting;
import me.anxuiz.settings.SettingBuilder;
import me.anxuiz.settings.SettingCallbackManager;
import me.anxuiz.settings.SettingRegistry;
import me.anxuiz.settings.bukkit.PlayerSettings;
import me.anxuiz.settings.types.EnumType;

public class Settings {

    public static void register() {
        SettingRegistry registry = PlayerSettings.getRegistry();
        SettingCallbackManager callbacks = PlayerSettings.getCallbackManager();

        registry.register(DEFAULT_CHANNEL);
        callbacks.addCallback(DEFAULT_CHANNEL, new DefaultChannelCallback());
    }

    public static final Setting DEFAULT_CHANNEL = new SettingBuilder()
        .name("DefaultChannel").alias("dfc")
        .summary("Sets the default channel give to you upon login.")
        .description("Available Options:\nGLOBAL: Default channel visible to everybody." +
                                       "\nTEAM: Default channel only visible to team-mates." +
                                       "\nADMIN: Default channel is administrator chat." +
                                       "\nNONE: No default channel specified.")
        .type(new EnumType<DefaultChannelOptions>("Default Channel Options", DefaultChannelOptions.class))
        .defaultValue(DefaultChannelOptions.GLOBAL_CHAT).get();
}
