package safro.hover.pets.api;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public class PetComponent implements AutoSyncedComponent {
    private final PlayerEntity owner;
    private int petID;

    public PetComponent(PlayerEntity player) {
        this.owner = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        if (nbt.contains("pet")) {
            this.petID = nbt.getInt("pet");
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt("pet", this.petID);
    }

    public int getPetId() {
        return this.petID;
    }

    public void setPetId(int id) {
        this.petID = id;
    }

    @Nullable
    public BasePetEntity getPet() {
        Entity e = owner.world.getEntityById(getPetId());
        if (e instanceof BasePetEntity pet && e.isAlive()) {
            return pet;
        }
        return null;
    }

    public boolean hasPet() {
        return getPet() != null;
    }
}
