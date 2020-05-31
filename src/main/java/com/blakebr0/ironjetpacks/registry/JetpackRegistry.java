package com.blakebr0.ironjetpacks.registry;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.item.ModItems;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class JetpackRegistry {
    private static final JetpackRegistry INSTANCE = new JetpackRegistry();
    private final ArrayList<Jetpack> jetpacks = new ArrayList<>();
    private final ArrayList<Integer> tiers = new ArrayList<>();
    private int lowestTier = Integer.MAX_VALUE;
    private boolean isErrored = false;
    
    public static JetpackRegistry getInstance() {
        return INSTANCE;
    }
    
    public static Jetpack createJetpack(String name, int tier, int color, int armorPoints, int enchantability, String craftingMaterialString) {
        return new Jetpack(name, tier, color, armorPoints, enchantability, craftingMaterialString);
    }
    
    public void register(Jetpack jetpack) {
        if (this.jetpacks.stream().anyMatch(j -> j.name.equals(jetpack.name))) {
            this.isErrored = true;
            throw new RuntimeException(String.format("Tried to register multiple jetpacks with the same name: %s", jetpack.name));
        }
        
        this.jetpacks.add(jetpack);
        
        if (jetpack.tier > -1 && !this.tiers.contains(jetpack.tier)) {
            this.tiers.add(jetpack.tier);
            this.tiers.sort(Integer::compareTo);
        }
        
        if (jetpack.tier > -1 && jetpack.tier < this.lowestTier) {
            this.lowestTier = jetpack.tier;
        }
    }
    
    public List<Jetpack> getAllJetpacks() {
        return this.jetpacks;
    }
    
    public List<Integer> getAllTiers() {
        return this.tiers;
    }
    
    public Integer getLowestTier() {
        return this.lowestTier;
    }
    
    public JetpackItem getJetpackForName(String name) {
        Jetpack jetpack = this.jetpacks.stream().filter(j -> j.name.equals(name)).findFirst().orElse(null);
        return jetpack == null ? null : jetpack.item;
    }
    
    public Item getCoilForTier(int tier) {
        switch (this.tiers.indexOf(tier)) {
            case 0:
            case 1:
            case 2:
                return ModItems.BASIC_COIL.get();
            case 3:
                return ModItems.ADVANCED_COIL.get();
            case 4:
                return ModItems.ELITE_COIL.get();
            case 5:
                return ModItems.ULTIMATE_COIL.get();
            case 6:
                return ModItems.EXPERT_COIL.get();
        }
        return null;
    }
    
    public boolean isErrored() {
        return this.isErrored;
    }
}