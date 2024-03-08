public class SuspectData {

    private final String name;
    private final String eyeColor;
    private final boolean beard;
    private final boolean glasses;
    private final boolean moustache;
    private final boolean bald;
    private final boolean rosyCheeks;
    private final String hairColor;
    private final boolean hat;
    private final String gender;

    public SuspectData(String name, String eyeColor, boolean beard, boolean glasses, boolean moustache, boolean bald, boolean rosyCheeks, String hairColor, boolean hat, String gender) {
        this.name = name;
        this.eyeColor = eyeColor;
        this.beard = beard;
        this.glasses = glasses;
        this.moustache = moustache;
        this.bald = bald;
        this.rosyCheeks = rosyCheeks;
        this.hairColor = hairColor;
        this.hat = hat;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public boolean isBeard() {
        return beard;
    }

    public boolean isGlasses() {
        return glasses;
    }

    public boolean isMoustache() {
        return moustache;
    }

    public boolean isBald() {
        return bald;
    }

    public boolean isRosyCheeks() {
        return rosyCheeks;
    }

    public String getHairColor() {
        return hairColor;
    }

    public boolean isHat() {
        return hat;
    }

    public String getGender() {
        return gender;
    }

}
