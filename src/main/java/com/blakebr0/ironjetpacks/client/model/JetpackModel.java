package com.blakebr0.ironjetpacks.client.model;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.item.storage.ItemSlotStorage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import team.reborn.energy.api.EnergyStorage;

/*
 * This is a slightly modified version of the model from Simply Jetpacks
 * https://github.com/Tomson124/SimplyJetpacks-2/blob/1.12/src/main/java/tonius/simplyjetpacks/client/model/ModelJetpack.java
 */
@Environment(EnvType.CLIENT)
public class JetpackModel extends HumanoidModel<LivingEntity> {
    private final JetpackItem jetpack;
    private final ModelPart[] energyBarLeft = new ModelPart[6];
    private final ModelPart[] energyBarRight = new ModelPart[6];
    
    public JetpackModel(JetpackItem jetpack) {
        super(newParts());
        this.jetpack = jetpack;
        
        this.body.visible = true;
        this.rightArm.visible = false;
        this.leftArm.visible = false;
        this.head.visible = false;
        this.hat.visible = false;
        this.rightLeg.visible = false;
        this.leftLeg.visible = false;
        
        for (int i = 0; i < 6; i++) {
            energyBarLeft[i] = this.body.getChild("energyBarLeft" + i);
            energyBarRight[i] = this.body.getChild("energyBarRight" + i);
        }
    }
    
    private static ModelPart newParts() {
        MeshDefinition data = createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition child = data.getRoot().getChild("body");
        child.addOrReplaceChild("middle", CubeListBuilder.create()
                        .mirror()
                        .texOffs(0, 54)
                        .addBox(-2F, 5F, 3.6F, 4, 3, 2),
                PartPose.ZERO);
        child.addOrReplaceChild("leftCanister", CubeListBuilder.create()
                        .mirror()
                        .texOffs(0, 32)
                        .addBox(0.5F, 2F, 2.6F, 4, 7, 4),
                PartPose.ZERO);
        child.addOrReplaceChild("rightCanister", CubeListBuilder.create()
                        .mirror()
                        .texOffs(17, 32)
                        .addBox(-4.5F, 2F, 2.6F, 4, 7, 4),
                PartPose.ZERO);
        child.addOrReplaceChild("leftTip1", CubeListBuilder.create()
                        .mirror()
                        .texOffs(0, 45)
                        .addBox(1F, 0F, 3.1F, 3, 2, 3),
                PartPose.ZERO);
        child.addOrReplaceChild("leftTip2", CubeListBuilder.create()
                        .mirror()
                        .texOffs(0, 50)
                        .addBox(1.5F, -1F, 3.6F, 2, 1, 2),
                PartPose.ZERO);
        child.addOrReplaceChild("rightTip1", CubeListBuilder.create()
                        .mirror()
                        .texOffs(17, 45)
                        .addBox(-4F, 0F, 3.1F, 3, 2, 3),
                PartPose.ZERO);
        child.addOrReplaceChild("rightTip2", CubeListBuilder.create()
                        .mirror()
                        .texOffs(17, 50)
                        .addBox(-3.5F, -1F, 3.6F, 2, 1, 2),
                PartPose.ZERO);
        child.addOrReplaceChild("leftExhaust1", CubeListBuilder.create()
                        .mirror()
                        .texOffs(35, 32)
                        .addBox(1F, 9F, 3.1F, 3, 1, 3),
                PartPose.ZERO);
        child.addOrReplaceChild("leftExhaust2", CubeListBuilder.create()
                        .mirror()
                        .texOffs(35, 37)
                        .addBox(0.5F, 10F, 2.6F, 4, 3, 4),
                PartPose.ZERO);
        child.addOrReplaceChild("rightExhaust1", CubeListBuilder.create()
                        .mirror()
                        .texOffs(48, 32)
                        .addBox(-4F, 9F, 3.1F, 3, 1, 3),
                PartPose.ZERO);
        child.addOrReplaceChild("rightExhaust2", CubeListBuilder.create()
                        .mirror()
                        .texOffs(35, 45)
                        .addBox(-4.5F, 10F, 2.6F, 4, 3, 4),
                PartPose.ZERO);
        
        for (int i = 0; i < 6; i++) {
            child.addOrReplaceChild("energyBarLeft" + i, CubeListBuilder.create()
                            .texOffs(16 + (i * 4), 55)
                            .addBox(2F, 3F, 5.8F, 1, 5, 1),
                    PartPose.ZERO);
            child.addOrReplaceChild("energyBarRight" + i, CubeListBuilder.create()
                            .texOffs(16 + (i * 4), 55)
                            .addBox(-3F, 3F, 5.8F, 1, 5, 1),
                    PartPose.ZERO);
        }
        
        return data.getRoot().bake(64, 64);
    }
    
    @Override
    public void setupAnim(LivingEntity entity, float f1, float f2, float f3, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, f1, f2, f3, netHeadYaw, headPitch);
        
        if (this.jetpack.getJetpack().creative) {
            this.resetEnergyBars();
            this.energyBarLeft[5].visible = true;
            this.energyBarRight[5].visible = true;
        } else {
            ItemSlotStorage storage = new ItemSlotStorage(entity, EquipmentSlot.CHEST);
            EnergyStorage energy = EnergyStorage.ITEM.find(storage.getStack(), ContainerItemContext.ofSingleSlot(storage));
            double stored = (double) energy.getAmount() / energy.getCapacity();
            
            int state = 0;
            if (stored > 0.8) {
                state = 5;
            } else if (stored > 0.6) {
                state = 4;
            } else if (stored > 0.4) {
                state = 3;
            } else if (stored > 0.2) {
                state = 2;
            } else if (stored > 0) {
                state = 1;
            }
            
            this.resetEnergyBars();
            this.energyBarLeft[state].visible = true;
            this.energyBarRight[state].visible = true;
        }
    }
    
    private void setRotation(ModelPart model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }
    
    private void resetEnergyBars() {
        for (int i = 0; i < 6; i++) {
            this.energyBarLeft[i].visible = false;
            this.energyBarRight[i].visible = false;
        }
    }
}
