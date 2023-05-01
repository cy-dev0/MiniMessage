/**  Project - Phase 3
 *   Seth Seibel
 *   MiniMessage is an application where users can enter their names and a message they would like to send, once user has entered inputs message will print with the date and time the message was sent. Once user is done sending messages, program will ask if there are other users who wish to use the application.
*/
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javax.swing.ImageIcon;

//P3-7 Code commented - Beginning of class
public class MiniMessage {
    public static void main(String[] args) throws Exception {

        System.out.println("Programmed by Seth Seibel");

        //Formats date and time output for message timestamp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        //Creates icon from image file - to be used in message display
        ImageIcon icon1 = new ImageIcon("pic1.jpg"); //assumes pic1 is placed in root folder

        //create file writer object, creates file if it does not exist
        FileWriter myWriter = new FileWriter("messageboard.txt", true);

        int userYN = 0;
        int newUser = 0;

        //P3-1 Parallel Arrays - creates array for day and message of the day
        ArrayList<String> day = new ArrayList<String>();
        ArrayList<String> motd = new ArrayList<String>();

        //P3-2 Load Files - loads file for day and message of the day
        day = loadArray("file1.txt");
        motd = loadArray("file2.txt");

        String userIn = JOptionPane.showInputDialog(null, 
                                    "What day of the week is it?", 
                                    "Day of Week", 
                                    JOptionPane.QUESTION_MESSAGE);

        userIn = valDay(userIn);
                               
        //compare userin to grab the correct message of the day
        for (int i = 0; i < day.size(); i++) {
            if (userIn.equalsIgnoreCase(day.get(i))) {
                JOptionPane.showMessageDialog(null, 
                motd.get(i), 
                "Message of the Day", 
                JOptionPane.INFORMATION_MESSAGE, 
                icon1);
            }
        }

        //main program loop which asks if there are other users, if so resets the message count
        do {
        
            int count = 1; 
            String userName = JOptionPane.showInputDialog(null, "Please enter your name:");
            userName = valName(userName);
        
            //Message loop - asks if current user wants to send another message, if so grabs the current date and time
            do {
                
                LocalDateTime now = LocalDateTime.now();
                String userMessage = JOptionPane.showInputDialog(null, "Enter the message you would like to send:");
                userMessage = valMessage(userMessage);
                
                String message = (dtf.format(now) + "\n" + userName + ": " + userMessage + "\n");

                //P3-8 New feature, uses try-catch to write user message to messageboard file, throws error if unable
                try {
                    myWriter.write(message);
                    myWriter.flush();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                
                //prints messageboard file to console
                try (BufferedReader br = new BufferedReader(new FileReader("messageboard.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                 }
                //message will display with timestamp, custom icon, and title will print with users name
                JOptionPane.showMessageDialog(null, 
                                            dtf.format(now) + "\n" + userName + ": " + userMessage + "\nMessage #" + count,
                                            "Message from " + userName, 
                                            JOptionPane.INFORMATION_MESSAGE,
                                            icon1);
        
                //counts the amount of messages current user has sent
                count++;

                userYN = JOptionPane.showConfirmDialog(null, "Would you like to send another message?");

            } while (userYN == 0); //end of message loop

            newUser = JOptionPane.showConfirmDialog(null, "Is there another user who would like to use this application?");

        } while (newUser == 0); //end of user loop

        myWriter.close();
    } //end of main method

    //P3-3 Method to load file into array
    public static ArrayList<String> loadArray(String file1) throws Exception {
        // Create an ArrayList object to hold messageboard file
        ArrayList<String> messageArray = new ArrayList<String>();
        //create file object
        File f1 = new File(file1);
        //create scanner objects
        Scanner scan1 = new Scanner(f1);
        while (scan1.hasNextLine()) {
            messageArray.add(scan1.nextLine());
        }
        scan1.close();
        return messageArray;
    }

    //P3-5 Name validation loop
    public static String valName(String userName1) { 
        //P3-4 String Method - checks to make sure input is not empty or blank space
        while (userName1.isEmpty() || userName1.isBlank())
        {
            userName1 = JOptionPane.showInputDialog(null, "ERROR: You have not entered your name. \nPlease enter your name:");
        }
        return userName1;
    }//end of method 1

    //P3-5 Message validation loop
    public static String valMessage(String userMessage1) {
        //checks to make sure input is not empty or blank space
        while (userMessage1.isEmpty() || userMessage1.isBlank()) 
        {
            userMessage1 = JOptionPane.showInputDialog(null, "ERROR: You have not entered a message. \nEnter the message you would like to send:");
        }
        return userMessage1;   
    }//end of method 2
    //P3-5 Method to validate day of week
    public static String valDay(String day1) { 
        //P3-6 Clean userin - checks to make sure input is not empty or blank space
        while (day1.isEmpty() || day1.isBlank())
        {
            day1 = JOptionPane.showInputDialog(null, "ERROR: You have not entered the day of the week. \nPlease try again:");
        }
        //P3-6 Clean userin - checks to make sure input is a valid day of the week
        while (!day1.equalsIgnoreCase("Monday") && !day1.equalsIgnoreCase("Tuesday") && !day1.equalsIgnoreCase("Wednesday") && !day1.equalsIgnoreCase("Thursday") && !day1.equalsIgnoreCase("Friday") && !day1.equalsIgnoreCase("Saturday") && !day1.equalsIgnoreCase("Sunday"))
        {
            day1 = JOptionPane.showInputDialog(null, "ERROR: You have not entered a valid day of the week. \nPlease try again:");
        }
        return day1;
    }//end of method 3

}// end of class