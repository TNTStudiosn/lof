// src/main/java/com/TNTStudios/lifeoffarce/ModEvents.java
package com.TNTStudios.lifeoffarce;

import com.TNTStudios.lifeoffarce.init.ModEntities;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Lifeoffarce.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    /**
     * Bloquea spawns "naturales" ANTES de que se instancie la entidad.
     * Aquí no hay instancia, solo EntityType; por eso comparamos contra ModEntities.
     */
    @SubscribeEvent
    public static void onSpawnPlacementCheck(MobSpawnEvent.SpawnPlacementCheck event) {
        MobSpawnType type = event.getSpawnType();

        // Consideramos natural: NATURAL, CHUNK_GENERATION y STRUCTURE.
        boolean isNaturalish = type == MobSpawnType.NATURAL
                || type == MobSpawnType.CHUNK_GENERATION
                || type == MobSpawnType.STRUCTURE;

        if (!isNaturalish) return;

        var et = event.getEntityType();

        boolean block =
                (et == ModEntities.EL_GIGANTE.get() && !Config.SPAWN_EL_GIGANTE)
                        || (et == ModEntities.EL_CUATRO_BRAZOS.get() && !Config.SPAWN_EL_CUATRO_BRAZOS)
                        || (et == ModEntities.MAYA_MASKA.get() && !Config.SPAWN_MAYA_MASKA);

        if (block) {
            // SpawnPlacementCheck respeta DENY; y si es cancelable, cancelamos por duplicado.
            event.setResult(Event.Result.DENY);
            if (event.isCancelable()) {
                event.setCanceled(true);
            }
        }
    }

    /**
     * Registro de comandos del mod.
     */
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }
}
