package safro.hover.pets;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import safro.hover.pets.registry.ClientRegistry;
import safro.hover.pets.registry.NetworkRegistry;

public class HoverPetsClient implements ClientModInitializer {
    public static KeyBinding PET_KEY = new KeyBinding("key.hoverpets.pet_ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, "category.hoverpets.hoverpets");
    private boolean pressed = false;

    @Override
    public void onInitializeClient() {
        ClientRegistry.init();

        // Keybinds
        KeyBindingHelper.registerKeyBinding(PET_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (PET_KEY.wasPressed()) {
                if (!pressed) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    ClientPlayNetworking.send(NetworkRegistry.PET_ABILITY, buf);
                }
                pressed = true;
            } else
                pressed = false;
        });
    }
}
