// src/main/java/com/TNTStudios/lifeoffarce/entity/client/MayaMaskaRenderer.java
package com.TNTStudios.lifeoffarce.entity.client;

import com.TNTStudios.lifeoffarce.entity.MayaMaskaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MayaMaskaRenderer extends GeoEntityRenderer<MayaMaskaEntity> {
    public MayaMaskaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MayaMaskaModel());
        this.shadowRadius = 0.9f; // Una sombra grande, ya que es un gusano gigante.
    }
}