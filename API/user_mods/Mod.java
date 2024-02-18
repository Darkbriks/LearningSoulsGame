package user_mods;

import lsg_api.ConsoleAPI;

import java.io.OutputStream;
import java.lang.reflect.Method;

public abstract class Mod
{
    protected String name;
    protected String description;
    protected String version;
    private int id;

    private static int nextId = 0;

    public Mod(String name, String description, String version)
    {
        this.name = name;
        this.description = description;
        this.version = version;
        this.id = nextId++;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getVersion() { return version; }
    public int getId() { return id; }

    public Method getMethod(String methodName)
    {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods)
        {
            if (method.getName().equals(methodName))
            {
                return method;
            }
        }
        return null;
    }

    public void awake() {}
    public void start() {}
    public void earlyUpdate() {}
    public void update() {}
    public void lateUpdate() {}
    public void stop() {}
    public void destroy() {}

    abstract public void onLoaded();
    abstract public void onUnloaded();

    public String toString()
    {
        return "Mod:\n" + name + "\n - " + description + "\n - " + version;
    }

    public void log(String message) { ConsoleAPI.log(message); }
    public void warn(String message) { ConsoleAPI.warn(message); }
    public void error(String message) { ConsoleAPI.error(message); }
    public void info(String message) { ConsoleAPI.info(message); }
    public void debug(String message) { ConsoleAPI.debug(message); }
    public void raw(String message) { ConsoleAPI.raw(message); }
    public void print(String message, String prefix, OutputStream stream) { ConsoleAPI.print(message, prefix, stream); }
}
