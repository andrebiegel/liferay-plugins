package io.github.andrebiegel.entity;
public class Drink {
    private String name;
    public Drink (String name){
        this.name = name;
    }
    
    /**
     * default contructor for jackson
     */
    public Drink(){}
  
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
