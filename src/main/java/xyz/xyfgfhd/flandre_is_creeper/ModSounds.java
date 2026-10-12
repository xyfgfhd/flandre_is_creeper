package xyz.xyfgfhd.flandre_is_creeper;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(Registries.SOUND_EVENT, "flandre_is_creeper");

    public static final RegistryObject<SoundEvent> Flandre_of_song =
            SOUNDS.register("flandre_of_song", () ->
                    SoundEvent.createVariableRangeEvent(
                            new ResourceLocation("flandre_is_creeper", "flandre_of_song")));
}