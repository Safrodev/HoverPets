package safro.hover.pets.util;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;

import java.util.function.Predicate;

public class CompatUtil {
    public static Predicate<ServerCommandSource> canRunCommand() {
        return Permissions.require("hoverpets.removepet", 2);
    }
}
