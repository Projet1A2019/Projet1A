package Menu.menu;

public class Question {

    public String mQuestions[]=
            {
                    "Quelle est la capitale de la Roumanie ?",
                    "Quelle est la capitale de l'Hongrie ?",
                    "Quelle est la capitale de la Lituanie ?",
                    "Quelle est la capitale de la Slovaquie ?",
                    "Quelle est la capitale de la Slovénie ?",
                    "Quelle est la capitale de la France ?",
                    "Quelle est la capitale de la Allemagne ?",
                    "Quelle est la capitale de l'Italie ?",
                    "Quelle est la capitale de la Suisse ?",
                    "Quelle est la capitale de l'Espagne ?",
                    "Qui est le meilleur prof ?"
            };

    private String mChoices[][]=
            {
                {"Rome", "Bucarest" , "Vienne" , "Lisbonne"},
                {"Paris" , "Bucarest" ,"Budapest"  ,"Zurich"},
                {"Rome" , "Vilnius" ,"Vienne"  ,"Amsterdam"},
                {"Bratislava" , "Bucarest" ,"Budapest" ,"Lisbonne"},
                    {"Bratislava" , "Bucarest" ,"Budapest" ,"Ljubljana"},
                    {"Rome" , "Mulhouse" ,"Paris" ,"Barcelone"},
                    {"Berlin" , "Vienne" ,"Munich" ,"Bâle"},
                    {"Rome" , "Milan" ,"Venise" ,"Le Vatican"},
                    {"Berne" , "Lausanne" ,"Bâle" ,"Zurich"},
                    {"Andorre" , "Barcelone" ,"Lisbonne" ,"Madrid"},
                    {"M. WEBER","M. WEBER","M. WEBER","M. WEBER"}
            };

    private String correctanswers[]={"Bucarest","Budapest","Vilnius","Bratislava","Ljubljana","Paris","Berlin","Rome","Berne","Madrid","M. WEBER"};

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

    public String getCorrectAnswer(int a){
        String answer =  correctanswers[a];
        return answer;
    }
}
