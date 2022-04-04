package com.blakebr0.ironjetpacks.compat.kubejs;

import com.blakebr0.ironjetpacks.crafting.JetpackDynamicRecipeManager;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class KubeJSCompat implements Consumer<Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>>> {
    @Override
    public void accept(Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipeTypeMapMap) {
        Map<ResourceLocation, Recipe<?>> builder = recipeTypeMapMap.computeIfAbsent(RecipeType.CRAFTING, recipeType -> new HashMap<>());
        JetpackDynamicRecipeManager.appendRecipes(builder::put);
    }
}
