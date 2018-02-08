package com.blakebr0.ironjetpacks;

import com.blakebr0.ironjetpacks.registry.JetpackRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class IJCreativeTab extends CreativeTabs {

	public IJCreativeTab() {
		super(IronJetpacks.MOD_ID);
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(JetpackRegistry.getInstance().getAllJetpacks().get(0).getRight());
	}
}