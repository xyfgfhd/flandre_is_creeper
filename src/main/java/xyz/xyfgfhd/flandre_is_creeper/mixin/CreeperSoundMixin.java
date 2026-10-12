package xyz.xyfgfhd.flandre_is_creeper.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.xyfgfhd.flandre_is_creeper.client.FlandreSongSoundInstance;

/**
 * 客户端 mixin，只负责「起播」。
 * 停止/暂停由 FlandreSongSoundInstance.tick() 自己做 —— stop() 是 protected final，外面调不了。
 *
 * 必须登记在 mixins.json 的 "client" 数组里，不要放 "mixins"：
 * 这个类引用了 Minecraft / FlandreSongSoundInstance 等 @OnlyIn(Dist.CLIENT) 的类，
 * 放进双端数组会让专用服务器在加载 mixin 时 NoClassDefFoundError 崩掉。
 */
@Mixin(Creeper.class)
public class CreeperSoundMixin {

    /** 当前这只苦力怕正在播的实例；@Unique 会把字段合并进 Creeper */
    @Unique
    private FlandreSongSoundInstance flandre$song;

    @Inject(method = "tick", at = @At("HEAD"))
    private void flandre$manageSong(CallbackInfo ci) {
        Creeper self = (Creeper)(Object) this;

        if (!self.isIgnited()) {
        }else if (this.flandre$song == null) {
            this.flandre$song = new FlandreSongSoundInstance(self);
            Minecraft.getInstance().getSoundManager().play(this.flandre$song);
        }
    }
}
