package pets.tiere;

import java.util.ArrayList;

public class Pets {
    private ArrayList<Animal> cats = new ArrayList<>();
    public static ArrayList<Animal> dogs = new ArrayList<>();
    public static ArrayList<Animal> birds = new ArrayList<>();
    public static ArrayList<Animal> rabbit = new ArrayList<>();
    public static ArrayList<Animal> hamster = new ArrayList<>();
    public static ArrayList<Animal> dragon = new ArrayList<>();
    public static ArrayList<Animal> special = new ArrayList<>();

    public Pets(){
        cats.add(new Animal("https://sensivity.team/bot/pets/cats/1.png", 4000, "Cat 1", 1));
        cats.add(new Animal("https://sensivity.team/bot/pets/cats/2.png", 4000, "Cat 2", 2));
        cats.add(new Animal("https://sensivity.team/bot/pets/cats/3.png", 4000, "Cat 3", 3));
        cats.add(new Animal("https://sensivity.team/bot/pets/cats/4.png", 4000, "Cat 4", 4));

        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/1.png", 5000, "Corgi", 5));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/2.png", 5000, "Dackel", 6));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/3.png", 5000, "Golden Retriever", 7));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/4.png", 5000, "", 8));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/5.png", 5000, "", 9));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/6.png", 5000, "", 10));

        birds.add(new Animal("https://sensivity.team/bot/pets/birds/1.png", 1000, "", 11));
        birds.add(new Animal("https://sensivity.team/bot/pets/birds/2.png", 1000, "", 12));

        rabbit.add(new Animal("https://sensivity.team/bot/pets/rabbits/1.png", 2000, "", 13));
        rabbit.add(new Animal("https://sensivity.team/bot/pets/rabbits/2.png", 2000, "", 14));

        hamster.add(new Animal("https://sensivity.team/bot/pets/hamster/1.png", 2000, "", 15));
        hamster.add(new Animal("https://sensivity.team/bot/pets/hamster/2.png", 2000, "", 16));

        dragon.add(new Animal("https://sensivity.team/bot/pets/dragon/1.png", 8000, "", 17));
        dragon.add(new Animal("https://sensivity.team/bot/pets/dragon/2.png", 8000, "", 18));

        special.add(new Animal("https://sensivity.team/bot/pets/special/1.png", 10000, "Raccoonie", 19));
    }

    public ArrayList<Animal> getAnimal(String name){
        if(name.equals("cat")){
            return cats;
        }else if(name.equals("dogs")){
            return dogs;
        }else if(name.equals("bird")){
            return birds;
        }else if(name.equals("rabbit")){
            return rabbit;
        }else if(name.equals("hamster")){
            return hamster;
        }else if(name.equals("dragon")){
            return dragon;
        }else {
            return special;
        }
    }

    public Animal getAnimalByID(int id){
        for (Animal a: cats) {
            if(a.getId() == id){
                return a;
            }
        }

        for (Animal a: dogs) {
            if(a.getId() == id){
                return a;
            }
        }

        for (Animal a: birds) {
            if(a.getId() == id){
                return a;
            }
        }

        for (Animal a: rabbit) {
            if(a.getId() == id){
                return a;
            }
        }

        for (Animal a: hamster) {
            if(a.getId() == id){
                return a;
            }
        }

        for (Animal a: special) {
            if(a.getId() == id){
                return a;
            }
        }

        for (Animal a: dragon) {
            if(a.getId() == id){
                return a;
            }
        }

        return null;
    }
}
