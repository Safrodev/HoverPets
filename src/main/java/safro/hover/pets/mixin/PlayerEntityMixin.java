package safro.hover.pets.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import safro.hover.pets.pet.CreeperPet;
import safro.hover.pets.pet.MagmaCubePet;
import safro.hover.pets.pet.PufferfishPet;
import safro.hover.pets.util.PetUtil;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(EntityType.PLAYER, world);
    }

    @Inject(method = "damage", at = @At("TAIL"))
    private void pufferfishPet(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity p = (PlayerEntity) (Object) this;
        if (source.getAttacker() instanceof LivingEntity attacker && p.getHealth() <= 6.0F) {
            if (PetUtil.hasPet(p) && PetUtil.getPet(p) instanceof PufferfishPet) {
                attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 120, 0), p);
                attacker.playSound(SoundEvents.ENTITY_PUFFER_FISH_STING, 1.0F, 1.0F);
            }
        }
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void creeperPet(RemovalReason reason, CallbackInfo ci) {
        PlayerEntity p = (PlayerEntity) (Object) this;
        if (p.isDead() && !world.isClient) {
            if (PetUtil.hasPet(p) && PetUtil.getPet(p) instanceof CreeperPet) {
                Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
                this.world.createExplosion(p, p.getX(), p.getY(), p.getZ(), 3, destructionType);
            }
        }
    }

    @Override
    public boolean isFireImmune() {
        PlayerEntity p = (PlayerEntity) (Object) this;
        if (PetUtil.hasPet(p) && PetUtil.getPet(p) instanceof MagmaCubePet) {
            if (p.getFluidHeight(FluidTags.LAVA) > 0.0D) {
                return true;
            }
        }
        return super.isFireImmune();
    }
}
