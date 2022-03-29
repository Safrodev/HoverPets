package safro.hover.pets.registry;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TagRegistry {
    public static TagKey<Item> SEEDS = registerItem(new Identifier("c", "seeds"));

    private static TagKey<Item> registerItem(Identifier id) {
        return TagKey.of(Registry.ITEM_KEY, id);
    }

    public static void init() {}
}
