// src/main/java/com/TNTStudios/lifeoffarce/entity/client/ElCuatroBrazosModel.java
package com.TNTStudios.lifeoffarce.entity.client;

import com.TNTStudios.lifeoffarce.Lifeoffarce;
import com.TNTStudios.lifeoffarce.entity.ElCuatroBrazosEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ElCuatroBrazosModel extends GeoModel<ElCuatroBrazosEntity> {
    @Override
    public ResourceLocation getModelResource(ElCuatroBrazosEntity object) {
        return new ResourceLocation(Lifeoffarce.MODID, "geo/el_cuatro_brazos.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ElCuatroBrazosEntity object) {
        return new ResourceLocation(Lifeoffarce.MODID, "textures/entity/el_cuatro_brazos.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ElCuatroBrazosEntity object) {
        return new ResourceLocation(Lifeoffarce.MODID, "animations/el_cuatro_brazos.animation.json");
    }
}