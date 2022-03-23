package safro.hover.pets.pet;

import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.TrackOwnerAttackerGoal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.pet.goal.FollowLavaGoal;
import safro.hover.pets.registry.ItemRegistry;

public class MagmaCubePet extends BasePetEntity {

    public MagmaCubePet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FollowLavaGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
    }

    protected EntityNavigation createNavigation(World world) {
        return new MagmaCubePet.Navigation(this, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.MAGMA_CUBE_PET);
    }

    @Override
    public void tickPerk(World world, PlayerEntity player) {
    }

    @Override
    public boolean canWalkOnFluid(FluidState fluid) {
        return fluid.isIn(FluidTags.LAVA);
    }

    public boolean isOnFire() {
        return false;
    }

    public void tick() {
        super.tick();
        this.updateFloating();
    }

    private void updateFloating() {
        if (this.isInLava()) {
            ShapeContext shapeContext = ShapeContext.of(this);
            if (shapeContext.isAbove(FluidBlock.COLLISION_SHAPE, this.getBlockPos(), true) && !this.world.getFluidState(this.getBlockPos().up()).isIn(FluidTags.LAVA)) {
                this.onGround = true;
            } else {
                this.setVelocity(this.getVelocity().multiply(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }
    }

    private static class Navigation extends MobNavigation {
        Navigation(MagmaCubePet entity, World world) {
            super(entity, world);
        }

        protected PathNodeNavigator createPathNodeNavigator(int range) {
            this.nodeMaker = new LandPathNodeMaker();
            return new PathNodeNavigator(this.nodeMaker, range);
        }

        protected boolean canWalkOnPath(PathNodeType pathType) {
            return pathType == PathNodeType.LAVA || pathType == PathNodeType.DAMAGE_FIRE || pathType == PathNodeType.DANGER_FIRE || super.canWalkOnPath(pathType);
        }

        public boolean isValidPosition(BlockPos pos) {
            return this.world.getBlockState(pos).isOf(Blocks.LAVA) || super.isValidPosition(pos);
        }
    }
}
