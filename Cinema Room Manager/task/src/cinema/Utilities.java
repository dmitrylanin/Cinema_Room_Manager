package cinema;

public class Utilities {
    public static int getNumberSize(int number){
        int numberSize = 1;
        if(number<10){
            return 1;
        }

        while(true){
            if(number/10 < 10){
                numberSize++;
                break;
            }
            number = number/10;
            numberSize++;
        }
        return numberSize;
    }

    public static String getSpace(int number){
        String space = "";
        for (int i = 0; i < number; i++) {
            space += " ";
        }
        return space;
    }
}
