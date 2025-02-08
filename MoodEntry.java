//updated mood entry class for handling btoh version of local and web app with webserver code

public class MoodEntry {
    protected int mood;
    protected String note;
    protected String suggestions;
    protected double sleepHours;
    protected String date;
    
    // Constructor for web version with suggestions
    public MoodEntry(int mood, String note, String suggestions, double sleepHours, String date) {
        this.mood = mood;
        this.note = note;
        this.suggestions = suggestions;
        this.sleepHours = sleepHours;
        this.date = date;
    }
    
    // Constructor for console version without suggestions
    public MoodEntry(int mood, String note) {
        this.mood = mood;
        this.note = note;
        this.suggestions = "";
    }
    
    public int getMood() {
        return mood;
    }
    
    public String getNote() {
        return note;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public double getSleepHours() {
        return sleepHours;
    }

    public String getDate() {
        return date;
    }
}




// public class MoodEntry {
//     protected int mood;
//     protected String note;
//     protected String suggestions;
    
//     public MoodEntry(int mood, String note, String suggestions) {
//         this.mood = mood;
//         this.note = note;
//         this.suggestions = suggestions;
//     }
    
//     public int getMood() {
//         return mood;
//     }
    
//     public String getNote() {
//         return note;
//     }

//     public String getSuggestions() {
//         return suggestions;
//     }
// }
