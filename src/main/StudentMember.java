public class StudentMember extends Member{

    private int studentId;
    private String collegeName;
    private String chosenPackage;


    public StudentMember(String email, String name, String address,
                         String gender, float height, float startWeight, String collegeName, int studentId) {

        super(email, name, address, gender, height,startWeight, collegeName);
        this.studentId=studentId;
        this.collegeName=collegeName;
        chosenPackage("");

    }

    public void chosenPackage(String packageChoice) {
        super.chosenPackage(packageChoice);
        this.chosenPackage = "Package 3";
        for (String key : getPackages().keySet())
            if (key.contains(collegeName)) {
                this.chosenPackage = key;
            }
    }

    public String toString() {
        String str = "";
        str += (super.toString());
        str += ("Student Number: " + studentId + "\n");
        str += ("College: " + collegeName + "\n");
        str += ("Student Package: " + chosenPackage + " : " + getPackages().get(chosenPackage));
        return str;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }


    @Override
    public String getChosenPackage() {
        return chosenPackage;
    }

    @Override
    public void setChosenPackage(String chosenPackage) {
        this.chosenPackage = chosenPackage;
    }
}
