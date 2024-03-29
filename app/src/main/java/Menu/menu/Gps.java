package Menu.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;



import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.sceneform.FrameTime;

import java.util.ArrayList;
import java.util.Locale;

public class Gps extends AppCompatActivity {
    private TextView mTextMessage;
    Floor third=new Floor();
    Spinner spinner;
    Spinner category;
    String QRPosition="Jonathan WEBER";
    ArrayList<String> rooms=new ArrayList<>();
    int result;
    TextView editText;
    TextToSpeech toSpeech;
    public static boolean InSearch=false;
    public static ArrayList<String> THEWAY=new ArrayList<String>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("SetTextI18n")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homeActivity = new Intent(Gps.this, MainActivity.class);
                    startActivity(homeActivity);
                    return true;
                case R.id.navigation_dashboard:
                    Intent imageActivity = new Intent(Gps.this, Plan.class);
                    startActivity(imageActivity);
                    return true;
                case R.id.navigation_notifications:
                    return true;
                case R.id.navigation_reality:
                    Intent realityActivity = new Intent(Gps.this, Reality.class);
                    startActivity(realityActivity);
                    return true;

            }
            return false;
        }
    };

    public int countDoors(Room position,Room destination){
        if(position.getArea()==destination.getArea()){
            third.orderByZone();
            int count=1;
            if(Integer.parseInt(position.getZone())<Integer.parseInt(destination.getZone())) {
                int previous=Integer.parseInt(position.getZone());
                for(int i=0;i<third.getFloor().size();i++) {
                    if(third.getFloor().get(i).getArea().equals(destination.getArea()) && third.getFloor().get(i).getPosition().equals(destination.getPosition())){
                        if(Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(position.getZone()) && Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(destination.getZone())){
                            if(Integer.parseInt(third.getFloor().get(i).getZone())!=previous){
                                count+=1;
                                previous=Integer.parseInt(third.getFloor().get(i).getZone());
                            }
                        }
                    }
                }
            }
            else{
                int previous=Integer.parseInt(destination.getZone());
                for(int i=0;i<third.getFloor().size();i++) {
                    if(third.getFloor().get(i).getArea().equals(destination.getArea()) && third.getFloor().get(i).getPosition().equals(destination.getPosition())){
                        if(Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(position.getZone()) && Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(destination.getZone())){
                            if(Integer.parseInt(third.getFloor().get(i).getZone())!=previous){
                                count+=1;
                                previous=Integer.parseInt(third.getFloor().get(i).getZone());
                            }
                        }
                    }
                }
            }
            third.order();
            return count;
        }
        return 0;
    }

    public String getWay(String position,String destination){
        Room pos=third.getRoomByName(position);
        Room dest=third.getRoomByName(destination);
        if(pos.getArea().equals(dest.getArea())){
            if(pos.getArea().equals("couloir central")){
                if(pos.getPosition().equals("nord")){
                    if(pos.getZone().equals(dest.getZone())){
                        return "C'est juste en face de vous.";
                    }
                    else if(Integer.parseInt(pos.getZone())<Integer.parseInt(dest.getZone())) {
                        if (dest.getPosition().equals("est")) {
                            return "Derrière vous, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre gauche.";
                        } else {
                            return "Derrière vous, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre droite.";
                        }
                    }
                }
                else if(pos.getPosition().equals("est")){
                    if(pos.getZone().equals(dest.getZone())){
                        if(dest.getPosition().equals("est")){
                            return "C'est juste en face de vous.";
                        }
                        else{
                            return "C'est juste derrière vous.";
                        }
                    }
                    else if(Integer.parseInt(pos.getZone())<Integer.parseInt(dest.getZone())){
                        if(dest.getPosition().equals("est")){
                            return "Sur votre droite, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre gauche.";
                        }
                        else{
                            return "Sur votre droite, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre droite.";
                        }
                    }
                    else if(Integer.parseInt(pos.getZone())>Integer.parseInt(dest.getZone())){
                        if(dest.getPosition().equals("nord")){
                            return "Sur votre gauche, ce sera au fond du couloir.";
                        }
                        else if(dest.getPosition().equals("est")){
                            return "Sur votre gauche, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre droite.";
                        }
                        else if(dest.getPosition().equals("ouest")){
                            return "Sur votre gauche, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre gauche.";
                        }
                    }
                }
                else if(pos.getPosition().equals("ouest")){
                    if(pos.getZone().equals(dest.getZone())){
                        if(dest.getPosition().equals("ouest")){
                            return "C'est juste en face de vous.";
                        }
                        else{
                            return "C'est juste derrière vous.";
                        }
                    }
                    else if(Integer.parseInt(pos.getZone())<Integer.parseInt(dest.getZone())){
                        if(dest.getPosition().equals("est")){
                            return "Sur votre gauche, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre gauche.";
                        }
                        else{
                            return "Sur votre gauche, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre droite.";
                        }
                    }
                    else if(Integer.parseInt(pos.getZone())>Integer.parseInt(dest.getZone())){
                        if(dest.getPosition().equals("nord")){
                            return "Sur votre droite, ce sera au fond du couloir.";
                        }
                        else if(dest.getPosition().equals("est")){
                            return "Sur votre droite, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre droite.";
                        }
                        else{
                            return "Sur votre droite, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre gauche.";
                        }
                    }
                }
            }
            else{
                if(pos.getPosition().equals("nord")){
                    if(pos.getZone().equals(dest.getZone())){
                        if(dest.getPosition().equals("nord")){
                            return "C'est juste en face de vous.";
                        }
                        else{
                            return "C'est juste derrière vous.";
                        }
                    }
                    else if(Integer.parseInt(pos.getZone())<Integer.parseInt(dest.getZone())){
                        if(dest.getPosition().equals("nord")){
                            return "Sur votre droite, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre gauche.";
                        }
                        else{
                            return "Sur votre droite, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre droite.";
                        }
                    }
                    else if(Integer.parseInt(pos.getZone())>Integer.parseInt(dest.getZone())){
                        if(dest.getPosition().equals("nord")){
                            return "Sur votre gauche, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre droite.";
                        }
                        else{
                            return "Sur votre gauche, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre gauche.";
                        }
                    }
                }
                else{
                    if(pos.getZone().equals(dest.getZone())){
                        if(dest.getPosition().equals("sud")){
                            return "C'est juste en face de vous.";
                        }
                        else{
                            return "C'est juste derrière vous.";
                        }
                    }
                    else if(Integer.parseInt(pos.getZone())<Integer.parseInt(dest.getZone())){
                        if(dest.getPosition().equals("sud")){
                            return "Sur votre gauche, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre droite.";
                        }
                        else{
                            return "Sur votre gauche, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre gauche.";
                        }
                    }
                    else if(Integer.parseInt(pos.getZone())>Integer.parseInt(dest.getZone())){
                        if(dest.getPosition().equals("sud")){
                            return "Sur votre droite, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre gauche.";
                        }
                        else{
                            return "Sur votre droite, dans le couloir, ce sera la porte numéro "+countDoors(pos,dest)+" sur votre droite.";
                        }
                    }
                }
            }
        }
        else{
            if(pos.getArea().equals("couloir central")){
                if(dest.getArea().equals("aile1")){
                    if(Integer.parseInt(pos.getZone())==11){
                        return "Retournez-vous.\n"+getWay("0",dest.getName());
                    }
                    else{
                        return getWay(pos.getName(),"E 32")+"\nFaites demi-tour vers l'aile 1.\n"+getWay("0",dest.getName());
                    }
                }
                else{
                    if(Integer.parseInt(pos.getZone())==16){
                        return "Retournez-vous.\n"+getWay("IARISS",dest.getName());
                    }
                    else{
                        return getWay(pos.getName(),"E 37")+"\nFaites demi-tour vers l'aile 1.\n"+getWay("IARISS",dest.getName());
                    }
                }
            }
            else if(pos.getArea().equals("aile1")){
                if(dest.getArea().equals("aile2")){
                    return "Allez jusqu'au couloir central, puis tournez à gauche jusqu'à l'aile 2.\n"+getWay("IARISS",dest.getName());
                }
                else{
                    return "Rejoignez le couloir central.\n"+getWay("E 32",dest.getName());
                }
            }
            else if(pos.getArea().equals("aile2")){
                if(dest.getArea().equals("aile1")){
                    return "Allez jusqu'au couloir central, puis tournez à droite jusqu'à l'aile 1.\n"+getWay("0",dest.getName());
                }
                else{
                    return "Rejoignez le couloir central.\n"+getWay("E 37",dest.getName());
                }
            }
        }
        return "Erreur.";
    }

    public void setRoomsList(ArrayList<String> rooms){
        this.rooms=rooms;
    }

    public void refresh(){
        ArrayAdapter Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,rooms);
        spinner.setAdapter(Adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //Toast.makeText(Gps.this,Reality.name,Toast.LENGTH_SHORT);
        if(!Reality.name.isEmpty()){
            QRPosition=Reality.name;
        }
        final ArrayList<String> categories=new ArrayList<>();
        categories.add("Tout");
        categories.add("Salle");
        categories.add("Enseignant");
        categories.add("IARISS");
        categories.add("MIAGE");
        categories.add("MIAM");
        categories.add("LSI");
        categories.add("Sanitaires");
        categories.add("Service");
        categories.add("Pause");
        TextView target = (TextView)findViewById(R.id.target);
        TextView position = (TextView)findViewById(R.id.position);
        TextView way = (TextView)findViewById(R.id.way);

        third.add(new Room("SALLE DU CONSEIL","salleduconseil","3.3","couloir central","1","nord","Salle"));
        third.add(new Room("VESTIAIRE","vestiaires","3.4","couloir central","2","ouest","Sanitaires"));
        third.add(new Room("TOILETTES","toilettes","3.5","couloir central","3","ouest","Sanitaires"));
        third.add(new Room("ANNEXE SANITAIRE","annexesanitaire","3.8","couloir central","4","ouest","Service"));
        third.add(new Room("TOILETTES HANDICAPES","toiletteshandicapes","3.9","couloir central","5","ouest","Sanitaires"));
        third.add(new Room("E 30","e30","3.12","couloir central","6","ouest","Salle"));
        third.add(new Room("E 31","e31","3.13","couloir central","7","ouest","Salle"));
        third.add(new Room("DISTRIBUTEURS DE BOISSONS","distributeursdeboissons","3.98","couloir central","8","est","Pause"));
        third.add(new Room("TOILETTES HOMMES","toiletteshommes","3.95","couloir central","9","est","Sanitaires"));
        third.add(new Room("TOILETTES FEMMES","toilettesfemmes","3.94","couloir central","10","est","Sanitaires"));
        third.add(new Room("E 32","e32","3.19","couloir central","11","ouest","Salle"));
        third.add(new Room("E 33","e33","3.20","couloir central","12","ouest","Salle"));
        third.add(new Room("E 34","e34","3.22","couloir central","13","ouest","Salle"));
        third.add(new Room("Patricia BONTE","patriciabontesecretariatmiage","3.24","couloir central","14","ouest","Enseignant"));
        third.add(new Room("SECRETARIAT MIAGE","patriciabontesecretariatmiage","3.24","couloir central","14","ouest","MIAGE"));
        third.add(new Room("E 36-MEF","e36mef","3.25","couloir central","15","ouest","Salle"));
        third.add(new Room("E 37","e37salleinfomiage1","3.26","couloir central","16","ouest","Salle"));
        third.add(new Room("SALLE INFO MIAGE 1","e37salleinfomiage1","3.26","couloir central","16","ouest","MIAGE"));
        third.add(new Room("E 37 bis","e37bis","3.29","couloir central","17","ouest","Salle"));
        third.add(new Room("E 38","e38salleinfomiage2","3.30","couloir central","18","ouest","Salle"));
        third.add(new Room("SALLE INFO MIAGE 2","e38salleinfomiage2","3.30","couloir central","18","ouest","MIAGE"));

        third.add(new Room("0","0","0","aile1","0","nord",""));
        third.add(new Room("BUREAU CHERCHEURS MIAM 2","bureauchercheursmiam2","3.92","aile1","2","nord","MIAM"));
        third.add(new Room("Jean-Philippe LAUFFENBURGER","jeanphilippelauffenburger","3.91","aile1","4","nord","Enseignant"));
        third.add(new Room("Gérard BINDER","gerardbinder","3.90","aile1","5","nord","Enseignant"));
        third.add(new Room("Pierre AMBS","pierreambs","3.89","aile1","7","nord","Enseignant"));
        third.add(new Room("Rodolfo ORJUELA","rodolfoorjuela","3.87","aile1","8","nord","Enseignant"));
        third.add(new Room("Michel BASSET","michelbasset","3.86","aile1","10","nord","Enseignant"));
        third.add(new Room("Evelyne AUBRY","evelyneaubry","3.85","aile1","11","nord","Enseignant"));
        third.add(new Room("BUREAU CHERCHEURS MIAM 1","bureauchercheursmiam1","3.83","aile1","11","sud","MIAM"));
        third.add(new Room("Jonathan LEDY","jonathanledy","3.81","aile1","9","sud","Enseignant"));
        third.add(new Room("Thomas LAURAIN","thomaslaurain","3.80","aile1","8","sud","Enseignant"));
        third.add(new Room("Raphael DUPUIS","raphaeldupuis","3.76","aile1","7","sud","Enseignant"));
        third.add(new Room("Thomas WEISSER","thomasweisser","3.75","aile1","6","sud","Enseignant"));
        third.add(new Room("Abderazik BIROUCHE","abderazikbirouchebenjaminmourllion","3.74","aile1","5","sud","Enseignant"));
        third.add(new Room("Benjamin MOURLLION","abderazikbirouchebenjaminmourllion","3.74","aile1","5","sud","Enseignant"));
        third.add(new Room("TABLEAU SECTORIEL 1","tableausectoriel1","3.70","aile1","3","sud","Service"));
        third.add(new Room("BUREAU CHERCHEURS MIAM 3","bureauchercheursmiam3","3.67","aile1","1","sud","MIAM"));

        third.add(new Room("IARISS","iariss","3.32","aile2","1","sud","IARISS"));
        third.add(new Room("LABORATOIRE LSI","laboratoirelsi","3.35","aile2","3","sud","LSI"));
        third.add(new Room("Jean-Marc PERRONNE","jeanmarcperronne","3.36","aile2","5","sud","Enseignant"));
        third.add(new Room("Souhir BEN SOUISSI","souhirbensouissi","3.37","aile2","6","sud","Enseignant"));
        third.add(new Room("Philippe STUDER","philippestuder","3.38","aile2","7","sud","Enseignant"));
        third.add(new Room("Frédéric FONDEMENT","fredericfondement","3.39","aile2","8","sud","Enseignant"));
        third.add(new Room("Jonathan WEBER","jonathanweber","3.43","aile2","9","sud","Enseignant"));
        third.add(new Room("Germain FORESTIER","germainforestier","3.44","aile2","10","sud","Enseignant"));
        third.add(new Room("BUREAU CHERCHEURS LSI","bureauchercheurslsi","3.45","aile2","11","sud","LSI"));
        third.add(new Room("LSI","lsi","3.48","aile2","11","nord","LSI"));
        third.add(new Room("PROFESSEUR INIVTE","professeurinvite","3.49","aile2","10","nord","Enseignant"));
        third.add(new Room("Gilbert PINOT","gilbertpinot","3.50","aile2","9","nord","Enseignant"));
        third.add(new Room("Pierre-Alain MULLER","pierrealainmuller","3.52","aile2","8","nord","Enseignant"));
        third.add(new Room("Laurent THIRY","laurentthiry","3.53","aile2","7","nord","Enseignant"));
        third.add(new Room("Michel HASSENFORDER","michelhassenforder","3.54","aile2","6","nord","Enseignant"));
        third.add(new Room("TABLEAU SECTORIEL 2","tableausectoriel2","3.58","aile2","4","nord","Service"));
        third.add(new Room("TP RESEAUX","salletpreseaux","3.60","aile2","2","nord","Salle"));
        third.add(new Room("CANAPES","canapesjeux","3.32","aile2","1","nord","Pause"));
        third.add(new Room("JEUX","canapesjeux","3.32","aile2","1","nord","Pause"));

        third.order();

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> rooms=new ArrayList<>();
        for (int j = 0; j < third.getFloor().size(); j++) {
            if(!third.getFloor().get(j).getName().equals("0")) {
                rooms.add(third.getFloor().get(j).getName());
            }
        }
        ArrayAdapter aAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,rooms);
        spinner.setAdapter(aAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                TextView target = (TextView)findViewById(R.id.target);
                target.setText("Destination: "+spinner.getSelectedItem().toString());

                TextView way = (TextView)findViewById(R.id.way);
                way.setText(getWay(QRPosition,spinner.getSelectedItem().toString()));

                ArrayList<String> theWay=wayByRoom(QRPosition,spinner.getSelectedItem().toString());
                THEWAY=theWay;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        category = (Spinner) findViewById(R.id.category);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                ArrayList<String> rooms=new ArrayList<>();
                switch(i){
                    case 0:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if(!third.getFloor().get(j).getName().equals("0")) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;
                    case 1:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if(!third.getFloor().get(j).getName().equals("0") && third.getFloor().get(j).getCategory().equals(categories.get(1))) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;
                    case 2:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if(!third.getFloor().get(j).getName().equals("0") && third.getFloor().get(j).getCategory().equals(categories.get(2))) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;
                    case 3:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if(!third.getFloor().get(j).getName().equals("0") && third.getFloor().get(j).getCategory().equals(categories.get(3))) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;
                    case 4:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if(!third.getFloor().get(j).getName().equals("0") && third.getFloor().get(j).getCategory().equals(categories.get(4))) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;
                    case 5:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if(!third.getFloor().get(j).getName().equals("0") && third.getFloor().get(j).getCategory().equals(categories.get(5))) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;
                    case 6:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if(!third.getFloor().get(j).getName().equals("0") && third.getFloor().get(j).getCategory().equals(categories.get(6))) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;
                    case 7:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if(!third.getFloor().get(j).getName().equals("0") && third.getFloor().get(j).getCategory().equals(categories.get(7))) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;
                    case 8:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if(!third.getFloor().get(j).getName().equals("0") && third.getFloor().get(j).getCategory().equals(categories.get(8))) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;
                    case 9:
                        rooms=new ArrayList<>();
                        for (int j = 0; j < third.getFloor().size(); j++) {
                            if (!third.getFloor().get(j).getName().equals("0") && third.getFloor().get(j).getCategory().equals(categories.get(9))) {
                                rooms.add(third.getFloor().get(j).getName());
                            }
                        }
                        break;                }
                setRoomsList(rooms);
                refresh();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
                ArrayList<String> rooms=new ArrayList<>();
                for (int j = 0; j < third.getFloor().size(); j++) {
                    if(!third.getFloor().get(j).getName().equals("0")) {
                        rooms.add(third.getFloor().get(j).getName());
                    }
                }
            }
        });

        target = (TextView)findViewById(R.id.target);
        target.setText("Destination: "+spinner.getSelectedItem().toString());

        position = (TextView)findViewById(R.id.position);
        position.setText("Dernière position: "+QRPosition);

        way = (TextView)findViewById(R.id.way);
        way.setText(getWay(QRPosition,spinner.getSelectedItem().toString()));

        editText = findViewById(R.id.way);
        toSpeech = new TextToSpeech(Gps.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = toSpeech.setLanguage(Locale.FRANCE);
                } else {
                    Toast.makeText(getApplicationContext(), "Cette langue n'est pas supportée par votre appareil", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View View){
                Intent positionActivity = new Intent(Gps.this, Reality.class);
                startActivity(positionActivity);
                InSearch=true;
            }
        });

        ArrayList<String> theWay=wayByRoom(QRPosition,spinner.getSelectedItem().toString());
        THEWAY=theWay;
    }

    public ArrayList<String> wayByRoom(String pos, String dest){
        Room position=third.getRoomByName(pos);
        Room destination=third.getRoomByName(dest);
        ArrayList<String> way=new ArrayList<>();
        way.add(position.getID());
        third.orderByZone();
        if(position.getArea()==destination.getArea()){
            for(int i=0;i<third.getFloor().size();i++) {
                if(third.getFloor().get(i).getArea().equals(destination.getArea())){
                    if(Integer.parseInt(position.getZone())<Integer.parseInt(destination.getZone())){
                        if(Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(position.getZone()) && Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(destination.getZone())){
                            way.add(third.getFloor().get(i).getID());
                        }
                    }
                    else{
                        if(Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(position.getZone()) && Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(destination.getZone())){
                            way.add(third.getFloor().get(i).getID());
                        }
                    }
                }
            }
        }
        else{
            if(position.getArea().equals("couloir central")){
                if(destination.getArea().equals("aile1")){
                    if(Integer.parseInt(position.getZone())<11){
                        for(int i=0;i<third.getFloor().size();i++) {
                            if(third.getFloor().get(i).getArea().equals("couloir central")){
                                if(Integer.parseInt(third.getFloor().get(i).getZone())<=11 && Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(position.getZone())){
                                    way.add(third.getFloor().get(i).getID());
                                }
                            }
                        }
                    }
                    else if(Integer.parseInt(position.getZone())>11){
                        for(int i=0;i<third.getFloor().size();i++) {
                            if(third.getFloor().get(i).getArea().equals("couloir central")){
                                if(Integer.parseInt(third.getFloor().get(i).getZone())>=11 && Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(position.getZone())){
                                    way.add(third.getFloor().get(i).getID());
                                }
                            }
                        }
                    }
                    for(int i=0;i<third.getFloor().size();i++) {
                        if(third.getFloor().get(i).getArea().equals("aile1")){
                            if(Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(destination.getZone())){
                                way.add(third.getFloor().get(i).getID());
                            }
                        }
                    }
                }
                else if(destination.getArea().equals("aile2")){
                    if(Integer.parseInt(position.getZone())<17){
                        for(int i=0;i<third.getFloor().size();i++) {
                            if(third.getFloor().get(i).getArea().equals("couloir central")){
                                if(Integer.parseInt(third.getFloor().get(i).getZone())<=16 && Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(position.getZone())){
                                    way.add(third.getFloor().get(i).getID());
                                }
                            }
                        }
                    }
                    else if(Integer.parseInt(position.getZone())>17){
                        for(int i=0;i<third.getFloor().size();i++) {
                            if(third.getFloor().get(i).getArea().equals("couloir central")){
                                if(Integer.parseInt(third.getFloor().get(i).getZone())>=17 && Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(position.getZone())){
                                    way.add(third.getFloor().get(i).getID());
                                }
                            }
                        }
                    }
                    for(int i=0;i<third.getFloor().size();i++) {
                        if(third.getFloor().get(i).getArea().equals("aile2")){
                            if(Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(destination.getZone())){
                                way.add(third.getFloor().get(i).getID());
                            }
                        }
                    }
                }
            }
            else{
                for(int i=0;i<third.getFloor().size();i++) {
                    if(third.getFloor().get(i).getArea().equals(position.getArea())){
                        if(Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(position.getZone())){
                            way.add(third.getFloor().get(i).getID());
                        }
                    }
                }
                if(destination.getArea().equals("couloir central")){
                    if(position.getArea().equals("aile1")){
                        if(Integer.parseInt(destination.getZone())<11){
                            for(int i=0;i<third.getFloor().size();i++) {
                                if(third.getFloor().get(i).getArea().equals("couloir central")){
                                    if(Integer.parseInt(third.getFloor().get(i).getZone())<=11 && Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(destination.getZone())){
                                        way.add(third.getFloor().get(i).getID());
                                    }
                                }
                            }
                        }
                        else if(Integer.parseInt(destination.getZone())>11){
                            for(int i=0;i<third.getFloor().size();i++) {
                                if(third.getFloor().get(i).getArea().equals("couloir central")){
                                    if(Integer.parseInt(third.getFloor().get(i).getZone())>=11 && Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(destination.getZone())){
                                        way.add(third.getFloor().get(i).getID());
                                    }
                                }
                            }
                        }
                    }
                    else if(position.getArea().equals("aile2")){
                        if(Integer.parseInt(destination.getZone())<17){
                            for(int i=0;i<third.getFloor().size();i++) {
                                if(third.getFloor().get(i).getArea().equals("couloir central")){
                                    if(Integer.parseInt(third.getFloor().get(i).getZone())<=16 && Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(destination.getZone())){
                                        way.add(third.getFloor().get(i).getID());
                                    }
                                }
                            }
                        }
                        else if(Integer.parseInt(destination.getZone())>17){
                            for(int i=0;i<third.getFloor().size();i++) {
                                if(third.getFloor().get(i).getArea().equals("couloir central")){
                                    if(Integer.parseInt(third.getFloor().get(i).getZone())>=17 && Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(destination.getZone())){
                                        way.add(third.getFloor().get(i).getID());
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    for(int i=0;i<third.getFloor().size();i++) {
                        if(third.getFloor().get(i).getArea().equals("couloir central")){
                            if(Integer.parseInt(third.getFloor().get(i).getZone())>=11 && Integer.parseInt(third.getFloor().get(i).getZone())<=16){
                                way.add(third.getFloor().get(i).getID());
                            }
                        }
                    }
                    if(destination.getArea().equals("aile1")){
                        for(int i=0;i<third.getFloor().size();i++) {
                            if(third.getFloor().get(i).getArea().equals("aile1")){
                                if(Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(destination.getZone())){
                                    way.add(third.getFloor().get(i).getID());
                                }
                            }
                        }
                    }
                    else{
                        for(int i=0;i<third.getFloor().size();i++) {
                            if(third.getFloor().get(i).getArea().equals("aile2")){
                                if(Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(destination.getZone())){
                                    way.add(third.getFloor().get(i).getID());
                                }
                            }
                        }
                    }
                }
            }
        }
        third.order();
        way.add(destination.getID());
        return way;
    }

    public void TTS(View view)
    {
        switch (view.getId()){
            case R.id.bspeak:
                if (result== TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(getApplicationContext(),"Cette langue n'est pas supportée par votre appareil",Toast.LENGTH_SHORT).show();
                }
                else{
                    String text = editText.getText().toString();
                    toSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                }
                break;
            case   R.id.bstop:
                if(toSpeech!=null){
                    toSpeech.stop();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(toSpeech!=null){
            toSpeech.stop();
            toSpeech.shutdown();
        }
    }
}
