public class Message {

    private String date;
    private String message;
    private String trainerName;
    private boolean read=false;

    public Message(String date, String message, String trainerName) {
        this.date=date;
        this.message=message;
        this.trainerName=trainerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
