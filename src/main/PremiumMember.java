import java.util.Map;

public class PremiumMember extends Member{

    private String chosenPackage;

    public PremiumMember(String email, String name, String address,
                         String gender, float height, float startWeight, String chosenPackage) {

        super(email, name, address,
                gender, height, startWeight, chosenPackage);
        chosenPackage(chosenPackage);
    }

    public void chosenPackage(String packageChoice){

        super.chosenPackage(packageChoice);
        for (String key: getPackages().keySet())
            if (key.contains(packageChoice)){
                this.chosenPackage = key;
            }
    }


    public String toString() {
        String str = "";
        str += (super.toString());
        str += ("Premium Package: " +  chosenPackage + " : " + getPackages().get(chosenPackage));
        return str;
    }

    @Override
    public String getChosenPackage() {
        return chosenPackage;
    }


    public void setChosenPackage(String chosenPackage) {

        this.chosenPackage=chosenPackage;
    }

}
