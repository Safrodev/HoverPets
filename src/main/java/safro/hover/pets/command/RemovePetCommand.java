package safro.hover.pets.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import safro.hover.pets.registry.ComponentsRegistry;
import safro.hover.pets.util.CompatUtil;
import safro.hover.pets.util.PetUtil;

import java.util.Collection;

public class RemovePetCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("removePet")
                .requires(CompatUtil.canRunCommand())
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                        .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets")))));
    }

    private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> players) {
        for (ServerPlayerEntity player : players) {
            if (PetUtil.hasPet(player)) {
                ComponentsRegistry.PET_COMPONENT.get(player).getPet().remove(Entity.RemovalReason.DISCARDED);
                PetUtil.setPet(player, 0);
            }
        }

        if (players.size() == 1) {
            source.sendFeedback(new TranslatableText("command.hoverpets.removepet", players.iterator().next().getDisplayName()), true);
        } else {
            source.sendFeedback(new TranslatableText("command.hoverpets.removepets", players.size()), true);
        }
        return players.size();
    }
}
