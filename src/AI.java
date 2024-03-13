import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

public class AI {
    private static final ArrayList<String> questions = new ArrayList<>();
    private static final ArrayList<String> characters = new ArrayList<>();

    public static void main(String[] args){
        initializeArrayList();

        Collections.shuffle(questions);
        String questionFromArrayList = questions.removeFirst();

        Random characterFromArrayList = new Random();

        int randomCharacter= characterFromArrayList.nextInt(characters.size());
        String randomCharacterElement = characters.get(randomCharacter);


        System.out.println("Random character: " + randomCharacterElement);
        System.out.println("Random question from AI: " + questionFromArrayList);


        Scanner scan = new Scanner(System.in);
        System.out.println("Enter true or false: ");
        boolean userAnswer = scan.nextBoolean();

        if(userAnswer){
            System.out.println("AI WINS");
        }else{
            System.out.println("Your turn");
        }


    }

    private static void initializeArrayList(){
        charactersArrayList();
        questionsArrayList();
    }

    private static void charactersArrayList(){
        characters.add("Alex");
        characters.add("Alfred");
        characters.add("Anita");
        characters.add("Anne");
        characters.add("Bernard");
        characters.add("Bill");
        characters.add("Charles");
        characters.add("Claire");
        characters.add("David");
        characters.add("Eric");
        characters.add("Frans");
        characters.add("George");
        characters.add("Herman");
        characters.add("Joe");
        characters.add("Maria");
        characters.add("Max");
        characters.add("Paul");
        characters.add("Peter");
        characters.add("Philip");
        characters.add("Richard");
        characters.add("Robert");
        characters.add("Sam");
        characters.add("Susan");
        characters.add("Tom");
    }
    private static void questionsArrayList(){
        questions.add("Is your character Male?");
        questions.add("Is your character Female?");
        questions.add("Does your character have a Hat?");
        questions.add("Is your character Bald?");
        questions.add("Does your character have a Beard?");
        questions.add("Does your character have a Moustache?");
        questions.add("Does your character have a RosyCheeks?");
        questions.add("Does your character have a Glasses?");
        questions.add("Does your character have a BlackHair?");
        questions.add("Does your character have a RedHair?");
        questions.add("Does your character have a BrownHair?");
        questions.add("Does your character have a BlondeHair?");
        questions.add("Does your character have a WhiteHair?");
        questions.add("Does your character have a Blue Eyes?");
        questions.add("Does your character have a Brown Eyes?");

    }

    private static AbstractList<String> getCharacters(){
        return characters;
    }

    private static AbstractList<String>getQuestions(){
        return questions;
    }
}
