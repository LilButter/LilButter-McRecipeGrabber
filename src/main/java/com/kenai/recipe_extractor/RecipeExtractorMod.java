package com.kenai.recipe_extractor;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("recipe_extractor_mod")
public class RecipeExtractorMod {
    public RecipeExtractorMod() {
        // Register event handlers
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupServer);
    }

    private void setupClient(final FMLClientSetupEvent event) {
        // Client setup code
        System.out.println("Recipe Extractor: Client setup complete!");
    }

    private void setupServer(final FMLDedicatedServerSetupEvent event) {
        // Server setup code
        System.out.println("Recipe Extractor: Server setup complete!");
    }
}