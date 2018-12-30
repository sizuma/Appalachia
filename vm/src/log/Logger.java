package log;

public interface Logger {
    enum Level {
        TRACE,
        INFO,
        WARN,
        ERROR,
        FATAL
    }

    void log(String log, Level level);

    void setLevel(Level level);
    Level getLevel();

    default boolean shouldLog(Level level) {
        return this.getLevel().ordinal() <= level.ordinal();
    }

    default void trace(String log) {
        if(!shouldLog(Level.TRACE)) return;
        this.log(log, Level.TRACE);
    }

    default void info(String log) {
        if(!shouldLog(Level.INFO)) return;
        this.log(log, Level.INFO);
    }

    default void warn(String log) {
        if(!shouldLog(Level.WARN)) return;
        this.log(log, Level.WARN);
    }

    default void error(String log) {
        if(!shouldLog(Level.ERROR)) return;
        this.log(log, Level.ERROR);
    }

    default void fatal(String log) {
        if(!shouldLog(Level.FATAL)) return;
        this.log(log, Level.FATAL);
    }
}
