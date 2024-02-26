package user_mods;

import lsg.utils.Version;
import lsg_api.ConsoleAPI;

import java.io.OutputStream;
import java.lang.reflect.Method;

public abstract class Mod
{
    protected String name;
    protected String description;
    protected Version modVersion;
    protected Version[] apiVersions;
    protected Version[] gameVersions;
    private final int id;

    @Deprecated
    protected String version;

    private static int nextId = 0;

    public Mod(String name, String description, Version modVersion, Version[] apiVersions, Version[] gameVersions)
    {
        this.name = name;
        this.description = description;
        this.modVersion = modVersion;
        this.apiVersions = apiVersions;
        this.gameVersions = gameVersions;
        this.id = nextId++;
    }

    public Mod(String name, String description, Version modVersion, Version apiVersion, Version gameVersion) { this(name, description, modVersion, new Version[] {apiVersion}, new Version[] {gameVersion}); }

    @Deprecated
    public Mod(String name, String description, String version)
    {
        ConsoleAPI.warn("[Mod][Constructor] Deprecated constructor used, please use #Mod(String, String, Version, Version[], Version[]) instead. This will be removed in 0.3.0 API update.");
        this.name = name;
        this.description = description;
        this.version = version;
        this.id = nextId++;
        this.modVersion = null;
        this.apiVersions = null;
        this.gameVersions = null;
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

    public boolean isCompatibleWithAPI(Version version)
    {
        if (apiVersions == null) { ConsoleAPI.warn("[Mod][isCompatibleWithAPI] No API version specified, assuming compatible. This will be changed in 0.3.0 API update."); return true; }
        for (Version apiVersion : apiVersions) { if (apiVersion.isEquals(version, true)) { return true; } }
        return false;
    }

    public boolean isCompatibleWithGame(Version version)
    {
        if (gameVersions == null) { ConsoleAPI.warn("[Mod][isCompatibleWithGame] No game version specified, assuming compatible. This will be changed in 0.3.0 API update."); return true; }
        for (Version gameVersion : gameVersions) { if (gameVersion.isEquals(version, true)) { return true; } }
        return false;
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

    public void monsterBeginAttack() {}
    public void monsterFinishAttack() {}
    public void monsterBeginTurn() {}
    public void monsterEndTurn() {}
    public void MonsterDie() {}
    public void newMonsterCreate() {}

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
