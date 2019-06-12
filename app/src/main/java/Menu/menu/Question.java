package Menu.menu;

public class Question {

    public String mQuestions[]=
            {
                    "Quelle est la capitale de la Roumanie ?",
                    "Quelle est la capitale de l'Hongrie ?",
                    "Quelle est la capitale de la Lituanie ?",
                    "Quelle est la capitale de la Slovaquie ?",
                    "Quelle est la capitale de la Slovénie ?"
            };

    private String mChoices[][]=
            {
                {"Paris", "Bucarest" , "Vienne" , "Lisbonne"},
                {"Paris" , "Bucarest" ,"Budapest"  ,"Zurich"},
                {"Rome" , "Vilnius" ,"Vienne"  ,"Amsterdam"},
                {"Bratislava" , "Bucarest" ,"Budapest" ,"Lisbonne"},
                    {"Bratislava" , "Bucarest" ,"Budapest" ,"Ljubljana"}
            };

    private String correctanswers[]={"Bucarest","Budapest","Vilnius","Bratislava","Ljubljana"};

    public String getQuestion(int a){
        String question = mQuestions[a];
        return question;
    }

    public String getChoice1(int a){
        String choice= mChoices[a][0];
        return choice;
    }

    public String getChoice2(int a){
        String choice= mChoices[a][1];
        return choice;
    }

    public String getChoice3(int a){
        String choice= mChoices[a][2];
        return choice;
    }

    public String getChoice4(int a){
        String choice= mChoices[a][3];
        return choice;
    }

    public String getChoice5(int a){
        String choice= mChoices[a][4];
        return choice;
    }

    public String getCorrectAnswer(int a){
        String answer =  correctanswers[a];
        return answer;
    }
}
