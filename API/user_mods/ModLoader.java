package user_mods;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.jar.JarFile;

public class ModLoader
{
    public static final String DEVELLOPMENT_MODS_PATH = "src/user_mods";
    public static final String RELEASE_MODS_PATH = "mods";
    public static final String MODS_EXTENSION = ".jar";
    private static HashSet<Mod> loadedMods;
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
        loadedMods = new HashSet<>();
        urlClassLoaders = new HashMap<>();
        jarFiles = new HashMap<>();
    }

    public void loadMods()
    {
        File modsDir = new File(DEVELLOPMENT_MODS_PATH);
        File[] mods = modsDir.listFiles();

        System.out.println("Debug: ModLoader.loadMods: " + modsDir.getAbsolutePath());

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
            System.out.println("Debug: ModLoader.loadMod: " + newMod);

            Method onLoaded = modClass.getMethod("onLoaded");
            onLoaded.invoke(newMod);
            System.out.println("Debug: ModLoader.loadMod: onLoaded invoked");

            loadedMods.add(newMod);
            urlClassLoaders.put(newMod.getId(), classLoader);
            jarFiles.put(newMod.getId(), jarFile);
        }
        catch (Exception e) { System.err.println("Error: ModLoader.loadMod: " + e); }
    }

    public void invoke(String methodName)
    {
        for (Mod mod : loadedMods)
        {
            try
            {
                Method method = mod.getMethod(methodName);
                if (method != null) { method.invoke(mod); }
            } catch (Exception e) { System.err.println("Error: ModLoader.invoke: " + e); }
        }
    }

    public void unloadMods()
    {
        for (Mod mod : loadedMods)
        {
            unloadMod(mod);
        }
    }

    private void unloadMod(Mod mod)
    {
        try
        {
            Method onUnloaded = mod.getMethod("onUnloaded");
            if (onUnloaded != null) { onUnloaded.invoke(mod); }

            loadedMods.remove(mod);

            urlClassLoaders.get(mod.getId()).close();
            urlClassLoaders.remove(mod.getId());

            jarFiles.get(mod.getId()).close();
            jarFiles.remove(mod.getId());

        }
        catch (Exception e) { System.err.println("Error: ModLoader.unloadMod: " + e); }
    }

}