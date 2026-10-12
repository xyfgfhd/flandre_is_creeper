package xyz.xyfgfhd.flandre_is_creeper.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.xyfgfhd.flandre_is_creeper.api.Scalable;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Entity implements Scalable {

    protected CreeperMixin(EntityType<? extends Creeper> type, Level level) {
        super(type, level);
    }

    @Shadow private int maxSwell;
    @Shadow private int explosionRadius;
    @Shadow private int swell;


    @Inject(method = "<init>", at = @At("TAIL"))
    private void flandre$setFuse(EntityType<? extends Creeper> type, Level level, CallbackInfo ci) {
        this.maxSwell = 230;
        // this.explosionRadius = 16;
    }

    /**
     * 故意留空 —— 吞掉 vanilla 的点燃嘶声。
     * 参数列表由「被重定向的那次调用」决定，不能改也不能删。
     */
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/Creeper;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"
            )
    )
    private void flandre$muteVanillaHiss(Creeper self, SoundEvent sound, float volume, float pitch) {
        // 故意留空
    }

    @Unique
    private boolean flandre$primed = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void flandre$driveFuse(CallbackInfo ci) {
        Creeper self = (Creeper) (Object) this;

        if (!flandre$primed && (self.isIgnited() || self.getSwellDir() > 0)) {
            flandre$phase = 0;
            flandre$primed = true;
        }

        if (flandre$primed) {
            if (!self.level().isClientSide) self.setSwellDir(1);
            flandre$phase++;
            if (flandre$phase == 7) flandre$phase = 0;
        }
    }

    @Inject(method = "setTarget", at = @At("TAIL"))
    private void flandre$syncTargetingPlayer(LivingEntity target, CallbackInfo ci) {
        Creeper self = (Creeper)(Object) this;
        boolean targetingPlayer = self.getTarget() instanceof Player;
        if (self.isAggressive() != targetingPlayer) {
            self.setAggressive(targetingPlayer);
        }
    }

    @Unique
    private int flandre$phase;

        @Override
        public float renderScale(float partialTick) {
            if (!flandre$primed) return 1.0F;

            float phase = (flandre$phase + partialTick) / 3.5F;

            float p = 0.75F - Math.abs(phase - 1.0F);
            p = (float) (Math.log(1.0F + p) / Math.log(2.0));
            return 1.0F + p * 0.375F;
        }
    }