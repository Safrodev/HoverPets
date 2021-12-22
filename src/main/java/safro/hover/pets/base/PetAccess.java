package safro.hover.pets.base;

import net.minecraft.entity.data.TrackedData;

public interface PetAccess {
    TrackedData<Boolean> get();

    void setPufferfish(boolean bl);
    void setMagmaCube(boolean bl);
}
