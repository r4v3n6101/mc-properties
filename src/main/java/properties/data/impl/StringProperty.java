package properties.data.impl;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import properties.data.Property;

public class StringProperty extends Property<String> {

    public StringProperty(String initValue, boolean syncable) {
        super(initValue, syncable);
    }

    @Override
    public NBTBase toNbt() {
        return new NBTTagString(getValue());
    }

    @Override
    public void loadFromNbt(NBTBase tag) {
        setValue(((NBTTagString) tag).func_150285_a_());
    }
}
