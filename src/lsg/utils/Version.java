package lsg.utils;

// Cette classe utilise la semantique de versionnement https://semver.org/lang/fr
public class Version
{
    private final int major;
    private final int minor;
    private final int patch;
    private final String label;
    private final String build;

    ////////// CONSTRUCTEURS //////////
    public Version(int major, int minor, int patch, String label, String build)
    {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.label = label;
        this.build = build;
    }

    public Version(int major, int minor, int patch, String label)
    {
        this(major, minor, patch, label, null);
    }

    public Version(int major, int minor, int patch)
    {
        this(major, minor, patch, null, null);
    }

    ////////// GETTERS //////////
    public int getMajor() { return major; }
    public int getMinor() { return minor; }
    public int getPatch() { return patch; }
    public String getLabel() { return label; }
    public String getBuild() { return build; }

    ////////// METHODES //////////
    public String toString()
    {
        String version = major + "." + minor + "." + patch;
        if (label != null)
        {
            version += "-" + label;
        }
        if (build != null)
        {
            version += "+" + build;
        }
        return version;
    }

    ////////// IS EQUALS //////////
    public boolean isEquals(Version other) { return this.major == other.major && this.minor == other.minor && this.patch == other.patch; }
    public boolean isEquals(Version other, boolean ignoreLabel) { return this.isEquals(other) && (ignoreLabel || this.label.equals(other.label)); }
    public boolean isEquals(Version other, boolean ignoreLabel, boolean ignoreBuild) { return this.isEquals(other, ignoreLabel) && (ignoreBuild || this.build.equals(other.build)); }

    public static boolean isEquals(Version v1, Version v2) { return v1.isEquals(v2); }
    public static boolean isEquals(Version v1, Version v2, boolean ignoreLabel) { return v1.isEquals(v2, ignoreLabel); }
    public static boolean isEquals(Version v1, Version v2, boolean ignoreLabel, boolean ignoreBuild) { return v1.isEquals(v2, ignoreLabel, ignoreBuild); }

    ////////// IS GREATER THAN //////////
    public boolean isGreaterThan(Version other) { return this.major > other.major || (this.major == other.major && (this.minor > other.minor || (this.minor == other.minor && this.patch > other.patch))); }
    public boolean isGreaterThan(Version other, boolean ignoreLabel) { return this.isGreaterThan(other) && (ignoreLabel || this.label.compareTo(other.label) > 0); }
    public boolean isGreaterThan(Version other, boolean ignoreLabel, boolean ignoreBuild) { return this.isGreaterThan(other, ignoreLabel) && (ignoreBuild || this.build.compareTo(other.build) > 0); }

    public static boolean isGreaterThan(Version v1, Version v2) { return v1.isGreaterThan(v2); }
    public static boolean isGreaterThan(Version v1, Version v2, boolean ignoreLabel) { return v1.isGreaterThan(v2, ignoreLabel); }
    public static boolean isGreaterThan(Version v1, Version v2, boolean ignoreLabel, boolean ignoreBuild) { return v1.isGreaterThan(v2, ignoreLabel, ignoreBuild); }

    ////////// IS LOWER THAN //////////
    public boolean isLowerThan(Version other) { return this.major < other.major || (this.major == other.major && (this.minor < other.minor || (this.minor == other.minor && this.patch < other.patch))); }
    public boolean isLowerThan(Version other, boolean ignoreLabel) { return this.isLowerThan(other) && (ignoreLabel || this.label.compareTo(other.label) < 0); }
    public boolean isLowerThan(Version other, boolean ignoreLabel, boolean ignoreBuild) { return this.isLowerThan(other, ignoreLabel) && (ignoreBuild || this.build.compareTo(other.build) < 0); }

    public static boolean isLowerThan(Version v1, Version v2) { return v1.isLowerThan(v2); }
    public static boolean isLowerThan(Version v1, Version v2, boolean ignoreLabel) { return v1.isLowerThan(v2, ignoreLabel); }
    public static boolean isLowerThan(Version v1, Version v2, boolean ignoreLabel, boolean ignoreBuild) { return v1.isLowerThan(v2, ignoreLabel, ignoreBuild); }

    ////////// IS GREATER OR EQUALS THAN //////////
    public boolean isGreaterOrEqualsThan(Version other) { return this.isEquals(other) || this.isGreaterThan(other); }
    public boolean isGreaterOrEqualsThan(Version other, boolean ignoreLabel) { return this.isEquals(other, ignoreLabel) || this.isGreaterThan(other, ignoreLabel); }
    public boolean isGreaterOrEqualsThan(Version other, boolean ignoreLabel, boolean ignoreBuild) { return this.isEquals(other, ignoreLabel, ignoreBuild) || this.isGreaterThan(other, ignoreLabel, ignoreBuild); }


    public static boolean isGreaterOrEqualsThan(Version v1, Version v2) { return v1.isGreaterOrEqualsThan(v2); }
    public static boolean isGreaterOrEqualsThan(Version v1, Version v2, boolean ignoreLabel) { return v1.isGreaterOrEqualsThan(v2, ignoreLabel); }
    public static boolean isGreaterOrEqualsThan(Version v1, Version v2, boolean ignoreLabel, boolean ignoreBuild) { return v1.isGreaterOrEqualsThan(v2, ignoreLabel, ignoreBuild); }

    ////////// IS LOWER OR EQUALS THAN //////////
    public boolean isLowerOrEqualsThan(Version other) { return this.isEquals(other) || this.isLowerThan(other); }
    public boolean isLowerOrEqualsThan(Version other, boolean ignoreLabel) { return this.isEquals(other, ignoreLabel) || this.isLowerThan(other, ignoreLabel); }
    public boolean isLowerOrEqualsThan(Version other, boolean ignoreLabel, boolean ignoreBuild) { return this.isEquals(other, ignoreLabel, ignoreBuild) || this.isLowerThan(other, ignoreLabel, ignoreBuild); }

    public static boolean isLowerOrEqualsThan(Version v1, Version v2) { return v1.isLowerOrEqualsThan(v2); }
    public static boolean isLowerOrEqualsThan(Version v1, Version v2, boolean ignoreLabel) { return v1.isLowerOrEqualsThan(v2, ignoreLabel); }
    public static boolean isLowerOrEqualsThan(Version v1, Version v2, boolean ignoreLabel, boolean ignoreBuild) { return v1.isLowerOrEqualsThan(v2, ignoreLabel, ignoreBuild); }
}
