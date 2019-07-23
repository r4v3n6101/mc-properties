package example.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import properties.data.CustomProperties;
import properties.data.impl.NBTBaseProperty;

public class TestInventory extends InventoryBasic {

    private EntityPlayer player;

    public TestInventory(EntityPlayer player) {
        super("TestInventory", true, 54);
        this.player = player;
    }

    @Override
    public void openInventory() {
        NBTBaseProperty prop = (NBTBaseProperty) CustomProperties.get(player).getProperties().get("customInventory");
        NBTTagList list = (NBTTagList) prop.getValue();

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            ItemStack is = ItemStack.loadItemStackFromNBT(tag);
            if (is != null) setInventorySlotContents(i, is);
        }
    }

    @Override
    public void closeInventory() {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++) {
            NBTTagCompound tag = new NBTTagCompound();

            ItemStack is = getStackInSlot(i);
            if (is != null) is.writeToNBT(tag);

            list.appendTag(tag);
        }

        NBTBaseProperty prop = (NBTBaseProperty) CustomProperties.get(player).getProperties().get("customInventory");
        prop.setValue(list);
    }
}
