package net.gnomecraft.obtainableend.tags;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.tag.TagManagerLoader;

import java.util.List;
import java.util.Map;

// TagsLoadedEvent: An event that fires based on loaded tag keys
public class TagsLoadedEvent {
    private static final Map<RegistryKey<?>, Event<TagLoadedHandler>> events = Maps.newHashMap();

    public static void runEvents(List<TagManagerLoader.RegistryTags<?>> tagsList) {
        for (TagManagerLoader.RegistryTags<?> tag:tagsList) {
            if (events.containsKey(tag.key()))
                events.get(tag.key()).invoker().run(tag);
        }
    }

    public static void register(RegistryKey<?> key, TagLoadedHandler callback) {
        if (!events.containsKey(key)) {
            events.put(key, EventFactory.createArrayBacked(TagLoadedHandler.class, (callbacks) -> (tags) -> {
                for (TagLoadedHandler handler : callbacks) {
                    handler.run(tags);
                }
            }));
        }

        events.get(key).register(callback);
    }

    public interface TagLoadedHandler {
        void run(TagManagerLoader.RegistryTags<?> tags);
    }
}
