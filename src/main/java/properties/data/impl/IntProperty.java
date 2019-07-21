package properties.data.impl;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import properties.data.Property;

public class IntProperty extends Property<Integer> {

    public IntProperty(int initValue, boolean syncable) {
        super(initValue, syncable);
    }

    @Override
    public NBTBase toNbt() {
        return new NBTTagInt(getValue());
    }

    @Override
    public void loadFromNbt(NBTBase tag) {
        setValue(((NBTTagInt) tag).func_150287_d());
    }
}
