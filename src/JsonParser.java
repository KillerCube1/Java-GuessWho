import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonParser {
    private final List<Suspect> suspects;

    public JsonParser(String json)throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Suspect>> map = objectMapper.readValue(json, new TypeReference<Map<String, List<Suspect>>>() {});
        suspects = map.get("Suspects");
    }

    public String getAttribute(int index, String attribute) {
        Suspect suspect = suspects.get(index);
        return switch (attribute) {
            case "name" -> suspect.getName();
            case "hairColor" -> suspect.getHairColor();
            case "eyeColor" -> suspect.getEyeColor();
            case "gender" -> suspect.getGender();
            case "bald" -> String.valueOf(suspect.isBald());
            case "hat" -> String.valueOf(suspect.hasHat());
            case "glasses" -> String.valueOf(suspect.hasGlasses());
            case "moustache" -> String.valueOf(suspect.hasMoustache());
            case "beard" -> String.valueOf(suspect.hasBeard());
            case "rosyCheeks" -> String.valueOf(suspect.hasRosyCheeks());
            default -> null;
        };
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
        return suspects.get(i).hasHat();
    }

    public boolean hasGlasses(int i) {
        return suspects.get(i).hasGlasses();
    }

    public boolean hasMoustache(int i) {
        return suspects.get(i).hasMoustache();
    }

    public boolean hasBeard(int i) {
        return suspects.get(i).hasBeard();
    }

    public boolean hasRosyCheeks(int i) {
        return suspects.get(i).hasRosyCheeks();

    }
}