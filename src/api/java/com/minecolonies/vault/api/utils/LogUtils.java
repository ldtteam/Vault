package com.minecolonies.vault.api.utils;

import com.minecolonies.vault.api.constants.ModConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for logging.
 */
public class LogUtils
{
    /**
     * Constructs a logger for a given class.
     * @param clazz The class to get the logger for.
     * @return The logger for a given class.
     */
    public static Logger constructLoggerForClass(Class clazz)
    {
        return constructLoggerForName(clazz.getSimpleName());
    }

    /**
     * Constructs a logger for a given name.
     * @param name The name of the logger.
     * @return The logger with a given name.
     */
    public static Logger constructLoggerForName(String name)
    {
        return LogManager.getLogger(ModConstants.CONST_MOD_ID + ":" + name);
    }
}
