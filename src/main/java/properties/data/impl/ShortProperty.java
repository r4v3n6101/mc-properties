package properties.data.impl;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagShort;
import properties.data.Property;

public class ShortProperty extends Property<Short> {

    public ShortProperty(short initValue, boolean syncable) {
        super(initValue, syncable);
    }

    @Override
    public NBTBase toNbt() {
        return new NBTTagShort(getValue());
    }

    @Override
    public void loadFromNbt(NBTBase tag) {
        setValue(((NBTTagShort) tag).func_150289_e());
    }
}
