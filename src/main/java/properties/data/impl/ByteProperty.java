package properties.data.impl;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import properties.data.Property;

public class ByteProperty extends Property<Byte> {

    public ByteProperty(byte initValue, boolean syncable) {
        super(initValue, syncable);
    }

    @Override
    public NBTBase toNbt() {
        return new NBTTagByte(getValue());
    }

    @Override
    public void loadFromNbt(NBTBase tag) {
        setValue(((NBTTagByte) tag).func_150290_f());
    }
}
