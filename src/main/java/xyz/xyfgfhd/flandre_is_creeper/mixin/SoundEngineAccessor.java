package xyz.xyfgfhd.flandre_is_creeper.mixin;

import java.util.Map;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * 只为读到 SoundEngine 里那个私有的 instanceToChannel。
 *
 * 有了它才能拿到「某个 SoundInstance 对应的 ChannelHandle」——这是对单个声音
 * 做 pause/unpause 的唯一入口。原版只公开了全局的 SoundEngine.pause()/resume()，
 * 那会把所有声音一起暂停。
 *
 * 拿到句柄后用 ChannelHandle.execute(Consumer<Channel>) 调 Channel::pause / ::unpause
 * （OpenAL 会保住播放位置，原版自己的暂停菜单就是这么做的）。
 */
@Mixin(SoundEngine.class)
public interface SoundEngineAccessor {
    @Accessor("instanceToChannel")
    Map<SoundInstance, ChannelAccess.ChannelHandle> flandre$instanceToChannel();
}
