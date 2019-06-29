package com.ldtteam.vault.api.event;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.server.permission.context.IContext;
import net.minecraftforge.server.permission.context.PlayerContext;

import java.util.function.Function;

public interface IEventPermissionHandler
{
    <E extends Event> IEventPermissionHandler registerGameProfileExtractor(Function<E, GameProfile> gameProfileExtractor, Class<? extends E>... targetClasses);

    <E extends Event> IEventPermissionHandler registerContextExtractor(Function<E, IContext> permissionContextExtractor, Class<? extends E>... targetClasses);

    default <E extends Event> IEventPermissionHandler registerContextAndGameProfileExtractor(Function<E, IContext> permissionContextExtractor, Function<E, GameProfile> gameProfileExtractor, Class<? extends E>... targetClasses)
    {
        return this.registerGameProfileExtractor(gameProfileExtractor, targetClasses).registerContextExtractor(permissionContextExtractor, targetClasses);
    }

    default <E extends Event> IEventPermissionHandler registerEntityPlayerExtractor(Function<E, EntityPlayer> playerExtractor, Class<? extends E>... targetClasses)
    {
        return this.registerContextAndGameProfileExtractor(
          (event) -> {
              final EntityPlayer player = playerExtractor.apply(event);
              if (player == null)
              {
                  return null;
              }

              return new PlayerContext(player);
          },
          (event) -> {
              final EntityPlayer player = playerExtractor.apply(event);
              if (player == null)
              {
                  return null;
              }

              return player.getGameProfile();
          },
          targetClasses
        );
    }
}
