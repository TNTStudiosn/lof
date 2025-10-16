// src/main/java/com/TNTStudios/lifeoffarce/init/ModEntities.java
package com.TNTStudios.lifeoffarce.init;

import com.TNTStudios.lifeoffarce.Lifeoffarce;
import com.TNTStudios.lifeoffarce.entity.ElGiganteEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Lifeoffarce.MODID);

    public static final RegistryObject<EntityType<ElGiganteEntity>> EL_GIGANTE =
            ENTITY_TYPES.register("el_gigante",
                    () -> EntityType.Builder.of(ElGiganteEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 3.0f) // Ajusta el tamaño de la hitbox (ancho, alto)
                            .build("el_gigante"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}