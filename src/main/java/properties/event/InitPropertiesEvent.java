package properties.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.Entity;
import properties.data.Property;

import java.util.Map;

public class InitPropertiesEvent extends Event {

    public final Map<String, Property> properties;
    public final Entity entity;

    public InitPropertiesEvent(Map<String, Property> properties, Entity entity) {
        this.properties = properties;
        this.entity = entity;
    }
}
