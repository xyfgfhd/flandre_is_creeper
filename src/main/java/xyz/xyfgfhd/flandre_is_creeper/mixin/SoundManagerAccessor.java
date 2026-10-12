package xyz.xyfgfhd.flandre_is_creeper.mixin;

import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * 只为读到 SoundManager 里那个私有的 soundEngine 字段。
 *
 * 必须是 interface：Mixin 会把接口合并到目标类上，之后就能
 * (SoundManagerAccessor)(Object) manager 转型拿到它。
 * 登记在 mixins.json 的 "client" 数组里（SoundManager 是客户端类）。
 */
@Mixin(SoundManager.class)
public interface SoundManagerAccessor {
    @Accessor("soundEngine")
    SoundEngine flandre$engine();
}
