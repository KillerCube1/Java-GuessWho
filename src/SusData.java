
/**
 * This class represents all the data necessary to create all Suspects for the Guess Who game.
 * The data is stored in a lexicographic manner based on Suspect names. All data is stored in Arrays
 * and represents the attributes of each Suspect in the Guess Who game.
 *
 * Each attribute is stored in a separate array, and getter methods are provided to access individual attributes
 * based on the index.
 *
 * @author Rylan, Damien
 */
public class SusData {//SusData

    String[] names = {
            "Alex","Alfred","Anita","Anne","Bernard","Bill",
            "Charles","Claire","David","Eric","Frans","George",
            "Herman","Joe","Maria","Max","Paul","Peter",
            "Philip","Richard","Robert","Sam","Susan","Tom"
    };

    String[] eyeColors = {
            "brown", "blue", "blue", "brown", "brown", "brown",
            "brown", "brown", "brown", "brown", "brown", "brown",
            "brown", "brown", "brown", "brown", "brown", "blue",
            "brown", "brown", "blue", "brown", "brown", "blue"
    };

    boolean[] beard = {
            false, false, false, false, false, true,
            false, false, true, false, false, false,
            false, false, false, false, false, false,
            true, true, false, false, false, false
    };

    boolean[] glasses = {
            false, false, false, false, false, false,
            false, true, false, false, false, false,
            false, true, false, false, true, false,
            false, false, false, true, false, true
    };

    boolean[] moustache = {
            true, true, false, false, false, false,
            true, false, false, false, false, false,
            false, false, false, true, false, false,
            false, true, false, false, false, false
    };

    boolean[] bald = {
            false, false, false, false, false, true,
            false, false, false, false, false, false,
            true, false, false, false, false, false,
            false, true, false, true, false, true
    };

    boolean[] rosyCheeks = {
            false, false, true, false, false, true,
            false, false, false, false, false, false,
            false, false, false, false, false, false,
            true, false, true, false, true, false
    };

    String[] hairColor = {
            "black","red", "white", "black", "brown", "red",
            "blonde", "red", "blonde", "blonde", "red", "white",
            "red", "blonde", "brown", "black", "white", "white",
            "black", "brown", "red", "white", "blonde", "black"
    };

    boolean[] hat = {
            false, false, false, false, true, false,
            false, true, false, true, false, true,
            false, false, true, false, false, false,
            false, false, false, false, false, false
    };

    String[] gender = {
            "male", "male", "female", "female", "male", "male",
            "male", "female", "male", "male", "male", "male",
            "male", "male", "female", "male", "male", "male",
            "male", "male", "male", "male", "female", "male"
    };


    /**
     * Get the name of the suspect at the specified index.
     *
     * @param index the index of the suspect
     * @return the name of the suspect
     */
    public String getName(int index)
    {
        return names[index];
    }



    /**
     * Get the eye color of the suspect at the specified index.
     *
     * @param index the index of the suspect
     * @return the eye color of the suspect
     */
    public String getEyeColor(int index)
    {
        return eyeColors[index];
    }



    /**
     * Check if the suspect at the specified index has a beard.
     *
     * @param index the index of the suspect
     * @return true if the suspect has a beard, false otherwise
     */
    public boolean getBeard(int index)
    {
        return beard[index];
    }



    /**
     * Check if the suspect at the specified index wears glasses.
     *
     * @param index the index of the suspect
     * @return true if the suspect wears glasses, false otherwise
     */
    public boolean getGlasses(int index)
    {
        return glasses[index];
    }



    /**
     * Check if the suspect at the specified index has a moustache.
     *
     * @param index the index of the suspect
     * @return true if the suspect has a moustache, false otherwise
     */
    public boolean getMoustache(int index)
    {
        return moustache[index];
    }



    /**
     * Check if the suspect at the specified index is bald.
     *
     * @param index the index of the suspect
     * @return true if the suspect is bald, false otherwise
     */
    public boolean getBald(int index)
    {
        return bald[index];
    }



    /**
     * Check if the suspect at the specified index has rosy cheeks.
     *
     * @param index the index of the suspect
     * @return true if the suspect has rosy cheeks, false otherwise
     */
    public boolean getRosyCheeks(int index)
    {
        return rosyCheeks[index];
    }



    /**
     * Get the hair color of the suspect at the specified index.
     *
     * @param index the index of the suspect
     * @return the hair color of the suspect
     */
    public String getHairColor(int index)
    {
        return hairColor[index];
    }



    /**
     * Check if the suspect at the specified index wears a hat.
     *
     * @param index the index of the suspect
     * @return true if the suspect wears a hat, false otherwise
     */
    public boolean getHat(int index){
        return hat[index];
    }



    /**
     * Get the gender of the suspect at the specified index.
     *
     * @param index the index of the suspect
     * @return the gender of the suspect
     */
    public String getGender(int index)
    {
        return gender[index];
    }


}//SusData
