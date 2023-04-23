package java_module.__asteroids__;

import java.io.*;
import java.util.Scanner;



public class Leaderboard {
    public String[] boardarray=new String[10];
    public static void main(String[] args){
        //called when you open scenecontroller
        //checks if leaderboard.csv exists
        //if it doesn't it creates it
        //returns the csv file (and maybe the highest score)
        System.out.println("Whats up");
        try {
            File leaderbord = new File("leaderboard.csv");
            if (leaderbord.createNewFile()){
                initBoard();
            }
        } catch (IOException e){
            System.out.println("An error occurred when creating leaderboard file.");
            e.printStackTrace();
        }
        System.out.println("The sky");
        readBoard();
      }

    public static void initBoard(){
        //called by the reset scores button and the main function
        //sets all the leaderboard values to --- 0
        //Does it need the file to exist to do so or can it create/overrwite the file in the same way?
        System.out.println("Initboard");
        try{
            PrintWriter writer = new PrintWriter("leaderboard.csv");
            for (int i = 0; i < 10; i++) {
                writer.println("---, "+000);
            }   
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred when writing leaderboard file.");
            e.printStackTrace();
        }

    }

    public static void readBoard(){
        System.out.println("Read the board");
        String csvFile = "leaderboard.csv";
        String line;
        String[] boardline = new String[2];
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] values = line.split(",");
                for (String value : values) {
                    System.out.print(value + ", ");

                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred when reading leaderboard file.");
        }
    }
    

    public void addScore(){
        //called when the person enters their name
        //checks if their score is lower than the 10th
        //if yes, does nothing
        //if no, boots out the 10th score and then checks each remaining score against itself
        //stops when it finds a score higher and inserts itself behind it
        //this is so all you losers tied with the 10th score can get on the leaderboard
        //and so a named person with 0 points isn't gonna rank below ---

    }
    public static void buildBoard(){
        //takes an array of objects?? and then turns them into a table



    }




}





