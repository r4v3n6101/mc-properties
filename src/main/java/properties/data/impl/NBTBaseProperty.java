package properties.data.impl;


import net.minecraft.nbt.NBTBase;
import properties.data.Property;

public class NBTBaseProperty extends Property<NBTBase> {

    public NBTBaseProperty(NBTBase initValue, boolean syncable) {
        super(initValue, syncable);
    }

    @Override
    public NBTBase toNbt() {
        return getValue().copy();
    }

    @Override
    public void loadFromNbt(NBTBase tag) {
        setValue(tag.copy());
    }
}