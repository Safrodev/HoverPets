package safro.hover.pets.base;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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
    private boolean spawned;
    public final EntityType<? extends BasePetEntity> basepet;
    public BasePetEntity pet = null;

    public PetItem(EntityType<? extends BasePetEntity> type, Settings settings) {
        super(settings);
        this.spawned = false;
        this.basepet = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            if (!PetUtil.hasPet(player)) {
                BasePetEntity basePet = (BasePetEntity) basepet.create(world);
                basePet.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), 0.0F, 0.0F);
                basePet.setOwner(player);
                world.spawnEntity(basePet);
                stack.decrement(1);
                player.getDataTracker().set(((PetAccess) player).get(), true);
                return TypedActionResult.consume(stack);
            } else {
                player.sendMessage(new TranslatableText("tooltip.hoverpets.one_pet").formatted(Formatting.RED), true);
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
        if (this.perkLore != null) {
            if (!Screen.hasShiftDown()) {
                tooltip.add(new TranslatableText("tooltip.hoverpets.shift").formatted(Formatting.GRAY));
            } else {
                tooltip.add(new LiteralText("Perk:").formatted(Formatting.WHITE));
                tooltip.add(new LiteralText("" + Formatting.AQUA + I18n.translate(this.perkLore)));
                if (this.abilityLore != null) {
                    tooltip.add(new LiteralText("Ability:").formatted(Formatting.WHITE));
                    tooltip.add(new LiteralText("" + Formatting.YELLOW + I18n.translate(this.abilityLore)));
                }
            }
        }
    }
}
