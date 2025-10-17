// src/main/java/com/TNTStudios/lifeoffarce/ModCommands.java
package com.TNTStudios.lifeoffarce;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommands {

    private static final String[] MOB_NAMES = {"el_gigante", "el_cuatro_brazos", "maya_maska"};

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Comando principal: /lifeoffarce
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal(Lifeoffarce.MODID)
                .requires(source -> source.hasPermission(2)) // Requiere OP Nivel 2 (admin)

                // Subcomando: /lifeoffarce spawn ...
                .then(Commands.literal("spawn")

                        // Subcomando: /lifeoffarce spawn set <mob> <bool>
                        .then(Commands.literal("set")
                                .then(Commands.argument("mob", StringArgumentType.string())
                                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(MOB_NAMES, builder))
                                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                                .executes(ModCommands::setSpawnConfig))))

                        // Subcomando: /lifeoffarce spawn reload
                        .then(Commands.literal("reload")
                                .executes(ModCommands::reloadSpawnConfig))

                        // Subcomando: /lifeoffarce spawn status
                        .then(Commands.literal("status")
                                .executes(ModCommands::showSpawnConfig))
                );

        dispatcher.register(command);
    }

    /**
     * Ejecuta el comando /... set <mob> <bool>
     */
    private static int setSpawnConfig(CommandContext<CommandSourceStack> context) {
        String mobName = StringArgumentType.getString(context, "mob");
        boolean enabled = BoolArgumentType.getBool(context, "enabled");
        CommandSourceStack source = context.getSource();

        ForgeConfigSpec.BooleanValue configValue = Config.getSpec(mobName);

        if (configValue == null) {
            source.sendFailure(Component.literal("Error: El mob '" + mobName + "' no existe en la config."));
            return 0;
        }

        // 1. Modifico el valor en la config
        configValue.set(enabled);
        // 2. Guardo el archivo .toml en el disco
        Config.SPEC.save();
        // 3. Fuerzo la recarga de las variables estáticas en Config.java
        Config.refreshConfig();

        source.sendSuccess(() -> Component.literal("Spawneo de '" + mobName + "' ahora es: " + (enabled ? "§aACTIVADO" : "§cDESACTIVADO")), true);
        return 1;
    }

    /**
     * Ejecuta el comando /... reload
     */
    private static int reloadSpawnConfig(CommandContext<CommandSourceStack> context) {
        // Esto fuerza a leer los valores del archivo .toml y cargarlos en las variables estáticas
        Config.refreshConfig();

        context.getSource().sendSuccess(() -> Component.literal("§aConfig de spawneo recargada desde el archivo."), true);
        showSpawnConfig(context); // Muestro el estado actual
        return 1;
    }

    /**
     * Ejecuta el comando /... status
     */
    private static int showSpawnConfig(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();

        // Leo las variables estáticas (que ya están actualizadas)
        String giganteStatus = Config.SPAWN_EL_GIGANTE ? "§aACTIVADO" : "§cDESACTIVADO";
        String cuatroBrazosStatus = Config.SPAWN_EL_CUATRO_BRAZOS ? "§aACTIVADO" : "§cDESACTIVADO";
        String maskaStatus = Config.SPAWN_MAYA_MASKA ? "§aACTIVADO" : "§cDESACTIVADO";

        source.sendSuccess(() -> Component.literal("--- Estado de Spawneo Natural ---"), false);
        source.sendSuccess(() -> Component.literal("El Gigante: " + giganteStatus), false);
        source.sendSuccess(() -> Component.literal("El Cuatro Brazos: " + cuatroBrazosStatus), false);
        source.sendSuccess(() -> Component.literal("Maya Maska: " + maskaStatus), false);

        return 1;
    }
}