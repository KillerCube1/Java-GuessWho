/**
 * This class represents all the data necessary to create all Suspects for the Guess Who game.  The data is stored in a 
 * lexographic manner based on Suspect names.  All data is stored in Arrays and represents the attributes of each Suspect
 * in the Guess Who game.
 * 
 * @author jbutka
 *
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



    public String getName(int index)
    {
        return names[index];
    }
    

    public String getEyeColor(int index)
    { 
    	return eyeColors[index];
    }
    

    public boolean getBeard(int index)
    { 
    	return beard[index];
    }
    

    public boolean getGlasses(int index)
    { 
    	return glasses[index];
    }
    

    public boolean getMoustache(int index)
    { 
    	return moustache[index];
    }
    

    public boolean getBald(int index)
    { 
    	return bald[index];
    }
    

    public boolean getRosyCheeks(int index)
    { 
    	return rosyCheeks[index];
    }
   

    public String getHairColor(int index)
    {
    	return hairColor[index];
    }
   

    public boolean getHat(int index){
    	return hat[index];
    }
   

    public String getGender(int index)
    { 
    	return gender[index];
    }


}//SusData
