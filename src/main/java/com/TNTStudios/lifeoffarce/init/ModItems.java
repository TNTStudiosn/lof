// src/main/java/com/TNTStudios/lifeoffarce/init/ModItems.java
package com.TNTStudios.lifeoffarce.init;

import com.TNTStudios.lifeoffarce.Lifeoffarce;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Lifeoffarce.MODID);

    public static final RegistryObject<Item> EL_GIGANTE_SPAWN_EGG = ITEMS.register("el_gigante_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.EL_GIGANTE, 0x343434, 0x787878, new Item.Properties()));

    // --- NUEVO ---
    public static final RegistryObject<Item> EL_CUATRO_BRAZOS_SPAWN_EGG = ITEMS.register("el_cuatro_brazos_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.EL_CUATRO_BRAZOS, 0x8B4513, 0x808080, new Item.Properties())); // Colores marrón y gris
    // --- FIN NUEVO ---

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}