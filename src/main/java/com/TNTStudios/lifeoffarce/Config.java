// src/main/java/com/TNTStudios/lifeoffarce/Config.java
package com.TNTStudios.lifeoffarce;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Lifeoffarce.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue EL_GIGANTE_SPAWN_WEIGHT;
    private static final ForgeConfigSpec.IntValue EL_GIGANTE_MIN_GROUP_SIZE;
    private static final ForgeConfigSpec.IntValue EL_GIGANTE_MAX_GROUP_SIZE;

    static {
        BUILDER.push("Spawning"); // Agrupo todo bajo la categoría 'Spawning' en el archivo de config.
        EL_GIGANTE_SPAWN_WEIGHT = BUILDER
                .comment("Peso de spawn para 'El Gigante'. Un valor más alto significa que aparece más a menudo. El de los zombis es 100 como referencia.")
                .defineInRange("elGiganteSpawnWeight", 60, 1, 1000); // Por defecto es 20, relativamente raro.

        EL_GIGANTE_MIN_GROUP_SIZE = BUILDER
                .comment("Tamaño mínimo del grupo en que 'El Gigante' aparece.")
                .defineInRange("elGiganteMinGroupSize", 1, 1, 5); // Siempre aparecerá al menos 1.

        EL_GIGANTE_MAX_GROUP_SIZE = BUILDER
                .comment("Tamaño máximo del grupo en que 'El Gigante' aparece.")
                .defineInRange("elGiganteMaxGroupSize", 1, 1, 5); // Y como máximo 1, porque es una entidad fuerte.
        BUILDER.pop();
    }

    static final ForgeConfigSpec SPEC = BUILDER.build();

    // Defino variables estáticas para poder acceder a estos valores desde cualquier parte del mod,
    // por ejemplo, desde mi lógica de eventos de spawn.
    public static int elGiganteSpawnWeight;
    public static int elGiganteMinGroupSize;
    public static int elGiganteMaxGroupSize;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Cuando la configuración se carga (o recarga), actualizo mis variables.
        // Esto asegura que los valores estén siempre sincronizados con el archivo .toml.
        elGiganteSpawnWeight = EL_GIGANTE_SPAWN_WEIGHT.get();
        elGiganteMinGroupSize = EL_GIGANTE_MIN_GROUP_SIZE.get();
        elGiganteMaxGroupSize = EL_GIGANTE_MAX_GROUP_SIZE.get();
    }
}