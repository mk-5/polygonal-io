package io.polygonal;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class Logger {
    public static LoggerMethods log;

    public static void setLogger(LoggerMethods loggerMethods) {
        Logger.log = loggerMethods;
    }

    public interface LoggerMethods {
        void info(String msg, Object... args);

        void error(String msg, Object... args);

        void warn(String msg, Object... args);

        void debug(String msg, Object... args);
    }
}
