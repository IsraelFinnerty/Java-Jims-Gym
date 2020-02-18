import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

// THIS CODE IS INCOMPLETE

public class GymAPI {
    private ArrayList<Member> members;
    private ArrayList<Trainer> trainers;

    public GymAPI() {
        this.members = new ArrayList<Member>();
        this.trainers = new ArrayList<Trainer>();
    }

    public ArrayList<Member> getMembers() {
        return members;
    }
    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }

    public int numberOfMembers() {
        return members.size();
    }

    public int numberOfTrainerss() {
        return trainers.size();
    }

    public boolean isValidMemberIndex(int index) {
         if (index < members.size() && index>=0) return true;
         else return false;

    }

    public boolean isValidTrainerIndex(int index) {
        if (index < trainers.size() && index >=0) return true;

        else return false;
    }

    public Member searchMembersByEmail(String emailEntered){
        for (Member member:members) {
            if (member.getEmail().equals(emailEntered)) return member;
        }
        return null;
    }

    public void addAssessment(String date, Assessment assessment, Member member){
        member.getAssessments().put(date, assessment);
    }

    public ArrayList<String> searchMembersByName(String nameEntered)
    {
        ArrayList<String> memberNames= new ArrayList<String>();
        for (Member member:members) {
            if (member.getName().contains(nameEntered)) memberNames.add(member.getName());
        }
        return memberNames;
    }


    public ArrayList<String> searchTrainersByName(String nameEntered)
    {
        ArrayList<String> trainerNames= new ArrayList<String>();
        for (Trainer trainer:trainers) {
            if (trainer.getName().contains(nameEntered)) trainerNames.add(trainer.getName());
        }
        return trainerNames;
    }

    public Trainer searchTrainersByEmail(String emailEntered){
        for (Trainer trainer:trainers) {
            if (trainer.getEmail().equals(emailEntered)) return trainer;
        }
        return null;
    }

     public ArrayList<Member> listMembers(){
        return members;
    }

    public Member findMemberEmail(String email){

        for (Member member:members)
        {
            if (member.getEmail().equals(email)) return member;
        }
        return null;
    }

    public ArrayList<Member> listMembersWithIdealWeight(){
        ArrayList<Member> membersIdealWeight = new ArrayList<Member>();
        for (Member member:members){
            if (GymUtility.isIdealBodyWeight(member, member.latestAssessment())) membersIdealWeight.add(member);
        }
        return  membersIdealWeight;
    }

    public ArrayList<Member> listMembersBySpecificBMICategory (String category){
        ArrayList<Member> membersBMI = new ArrayList<Member>();
        if (members!=null) {
            for (Member member : members) {
                if (GymUtility.determineBMICategory(GymUtility.calculateBMI(member, member.latestAssessment())).contains(category.toUpperCase())) membersBMI.add(member);
            }
        }
        return membersBMI;
    }

    public String listMemberDetailsImperialAndMetric(){
        String str = "";
        if (members.size()>0) {
            for (Member member:members)
            {
                float weight;
                Assessment temp;
                if (member.latestAssessment()!=null) {   temp = member.latestAssessment();}
                else temp = null;
                if (temp!=null) weight = temp.getWeight();
                else weight=member.getStartWeight();
                str+= "Name: " + member.getName();
                str+= "  |  Weight: " +Math.round(weight) + " kg (" + Math.round(weight* 2.2) + " lbs)";
                str+= "  |  Height: " +Math.round(member.getHeight()*10)/10.0 + " metres (" + Math.round(member.getHeight()*39.37) + " inches)\n";
            }
            return str;
        }
        else return "No registered members";
    }

    public String listAssessments(Member member) {

        String str = "";
        int index = 0;
        if (member.latestAssessment()!=null) {
            for (String name : member.getAssessments().keySet()) {
                String key = name;
                String value = member.getAssessments().get(name).toString();
                str += key + " " + value + "\n";
            }
        }
        else str+= "No assessments yet";
          return str;
    }

 public void trend(Member member, float weight, float waist, float thigh, String comment, Trainer trainer){

    boolean weightTrend=false;
    boolean waistTrend=false;
    float currentWeight;
    float currentWaist=0;
    if (member.latestAssessment()!=null) {
        currentWeight=member.latestAssessment().getWeight();
        currentWaist=member.latestAssessment().getWaist();
    }
    else {
        currentWeight=member.getStartWeight();
        currentWaist=1000;
    }

        if (currentWeight > weight) {weightTrend=true;}
        if (currentWaist > waist) {waistTrend=true;}
    Assessment assessment = new Assessment(weight, thigh, waist, comment, trainer.getName());
        assessment.setWeightTrend(weightTrend);
        assessment.setWaistTrend(waistTrend);
        addAssessment(timeStamp(), assessment, member);
        member.setAssessmentRequested(false);
        System.out.println("Assessment added.");
}

    private String timeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    public int assessmentsRequested() {
        int count=0;
        for (Member member:members)
        {
         if (member.isAssessmentRequested()) count++;
        }
        return count;
    }

    public String listRequestedAssessments()
    {
        String str="";
        int count=0;
        for (Member member:members)
        {
         if(member.isAssessmentRequested()) {
             count++;
             str += "\nName: " + member.getName() + " Email: " + member.getEmail();

             if (member.latestAssessment() != null)
                 str += " Most Recent Assessment: " + member.sortedAssessmentDates().last();
             else str += " No Previous Assessments";
         }
        }
        if (count == 0) str+= "No Assessments Requested";
        return str;
    }

    public String listMemberDetails(){
        String str= "";
        for (Member member: members) {
            String date = "";
            if (member.latestAssessment()!=null) date= member.getAssessments().lastKey();
            else date="No Assessments";
            str+= getMembers().indexOf(member) + ") Name: " + member.getName() + "  |  Email: " + member.getEmail() + "  |  Most recent assessment: " + date +"\n";
        }
        return str;
    }

    public void addMessage(Member member, Trainer trainer, String comment)
    {
        Message message = new Message(timeStamp(), comment, trainer.getName());
        member.getMessages().add(message);
    }

    public String listMessages(Member member){
        String str ="";
        if (member.getMessages()!=null){
            for (Message message:member.getMessages()){
               str+= "\u2709 Message Status: " + (message.isRead()?"Read":"Unread \uD83C\uDD95");
               str+= "\nDate: " + message.getDate();
               str+= "\nTrainer: " + message.getTrainerName();
               str+= "\nMessage: " + message.getMessage();
               str+= "\n----------\n";
               message.setRead(true);
            }
        }
        else str= "No messages";
        return str;
    }

    public int numberUnreadMessages(Member member) {
        int count=0;
        if (member.getMessages()!=null) {
            for (Message message : member.getMessages()) {
                if (!message.isRead()) count++;
            }
        }
        return count;
    }

    public String weightProgress(Member member){
        String str="";
        if (member.latestAssessment()!=null) {
            for (Map.Entry<String, Assessment> entry : member.getAssessments().entrySet()) {
                str += "Date: " + entry.getKey();
                str += " | Weight: " + entry.getValue().getWeight();
                str += " | Trend: " + (entry.getValue().isWeightTrend() ? "Positive \uD83D\uDC4D" : "Negative \uD83D\uDC4E") + "\n";
            }
        }
        else str = "No previous assessments.";
    return str;
    }

    public String waistProgress(Member member){
        String str="";
        if (member.latestAssessment()!=null) {
            for (Map.Entry<String, Assessment> entry : member.getAssessments().entrySet()) {
                str += "Date: " + entry.getKey();
                str += " | Waist: " + entry.getValue().getWaist();
                str += " | Trend: " + (entry.getValue().isWaistTrend() ? "Positive \uD83D\uDC4D" : "Negative \uD83D\uDC4E") + "\n";
            }
        }
        else str = "No previous assessments.";
        return str;
    }

    public boolean isEmailUsed(String email) {
        if (searchTrainersByEmail(email)==null && searchMembersByEmail(email)==null) return false;
        else return true;
    }

    public void removeMember(Member member) {
        members.remove(member);
    }

    public void store() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream outMembers = xstream.createObjectOutputStream(new FileWriter("members.xml"));
        outMembers.writeObject(members);
        outMembers.close();
        ObjectOutputStream outTrainers = xstream.createObjectOutputStream(new FileWriter("trainers.xml"));
        outTrainers.writeObject(trainers);
        outTrainers.close();
    }




    public void load() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        Class<?>[] classes = new Class[] { Member.class, Trainer.class, PremiumMember.class, StudentMember.class, Assessment.class, Message.class };
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        ObjectInputStream isMembers = xstream.createObjectInputStream(new FileReader("members.xml"));
        members = (ArrayList<Member>) isMembers.readObject();
        isMembers.close();

        ObjectInputStream isTrainers = xstream.createObjectInputStream(new FileReader("trainers.xml"));
        trainers = (ArrayList<Trainer>) isTrainers.readObject();
        isTrainers.close();
    }

}