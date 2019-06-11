package com.example.ensilost;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    Floor third=new Floor();
    Spinner spinner;
    String QRPosition="Jonathan WEBER";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    public int countDoors(Room position,Room destination){
        if(position.getArea()==destination.getArea()){
            third.orderByZone();
            int count=1;
            if(position.getZone().compareTo(destination.getZone())<0) {
                String previous=position.getZone();
                for(int i=0;i<third.getFloor().size();i++) {
                    if(third.getFloor().get(i).getArea().equals(destination.getArea()) && third.getFloor().get(i).getPosition().equals(destination.getPosition())){
                        if(Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(position.getZone()) && Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(destination.getZone())){
                            if(!third.getFloor().get(i).getZone().equals(previous)){
                                count+=1;
                                previous=third.getFloor().get(i).getZone();
                            }
                        }
                    }
                }
            }
            else{
                for(int i=0;i<third.getFloor().size();i++) {
                    String previous=destination.getZone();
                    if(third.getFloor().get(i).getArea().equals(destination.getArea()) && third.getFloor().get(i).getPosition().equals(destination.getPosition())){
                        if(Integer.parseInt(third.getFloor().get(i).getZone())<Integer.parseInt(position.getZone()) && Integer.parseInt(third.getFloor().get(i).getZone())>Integer.parseInt(destination.getZone())){
                            if(!third.getFloor().get(i).getZone().equals(previous)){
                                count+=1;
                                previous=third.getFloor().get(i).getZone();
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
                    else if(pos.getZone().compareTo(dest.getZone())<0) {
                        if (dest.getPosition().equals("est")) {
                            return "Derrière vous, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre gauche.";
                        } else {
                            return "Derrière vous, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre droite.";
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
                    else if(pos.getZone().compareTo(dest.getZone())<0){
                        if(dest.getPosition().equals("est")){
                            return "Sur votre droite, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre gauche.";
                        }
                        else{
                            return "Sur votre droite, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre droite.";
                        }
                    }
                    else if(pos.getZone().compareTo(dest.getZone())>0){
                        if(dest.getPosition().equals("nord")){
                            return "Sur votre gauche, ce sera au fond du couloir.";
                        }
                        else if(dest.getPosition().equals("est")){
                            return "Sur votre gauche, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre droite.";
                        }
                        else if(dest.getPosition().equals("ouest")){
                            return "Sur votre gauche, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre gauche.";
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
                    else if(pos.getZone().compareTo(dest.getZone())<0){
                        if(dest.getPosition().equals("est")){
                            return "Sur votre gauche, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre gauche.";
                        }
                        else{
                            return "Sur votre gauche, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre droite.";
                        }
                    }
                    else if(pos.getZone().compareTo(dest.getZone())>0){
                        if(dest.getPosition().equals("est")){
                            return "Sur votre droite, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre droite.";
                        }
                        else{
                            return "Sur votre droite, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre gauche.";
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
                    else if(pos.getZone().compareTo(dest.getZone())<0){
                        if(dest.getPosition().equals("nord")){
                            return "Sur votre droite, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre gauche.";
                        }
                        else{
                            return "Sur votre droite, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre droite.";
                        }
                    }
                    else if(pos.getZone().compareTo(dest.getZone())>0){
                        if(dest.getPosition().equals("nord")){
                            return "Sur votre gauche, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre droite.";
                        }
                        else{
                            return "Sur votre gauche, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre gauche.";
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
                    else if(pos.getZone().compareTo(dest.getZone())<0){
                        if(dest.getPosition().equals("sud")){
                            return "Sur votre gauche, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre droite.";
                        }
                        else{
                            return "Sur votre gauche, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre gauche.";
                        }
                    }
                    else if(pos.getZone().compareTo(dest.getZone())>0){
                        if(dest.getPosition().equals("sud")){
                            return "Sur votre droite, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre gauche.";
                        }
                        else{
                            return "Sur votre droite, dans le couloir, ce sera la "+countDoors(pos,dest)+"° porte sur votre droite.";
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
                    else if(Integer.parseInt(pos.getZone())<11){
                        return getWay(pos.getName(),"E 32")+"\nTournez à gauche vers l'aile 1.\n"+getWay("0",dest.getName());
                    }
                    else if(Integer.parseInt(pos.getZone())>11){
                        return getWay(pos.getName(),"E 32")+"\nTournez à droite vers l'aile 1.\n"+getWay("0",dest.getName());
                    }
                }
                else{
                    if(Integer.parseInt(pos.getZone())==16){
                        return "Retournez-vous.\n"+getWay("IARISS",dest.getName());
                    }
                    else if(Integer.parseInt(pos.getZone())<16){
                        return getWay(pos.getName(),"E 37")+"\nTournez à gauche vers l'aile 1.\n"+getWay("IARISS",dest.getName());
                    }
                    else if(Integer.parseInt(pos.getZone())>16){
                        return getWay(pos.getName(),"E 37")+"\nTournez à droite vers l'aile 1.\n"+getWay("IARISS",dest.getName());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        third.add(new Room("SALLE DU CONSEIL","salleduconseil","3.3","couloir central","1","nord","Salle"));
        third.add(new Room("VESTIAIRE","vestiaire","3.4","couloir central","2","ouest","Sanitaires"));
        third.add(new Room("TOILETTES","toilettes","3.5","couloir central","3","ouest","Sanitaires"));
        third.add(new Room("ANNEXE SANITAIRE","annexesanitaire","3.8","couloir central","4","ouest","Sanitaires"));
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
        third.add(new Room("E 37","e37salleinfomiage","3.26","couloir central","16","ouest","Salle"));
        third.add(new Room("SALLE INFO MIAGE 1","e37salleinfomiage1","3.26","couloir central","16","ouest","MIAGE"));
        third.add(new Room("E 37 bis","e37bis","3.29","couloir central","17","ouest","Salle"));
        third.add(new Room("E 38","e38salleinfomiage","3.30","couloir central","18","ouest","Salle"));
        third.add(new Room("SALLE INFO MIAGE 2","e38salleinfomiage2","3.30","couloir central","18","ouest","MIAGE"));

        third.add(new Room("0","0","0","aile1","0","nord",""));
        third.add(new Room("BUREAU CHERCHEURS MIAM 2","bureauchercheursmiam2","3.92","aile1","2","nord","MIAM"));
        third.add(new Room("Jean-Philippe LAUFFENBURGER","jeanphilippelauffenburger","3.91","aile1","4","nord","Enseignant"));
        third.add(new Room("Gérard BINDER","gerardbinder","3.90","aile1","5","nord","Enseignant"));
        third.add(new Room("Pierre AMBS","pierreambs","3.89","aile1","7","nord","Enseignant"));
        third.add(new Room("Rodolfo ORJUELA","rodolfoorjuela","3.87","aile1","8","nord","Enseignant"));
        third.add(new Room("Michel BASSET","michelbasset","3.86","aile1","10","nord","Enseignant"));
        third.add(new Room("Evelyne AUBRY","evelyneaubry","3.85","aile1","12","nord","Enseignant"));
        third.add(new Room("BUREAU CHERCHEURS MIAM 1","bureauchercheursmiam1","3.83","aile1","11","sud","MIAM"));
        third.add(new Room("Jonathan LEDY","jonathanledy","3.81","aile1","9","sud","Enseignant"));
        third.add(new Room("Thomas LAURAIN","thomaslaurain","3.80","aile1","8","sud","Enseignant"));
        third.add(new Room("Raphael DUPUIS","raphaeldupuis","3.76","aile1","7","sud","Enseignant"));
        third.add(new Room("Thomas WEISSER","thomasweisser","3.75","aile1","6","sud","Enseignant"));
        third.add(new Room("Abderazik BIROUCHE","abderazikbirouchebenjaminmourillon","3.74","aile1","5","sud","Enseignant"));
        third.add(new Room("Benjamin MOURLLION","abderazikbirouchebenjaminmourillon","3.74","aile1","5","sud","Enseignant"));
        third.add(new Room("TABLEAU SECTORIEL 1","tableausectoriel","3.70","aile1","3","sud","Service"));
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
        third.add(new Room("TABLEAU SECTORIEL 2","tableausectoriel","3.58","aile2","4","nord","Service"));
        third.add(new Room("TP RESEAUX","tpreseaux","3.60","aile2","2","nord","Salle"));
        third.add(new Room("CANAPES","canapesjeux","3.32","aile2","1","nord","Pause"));
        third.add(new Room("JEUX","canapesjeux","3.32","aile2","1","nord","Pause"));

        third.order();

        for (int i = 0; i < third.getFloor().size(); i++) {
            third.getFloor().get(i).print();
        }

        spinner = (Spinner) findViewById(R.id.spinner);
        List rooms = new ArrayList();
        for (int i = 0; i < third.getFloor().size(); i++) {
            if(!third.getFloor().get(i).getName().equals("0")) {
                rooms.add(third.getFloor().get(i).getName());
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView target = (TextView)findViewById(R.id.target);
        target.setText("Destination: "+spinner.getSelectedItem().toString());

        TextView position = (TextView)findViewById(R.id.position);
        position.setText("Dernière position: "+QRPosition);

        TextView area = (TextView)findViewById(R.id.way);
        area.setText(getWay(QRPosition,spinner.getSelectedItem().toString()));

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView target = (TextView)findViewById(R.id.target);
                target.setText("Destination: "+spinner.getSelectedItem().toString());

                TextView area = (TextView)findViewById(R.id.way);
                area.setText(getWay(QRPosition,spinner.getSelectedItem().toString()));
            }
        });
    }
}
