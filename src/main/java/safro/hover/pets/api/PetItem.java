package safro.hover.pets.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import safro.hover.pets.util.PetUtil;

import java.util.List;

public class PetItem extends Item {
    String abilityLore = null;
    String perkLore = null;
    public final EntityType<? extends BasePetEntity> basepet;
    public BasePetEntity pet = null;

    public PetItem(EntityType<? extends BasePetEntity> type, Settings settings) {
        super(settings);
        this.basepet = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            if (!PetUtil.hasPet(player)) {
                BasePetEntity basePet = basepet.create(world);
                if (basePet.canBeSummoned(player)) {
                    basePet.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), 0.0F, 0.0F);
                    basePet.setOwner(player);
                    world.spawnEntity(basePet);
                    stack.decrement(1);
                    PetUtil.setPet(player, basePet.getId());
                    return TypedActionResult.consume(stack);
                } else {
                    basePet = null;
                    player.sendMessage(Text.translatable("tooltip.hoverpets.no_permission").formatted(Formatting.RED), true);
                    return TypedActionResult.fail(stack);
                }
            } else {
                player.sendMessage(Text.translatable("tooltip.hoverpets.one_pet").formatted(Formatting.RED), true);
                return TypedActionResult.fail(stack);
            }
        }
        return TypedActionResult.pass(stack);
    }

    public PetItem setAbilityTooltip(String tooltip) {
        this.abilityLore = tooltip;
        return this;
    }

    public PetItem setPerkTooltip(String tooltip) {
        this.perkLore = tooltip;
        return this;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.perkLore != null || this.abilityLore != null) {
            if (!Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("tooltip.hoverpets.shift").formatted(Formatting.GRAY));
            } else {
                if (this.perkLore != null) {
                    tooltip.add(Text.literal("Perk:").formatted(Formatting.WHITE));
                    tooltip.add(Text.literal("" + Formatting.AQUA + I18n.translate(this.perkLore)));
                }
                if (this.abilityLore != null) {
                    tooltip.add(Text.literal("Ability:").formatted(Formatting.WHITE));
                    tooltip.add(Text.literal("" + Formatting.YELLOW + I18n.translate(this.abilityLore)));
                }
            }
        }
    }
}
