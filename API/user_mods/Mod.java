package user_mods;

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
}
