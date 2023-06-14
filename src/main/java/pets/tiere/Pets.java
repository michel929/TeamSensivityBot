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
        cats.add(new Animal("https://sensivity.team/bot/pets/cats/1.png", 4000, "Calico", 1, 1));
        cats.add(new Animal("https://sensivity.team/bot/pets/cats/2.png", 4000, "Tuxedo cat", 2,2));
        cats.add(new Animal("https://sensivity.team/bot/pets/cats/3.png", 4000, "Cat 3", 3, 3));
        cats.add(new Animal("https://sensivity.team/bot/pets/cats/4.png", 4000, "Birma Katze", 4, 4));

        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/1.png", 5000, "Corgi", 5, 1));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/2.png", 5000, "Dackel", 6, 2));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/3.png", 5000, "Golden Retriever", 7,3));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/4.png", 5000, "Dog 4", 8, 4));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/5.png", 5000, "Samoyed", 9, 5));
        dogs.add(new Animal("https://sensivity.team/bot/pets/dogs/6.png", 5000, "Shiba Inu", 10, 6));

        birds.add(new Animal("https://sensivity.team/bot/pets/birds/1.png", 1000, "Bird 1", 11, 1));
        birds.add(new Animal("https://sensivity.team/bot/pets/birds/2.png", 1000, "Bird 2", 12, 2));

        rabbit.add(new Animal("https://sensivity.team/bot/pets/rabbits/1.png", 2000, "Rabbit 1", 13, 1));
        rabbit.add(new Animal("https://sensivity.team/bot/pets/rabbits/2.png", 2000, "Rabbit 2", 14, 2));

        hamster.add(new Animal("https://sensivity.team/bot/pets/hamster/1.png", 2000, "Hamster 1", 15, 1));
        hamster.add(new Animal("https://sensivity.team/bot/pets/hamster/2.png", 2000, "Hamster 2", 16, 2));

        dragon.add(new Animal("https://sensivity.team/bot/pets/dragon/3.png", 8000, "Green Dragon", 17, 1));
        dragon.add(new Animal("https://sensivity.team/bot/pets/dragon/2.png", 8000, "Red Dragon", 18, 2));

        special.add(new Animal("https://sensivity.team/bot/pets/special/2.png", 10000, "Raccoonie", 19, 1));
    }

    public ArrayList<Animal> getAnimal(String name){
        if(name.equals("cat")){
            return cats;
        }else if(name.equals("dog")){
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
