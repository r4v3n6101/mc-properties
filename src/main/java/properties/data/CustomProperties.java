package properties.data;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.HashMap;
import java.util.Map;

public class CustomProperties implements IExtendedEntityProperties {

    private static final String PROP_ID = "CustomProperties";
    private Map<String, Property> properties = new HashMap<>();

    private CustomProperties() {
    }

    public static CustomProperties get(Entity entity) {
        if (entity.getExtendedProperties(PROP_ID) == null) {
            entity.registerExtendedProperties(PROP_ID, new CustomProperties());
        }
        return (CustomProperties) entity.getExtendedProperties(PROP_ID);
    }

    public Map<String, Property> getProperties() {
        return properties;
    }

    public NBTTagCompound makeSyncTag() {
        NBTTagList list = new NBTTagList();
        properties.forEach((k, v) -> {
            if (v.isSyncable() && v.isDirty()) {
                v.setDirty(false);

                NBTTagCompound tag = new NBTTagCompound();
                tag.setString("id", k);
                tag.setTag("value", v.toNbt());
                list.appendTag(tag);
            }
        });

        NBTTagCompound tag = new NBTTagCompound();
        if (list.tagCount() > 0) tag.setTag("list", list);
        return tag;
    }

    public void readSyncTag(NBTTagCompound tag) {
        NBTTagList list = tag.getTagList("list", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound p = list.getCompoundTagAt(i);
            String k = p.getString("id");
            NBTBase v = p.getTag("value");
            properties.get(k).loadFromNbt(v);
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound rootTag = new NBTTagCompound();
        properties.forEach((key, value) -> rootTag.setTag(key, value.toNbt()));
        compound.setTag(PROP_ID, rootTag);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound rootTag = compound.getCompoundTag(PROP_ID);
        properties.forEach((key, value) -> value.loadFromNbt(rootTag.getTag(key)));
    }

    @Override
    public void init(Entity entity, World world) {
    }

    public void copy(CustomProperties dest) {
        dest.properties = properties;
    }
}
