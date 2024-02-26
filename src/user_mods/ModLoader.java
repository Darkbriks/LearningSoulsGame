package user_mods;

import lsg.utils.Constants;
import lsg_api.ConsoleAPI;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.jar.JarFile;

public class ModLoader
{
    public static final String DEVELLOPMENT_MODS_PATH = "src/user_mods";
    public static final String RELEASE_MODS_PATH = "mods";
    public static final String MODS_EXTENSION = ".jar";
    private static LinkedHashSet<Mod> loadedMods;
    private static HashMap<Integer, URLClassLoader> urlClassLoaders;
    private static HashMap<Integer, JarFile> jarFiles;

    private static ModLoader instance = null;

    public static ModLoader getInstance()
    {
        if (instance == null)
        {
            instance = new ModLoader();
        }
        return instance;
    }

    private ModLoader()
    {
        loadedMods = new LinkedHashSet<>();
        urlClassLoaders = new HashMap<>();
        jarFiles = new HashMap<>();
    }

    public void loadMods()
    {
        File modsDir = new File(DEVELLOPMENT_MODS_PATH);
        File[] mods = modsDir.listFiles();

        ConsoleAPI.print("Mod directory: " + modsDir.getAbsolutePath(), "[ModLoader][loadMods] ", ConsoleAPI.infoStream);

        if (mods != null)
        {
            for (File mod : mods)
            {
                if (mod.isFile() &&  mod.getName().endsWith(MODS_EXTENSION))
                {
                    loadMod(mod);
                }
            }
        }
    }

    private void loadMod(File mod)
    {
        try
        {
            JarFile jarFile = new JarFile(mod);
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { mod.toURI().toURL() }, getClass().getClassLoader());
            String modClassName = jarFile.getManifest().getMainAttributes().getValue("Main-Class");
            Class<?> modClass = classLoader.loadClass(modClassName);
            Mod newMod = (Mod) modClass.getDeclaredConstructor().newInstance();

            if (!newMod.isCompatibleWithAPI(Constants.API_VERSION) || !newMod.isCompatibleWithGame(Constants.GAME_VERSION))
            {
                ConsoleAPI.warn("[ModLoader][loadMod] Mod " + newMod.getName() + " is not compatible with the current API or Game version. It will not be loaded.");
                classLoader.close();
                jarFile.close();
                return;
            }

            newMod.onLoaded();

            String modClassWindow = jarFile.getManifest().getMainAttributes().getValue("Window-Class");
            if (modClassWindow != null)
            {
                Class<?> windowClass = classLoader.loadClass(modClassWindow);
                ModdedWindow window = (ModdedWindow) windowClass.getDeclaredConstructor().newInstance();
                newMod.setWindow(window);
            }

            loadedMods.add(newMod);
            urlClassLoaders.put(newMod.getId(), classLoader);
            jarFiles.put(newMod.getId(), jarFile);
        }
        catch (Exception e) { ConsoleAPI.print("Error: " + e, "[ModLoader][loadMod] ", ConsoleAPI.errorStream); }
    }

    public void invoke(String methodName)
    {
        for (Mod mod : loadedMods)
        {
            try
            {
                Method method = mod.getMethod(methodName);
                if (method != null) { method.invoke(mod); }
            }
            catch (Exception e) { ConsoleAPI.print("Error: ModLoader.invoke: " + e, "[ModLoader][invoke] ", ConsoleAPI.errorStream); }
        }
    }

    public void unloadMods()
    {
        int size = loadedMods.size();
        for (int i = 0; i < size; i++)
        {
            unloadMod(loadedMods.iterator().next());
        }
    }

    private void unloadMod(Mod mod)
    {
        try
        {
            mod.onUnloaded();

            loadedMods.remove(mod);

            urlClassLoaders.get(mod.getId()).close();
            urlClassLoaders.remove(mod.getId());

            jarFiles.get(mod.getId()).close();
            jarFiles.remove(mod.getId());

        }
        catch (Exception e) { ConsoleAPI.print("Error: ModLoader.unloadMod: " + e, "[ModLoader][unloadMod] ", ConsoleAPI.errorStream); }
    }

}
