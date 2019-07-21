package properties.data.impl;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;
import properties.data.Property;

public class FloatProperty extends Property<Float> {

    public FloatProperty(float initValue, boolean syncable) {
        super(initValue, syncable);
    }

    @Override
    public NBTBase toNbt() {
        return new NBTTagFloat(getValue());
    }

    @Override
    public void loadFromNbt(NBTBase tag) {
        setValue(((NBTTagFloat) tag).func_150288_h());
    }
}
