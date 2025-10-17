// src/main/java/com/TNTStudios/lifeoffarce/Config.java
package com.TNTStudios.lifeoffarce;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Lifeoffarce.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // --- TOGGLES DE SPAWN ---
    // Estas son las únicas opciones que necesito, ya que los JSON manejan el resto.
    private static final ForgeConfigSpec.BooleanValue EL_GIGANTE_SPAWN_ENABLED;
    private static final ForgeConfigSpec.BooleanValue EL_CUATRO_BRAZOS_SPAWN_ENABLED;
    private static final ForgeConfigSpec.BooleanValue MAYA_MASKA_SPAWN_ENABLED;

    static {
        // --- CATEGORÍA PARA LOS TOGGLES DE SPAWN ---
        BUILDER.push("SpawnToggles");
        BUILDER.comment("Controla si las entidades pueden spawnear naturalmente. (Requiere /lifeoffarce spawn reload para aplicar cambios manuales en el archivo)");

        EL_GIGANTE_SPAWN_ENABLED = BUILDER
                .define("spawnElGigante", true);

        EL_CUATRO_BRAZOS_SPAWN_ENABLED = BUILDER
                .define("spawnElCuatroBrazos", true);

        MAYA_MASKA_SPAWN_ENABLED = BUILDER
                .define("spawnMayaMaska", true);

        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    // --- VARIABLES ESTÁTICAS PARA LOS TOGGLES ---
    // Defino variables estáticas para poder acceder a estos valores desde mi evento (ModEvents.java).
    public static boolean SPAWN_EL_GIGANTE;
    public static boolean SPAWN_EL_CUATRO_BRAZOS;
    public static boolean SPAWN_MAYA_MASKA;


    // Este método se llama solo, no necesito llamarlo manually desde el comando.
    // El comando solo necesita re-leer los valores de la config.
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Cuando la configuración se carga (o recarga), actualizo mis variables.
        // Esto asegura que los valores estén siempre sincronizados con el archivo .toml.

        // --- ACTUALIZO MIS VARIABLES ---
        SPAWN_EL_GIGANTE = EL_GIGANTE_SPAWN_ENABLED.get();
        SPAWN_EL_CUATRO_BRAZOS = EL_CUATRO_BRAZOS_SPAWN_ENABLED.get();
        SPAWN_MAYA_MASKA = MAYA_MASKA_SPAWN_ENABLED.get();
    }

    /**
     * Método helper para que mi comando pueda forzar la recarga de las variables estáticas
     * después de cambiar un valor.
     */
    public static void refreshConfig() {
        SPAWN_EL_GIGANTE = EL_GIGANTE_SPAWN_ENABLED.get();
        SPAWN_EL_CUATRO_BRAZOS = EL_CUATRO_BRAZOS_SPAWN_ENABLED.get();
        SPAWN_MAYA_MASKA = MAYA_MASKA_SPAWN_ENABLED.get();
    }

    // --- AÑADO MÉTODO PARA OBTENER LOS SPEC VALUES DESDE EL COMANDO ---
    // Esto es necesario para que el comando /... set ... funcione
    public static ForgeConfigSpec.BooleanValue getSpec(String mobName) {
        return switch (mobName) {
            case "el_gigante" -> EL_GIGANTE_SPAWN_ENABLED;
            case "el_cuatro_brazos" -> EL_CUATRO_BRAZOS_SPAWN_ENABLED;
            case "maya_maska" -> MAYA_MASKA_SPAWN_ENABLED;
            default -> null;
        };
    }
}