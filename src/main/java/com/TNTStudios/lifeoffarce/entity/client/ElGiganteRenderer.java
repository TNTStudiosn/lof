// src/main/java/com/TNTStudios/lifeoffarce/entity/client/ElGiganteRenderer.java
package com.TNTStudios.lifeoffarce.entity.client;

import com.TNTStudios.lifeoffarce.entity.ElGiganteEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ElGiganteRenderer extends GeoEntityRenderer<ElGiganteEntity> {
    public ElGiganteRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ElGiganteModel());
        this.shadowRadius = 0.8f; // Ajusta el tamaño de la sombra
    }
}