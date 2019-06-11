package Menu.menu;

import java.util.ArrayList;

public class Room implements Comparable<Room>{
    ArrayList<String> room;

    public Room(String name,String id,String number,String area,String zone,String position,String category){
        this.room=new ArrayList<>();
        this.room.add(name);
        this.room.add(id);
        this.room.add(number);
        this.room.add(area);
        this.room.add(zone);
        this.room.add(position);
        this.room.add(category);
    }

    public ArrayList<String> getRoom(){
        return this.room;
    }

    public int compareTo(Room r){
        return this.getName().compareTo(r.getName());
    }

    public int compareByZone(Room r){
        return this.getZone().compareTo(r.getZone());
    }

    public String getName(){
        return this.room.get(0);
    }

    public String getID(){
        return this.room.get(1);
    }

    public String getNumber(){
        return this.room.get(2);
    }

    public String getArea(){
        return this.room.get(3);
    }

    public String getZone(){
        return this.room.get(4);
    }

    public String getPosition(){
        return this.room.get(5);
    }

    public String getCategory(){
        return this.room.get(6);
    }

    public String toString(){
        return "Name: "+this.getName()+"\nID: "+this.getID()+"\nNumber: "+this.getNumber()+"\nArea: "+this.getArea()+"\nZone: "+this.getZone()+"\nPosition: "+this.getPosition()+"\nCategory: "+this.getCategory();
    }

    public void print(){
        System.out.println(this.toString());
    }
}