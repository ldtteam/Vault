package com.ldtteam.vault.common.event;

import com.ldtteam.vault.api.utils.LogUtils;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Function;

public class EventAnalyzer
{
    private static EventAnalyzer ourInstance = new EventAnalyzer();

    private static final Logger         logger     = LogUtils.constructLoggerForClass(EventAnalyzer.class);
    private final        List<Class<?>> eventNodes = new ArrayList<>();

    public static EventAnalyzer getInstance()
    {
        return ourInstance;
    }

    private EventAnalyzer()
    {
    }

    public void discoverEvents(FMLPreInitializationEvent preInitializationEvent)
    {
        logger.info("Starting cancelable event discovery.");
        preInitializationEvent.getAsmData().getAll("net.minecraftforge.fml.common.eventhandler.Cancelable").stream()
          .map(asmData -> {
              try
              {
                  return Class.forName(asmData.getClassName());
              }
              catch (ClassNotFoundException e)
              {
                  logger.error("Failed to get event class for analysis from cancelable annotation data.", e);
                  return null;
              }
          })
          .filter(Objects::nonNull)
          .filter(cls -> !cls.getName().toLowerCase().contains("client"))
          .forEach(eventNodes::add);
        eventNodes.sort(Comparator.comparing(Class::getName));

        logger.info("Finished cancelable event discovery. Found a total of: " + eventNodes.size() + " events, which will be registered as permission nodes.");
    }

    public void registerEvents(FMLInitializationEvent initializationEvent)
    {
        logger.info("Registering previously discovered cancelable events as permission nodes.");
        eventNodes.forEach(cls -> PermissionAPI.registerNode("event." + (cls.getName().replace("$", ".")).toLowerCase(), DefaultPermissionLevel.ALL, "Event permission node for: " + cls.getSimpleName()));
        logger.info("Registered: " + eventNodes.size() + " events as permission nodes.");

        analyzeEvents();
    }

    public void analyzeEvents()
    {
        eventNodes.forEach(cls -> {
            logger.info("Performing event analysis on class: " + cls.getName());
            Arrays.stream(cls.getMethods())
              .filter(method -> method.getName().toLowerCase().contains("player"))
              .forEach(method -> logger.info("  * " + method.getName()));
        });
    }

}
