package safro.hover.pets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import safro.hover.pets.command.RemovePetCommand;
import safro.hover.pets.registry.EntityRegistry;
import safro.hover.pets.registry.ItemRegistry;

public class HoverPets implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("hoverpets");
	public static final String MODID = "hoverpets";
	public static ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(ItemRegistry.BLAZE_PET));

	@Override
	public void onInitialize() {
		EntityRegistry.init();
		ItemRegistry.init();

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			RemovePetCommand.register(dispatcher);
		});
	}

}
