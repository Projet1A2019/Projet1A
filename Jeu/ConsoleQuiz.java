package Jeu;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ConsoleQuiz {
    private int score;
    private long timeElapsed;
    private boolean done = false;
    private int nbQuestions;
    Scanner clavier = new Scanner(System.in);

    public ConsoleQuiz(int nbQuestion) {
        nbQuestions = nbQuestion;
    }

    public void start () {
        try {
            long startTime = System.currentTimeMillis();
            for (Question question : generate(nbQuestions)) {
                System.out.println(question.getText());
                String userAnswer = clavier.nextLine();

                if (userAnswer.equalsIgnoreCase(question.getReponse())){
                    score++;
                    System.out.println("Bonne Réponse");
                }else{
                    System.out.println("Mauvaise Réponse");
                    System.out.println("La bonne réponse est : " + question.getReponse());
                }
            }
            done = true;
            long endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
        } catch(IllegalArgumentException e){
            done = false;
            System.out.println(e.getMessage());
        }

    }
    private int getTimeElapsedInSeconds(long timeInMilliSeconds) {
        // TODO Auto-generated method stub
        return (int) (timeInMilliSeconds / 1000);
    }

    public void displayResultats(){
        if(done){
            displayScore();
            displayTimeElapsed();
        }
    }

    private void displayTimeElapsed() {
        System.out.printf("Temps nécessité pour répondre aux questions : %d secondes ", getTimeElapsedInSeconds(timeElapsed), nbQuestions);
    }

    private void displayScore() {
        System.out.printf("Score final : %d/%d\n ", score, nbQuestions);
    }

    public ArrayList<Question> generate(int nbQuestions){
        String[][] data = getData();

        if (nbQuestions > data.length) {
            throw new IllegalArgumentException("Nombre de questions à générer au maximum : " + data.length + " ");
        }
        ArrayList<Question> questions = new ArrayList<Question>();
        int index;
        ArrayList<Integer> indexesAlreadyTaken = new ArrayList<Integer>();
        indexesAlreadyTaken.clear();

        for (int i = 0; i < nbQuestions; i++){
            do {
                Random random = new Random();
                index = random.nextInt(data.length);
            } while (indexesAlreadyTaken.contains(index));
            indexesAlreadyTaken.add(index);
            String pays = data[index][0];
            String capitale = data[index][1];
            String questionText = String.format("Quelle est la capitale de ce pays : %s? ", pays);
            questions.add(new Question(questionText, capitale));
        }
        return questions;
    }

    private static String[][] getData(){
        String[][] data  = { {"Allemagne", "Berlin"}, {"Autriche", "Vienne"}, {"Roumanie", "Bucarest"},
                {"France", "Paris"},        {"Belgique", "Bruxelles"},          {"Bulgarie", "Sofia"},
                {"Lituanie", "Vilnius"} ,   {"République Tchèque", "Prague"},   {"Espagne", "Madrid"},
                {"Grèce", "Athènes"} ,      {"Hongrie", "Budapest"},            {"Pays-Bas", "Amsterdam"},
                {"Suède", "Stockholm"},     {"Royaume-Unis", "Londres"},        {"Pologne", "Varsovie"},
                {"Portugal", "Lisbonne"},   {"Luxembourg", "Luxembourg"},       {"Lettonie", "Riga"},
                {"Italie", "Rome"} ,        {"Slovénie", "Ljubljana"} ,         {"Slovaquie", "Bratislava"}};
        return data;
    }

}
