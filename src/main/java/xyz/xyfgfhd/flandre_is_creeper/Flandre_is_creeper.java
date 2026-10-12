package xyz.xyfgfhd.flandre_is_creeper;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Flandre_is_creeper.MODID)
public class Flandre_is_creeper {

    public static final String MODID = "flandre_is_creeper";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Flandre_is_creeper() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModSounds.SOUNDS.register(modEventBus);

        modEventBus.addListener(this::onRegisterRenderers);
        modEventBus.addListener(this::onRegisterLayers);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    @SuppressWarnings("removal")
    public void onEntitySize(EntityEvent.Size event) {
        if (event.getEntity() instanceof Creeper) {
            event.setNewSize(new EntityDimensions(0.5F, 1.0F, false), true);
        }
    }

    private void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.CREEPER, FlandreCreeperRenderer::new);
    }

    private void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelFumo.LAYER_LOCATION, ModelFumo::createBodyLayer);
    }
}