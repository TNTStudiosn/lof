// src/main/java/com/TNTStudios/lifeoffarce/entity/client/ElCuatroBrazosRenderer.java
package com.TNTStudios.lifeoffarce.entity.client;

import com.TNTStudios.lifeoffarce.entity.ElCuatroBrazosEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ElCuatroBrazosRenderer extends GeoEntityRenderer<ElCuatroBrazosEntity> {
    public ElCuatroBrazosRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ElCuatroBrazosModel());
        this.shadowRadius = 0.6f; // Sombra un poco más pequeña que la del gigante
    }
}