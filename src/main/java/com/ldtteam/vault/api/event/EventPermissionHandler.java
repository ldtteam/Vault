package com.ldtteam.vault.api.event;

import com.google.common.collect.Maps;
import com.ldtteam.vault.api.permission.VaultPermissionHandler;
import com.mojang.authlib.GameProfile;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.server.permission.context.IContext;

import java.util.Map;
import java.util.function.Function;

public class EventPermissionHandler implements com.ldtteam.vault.api.event.IEventPermissionHandler
{
    private static EventPermissionHandler ourInstance = new EventPermissionHandler();

    private Map<Class<?>, Function<Event, GameProfile>> gameProfileExtractors = Maps.newHashMap();
    private Map<Class<?>, Function<Event, IContext>> permissionContextExtractors = Maps.newHashMap();

    public static EventPermissionHandler getInstance()
    {
        return ourInstance;
    }

    private EventPermissionHandler()
    {
    }

    @SubscribeEvent
    public void onEvent(final Event event)
    {
        if (!event.isCancelable() || event.isCanceled())
            return;

        if (event.getClass().getName().toLowerCase().contains("client"))
            return;

        final GameProfile targetProfile = gameProfileExtractors.getOrDefault(event.getClass(), (e) -> null).apply(event);
        final IContext targetContext = permissionContextExtractors.getOrDefault(event.getClass(), (e) -> null).apply(event);

        event.setCanceled(!VaultPermissionHandler.getInstance().hasPermission(targetProfile,"event." + (event.getClass().getName().replace("$", ".")).toLowerCase(), targetContext));
    }

    @Override
    public <E extends Event> IEventPermissionHandler registerGameProfileExtractor(final Function<E, GameProfile> gameProfileExtractor, final Class<? extends E>... targetClasses)
    {
        for (final Class<? extends E> targetClass : targetClasses)
        {
            this.gameProfileExtractors.put(targetClass, (rawEvent)-> {
                if (!targetClass.isInstance(rawEvent))
                    return null;

                return gameProfileExtractor.apply((E) rawEvent);
            });
        }

        return this;
    }

    @Override
    public <E extends Event> IEventPermissionHandler registerContextExtractor(
      final Function<E, IContext> permissionContextExtractor,
      final Class<? extends E>... targetClasses)
    {
        for (final Class<? extends E> targetClass : targetClasses)
        {
            this.permissionContextExtractors.put(targetClass, (rawEvent)-> {
                if (!targetClass.isInstance(rawEvent))
                    return null;

                return permissionContextExtractor.apply((E) rawEvent);
            });
        }

        return this;
    }
}
