package log;

public class Console implements Logger {
    private Level level;

    public Console(Level level) {
        this.level = level;
    }

    public Console() {
        this(Level.WARN);
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public void log(String log, Level level) {
        switch (level) {
            case PRINT:
            case TRACE:
            case INFO:
            case WARN:
                System.out.printf("[%s]: %s\n", level, log);
                break;
            case ERROR:
            case FATAL:
                System.err.printf("[%s]: %s\n", level, log);
                break;
        }
    }
}
