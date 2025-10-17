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
            () -> new ForgeSpawnEggItem(() -> ModEntities.EL_GIGANTE.get(), 0x343434, 0x787878, new Item.Properties()));

    public static final RegistryObject<Item> EL_CUATRO_BRAZOS_SPAWN_EGG = ITEMS.register("el_cuatro_brazos_spawn_egg",
            () -> new ForgeSpawnEggItem(() -> ModEntities.EL_CUATRO_BRAZOS.get(), 0x3A2E2E, 0x4F7942, new Item.Properties()));

    public static final RegistryObject<Item> MAYA_MASKA_SPAWN_EGG = ITEMS.register("maya_maska_spawn_egg",
            () -> new ForgeSpawnEggItem(() -> ModEntities.MAYA_MASKA.get(), 0x967969, 0x4f4f4f, new Item.Properties()));

    public static final RegistryObject<Item> MEDALLA_COCINA = ITEMS.register("medalla_cocina",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEDALLA_CUIDADODELANATURALEZA = ITEMS.register("medalla_cuidadodelanaturaleza",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEDALLA_MECANICAYCARPINTERIA = ITEMS.register("medalla_mecanicaycarpinteria",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEDALLA_DEFENSAPERSONALYMEDITACION = ITEMS.register("medalla_defensapersonalymeditacion",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEDALLA_FILOSOFIA = ITEMS.register("medalla_filosofia",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEDALLA_MEDICINA = ITEMS.register("medalla_medicina",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TICKET = ITEMS.register("ticket",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}