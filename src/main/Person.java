public class Person {
    private String name;
    private String email;
    private String address;
    private String gender;

    public String toString(){
        String str = "";
        str += ("Name: " + name + "\n");
        str += ("Email: " + email + "\n");
        str += ("Address: " + address + "\n");
        str += ("Gender: " + gender + "\n");
        return str;
    }

    public void setName(String name) {
        if (name.length()>30) name=name.substring(0,30);
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        gender = gender.toUpperCase();
        if (gender.equals("M") || gender.equals("F")) this.gender=gender;
        else this.gender = "Unspecified";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public Person(String email, String name, String address, String gender) {
        setName(name);
        this.email = email;
        this.address = address;
        setGender(gender);
    }
}
