package Jeu;

public class Question {
    private String text;
    private String reponse;

    public Question(String text, String reponse) {
        this.text = text;
        this.reponse = reponse;
    }

    public String getText() {
        return text;
    }

    public String getReponse() {
        return reponse;
    }
}
