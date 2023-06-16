package pets;

import org.joda.time.DateTime;

public class Function {
    public static int amountFoodDay(){
        int amount = 0;

        DateTime now = DateTime.now();
        int hour = now.hourOfDay().get();

        amount = hour / 3;

        return amount;
    }

    public static int amountDrinkDay(){
        DateTime now = DateTime.now();
        int hour = now.hourOfDay().get();

        return hour;
    }

    public static int levelUpdate(int amount){
        switch (amount){
            case 10:
                return 2;
            case 20:
                return 3;
            case 30:
                return 4;
            case 50:
                return 5;
            case 70:
                return 6;
            case 100:
                return 7;
            case 130:
                return 8;
            case 160:
                return 9;
            case 200:
                return 10;
            case 240:
                return 11;
            case 300:
                return 12;
            default:
                return -1;
        }
    }
}
