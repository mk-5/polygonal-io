package io.polygonal.plugin;

import org.apache.maven.plugin.logging.Log;

class Logger implements io.polygonal.Logger.LoggerMethods {
    private final Log log;

    Logger(Log log) {
        this.log = log;
    }

    @Override
    public void info(String msg, Object... args) {
        log.info(msg);
    }

    @Override
    public void error(String msg, Object... args) {
        if (args.length > 0 && args[0] instanceof Throwable) {
            log.error(msg, (Throwable) args[0]);
        } else {
            log.error(msg);
        }
    }

    @Override
    public void warn(String msg, Object... args) {
        if (args.length > 0 && args[0] instanceof Throwable) {
            log.warn(msg, (Throwable) args[0]);
        } else {
            log.warn(msg);
        }
    }

    @Override
    public void debug(String msg, Object... args) {
        if (args.length > 0 && args[0] instanceof Throwable) {
            log.debug(msg, (Throwable) args[0]);
        } else {
            log.debug(msg);
        }
    }
}
