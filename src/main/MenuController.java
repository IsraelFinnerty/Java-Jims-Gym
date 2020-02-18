import java.text.DecimalFormat;
import java.util.Scanner;
import static java.lang.System.exit;

public class MenuController {

    private Scanner input = new Scanner(System.in);
    private GymAPI gym;
    private Person person;
    private boolean trainer = false;
    private boolean member = false;
    private boolean trainerView = false;
    private String loggedInTrainer;
    private boolean updating = false;
    private Member loggedInMember = null;

    public static void main(String args[]) {

        MenuController c = new MenuController();
    }

    public MenuController() {
        gym = new GymAPI();
        run();
    }


    private String welcomeMenu() {
        System.out.println("                       Welcome to                  ");
        displayLogo();
        System.out.println("  Press 't' if you are a trainer.");
        System.out.println("  Press 'm' if you are a member or want to be a member.");
        System.out.println("  0) Exit Program");
        System.out.println("---------");
        System.out.print("==>> ");

        boolean goodInput = false;
        String option = "";
        do {
            try {
                option = input.nextLine();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Character expected");
            }
        } while (!goodInput);

        return option;
    }

    private String mainMenu() {
        System.out.println("Welcome to Jim's Gym");
        System.out.println("---------");
        System.out.println("  Press 'l' to login if you have an account.");
        System.out.println("  Press 'r' to register if you are new.");
        System.out.println("  0) Exit Program");
        System.out.println("---------");
        System.out.print("==>> ");

        boolean goodInput = false;
        String option = "";
        do {
            try {
                option = input.nextLine();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Character expected");
            }
        } while (!goodInput);

        return option;
    }

    private void run() {
        try {
            gym.load();
        } catch (Exception e) {
            System.err.println("Error loading from file: " + e);
        }
        trainerMenu();
    }

    public void trainerMenu() {

        String trainerMember = welcomeMenu();


            switch (trainerMember) {
                case "t":
                    trainer = true;
                    optionMenu();
                    break;
                case "m":
                    member = true;
                    optionMenu();
                    break;
                case "0":
                    exitCase();
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    trainerMenu();
                    break;

            }
    }

    public void optionMenu() {

        String option = mainMenu();


            switch (option) {
                case "l":
                    loginMenu();
                    break;
                case "r":
                    registerMenu(loggedInMember);
                    break;
                case "0":
                    exitCase();
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    optionMenu();
                    break;

            }
    }

    public void loginMenu(){
        System.out.println("---------");
        System.out.println("Login Menu");
        System.out.println("---------");
        System.out.println("  Enter your email address to login.");
        System.out.println("  Press 'r' to register if you are new.");
        System.out.println("  0) Exit Program");
        System.out.println("---------");
        System.out.print("==>> ");

        boolean goodInput = false;
        String option = "";
        do {
            try {
                option = input.nextLine();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Email expected");
            }
        } while (!goodInput);
        if (trainer) {
            for (Trainer trainer : gym.getTrainers()) {
                if (option.equals(trainer.getEmail())) trainerDashboard(option);
            }
        }
    else {
            for (Member member : gym.getMembers()) {
                if (option.equals(member.getEmail())) {
                    memberDashboard(option);
                }
            }
        }
        if (option.equals("r")) registerMenu(loggedInMember);
        if (option.equals("0")) exitCase();
        else {
            System.out.println("Invalid email entered - try again or press r to register");
            loginMenu();
        }

    }

    public void registerMenu(Member loggedInMember)
    {
        System.out.println("---------");
        System.out.println("Registration Menu");
        System.out.println("---------");

        boolean goodInput=false;
        String name = "";

        do {
            try {
                System.out.print("Enter your name: ");
                name = input.nextLine();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Name expected - you entered a number");
            }
        } while (!goodInput);

        goodInput=false;
        String email="";
        if (!updating) {
            do {
                try {
                    System.out.print("Enter your email address: ");
                    email = input.nextLine();
                    if (gym.isEmailUsed(email)) {
                        System.out.println("Email is already in use! Use another email address.");
                        goodInput=false;
                    }
                    else goodInput = true;
                } catch (Exception e) {
                    input.nextLine(); //swallows Scanner bug
                    System.err.println("Email expected");
                }
            } while (!goodInput);
        }



        goodInput=false;
        String address="";
        do {
            try {
                System.out.print("Enter your address: ");
                address = input.nextLine();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Address expected");
            }
        } while (!goodInput);

        goodInput=false;
        String gender="";
        do {
            try {
                System.out.print("Enter your gender - M for Male - F for Female: ");
                gender = input.nextLine();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Gender expected");
            }
        } while (!goodInput);

        if (trainer==true) {
            goodInput = false;
            String speciality = "";
            do {
                try {
                    System.out.print("Enter your training speciality: ");
                    speciality = input.nextLine();
                    goodInput = true;
                } catch (Exception e) {
                    input.nextLine(); //swallows Scanner bug
                    System.err.println("Speciality expected");
                }
            } while (!goodInput);

            Trainer trainer = new Trainer(email, name, address, gender, speciality);
            gym.addTrainer(trainer);
            trainerDashboard(email);
        }


        if (member==true) {

            goodInput = false;
            float height = 0;
            if (!updating) {
                do {
                    try {
                        System.out.print("Enter your height in metres: ");
                        height = input.nextFloat();
                        goodInput = true;
                    } catch (Exception e) {
                        input.nextLine(); //swallows Scanner bug
                        System.err.println("Number expected - you enetered text");
                    }
                } while (!goodInput);
            }
            goodInput = false;
            float startingWeight = 0;

            if (!updating) {
                do {
                    try {
                        System.out.print("Enter your weight in kgs: ");
                        startingWeight = input.nextFloat();
                        goodInput = true;
                        input.nextLine();
                    } catch (Exception e) {
                        input.nextLine(); //swallows Scanner bug
                        System.err.println("Number expected - you enetered text");
                    }
                } while (!goodInput);
            }


            goodInput = false;
            String memberType = "";
            if (!updating){
                while (!memberType.equals("s") && !memberType.equals("p")) {
                    do {
                        try {
                            System.out.println("Are you a premium member 'p' or student 's'?");
                            memberType = input.nextLine();
                            goodInput = true;
                        }   catch (Exception e) {
                            input.nextLine(); //swallows Scanner bug
                            System.err.println("Speciality expected");
                     }
                    } while (!goodInput);
                }
            }

            if (memberType.equals("s") || loggedInMember instanceof StudentMember)
            {
                goodInput = false;
                String collegeName = "";
                do {
                    try {
                        System.out.print("Enter your College name:");
                        collegeName = input.nextLine();
                        goodInput = true;
                    } catch (Exception e) {
                        input.nextLine(); //swallows Scanner bug
                        System.err.println("College name expected");
                    }
                } while (!goodInput);

                goodInput = false;
                int studentId = 0;
                do {
                    try {
                        System.out.print("Enter your Student Number:");
                        studentId = input.nextInt();
                        goodInput = true;
                    } catch (Exception e) {
                        input.nextLine(); //swallows Scanner bug
                        System.err.println("Student Number Expected - you entered text");
                    }
                } while (!goodInput);

                if (updating) {
                        StudentMember studentMember = (StudentMember) loggedInMember;
                        studentMember.setName(name);
                        studentMember.setGender(gender);
                        studentMember.setAddress(address);
                        studentMember.setCollegeName(collegeName);
                        studentMember.setStudentId(studentId);
                        System.out.println("Your details have been updated...press any key to return to your dashboard");
                        input.nextLine();
                        memberDashboard(studentMember.getEmail());
                }

                else { StudentMember studentMember = new StudentMember(email, name, address, gender, height, startingWeight, collegeName, studentId);
                    gym.addMember(studentMember);}

                if (trainerView) {
                    System.out.println("Member added.");
                    trainerDashboard(loggedInTrainer);
                }
                else memberDashboard(email);
            }

            if (memberType.equals("p") || loggedInMember instanceof PremiumMember)
            {
                goodInput = false;
                String packageOption = "";
                do {
                    try {
                        System.out.println("Please select a Premium Package:");
                        System.out.println("1) Access anytime to gym. Free access to all classes. Access to all changing areas including deluxe changing rooms.");
                        System.out.println("2) Access anytime to gym. €3 fee for all classes. Access to all changing areas including deluxe changing rooms.");
                        System.out.println("3) Access to gym at off-peak times. €5 fee for all classes. No access to deluxe changing rooms.");
                        packageOption = input.nextLine();
                        goodInput = true;
                    } catch (Exception e) {
                        input.nextLine(); //swallows Scanner bug
                        System.err.println("Packacge number expected");
                    }
                } while (!goodInput);


                if (updating) {
                    loggedInMember.setName(name);
                    loggedInMember.setGender(gender);
                    loggedInMember.setAddress(address);
                    loggedInMember.setChosenPackage(packageOption);
                    System.out.println("Your details have been updated...press any key to return to your dashboard");
                    input.nextLine();
                    memberDashboard(loggedInMember.getEmail());
                }

                else {
                    PremiumMember premiumMember = new PremiumMember(email, name, address, gender, height, startingWeight, packageOption);
                    gym.addMember(premiumMember);
                }
                if (trainerView) {
                    System.out.println("Member added.");
                    trainerDashboard(loggedInTrainer);
                }
                else memberDashboard(email);
            }

        }
    }


    public void trainerDashboard(String email){
        Trainer currentTrainer = gym.searchTrainersByEmail(email);

        System.out.println("---------");
        System.out.println(currentTrainer.getName() + "'s Dashboard");
        System.out.println("Number of members requesting assessments: " + gym.assessmentsRequested());
        System.out.println("---------");
        System.out.println("  1) \u2795 Add a new member.");
        System.out.println("  2) \uD83D\uDDD2 List all members:*" + gym.numberOfMembers() + "*");
        System.out.println("  3) \uD83D\uDD0E Search for a member by email.");
        System.out.println("  4) \uD83D\uDEA9 List all members who have requested an assessment:*" + gym.assessmentsRequested()+"*");
        System.out.println("  5) \uD83E\uDC82 Assessments sub-menu - add an assessment or comment on assessments.");
        System.out.println("  6) \u2696 View all members weight & height in metric & imperial.");
        System.out.println("  7) \u2709 Send a member an assessment invitation.");
        System.out.println("  8) \uD83C\uDD87 Remove a member.");
        System.out.println("  9) \uD83D\uDCBE Save & Logout.");
        System.out.println("  0) \uD83D\uDEAA Save & Exit Program.");
        System.out.println("---------");
        System.out.print("==>> ");

        boolean goodInput=false;
        int option=0;
        do {
            try {
                option = input.nextInt();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Number expected - you entered text");
            }
        } while (!goodInput);

        switch (option){
            case 1:
                trainerView=true;
                trainer=false;
                member=true;
                loggedInTrainer = email;
                input.nextLine();
                registerMenu(loggedInMember);
                break;
            case 2:
                System.out.println(gym.listMemberDetails());
                System.out.println("\nPress any key to return to the Trainer Dashboard...");
                input.nextLine();
                input.nextLine();
                trainerDashboard(email);
                break;
            case 3:
                input.nextLine();
                System.out.println("  Enter the member's email address:");
                String memberEmail = input.nextLine();
                System.out.println(gym.findMemberEmail(memberEmail));
                trainerDashboard(email);
                break;
            case 4:
                System.out.println(gym.listRequestedAssessments());
                System.out.println("Press any key to return to the trainer dashboard");
                input.nextLine();
                input.nextLine();
                trainerDashboard(email);
            case 5:    assessmentSubMenu(email);
                break;
            case 6:    System.out.println(gym.listMemberDetailsImperialAndMetric());
                System.out.println("Press any key to continue...");
                input.nextLine();
                input.nextLine();
                trainerDashboard(email);
                break;
            case 7:
                System.out.println(gym.listMemberDetails());
                Member currentMember = memberNumber();
                sendMessage(email, currentMember);
                break;
            case 8:
                System.out.println(gym.listMemberDetails());
                gym.removeMember(memberNumber());
                System.out.println("Member removed. Press any key to continue...");
                input.nextLine();
                input.nextLine();
                trainerDashboard(email);
            case 9:     logout();
                break;
            case 0:     exitCase();
                break;
            default:
                System.out.println("Invalid option entered: " + option);
                trainerDashboard(email);
                break;
        }
    }

    public void assessmentSubMenu(String email){
        System.out.println("---------");
        System.out.println("Assessment Sub Menu");
        System.out.println("---------");
        System.out.println("  1) \u2795 Add an assemsent for a member.");
        System.out.println("  2) \u21BB Update comment on an assessment for a member");
        System.out.println("  3) \uD83E\uDC80 Return to Trainer Dashboard");
        System.out.println("  0) \uD83D\uDEAA Save & Exit Program");
        System.out.println("---------");
        System.out.print("==>> ");

        boolean goodInput=false;
        int option=0;
        do {
            try {
                option = input.nextInt();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Number expected - you entered text");
            }
        } while (!goodInput);

        switch (option){
            case 1:
                System.out.println(gym.listMemberDetails());
                addAssessment(memberNumber(), email);
                System.out.println("\nPress any key to return to Trainer Dashboard...");
                input.nextLine();
                trainerDashboard(email);
                break;
            case 2:
               System.out.println(gym.listMemberDetails());
               Member memberAssessment = memberNumber();
               System.out.println("---------");
               System.out.println(memberAssessment.getName() + "'s Assessments");
               System.out.println("---------");
               System.out.println(gym.listAssessments(memberAssessment));
               commentOnAssessment(email, memberAssessment);

               trainerDashboard(email);
                break;
            case 3: trainerDashboard(email);
                break;
            case 0:     exitCase();
                break;
            default:
                System.out.println("Invalid option entered: " + option);
                trainerDashboard(email);
                break;
        }
    }



    private void commentOnAssessment(String email, Member member) {

        System.out.println("To update a comment enter the date of assessment YY/MM/DD\n 0) Back to Trainer Dashboard");
        boolean goodInput=false;
        String date="";
        do {
            try {
                input.nextLine();
                date = input.nextLine();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Date expected");
            }
        } while (!goodInput);


        for (String key: member.getAssessments().keySet()) {
         if (date.equals(key)) {
             System.out.println("Enter your comment:");

             goodInput=false;
             String comment="";
             do {
                 try {
                     comment = input.nextLine();
                     goodInput = true;
                 }
                 catch (Exception e) {
                     input.nextLine(); //swallows Scanner bug
                     System.err.println("Comment expected");
                 }
             } while (!goodInput);
             Trainer trainer = gym.searchTrainersByEmail(email);
             member.getAssessments().get(date).setComment(comment);
             member.getAssessments().get(date).setPersonalTrainer(trainer.getName());
             System.out.println("Comment Added...press any key to return to trainer dashboard");
             input.nextLine();
             trainerDashboard(email);
         }

        }
        if (date.equals("0")) trainerDashboard(email);
        else commentOnAssessment(email, member);
    }

    private void sendMessage(String email, Member member){
        Trainer trainer = gym.searchTrainersByEmail(email);
        System.out.println("---------");
        System.out.println("Send an Assessment Invitation to " + member.getName());
        System.out.println("---------");

        boolean goodInput = false;
        String message = "";
        do {
            try {
                System.out.print("Enter the invitation message including the proposed date and time: ");
                input.nextLine();
                message = input.nextLine();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Message expected");
            }
        } while (!goodInput);

        gym.addMessage(member, trainer, message);
        System.out.println("Invitation Sent...press any key to return to trainer dashboard");
        input.nextLine();
        trainerDashboard(email);
    }

    private Member memberNumber() {
        System.out.println("\nEnter the number of the member you want to select: ");
        int memberNumber = 0;
        boolean goodInput=false;

        do {
            try {
                memberNumber = input.nextInt();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Number expected - you entered text");
            }
        } while (!goodInput);
        if (memberNumber < gym.getMembers().size() && memberNumber >=0) return gym.getMembers().get(memberNumber);
        else {
            System.out.println("Please enter a valid member number");
            memberNumber();
        }
        return null;
    }


    public void addAssessment(Member member, String email) {
        System.out.println("---------");
        System.out.println("Add Assessment for " + member.getName());
        System.out.println("---------");

        boolean goodInput = false;
        float weight = 0;

        do {
            try {
                System.out.print("Enter weight: ");
                weight = input.nextFloat();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Number expexted - you entered text");
            }
        } while (!goodInput);

        goodInput = false;
        float thigh = 0;

        do {
            try {
                System.out.print("Enter thigh measurement: ");
                thigh = input.nextFloat();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Number expexted - you entered text");
            }
        } while (!goodInput);

        goodInput = false;
        float waist = 0;

        do {
            try {
                System.out.print("Enter waist measurement: ");
                waist = input.nextFloat();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Number expexted - you entered text");
            }
        } while (!goodInput);

        goodInput = false;
        String comment = "";
        do {
            try {
                System.out.print("Enter comment: ");
                input.nextLine();
                comment = input.nextLine();
                goodInput = true;
            } catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Comment expected");
            }
        } while (!goodInput);
        Trainer currentTrainer = gym.searchTrainersByEmail(email);

        gym.trend(member, weight, waist, thigh, comment, currentTrainer);
    }

    public void memberDashboard(String email) {
        Member member = gym.findMemberEmail(email);
        DecimalFormat df2 = new DecimalFormat("#.##");
        System.out.println("---------");
        System.out.println("\uD83C\uDFCB " +member.getName() + "'s Dashboard \uD83C\uDFCB");
        System.out.print("\u2696 BMI:" + df2.format(GymUtility.calculateBMI(member, member.latestAssessment())));
        System.out.print(" | \u2696 BMI Categorgy:" + GymUtility.determineBMICategory(GymUtility.calculateBMI(member, member.latestAssessment())));
        System.out.print(" | \u2696 Ideal Body Weight:" + (GymUtility.isIdealBodyWeight(member, member.latestAssessment())?"Yes":"No"));
        System.out.println("\n---------");
        System.out.println("  1) \uD83D\uDC68 View your profile.");
        System.out.println("  2) \u21BB Update your profile.");
        System.out.println("  3) \uD83E\uDC82 Progress sub-menu - view assessments, progress by weight or waist measurement");
        System.out.println("  4) \uD83D\uDEA9 Request an assessment.");
        System.out.println("  5) \u2709" +gym.numberUnreadMessages(member) + " View your message inbox.");
        System.out.println("  6) \uD83C\uDD87 Delete your account.");
        System.out.println("  9) \uD83D\uDCBE Save & Logout.");
        System.out.println("  0) \uD83D\uDEAA Save & Exit Program.");
        System.out.println("---------");
        System.out.print("==>> ");


        boolean goodInput=false;
        int option=0;
        do {
            try {
                option = input.nextInt();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Number expected - you entered text");
            }
        } while (!goodInput);

        switch (option){
            case 1: System.out.println(member);
                    System.out.println("Press any key to continue...");
                    input.nextLine();
                    input.nextLine();
                    memberDashboard(email);
                break;
            case 2: updating=true;
            loggedInMember =member;
            input.nextLine();
                registerMenu(loggedInMember);
                break;
            case 3:
                progressSubMenu(member);
                 break;
            case 4:
                requestAssessment(member);
                break;
            case 5:
                System.out.println("---------");
                System.out.println(member.getName() + "'s Message Inbox: ");
                System.out.println("---------");
                System.out.println(gym.listMessages(member));
                System.out.println("Press any key to return to your dashboard...");
                input.nextLine();
                input.nextLine();
                memberDashboard(email);
            case 6:
                System.out.println("Are you sure you want to delete you gym account? y or n");
                input.nextLine();
                String choice = input.nextLine();
                if (choice.equals("y")) {
                    gym.removeMember(member);
                    logout();
                }
                else memberDashboard(email);
            case 9:
                logout();
                break;
            case 0:     exitCase();
                break;
            default:
                System.out.println("Invalid option entered: " + option);
                memberDashboard(email);
                break;
        }
    }

    private void progressSubMenu(Member member) {
        System.out.println("---------");
        System.out.println("Progress Sub Menu");
        System.out.println("---------");
        System.out.println("  1) \uD83D\uDDD2 View your assessments.");
        System.out.println("  2) \uD83C\uDFC3 View progress by weight.");
        System.out.println("  3) \uD83D\uDC68 View progress by waist.");
        System.out.println("  4) \uD83E\uDC80 Return to your dashboard.");
        System.out.println("  0) \uD83D\uDEAA Save & Exit Program");
        System.out.println("---------");
        System.out.print("==>> ");


        boolean goodInput=false;
        int option=0;
        do {
            try {
                option = input.nextInt();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Number expected - you entered text");
            }
        } while (!goodInput);

        switch (option) {
            case 1:
                System.out.println(gym.listAssessments(member));
                System.out.println("Press any key to continue...");
                input.nextLine();
                input.nextLine();
                progressSubMenu(member);
                break;
            case 2:
                System.out.println(gym.weightProgress(member));
                System.out.println("Press any key to return to the progress submenu...");
                input.nextLine();
                input.nextLine();
                progressSubMenu(member);
                break;
            case 3:
                System.out.println(gym.waistProgress(member));
                System.out.println("Press any key to return to the progress submenu...");
                input.nextLine();
                input.nextLine();
                progressSubMenu(member);
                break;
            case 4:
                memberDashboard(member.getEmail());
                break;
            case 0:     exitCase();
                break;
            default:
                System.out.println("Invalid option entered: " + option);
                progressSubMenu(member);
                break;
        }


        member.latestAssessment().isWeightTrend();

    }

    private void requestAssessment(Member member){
        if (member.getAssessments().lastEntry()!=null) System.out.println("Your most recent assessment was on: " + member.getAssessments().lastEntry().getKey());
        else System.out.println("You have never had an assessment!");
        System.out.println("Enter 'y' to request an assessment or any other key to cancel.");
        input.nextLine();
        boolean goodInput=false;
        String choice="";
        do {
            try {
                choice = input.nextLine();
                goodInput = true;
            }
            catch (Exception e) {
                input.nextLine(); //swallows Scanner bug
                System.err.println("Text expected");
            }
        } while (!goodInput);

        if (choice.equals("y")) {
            member.setAssessmentRequested(true);
            System.out.println("Assessmsnet requested.\nPress any key to return to your dashboard...");
            input.nextLine();
        }

        memberDashboard(member.getEmail());
    }

    private void logout(){
        try{
            gym.store();
        }
        catch(Exception e) {
            System.err.println("Error writing to file: " + e);
        }
        input.nextLine();
        trainer = false;
        member = false;
        trainerView = false;
        loggedInTrainer = null;
        updating = false;
        loggedInMember = null;
        run();
    }

    private void exitCase(){
        try{
            gym.store();
        }
        catch(Exception e) {
            System.err.println("Error writing to file: " + e);
        }
        System.out.println("                       Now Leaving                   ");
        displayLogo();
        System.out.println("Now leaving Jim's Gym... thank you and come again!");
        exit(0);

    }

    private void displayLogo() {
        System.out.println("            _          Jim's Gym          _         ");
        System.out.println("           | |                           | |  	");
        System.out.println("         =H| |========mn=======nm========| |H=	");
        System.out.println("           |_|        ( \\     / )        |_|	");
        System.out.println("                       \\ )(\")( /			");
        System.out.println("                       ( /\\_/\\ )			");
        System.out.println("                        \\ Jim /			");
        System.out.println("                         )=O=(			");
        System.out.println("                        /  _  \\			");
        System.out.println("                       /__/ \\__\\			");
        System.out.println("                       | |   | |			");
        System.out.println("                       |_|   |_|			");
        System.out.println("                       (_)   (_)			");
    }
}
