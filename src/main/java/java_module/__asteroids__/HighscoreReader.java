package java_module.__asteroids__;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HighscoreReader {
    private final HashMap<String, Integer> highscores = new HashMap<>();
    public void createHighscoreFile(){
        // create highscores file if not exists
        try {
            File highscore = new File("highscores.txt");
            if (highscore.createNewFile()){
                BufferedWriter writer = new BufferedWriter(new FileWriter("highscores.txt"));
                for (int i = 0; i < 10; i++){
                    writer.append("--- 000");
                }
                writer.append("--- 000");
                writer.close();
            }
        } catch (IOException e){
            System.out.println("An error occurred when creating highscores file.");
            e.printStackTrace();
        }
    }
    public Integer returnHighscore(){
        try {
            Scanner scanner = new Scanner(new File("highscores.txt"));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                highscores.put(name,score);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return Collections.max(highscores.values());
    }
    public void resetHighscores(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("highscores.txt"));
            for (int i=0; i<9; i++){
                writer.append("--- 000\n");
            }
            writer.append("--- 000");
            writer.close();
        } catch (IOException e){
            System.out.println("An error occurred when creating highscores file.");
            e.printStackTrace();
        }
    }
    public ArrayList<String> getHighscoreString(){
        ArrayList<String> highscoreString = new ArrayList<>(highscores.size());
        LinkedHashMap<String, Integer> h = sortedHighscores(highscores);
        for (Map.Entry<String, Integer> entry : h.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            String keyValuePair = key + " " + value.toString();
            highscoreString.add(keyValuePair);
        }
        return highscoreString;
    }
    private LinkedHashMap<String, Integer> sortedHighscores(HashMap<String, Integer> highscores){
        List<Map.Entry<String, Integer>> list = new LinkedList<>(highscores.entrySet());

        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        LinkedHashMap<String, Integer> sortedHighscores = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list){
            sortedHighscores.put(entry.getKey(), entry.getValue());
        }
        return sortedHighscores;
    }
    public void addHighscore(String username, Integer score){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("highscores.txt", true));
            String newScore = "\n" + username + " " + score.toString();
            writer.append(newScore);
            writer.close();

        } catch (IOException e){
            System.out.println("An error occurred when creating highscores file.");
            e.printStackTrace();
        }
    }
}
