import java.util.*;

public class Member extends Person {
    private float height;
    private float startWeight;
    private String chosenPackage;
    private boolean idealWeight;
    private String bmiCategory;
    private boolean assessmentRequested;
    //HashMap of Assessments with key of Dates
    private TreeMap<String, Assessment> assessments;
    private HashMap<String, String> packages;
    private ArrayList<Message> messages;


    public Member(String email, String name, String address,
                  String gender, float height, float startWeight, String chosenPackage) {
        super(email, name, address, gender);
        setHeight(height);
        setStartWeight(startWeight);
        assessments = new TreeMap<>();
        messages = new ArrayList<Message>();
        packages = new HashMap<>();
    }

    public String toString() {
        String str = "";
        str += (super.toString());
        str += ("Height: " + height + "\n");
        str += ("Starting Weight: " + startWeight + "\n");

        return str;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        if (height>=1 && height<=3)   this.height = height;
        else this.height = 2;
    }

    public float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(float startWeight) {
        if (startWeight>=35 && startWeight<=250) this.startWeight = startWeight ;
        else this.startWeight = 108;
    }

    public String getChosenPackage() {
        return chosenPackage;
    }

    public void setChosenPackage(String chosenPackage) {
        this.chosenPackage = chosenPackage;
    }

    public boolean isIdealWeight() {
        return idealWeight;
    }

    public void setIdealWeight(boolean idealWeight) {
        this.idealWeight = idealWeight;
    }

    public String getBmiCategory() {
        return bmiCategory;
    }

    public void setBmiCategory(String bmiCategory) {
        this.bmiCategory = bmiCategory;
    }

    public TreeMap<String, Assessment> getAssessments() {
        return assessments;
    }

    public Assessment latestAssessment()
    {
       if (assessments.lastEntry()!=null) return assessments.lastEntry().getValue();
        else return null;
    }

    public SortedSet sortedAssessmentDates(){
        TreeSet<String> dates = new TreeSet<String>();

        for (String keys: assessments.keySet())
        {
          dates.add(keys);
        }
        return dates;
    }

    public boolean isAssessmentRequested() {
        return assessmentRequested;
    }

    public void setAssessmentRequested(boolean assessmentRequested) {
        this.assessmentRequested = assessmentRequested;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void chosenPackage(String chosenPackage)
    {
        packages.put("Package 1", "Allowed access anytime to gym. | Free access to all classes. | Access to all changing areas including deluxe changing rooms.");

        packages.put("Package 2", "Allowed access anytime to gym. | €3 fee for all classes. | Access to all changing areas including deluxe changing rooms.");

        packages.put("Package 3", "Allowed access to gym at off-peak times. | €5 fee for all classes. | No access to deluxe changing rooms.");

        packages.put("WIT", "Allowed access to gym during term time. | €4 fee for all classes. | No access to deluxe changing rooms.");

        packages.put("Harvard", "Allowed access to gym during term time. | €4 fee for all classes. | No access to deluxe changing rooms.");

    }

    public HashMap<String, String> getPackages() {
        return packages;
    }

    public void setPackages(HashMap<String, String> packages) {
        this.packages = packages;
    }
}


