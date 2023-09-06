import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File myObj = new File("src/db_exchangerates_table.sql");
            Scanner myReader = new Scanner(myObj);
            String data ="";
            String[] arr = data.split(";");
            int i = 0;
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
                System.out.println(i);
                i++;
            }
            String s = "";
            System.out.println("intering for loop");
            for (int j = 0; j < arr.length; j++){
                arr[j] = data.substring(36, 47);
                s+=arr[j];
            }
            writer(s);

            System.out.println("Out of loop");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Seeding file db_exchangerates_table.sql not found.");
            e.printStackTrace();
        }

    }
    static void writer(String arr){
        try {
            FileWriter myWriter = new FileWriter("src/db_exchangerates_table2.sql");
            myWriter.write(arr);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}