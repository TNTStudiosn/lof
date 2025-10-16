// src/main/java/com/TNTStudios/lifeoffarce/event/ModEvents.java
package com.TNTStudios.lifeoffarce.event;

import com.TNTStudios.lifeoffarce.Lifeoffarce;
import com.TNTStudios.lifeoffarce.init.ModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Lifeoffarce.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        // Esto está perfecto y se debe quedar.
        // Define las condiciones FÍSICAS para el spawn (dónde puede aparecer).
        // El biome modifier define los BIOMAS en los que puede aparecer.
        // Ambos son necesarios.
        event.register(ModEntities.EL_GIGANTE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.OR);
    }

    // La clase interna 'ForgeEvents' y el método 'onBiomeLoading' se han eliminado.
    // Su lógica ahora vive en el archivo add_el_gigante_spawn.json
}