package properties.data.impl;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import properties.data.Property;

public class ItemStackProperty extends Property<ItemStack> {

    public ItemStackProperty(ItemStack initValue, boolean syncable) {
        super(initValue, syncable);
    }

    @Override
    public NBTBase toNbt() {
        NBTTagCompound tag = new NBTTagCompound();
        getValue().writeToNBT(tag);
        return tag;
    }

    @Override
    public void loadFromNbt(NBTBase tag) {
        setValue(ItemStack.loadItemStackFromNBT((NBTTagCompound) tag));
    }
}
