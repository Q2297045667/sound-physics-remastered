package com.sonicether.soundphysics;

import com.sonicether.soundphysics.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.block.SoundType;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundPhysicsMod implements ModInitializer {
    public static Map<SoundType, String> blockSoundGroups;
    @Override
    public void onInitialize()
    {
        blockSoundGroups = Arrays.stream(SoundType.class.getDeclaredFields())
                .filter((f) -> {
                    try {
                        return Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers()) && f.get(null) instanceof SoundType;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .collect(Collectors.toMap(
                        (f) -> {
                            try {
                                return (SoundType)f.get(null);
                            } catch (IllegalAccessException | ClassCastException e) {
                                e.printStackTrace();
                            }
                            return null;
                        },
                        Field::getName));

        ConfigManager.registerAutoConfig();
    }
}
