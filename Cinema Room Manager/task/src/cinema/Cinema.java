package cinema;
import java.util.Scanner;

public class Cinema {

    public static int seatsNumber = 0;
    public static int rowsNumber = 0;
    public static String [][] cinemaHall = null;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Write your code here
        System.out.println("Enter the number of rows:");
        rowsNumber = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatsNumber = scanner.nextInt();
        //СОЗДАНИЕ массива со схемой кинозала
        cinemaHall = createCinemaHall(rowsNumber, seatsNumber);

        int data = 0;

        printMenu();
        boolean marker = true;
        while (marker){
           data = scanner.nextInt();

           if(data == 1){
               printCinemaHall(cinemaHall);
               System.out.println();
               printMenu();
           }

           else if (data == 2){
               Integer price = null;

               while(price == null){
                   price = getPrice(0, 0 );
               }
               System.out.format("Ticket price: $%d", price);System.out.println();
               System.out.println();
               printMenu();
           }

           else if(data == 3){
               int [] statistics = getStatistics();
               System.out.format("Number of purchased tickets: %d\n", statistics[0]);
               System.out.format("Percentage: %.2f%%\n",  ((float)statistics[0]/(float) (seatsNumber*rowsNumber))*100);
               System.out.format("Current income: $%d\n", statistics[1]);
               System.out.format("Total income: $%d\n", statistics[2]);
               System.out.println();
               printMenu();
           }

           else if(data == 0){
               break;
           }
        }
    }


    public static String [][] markPlace(String [][] cinemaHall, int rowNumber, int seatNumber){
        cinemaHall[rowNumber][seatNumber] = "B";
        return cinemaHall;
    }

    public static void printMenu(){
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }


    public static String [][] createCinemaHall(int rowsNumber, int seatsNumber){
        String [][] cinemaHall = new String[rowsNumber+1][seatsNumber+1];
        for (int i = 0; i < cinemaHall.length; i++) {
            for (int j = 0; j < cinemaHall[i].length; j++){
                if(i == 0 && j == 0){
                    cinemaHall[i][j] = "";
                }else if(i == 0 && j !=0){
                    cinemaHall[i][j] = j+"";
                }else if (i !=0 && j > 0){
                    cinemaHall[i][j] = "S"+"";
                }else if (i !=0 && j == 0){
                    cinemaHall[i][j] = i+"";
                }
            }
        }
        return cinemaHall;
    }


    public static void printCinemaHall(String [][] cinemaHall){
        String row = "";
        System.out.println("Cinema:");

        int countSpaceX = Utilities.getNumberSize(rowsNumber);
        int countSpaceY = Utilities.getNumberSize(seatsNumber);
        boolean marker = true;

        for (int i = 0; i < cinemaHall.length; i++){
            if (i >9 && i%10 == 0){
                countSpaceX--;
            }

            for (int j = 0; j < cinemaHall[i].length; j++){
                //КООРДИНАТНЫЙ РЯД С НОМЕРАМИ КРЕСЕЛ
                if(i == 0 && j == 0){
                    row = Utilities.getSpace(Utilities.getNumberSize(rowsNumber)) + " ";
                }else if(i == 0 && j !=0){
                    row += cinemaHall[i][j]+" ";
                }else if(i>=1 && j == 0) {
                    row += cinemaHall[i][j]+Utilities.getSpace(countSpaceX);
                }else{
                    row += cinemaHall[i][j]+Utilities.getSpace(countSpaceY);
                }
            }

            System.out.println(row);
            row = "";
        }
    }

    public static boolean isFree(int rowNumber, int seatNumber){
        if(cinemaHall[rowNumber][seatNumber] == "B"){
            return false;
        }

        return true;
    }

    public static Integer getPrice(int rowNumber, int seatNumber){
        if (rowNumber == 0 && seatNumber == 0){
            System.out.println("Enter a row number:");
            rowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatNumber = scanner.nextInt();

            if (rowNumber>rowsNumber || seatNumber > seatsNumber){
                System.out.println("Wrong input!");
                System.out.println();
                return null;
            }

            if(!isFree(rowNumber, seatNumber)){
                System.out.println("That ticket has already been purchased!");
                System.out.println();
                return null;
            }
        }

        cinemaHall = markPlace(cinemaHall, rowNumber, seatNumber);

        int frontHalf = 0;

        if(rowsNumber*seatsNumber<60){
            return 10;
        }else{
            frontHalf = rowsNumber/2;
            if(rowNumber<=frontHalf){
                return 10;
            }else{
                return 8;
            }
        }
    }


    public static int[] getStatistics(){
        int countTickets = 0;
        int countCurrentIncome = 0;
        int totalIncome = 0;

        for (int i = 0; i < cinemaHall.length; i++){
            for (int j = 0; j < cinemaHall.length; j++){
                if(cinemaHall[i][j] == "B"){
                    countTickets++;
                    countCurrentIncome += getPrice(i, j);
                }
            }
        }

        if(rowsNumber*seatsNumber<60 ){
            totalIncome = 10 * rowsNumber * seatsNumber;
        }else{
            if (rowsNumber/2 == 0){
                totalIncome = 10*rowsNumber*seatsNumber/2 + 8*rowsNumber*seatsNumber/2;
            }else{
                totalIncome = (10*(rowsNumber/2)*seatsNumber + 8*((rowsNumber+1)/2)*seatsNumber);
            }
        }

        return new int[]{countTickets, countCurrentIncome, totalIncome};
    }
}