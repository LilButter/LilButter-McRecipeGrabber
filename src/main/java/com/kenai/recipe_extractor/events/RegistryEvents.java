package com.kenai.recipe_extractor.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

@Mod.EventBusSubscriber
public class RegistryEvents {

    @SubscribeEvent
    public static void onServerStart(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        Collection<Recipe<?>> recipes = server.getRecipeManager().getRecipes();

        JsonArray recipesJson = new JsonArray();

        for (Recipe<?> recipe : recipes) {
            JsonObject recipeJson = new JsonObject();

            // Add recipe type and ID
            recipeJson.addProperty("type", recipe.getType().toString());
            recipeJson.addProperty("id", recipe.getId().toString());

            // Add output item
            ItemStack resultItem = recipe.getResultItem(server.registryAccess());
            Item outputItem = resultItem.getItem();
            ResourceLocation outputRegistryName = ForgeRegistries.ITEMS.getKey(outputItem);
            recipeJson.addProperty("output", outputRegistryName != null ? outputRegistryName.toString() : "unknown");

            // Add inputs (ingredients)
            JsonArray inputsJson = new JsonArray();
            for (Ingredient ingredient : recipe.getIngredients()) {
                JsonObject inputJson = new JsonObject();
                for (ItemStack stack : ingredient.getItems()) {
                    ResourceLocation inputRegistryName = ForgeRegistries.ITEMS.getKey(stack.getItem());
                    inputJson.addProperty("item", inputRegistryName != null ? inputRegistryName.toString() : "unknown");
                }
                inputsJson.add(inputJson);
            }
            recipeJson.add("inputs", inputsJson);

            recipesJson.add(recipeJson);
        }

        // Save the recipes to a file
        try (FileWriter file = new FileWriter("recipes.json")) {
            file.write(recipesJson.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}