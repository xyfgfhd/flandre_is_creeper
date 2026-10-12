package xyz.xyfgfhd.flandre_is_creeper.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.xyfgfhd.flandre_is_creeper.client.FlandreSongSoundInstance;

@Mixin(Creeper.class)
public class CreeperSoundMixin {

    @Unique private FlandreSongSoundInstance flandre$song;
    @Unique private boolean flandre$songStarted = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void flandre$manageSong(CallbackInfo ci) {
        Creeper self = (Creeper)(Object) this;

        if (this.flandre$songStarted) return;
        if (!self.isIgnited() && self.getSwellDir() <= 0) return;
        this.flandre$songStarted = true;
        this.flandre$song = new FlandreSongSoundInstance(self);
        Minecraft.getInstance().getSoundManager().play(this.flandre$song);
    }
}
