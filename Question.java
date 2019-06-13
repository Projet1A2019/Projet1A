public class Question {

    public String mQuestions[]=
            {
                    "Quelle est la capitale de l'Allemagne ?",
                    "Quelle est la capitale de l'Hongrie ?",
                    "Quelle est la capitale de la Lituanie ?",
                    "Quelle est la capitale de la France ?",
                    "Quelle est la capitale de la Slovénie ?",
                    "Quelle est la capitale de la Belgique ?",
                    "Quelle est la capitale du Portugal ?",
                    "Quelle est la capitale de l'Italie ?",
                    "Quelle est la capitale du Danemark ?",
                    "Quelle est la capitale de l'Autriche ?"
            };

    private String mChoices[][]=
            {
                {"Paris", "Berlin", "Vienne", "Lisbonne"},
                {"Paris", "Bucarest", "Budapest", "Zurich"},
                {"Rome", "Vilnius", "Vienne", "Amsterdam"},
                {"Paris", "Bucarest", "Budapest", "Lisbonne"},
                {"Bratislava", "Bucarest", "Budapest", "Ljubljana"},
                {"Bruxelles", "Paris", "Budapest", "Athènes"},
                {"Bruxelles", "Madrid", "Berlin", "Lisbonne"},
                {"Paris", "Copenhague", "Budapest", "Rome"},
                {"Madrid", "Rome", "Bruxelles", "Copenhague"},
                {"Paris", "Madrid", "Berlin", "Vienne"},
            };

    private String correctanswers[]={"Berlin","Budapest","Vilnius","Paris","Ljubljana","Bruxelles","Lisbonne","Rome","Copenhague","Vienne"};

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
    
        public String getChoice6(int a){
        String choice= mChoices[a][5];
        return choice;
    }
    
        public String getChoice7(int a){
        String choice= mChoices[a][6];
        return choice;
    }
    
        public String getChoice8(int a){
        String choice= mChoices[a][7];
        return choice;
    }
    
        public String getChoice9(int a){
        String choice= mChoices[a][8];
        return choice;
    }
    
        public String getChoice10(int a){
        String choice= mChoices[a][9];
        return choice;
    }
    
        public String getChoice11(int a){
        String choice= mChoices[a][10];
        return choice;
    }

    public String getCorrectAnswer(int a){
        String answer =  correctanswers[a];
        return answer;
    }
}
