package user_mods;

import lsg_api.ConsoleAPI;

import java.io.OutputStream;
import java.lang.reflect.Method;

public abstract class Mod
{
    protected String name;
    protected String description;
    protected String version;
    private final int id;

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
    public void beginTurn() {}
    public void endTurn() { info("EndTurn"); } // TODO : Called
    public void stop() {}

    public void heroBeginAttack() {}
    public void heroFinishAttack() {}
    public void heroBeginRecuperate() {}
    public void heroFinishRecuperate() {}
    public void heroBeginConsume() {}
    public void heroFinishConsume() {}
    public void heroBeginTurn() { info("heroBeginTurn"); } // TODO : Called
    public void heroFinishTurn() { info("heroFinishTurn"); } // TODO : Called
    public void heroDie() {}

    public void monsterBeginAttack() { info("monsterBeginAttack"); }
    public void monsterFinishAttack() { info("monsterFinishAttack"); }
    public void monsterBeginTurn() { info("monsterBeginTurn"); }
    public void monsterEndTurn() { info("monsterEndTurn"); }
    public void MonsterDie() { info("MonsterDie"); }
    public void newMonsterCreate() { info("NewMonsterCreate"); }

    public void setWindow(ModdedWindow window) {}

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
