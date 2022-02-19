package safro.hover.pets.api;

import net.minecraft.entity.data.TrackedData;

public interface PetAccess {
    TrackedData<Boolean> get();

    void setPufferfish(boolean bl);
    void setMagmaCube(boolean bl);
    void setCreeper(boolean bl);
}
