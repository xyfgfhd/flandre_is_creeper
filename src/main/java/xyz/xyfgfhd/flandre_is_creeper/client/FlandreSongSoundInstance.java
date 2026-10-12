package xyz.xyfgfhd.flandre_is_creeper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.xyfgfhd.flandre_is_creeper.ModSounds;
import xyz.xyfgfhd.flandre_is_creeper.mixin.SoundEngineAccessor;
import xyz.xyfgfhd.flandre_is_creeper.mixin.SoundManagerAccessor;

@OnlyIn(Dist.CLIENT)
public class FlandreSongSoundInstance extends AbstractTickableSoundInstance {

    private final Creeper creeper;

    public FlandreSongSoundInstance(Creeper creeper) {
        super(ModSounds.Flandre_of_song.get(), SoundSource.HOSTILE, SoundInstance.createUnseededRandom());
        this.creeper = creeper;
        this.delay = 0;
        this.x = creeper.getX();
        this.y = creeper.getY();
        this.z = creeper.getZ();
    }

    public boolean canPlaySound() {
        return !this.creeper.isSilent();
    }

    public boolean canStartSilent() {
        return true;
    }

    public void tick() {
        if (this.creeper.isRemoved()) {
            this.stop();
            return;
        }

        this.x = this.creeper.getX();
        this.y = this.creeper.getY();
        this.z = this.creeper.getZ();
    }

    private ChannelAccess.ChannelHandle flandre$handle() {
        SoundManager manager = Minecraft.getInstance().getSoundManager();
        SoundEngine engine = ((SoundManagerAccessor) manager).flandre$engine();
        return ((SoundEngineAccessor) engine).flandre$instanceToChannel().get(this);
    }
}
