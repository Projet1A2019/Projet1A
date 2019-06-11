package com.example.ensilost;

import java.util.ArrayList;
import java.util.Collections;

public class Floor {
    ArrayList<Room> floor;

    public Floor(){
        this.floor=new ArrayList<>();
    }

    public void add(Room r){
        this.floor.add(r);
    }

    public ArrayList<Room> getFloor(){
        return this.floor;
    }

    public void order(){
        for(int step=1;step<=this.floor.size()-1;step++){
            int first=step-1;
            int smallest=smallest(first,this.floor.size()-1);
            swap(first,smallest);
        }
    }

    public void orderByZone(){
        for(int step=1;step<=this.floor.size()-1;step++){
            int first=step-1;
            int smallest=smallestByZone(first,this.floor.size()-1);
            swap(first,smallest);
        }
    }

    public int smallest(int min,int max){
        int result=min;
        for(int i=min+1;i<=max;i++){
            if(floor.get(result).compareTo(floor.get(i))>0){
                result=i;
            }
        }
        return result;
    }

    public int smallestByZone(int min,int max){
        int result=min;
        for(int i=min+1;i<=max;i++){
            if(floor.get(result).compareByZone(floor.get(i))>0){
                result=i;
            }
        }
        return result;
    }

    public void swap(int i,int j){
        Collections.swap(this.floor,i,j);
    }

    public Room getRoomByName(String name){
        for(int i=0;i<=floor.size();i++){
            if(floor.get(i).getName().equals(name)){
                return floor.get(i);
            }
        }
        Room room=new Room("","","","","","","");
        return room;
    }
}
