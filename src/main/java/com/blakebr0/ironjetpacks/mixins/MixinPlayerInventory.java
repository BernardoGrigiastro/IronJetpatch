package com.blakebr0.ironjetpacks.mixins;

import com.blakebr0.ironjetpacks.item.ArmorTickable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class MixinPlayerInventory {
    @Shadow @Final public DefaultedList<ItemStack> armor;
    
    @Shadow @Final public PlayerEntity player;
    
    @Inject(method = "updateItems", at = @At("RETURN"))
    private void updateItems(CallbackInfo ci) {
        for (ItemStack stack : armor) {
            if (stack.getItem() instanceof ArmorTickable) {
                ((ArmorTickable) stack.getItem()).tickArmor(stack, player.world, player);
            }
        }
    }
}
