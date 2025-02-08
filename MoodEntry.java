public class MoodEntry {
    protected int mood;
    protected String note;
    
    public MoodEntry(int mood, String note) {
        this.mood = mood;
        this.note = note;
    }
    
    public int getMood() {
        return mood;
    }
    
    public String getNote() {
        return note;
    }
}
