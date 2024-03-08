import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonParser {
    private final List<SuspectData> suspects;
    public int index;

    public JsonParser(String json)throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<SuspectData>> map = objectMapper.readValue(json, new TypeReference<Map<String, List<SuspectData>>>() {});
        suspects = map.get("Suspects");
    }

    public String getAttribute(int index, String attribute) {
        SuspectData suspect = suspects.get(index);
        return switch (attribute) {
            case "name" -> suspect.getName();
            case "hairColor" -> suspect.getHairColor();
            case "eyeColor" -> suspect.getEyeColor();
            case "gender" -> suspect.getGender();
            case "bald" -> String.valueOf(suspect.isBald());
            case "hat" -> String.valueOf(suspect.isHat());
            case "glasses" -> String.valueOf(suspect.isGlasses());
            case "moustache" -> String.valueOf(suspect.isMoustache());
            case "beard" -> String.valueOf(suspect.isBeard());
            case "rosyCheeks" -> String.valueOf(suspect.isRosyCheeks());
            default -> null;
        };
    }

    public List<SuspectData> getSuspects() {
        return suspects;
    }


    public String getName(int i){
        return suspects.get(i).getName();
    }

    public String getHairColor(int i) {
        return suspects.get(i).getHairColor();
    }

    public boolean isBald(int i) {
        return suspects.get(i).isBald();
    }

    public String getEyeColor(int i) {
        return suspects.get(i).getEyeColor();
    }

    public String getGender(int i) {
        return suspects.get(i).getGender();
    }

    public boolean hasHat(int i) {
        return suspects.get(i).isHat();
    }

    public boolean hasGlasses(int i) {
        return suspects.get(i).isGlasses();
    }

    public boolean hasMoustache(int i) {
        return suspects.get(i).isMoustache();
    }

    public boolean hasBeard(int i) {
        return suspects.get(i).isBeard();
    }

    public boolean hasRosyCheeks(int i) {
        return suspects.get(i).isRosyCheeks();

    }
}