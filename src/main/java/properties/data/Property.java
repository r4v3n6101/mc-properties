package properties.data;

import net.minecraft.nbt.NBTBase;

public abstract class Property<T> {

    private final T initValue;
    private final boolean syncable;

    private T value;
    private boolean dirty;

    public Property(T initValue, boolean syncable) {
        this.initValue = initValue;
        this.syncable = syncable;

        reset();
    }

    public T getInitValue() {
        return initValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (this.value != value) setDirty(true);
        this.value = value;
    }

    public void reset() {
        setValue(getInitValue());
    }

    public abstract NBTBase toNbt();

    public abstract void loadFromNbt(NBTBase tag);

    boolean isDirty() {
        return dirty;
    }

    void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    boolean isSyncable() {
        return syncable;
    }
}
