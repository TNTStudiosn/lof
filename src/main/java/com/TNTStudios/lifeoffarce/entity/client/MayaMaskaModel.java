// src/main/java/com/TNTStudios/lifeoffarce/entity/client/MayaMaskaModel.java
package com.TNTStudios.lifeoffarce.entity.client;

import com.TNTStudios.lifeoffarce.Lifeoffarce;
import com.TNTStudios.lifeoffarce.entity.MayaMaskaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MayaMaskaModel extends GeoModel<MayaMaskaEntity> {
    @Override
    public ResourceLocation getModelResource(MayaMaskaEntity object) {
        return new ResourceLocation(Lifeoffarce.MODID, "geo/maya_maska.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MayaMaskaEntity object) {
        return new ResourceLocation(Lifeoffarce.MODID, "textures/entity/maya_maska.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MayaMaskaEntity object) {
        return new ResourceLocation(Lifeoffarce.MODID, "animations/maya_maska.animation.json");
    }
}