import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<MoodEntry> entries = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to MoodMate!");
        System.out.println("How are you feeling today? Rate your mood (1-5): ");
        int mood = scanner.nextInt();
        scanner.nextLine(); // clear buffer <-- is that your comment aneira, what does clear buffer mean aneira? are you able to tell me?
        
        System.out.println("Add a quick note about your day: ");
        String note = scanner.nextLine();
        
        MoodEntry entry = new MoodEntry(mood, note);
        entries.add(entry);
        
        // Print back what was entered
        System.out.println("\nYour Entry:");
        System.out.println("Mood: " + entry.getMood() + "/5");
        System.out.println("Note: " + entry.getNote());
        
        scanner.close();
    }
}