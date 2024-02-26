package lsg_api;

import java.io.OutputStream;

public class ConsoleAPI
{
    public static final OutputStream logStream = new APIOutputStream(System.out, "\u001B[34m", "\u001B[0m");
    public static final OutputStream warnStream = new APIOutputStream(System.out, "\u001B[33m", "\u001B[0m");
    public static final OutputStream errorStream = new APIOutputStream(System.err, "\u001B[31m", "\u001B[0m");
    public static final OutputStream infoStream = new APIOutputStream(System.out, "\u001B[32m", "\u001B[0m");
    public static final OutputStream debugStream = new APIOutputStream(System.out, "\u001B[36m", "\u001B[0m");
    public static final OutputStream rawStream = new APIOutputStream(System.out, "", "");

    private static void write(String message, String prefix, OutputStream stream)
    {
        try { stream.write((prefix + message + "\n").getBytes()); }
        catch (Exception e) { System.err.println("ConsoleAPI.write: " + e.getMessage() + " - " + message); }
    }

    public static void log(String message) { write(message, "[LOG]", logStream); }

    public static void warn(String message) { write(message, "[WARN]", warnStream); }

    public static void error(String message) { write(message, "[ERROR]", errorStream); }

    public static void info(String message) { write(message, "[INFO]", infoStream); }

    public static void debug(String message) { write(message, "[DEBUG]", debugStream); }

    public static void raw(String message) { write(message, "[RAW]", rawStream); }

    public static void print(String message, String prefix, OutputStream stream) { write(message, "[LSG_API]" + prefix, stream); }

    public static void testStreams()
    {
        log("log message");
        warn("warn message");
        error("error message");
        info("info message");
        debug("debug message");
        raw("raw message");
        print("print message", "test", logStream);
    }
}

class APIOutputStream extends OutputStream
{
    private final OutputStream out;
    private final String prefix;
    private final String suffix;

    public APIOutputStream(OutputStream out, String prefix, String suffix)
    {
        this.out = out;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public void write(int b) { System.out.print(prefix + (char) b + suffix); }
}