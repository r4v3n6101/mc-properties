package properties.data.impl;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import properties.data.Property;

public class DoubleProperty extends Property<Double> {

    public DoubleProperty(double initValue, boolean syncable) {
        super(initValue, syncable);
    }

    @Override
    public NBTBase toNbt() {
        return new NBTTagDouble(getValue());
    }

    @Override
    public void loadFromNbt(NBTBase tag) {
        setValue(((NBTTagDouble) tag).func_150286_g());
    }
}
