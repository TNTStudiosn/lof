// src/main/java/com/TNTStudios/lifeoffarce/entity/client/ElGiganteModel.java
package com.TNTStudios.lifeoffarce.entity.client;

import com.TNTStudios.lifeoffarce.Lifeoffarce;
import com.TNTStudios.lifeoffarce.entity.ElGiganteEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ElGiganteModel extends GeoModel<ElGiganteEntity> {
    @Override
    public ResourceLocation getModelResource(ElGiganteEntity object) {
        return new ResourceLocation(Lifeoffarce.MODID, "geo/el_gigante.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ElGiganteEntity object) {
        return new ResourceLocation(Lifeoffarce.MODID, "textures/entity/el_gigante.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ElGiganteEntity object) {
        return new ResourceLocation(Lifeoffarce.MODID, "animations/el_gigante.animation.json");
    }
}