package java_5sem;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.Date;

class bankAccount{
    private double balance;
    protected double minimumBalance=1500;
    private String name,address,gender,customerId;
    protected String countryCode="+91";
    private long phoneNo;
    private int age;
    protected boolean loginStatus = false;
    protected String accountType = null;
    private final double B_R_I;
    {
        B_R_I = 3.5;
    }
    protected  bankAccount(){
        setBalance(5000);
    }
    public double getB_R_I() {
        return B_R_I;
    }
    public double getBalance(){
        return balance;
    }
    protected void setBalance(double balance){
        this.balance = balance;
    }

    public String getName(){
        return name;
    }
    protected void setName(String name){
        this.name = name;
    }
    protected String getAddress(){
        return address;
    }
    protected void setAddress(String address){
        this.address = address;
    }
    protected int getAge(){
        return age;
    }
    protected void setAge(int age){
        this.age = age;
    }
    protected String getGender(){
        return gender;
    }
    protected void setGender(String gender){
        if(Objects.equals(gender, "M")||Objects.equals(gender, "m") ||Objects.equals(gender, "Male")){
            this.gender="Male";
        }
        else if(Objects.equals(gender, "F")||Objects.equals(gender, "f")|| Objects.equals(gender, "Female")){
            this.gender="Female";
        }
        else if(Objects.equals(gender, "O")||Objects.equals(gender, "o")|| Objects.equals(gender, "Other")){
            this.gender="Other";
        }
        else{
            this.gender="Invalid";
        }
    }
    protected String getPhoneNo(){
        return countryCode+" "+phoneNo;
    }
    protected void setPhoneNo(long phoneNo){
        this.phoneNo = phoneNo;
    }
    protected String getCustomerId(){
        return customerId;
    }
    protected void setCustomerId(String customerId){
        this.customerId = customerId;
    }
    //     Method to generate the customer id
    protected String generateId(String type) {
        char[] num = {'0', '1', '2', '3', '4', '5', '6'};
        int len = 5;
        StringBuilder strBuilder = new StringBuilder();
        String s = null;
        for (int i = 0; i < len; i++) {
            s = String.valueOf(strBuilder.append(num[(int) Math.floor(Math.random() * 5)]));
        }
        return type+s;
    }
    static public void setDatabaseData(Double balance, String customerId){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "********");
            Statement statement = connection.createStatement();
            int count = statement.executeUpdate("update accountdetails set balance = '"+balance+"'where customerid = '"+customerId+"'");
            if (count >= 0) System.out.println(" Balance Updated.");
            else System.out.println(" No Record found..");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //    deposit method
    protected double deposit(double balance,String id){
        this.balance = this.balance+balance;
        setDatabaseData(this.balance,id);
        return this.balance;
    }
    //     withdrawal method
    protected double withdrawal(double balance, double accountBalance, String id){
        double availableBalance = this.balance-balance;
        if(balance<accountBalance){
            if (availableBalance<=minimumBalance && balance>availableBalance){
                System.out.println("  Your minimum balance should be greater than "+minimumBalance+", after withdraw "+balance+" but your balance is "+availableBalance+" Withdraw canceled.");
            }
            else{
                this.balance = this.balance-balance;
                setDatabaseData(this.balance,id);
            }
        }
        else{
            System.out.println("Insufficient balance.... cannot withdrawal");
        }
        return this.balance;
    }
    //     Display method
    public void display(double balance){
        System.out.println(" Your Current account balance is :: "+balance);
    }
    //     Display the Account holder Details
    public void showMyDetails(){
        System.out.print( "\n Name:: "+getName());
        System.out.print( "\n Name:: "+getAge());
        System.out.print( "\n Gender :: "+getGender() );
        System.out.print( "\n Phone No :: "+getPhoneNo());
        System.out.print( "\n Address :: "+getAddress()+"\n");
    }
    //      Display and store the Account Holder Details
    protected void storeDetails(String accountType) throws IOException {
        FileWriter fileWriter = null;
        String str ;
        Date date = new Date();
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        try {
            str = "Details.txt";
            fileWriter = new FileWriter(str, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
            printWriter.println("\nAccount Holder Details\n" + "Account Type : "
                    + accountType + "\nCustomer I'd : "+getCustomerId()+"\nName : "
                    + getName() + "\nAge : " + getAge() + "\nGender : " + getGender() + "\nPhone No : "
                    +getPhoneNo() + "\nAddress : " + getAddress() + "\nAccount Balance : "
                    + getBalance()+"\n"+date+"\n------------------------------");
            System.out.println(" Your Account Details is successfully store into " + str);
            printWriter.flush();
        } finally {
            try {
                assert printWriter != null;
                printWriter.close();
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException io) {
                System.out.println(" "+io);
            }
        }
    }
    //    Calculate the Simple Interest of yur Balance
    public double interestCalculator(double E_R_I, double balance, int time){
        return (balance*E_R_I*time)/100+balance;
    }
    static public String getUserIdPassword(String userId){
        Scanner scan = new Scanner(System.in);
        System.out.println("*** Create Account ***");
        System.out.println(" Your User id is :: "+userId);
        System.out.print(" Enter Your Password : ");
        return scan.nextLine();
    }
}

//Children Account Class
class childrenAccount extends bankAccount{
    double E_R_I = getB_R_I()+0.05;
    int time=1;
    childrenAccount(){
        setCustomerId(generateId("C"));
        String customerId = getCustomerId();
        String password =getUserIdPassword(customerId);
        deposit(1000,customerId);
        Date date = new Date();
        try {
            boolean yes = JDBC_adminInsert.adminDataInsert(customerId,password, String.valueOf(date));
            if(yes) System.out.println(" Successfully account created.");
            else System.out.println(" Try later.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    childrenAccount(Boolean status,String type){
        loginStatus = status;
        accountType = type;
    }
}

//NormalAccount Class
class adultAccount extends bankAccount{
    double E_R_I = getB_R_I()+0.03;
    int time=1;
    adultAccount(){
        setCustomerId(generateId("A"));
        String customerId=getCustomerId();
        String password=getUserIdPassword(customerId);
        deposit(500,customerId);
        Date date = new Date();
        try{
            boolean yes = JDBC_adminInsert.adminDataInsert(customerId,password, String.valueOf(date));
            if(yes) System.out.println(" Successfully account created.");
            else System.out.println(" Try Later.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    adultAccount(Boolean status,String type){
        loginStatus = status;
        accountType = type;
    }
    public static Calendar getTime(int add) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,add);
        return cal;
    }
}
//Senior citizen Account Class
class seniorAccount extends bankAccount{
    double E_R_I = getB_R_I()+1.0;
    int time=1;
    seniorAccount(){
        setCustomerId(generateId("S"));
        String customerId = getCustomerId();
        String password = getUserIdPassword(customerId);
        deposit(2000,customerId);
        Date date = new Date();
        try{
            boolean yes = JDBC_adminInsert.adminDataInsert(customerId,password, String.valueOf(date));
            if(yes) System.out.println(" Successfully account created.");
            else System.out.println(" Try Later.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    seniorAccount(Boolean status,String type){
        loginStatus = status;
        accountType = type;
    }
}

//Display Details class
class displayAcceptBankDetails{
    static Scanner scanner = new Scanner(System.in);
    static Scanner scanner1 = new Scanner(System.in);
    protected static void mainMenu(){
        System.out.println( "\n :**: MAIN MENU :**: ");
        System.out.println( " 1. Depositions");
        System.out.println( " 2. Withdrawal");
        System.out.println( " 3. Rate Of Interest");
        System.out.println( " 4. Display Balance");
        System.out.println( " 5. Show Customer Details");
        System.out.println( " 6. EXIT");
        System.out.print( " Enter your option :: ");
    }
    protected static void chooseOption(){
        System.out.println( "\n :**: ACCOUNT MENU :**: ");
        System.out.println( " 1. Create Account");
        System.out.println( " 2. Go to your Account");
        System.out.println( " 3. EXIT");
        System.out.print( " Enter your option :: ");
    }
    protected static void chooseAccountType(){
        System.out.println( "\n :**: ACCOUNT TYPE :**: ");
        System.out.println( " 1. Children Account");
        System.out.println( " 2. Adult Account");
        System.out.println( " 3. Senior Citizen Account");
        System.out.println( " 4. EXIT");
        System.out.print( " Enter your option :: ");
    }
    protected static String getName(){
        System.out.print(" Enter Your Name :: ");
        return scanner1.nextLine();
    }
    protected static int age = 0;
    protected static int getAge(){
        try {
            System.out.print(" Enter Your Age :: ");
            age = scanner.nextInt();
            if((age<5) || (age>150)){
                throw new Exception();
            }
        } catch (InputMismatchException e) {
            System.out.println(" Age must be a numeric value.");
            System.out.println(" Renter the Age again.");
            scanner.nextLine();
            getAge();
        } catch (Exception e) {
            System.out.println(" Renter the age ");
            System.out.println(" you can't create an account those age are less than 5 or greater than 150.");
            getAge();
        }
        return age;
    }
    protected static String getGender() {
        System.out.print(" Enter Your Gender (M/F/O) :: ");
        return scanner1.nextLine();
    }
    protected static long phoneNo = 0;
    protected static long getPhoneNo(){
        try {
            System.out.print(" Enter your Phone No :: ");
            phoneNo = scanner.nextLong();
            if(String.valueOf(phoneNo).length() != 10){
                throw new StringIndexOutOfBoundsException();
            }
        }
        catch(InputMismatchException e) {
            System.out.println(" Phone no must be a numeric.");
            System.out.println(" Renter the Phone No again.");
            scanner.nextLine();
            getPhoneNo();
        }
        catch(Exception e) {
            System.out.println("Please put a valid Phone No..");
            getPhoneNo();
        }
        return phoneNo;
    }
    protected static String getAddress(){
        System.out.print(" Enter Your Address :: ");
        return scanner1.nextLine();
    }

}

//Main Class
public class bankDemo extends displayAcceptBankDetails {
    private static int count = 0;
    private static final int cLimit=8;
    private static final int aLimit=5;
    private static final int sLimit=10;
    private static final String duration = "Minute";

    public static void main(String[] args) {


        Calendar cl1 = adultAccount.getTime(1);
        Scanner scanner2 = new Scanner(System.in);
        int chooseOption;
        do {
            chooseOption();
            chooseOption = scanner2.nextInt();
            switch (chooseOption) {
                case 1 -> {
                    // Another switch case to create types of account
                    int accountType;
                    do {
                        chooseAccountType();
                        accountType = scanner2.nextInt();

                        switch (accountType) {

                            /* ******* Create the Children Account ******** */

                            case 1 -> {
//                                Children Account
                                childrenAccount child = new childrenAccount();
                                String name = getName();
                                int age = getAge();
                                if(age<= 18) {
                                    String gender = getGender();
                                    long phoneNo = getPhoneNo();
                                    String address = getAddress();
                                    child.setName(name);
                                    child.setAddress(address);
                                    child.setAge(age);
                                    child.setGender(gender);
                                    child.setPhoneNo(phoneNo);
                                    try {
                                        Date date = new Date();
                                        boolean success = JDBC_Insert.dataInsert("Children",child.getCustomerId(), child.getName(), child.getAge(), child.getGender(), child.getPhoneNo(), child.getAddress(), child.getBalance(), String.valueOf(date));
                                        if(success) System.out.println("Data Entered Successfully.");
                                        else System.out.println("Something went wrong.");
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    int option;
                                    do {
                                        mainMenu();
                                        option = scanner.nextInt();
                                        switch (option) {
                                            case 1 -> {
                                                System.out.print(" Enter your Deposit amount  :: ");
                                                try {
                                                    double depositAmount = scanner.nextDouble();
                                                    child.setBalance(child.deposit(depositAmount,child.getCustomerId()));
//                                                        setDatabaseData(child.getBalance(),child.getCustomerId());
                                                } catch (InputMismatchException e) {
                                                    System.out.println(" Amount must be a numeric value.");
                                                    return;
                                                }
                                            }
                                            case 2 -> {
                                                Calendar cl = adultAccount.getTime(0);
                                                if (cl.equals(cl1) || (cl.after(cl1))) {
                                                    count = 0;
                                                    cl1 = adultAccount.getTime(1);

                                                }
                                                if (count < cLimit) {
                                                    System.out.print(" Enter your Withdrawal amount  :: ");
                                                    try {
                                                        double withdrawalAmount = scanner.nextDouble();
                                                        child.setBalance(child.withdrawal(withdrawalAmount, child.getBalance(),child.getCustomerId()));
//                                                            setDatabaseData(child.getBalance(),child.getCustomerId());
                                                        count++;
                                                    } catch (InputMismatchException e) {
                                                        System.out.println(" Amount must be a numeric value.");
                                                        return;
                                                    }
                                                } else {
                                                    System.out.println(" Withdrawal Cancel. You reached Your daily transaction limit... Try after "+1+" " +duration+" later.");
                                                }
                                            }
                                            case 3 -> System.out.print(" The total amount of " + child.E_R_I + " % Interest in " + child.time + " year is :: " + child.interestCalculator(child.E_R_I, child.getBalance(), child.time) + "\n");
                                            case 4 -> child.display(child.getBalance());
                                            case 5 -> child.showMyDetails();
                                            case 6 -> {
                                                try {
                                                    child.storeDetails("Children");
                                                } catch (IOException e) {
                                                    System.out.println(" " + e);
                                                }
                                                return;
                                            }
                                            default -> System.out.println("Choose Correct Option.");
                                        }
                                    } while (true);
                                }
                                else{
                                    System.out.println(" Your age doesn't consider to create a Children Account.");
                                }
                            }

                            /* ******* Create the Adult Account ******** */

                            case 2 -> {
//                                AdultAccount
                                adultAccount adult = new adultAccount();
                                String name = getName();
                                int age = getAge();
                                if(age<= 18) {
                                    System.out.println(" Your age doesn't consider to create a Adult Account. Instead of create Adult Account try Children A/C");
                                }
                                else if(age<=60) {
                                    String gender = getGender();
                                    long phoneNo = getPhoneNo();
                                    String address = getAddress();
                                    adult.setName(name);
                                    adult.setAddress(address);
                                    adult.setAge(age);
                                    adult.setGender(gender);
                                    adult.setPhoneNo(phoneNo);
                                    try {
                                        Date date = new Date();
                                        boolean success = JDBC_Insert.dataInsert("Adult",adult.getCustomerId(), adult.getName(), adult.getAge(), adult.getGender(), adult.getPhoneNo(), adult.getAddress(), adult.getBalance(), String.valueOf(date));
                                        if(success) System.out.println("Data Entered Successfully.");
                                        else System.out.println("Something went wrong.");
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    int option;
                                    do {
                                        mainMenu();
                                        option = scanner.nextInt();
                                        switch (option) {
                                            case 1 -> {
                                                System.out.print(" Enter your Deposit amount  :: ");
                                                try {
                                                    double depositAmount = scanner.nextDouble();
                                                    adult.setBalance(adult.deposit(depositAmount,adult.getCustomerId()));
//                                                        setDatabaseData(adult.getBalance(),adult.getCustomerId());
                                                } catch (InputMismatchException e) {
                                                    System.out.println(" Amount must be a numeric value.");
                                                    return;
                                                }
                                            }
                                            case 2 -> {
                                                Calendar cl = adultAccount.getTime(0);
                                                if (cl.equals(cl1) || (cl.after(cl1))) {
                                                    count = 0;
                                                    cl1 = adultAccount.getTime(1);
                                                }
                                                if (count < aLimit) {
                                                    System.out.print(" Enter your Withdrawal amount  :: ");
                                                    try {
                                                        double withdrawalAmount = scanner.nextDouble();
                                                        adult.setBalance(adult.withdrawal(withdrawalAmount, adult.getBalance(),adult.getCustomerId()));
//                                                            setDatabaseData(adult.getBalance(),adult.getCustomerId());
                                                        count++;
                                                    } catch (InputMismatchException e) {
                                                        System.out.println(" Amount must be a numeric value.");
                                                        return;
                                                    }
                                                } else {
                                                    System.out.println(" Withdrawal Cancel. You reached Your daily transaction limit... Try after "+1+" " +duration+" later.");
                                                }
                                            }
                                            case 3 -> System.out.print(" The total amount of " + adult.E_R_I + " % Interest in " + adult.time + " year is :: " + adult.interestCalculator(adult.E_R_I, adult.getBalance(), adult.time) + "\n");
                                            case 4 -> adult.display(adult.getBalance());
                                            case 5 -> adult.showMyDetails();
                                            case 6 -> {
                                                try {
                                                    adult.storeDetails("Adult");
                                                } catch (IOException e) {
                                                    System.out.println(" " + e);
                                                }
                                                return;
                                            }
                                            default -> System.out.println("Choose Correct Option.");
                                        }
                                    } while (true);
                                }
                                else{
                                    System.out.println(" Your age doesn't consider to create a Adult Account.");
                                }

                            }

                            /* ******* Create the Senior Citizen Account ******** */

                            case 3 -> {
//                                SeniorCitizenAc
                                seniorAccount senior = new seniorAccount();
                                String name = getName();
                                int age = getAge();
                                if(age<=60) {
                                    System.out.println(" Your age doesn't consider to create a Senior Citizen Account. try Adult A/C or Children A/C");
                                }
                                else {
                                    String gender = getGender();
                                    long phoneNo = getPhoneNo();
                                    String address = getAddress();
                                    senior.setName(name);
                                    senior.setAddress(address);
                                    senior.setAge(age);
                                    senior.setGender(gender);
                                    senior.setPhoneNo(phoneNo);
                                    try {
                                        Date date = new Date();
                                        boolean success = JDBC_Insert.dataInsert("Senior Citizen",senior.getCustomerId(), senior.getName(), senior.getAge(), senior.getGender(), senior.getPhoneNo(), senior.getAddress(), senior.getBalance(), String.valueOf(date));
                                        if(success) System.out.println("Data Entered Successfully.");
                                        else System.out.println("Something went wrong.");
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    int option;
                                    do {
                                        mainMenu();
                                        option = scanner.nextInt();
                                        switch (option) {
                                            case 1 -> {
                                                System.out.print(" Enter your amount  :: ");
                                                try {
                                                    double depositAmount = scanner.nextInt();
                                                    senior.setBalance(senior.deposit(depositAmount,senior.getCustomerId()));
//                                                        setDatabaseData(senior.getBalance(),senior.getCustomerId());
                                                } catch (InputMismatchException e) {
                                                    System.out.println(" Amount must be a numeric value.");
                                                    return;
                                                }
                                            }
                                            case 2 -> {
                                                Calendar cl = adultAccount.getTime(0);
                                                if (cl.equals(cl1) || (cl.after(cl1))) {
                                                    count = 0;
                                                    cl1 = adultAccount.getTime(1);
                                                }
                                                if (count < sLimit) {
                                                    System.out.print(" Enter your Withdrawal amount  :: ");
                                                    try {
                                                        double withdrawalAmount = scanner.nextDouble();
                                                        senior.setBalance(senior.withdrawal(withdrawalAmount, senior.getBalance(),senior.getCustomerId()));
//                                                            setDatabaseData(senior.getBalance(),senior.getCustomerId());
                                                        count++;
                                                    } catch (InputMismatchException e) {
                                                        System.out.println(" Amount must be a numeric value.");
                                                        return;
                                                    }
                                                } else {
                                                    System.out.println(" Withdrawal Cancel. You reached Your daily transaction limit... Try after "+1+" " +duration+" later.");
                                                }
                                            }
                                            case 3 ->
                                                //System.out.print(" The Current Balance is :: "+senior.balance+"\n");
                                                    System.out.print(" The total amount of " + senior.E_R_I + " % Interest in " + senior.time + " year is :: " + senior.interestCalculator(senior.E_R_I, senior.getBalance(), senior.time) + "\n");
                                            case 4 -> senior.display(senior.getBalance());
                                            case 5 -> senior.showMyDetails();
                                            case 6 -> {
                                                try {
                                                    senior.storeDetails("Senior");
                                                } catch (IOException io) {
                                                    System.out.println(" " + io);
                                                }
                                                return;
                                            }
                                            default -> System.out.println("Choose Correct Option.");
                                        }
                                    } while (true);
                                }
                            }
                            case 4 ->{
                                return;
                            }
                            default -> System.out.println(" Choose Correct Option");
                        }
                    } while (true);
                }
                case 2 -> {
//                    System.out.println(" There is nothing there. I am updated soon.");
                    Scanner scanner6 = new Scanner(System.in);
                    System.out.print(" Enter the Username : ");
                    String username = scanner6.nextLine();
                    System.out.print(" Enter the Password :: ");
                    String password = scanner6.nextLine();
                    try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "********");
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("select * from account where customerid ='"+username+"'");

                        String customer_Id = null;
                        String getPassword = null;
                        while(rs.next()){
                            customer_Id = rs.getString(1);
                            getPassword = rs.getString(2);
                        }
                        if(customer_Id != null) {
                            if (password.equals(getPassword)) {
                                try {
                                    ResultSet resultSet = statement.executeQuery("select * from accountdetails where customerid ='" + username + "'");
                                    String id = null;
                                    String accountType = null;
                                    String name = null;
                                    int age = 0;
                                    String gender = null;
                                    String phone_No = null;
                                    String address = null;
                                    double balance = 0.0;
                                    while (resultSet.next()) {
                                        accountType = resultSet.getString(1);
                                        id = resultSet.getString(2);
                                        name = resultSet.getString(3);
                                        age = resultSet.getInt(4);
                                        gender = resultSet.getString(5);
                                        phone_No = resultSet.getString(6);
                                        address= resultSet.getString(7);
                                        balance = resultSet.getInt(8);
                                    }
                                    System.out.println(" Welcome " + name);
                                    System.out.println(" Welcome " + phone_No);
                                    System.out.println(" Welcome " + gender);

//                                    children Account
                                    if(age<= 18) {
                                        childrenAccount child = new childrenAccount(true,accountType);
                                        child.setCustomerId(id);
                                        child.setName(name);
                                        child.setAddress(address);
                                        child.setAge(age);
                                        child.setGender(gender);
                                        child.setBalance(balance);
                                        if (phone_No != null) {
                                            child.setPhoneNo(Long.parseLong(phone_No.split(" ")[1]));
                                        }
                                        int option;
                                        do {
                                            mainMenu();
                                            option = scanner.nextInt();
                                            switch (option) {
                                                case 1 -> {
                                                    System.out.print(" Enter your Deposit amount  :: ");
                                                    try {
                                                        double depositAmount = scanner.nextDouble();
                                                        child.setBalance(child.deposit(depositAmount,child.getCustomerId()));
//                                                        setDatabaseData(child.getBalance(),child.getCustomerId());
                                                    } catch (InputMismatchException e) {
                                                        System.out.println(" Amount must be a numeric value.");
                                                        return;
                                                    }
                                                }
                                                case 2 -> {
                                                    Calendar cl = adultAccount.getTime(0);
                                                    if (cl.equals(cl1) || (cl.after(cl1))) {
                                                        count = 0;
                                                        cl1 = adultAccount.getTime(1);

                                                    }
                                                    if (count < cLimit) {
                                                        System.out.print(" Enter your Withdrawal amount  :: ");
                                                        try {
                                                            double withdrawalAmount = scanner.nextDouble();
                                                            child.setBalance(child.withdrawal(withdrawalAmount, child.getBalance(),child.getCustomerId()));
//                                                            setDatabaseData(child.getBalance(),child.getCustomerId());
                                                            count++;
                                                        } catch (InputMismatchException e) {
                                                            System.out.println(" Amount must be a numeric value.");
                                                            return;
                                                        }
                                                    } else {
                                                        System.out.println(" Withdrawal Cancel. You reached Your daily transaction limit... Try after "+1+" " +duration+" later.");
                                                    }
                                                }
                                                case 3 -> System.out.print(" The total amount of " + child.E_R_I + " % Interest in " + child.time + " year is :: " + child.interestCalculator(child.E_R_I, child.getBalance(), child.time) + "\n");
                                                case 4 -> child.display(child.getBalance());
                                                case 5 -> child.showMyDetails();
                                                case 6 -> {
                                                    try {
                                                        child.storeDetails("Children");
                                                    } catch (IOException e) {
                                                        System.out.println(" " + e);
                                                    }
                                                    return;
                                                }
                                                default -> System.out.println("Choose Correct Option.");
                                            }
                                        } while (true);
                                    }

                                    /* ******* Create the Adult Account ******** */

                                    else if(age<=60) {
//                                    AdultAccount
                                        adultAccount adult = new adultAccount(true,accountType);
                                        adult.setCustomerId(id);
                                        adult.setName(name);
                                        adult.setAddress(address);
                                        adult.setAge(age);
                                        adult.setGender(gender);
                                        adult.setBalance(balance);
                                        if (phone_No != null) {
                                            adult.setPhoneNo(Long.parseLong(phone_No.split(" ")[1]));
                                        }
                                        int option;
                                        do {
                                            mainMenu();
                                            option = scanner.nextInt();
                                            switch (option) {
                                                case 1 -> {
                                                    System.out.print(" Enter your Deposit amount  :: ");
                                                    try {
                                                        double depositAmount = scanner.nextDouble();
                                                        adult.setBalance(adult.deposit(depositAmount,adult.getCustomerId()));
//                                                        setDatabaseData(adult.getBalance(),adult.getCustomerId());
                                                    } catch (InputMismatchException e) {
                                                        System.out.println(" Amount must be a numeric value.");
                                                        return;
                                                    }
                                                }
                                                case 2 -> {
                                                    Calendar cl = adultAccount.getTime(0);
                                                    if (cl.equals(cl1) || (cl.after(cl1))) {
                                                        count = 0;
                                                        cl1 = adultAccount.getTime(1);
                                                    }
                                                    if (count < aLimit) {
                                                        System.out.print(" Enter your Withdrawal amount  :: ");
                                                        try {
                                                            double withdrawalAmount = scanner.nextDouble();
                                                            adult.setBalance(adult.withdrawal(withdrawalAmount, adult.getBalance(),adult.getCustomerId()));
//                                                            setDatabaseData(adult.getBalance(),adult.getCustomerId());
                                                            count++;
                                                        } catch (InputMismatchException e) {
                                                            System.out.println(" Amount must be a numeric value.");
                                                            return;
                                                        }
                                                    } else {
                                                        System.out.println(" Withdrawal Cancel. You reached Your daily transaction limit... Try after "+1+" " +duration+" later.");
                                                    }
                                                }
                                                case 3 -> System.out.print(" The total amount of " + adult.E_R_I + " % Interest in " + adult.time + " year is :: " + adult.interestCalculator(adult.E_R_I, adult.getBalance(), adult.time) + "\n");
                                                case 4 -> adult.display(adult.getBalance());
                                                case 5 -> adult.showMyDetails();
                                                case 6 -> {
                                                    try {
                                                        adult.storeDetails("Adult");
                                                    } catch (IOException e) {
                                                        System.out.println(" " + e);
                                                    }
                                                    return;
                                                }
                                                default -> System.out.println("Choose Correct Option.");
                                            }
                                        } while (true);
                                    }

                                    /* ******* Create the Senior Citizen Account ******** */

                                    else if(age<=120) {
//                                      SeniorCitizenAc
                                        seniorAccount senior = new seniorAccount(true,accountType);
                                        senior.setCustomerId(id);
                                        senior.setName(name);
                                        senior.setAddress(address);
                                        senior.setAge(age);
                                        senior.setGender(gender);
                                        senior.setBalance(balance);
                                        if (phone_No != null) {
                                            senior.setPhoneNo(Long.parseLong(phone_No.split(" ")[1]));
                                        }
                                        int option;
                                        do {
                                            mainMenu();
                                            option = scanner.nextInt();
                                            switch (option) {
                                                case 1 -> {
                                                    System.out.print(" Enter your amount  :: ");
                                                    try {
                                                        double depositAmount = scanner.nextInt();
                                                        senior.setBalance(senior.deposit(depositAmount,senior.getCustomerId()));
//                                                        setDatabaseData(senior.getBalance(),senior.getCustomerId());
                                                    } catch (InputMismatchException e) {
                                                        System.out.println(" Amount must be a numeric value.");
                                                        return;
                                                    }
                                                }
                                                case 2 -> {
                                                    Calendar cl = adultAccount.getTime(0);
                                                    if (cl.equals(cl1) || (cl.after(cl1))) {
                                                        count = 0;
                                                        cl1 = adultAccount.getTime(1);
                                                    }
                                                    if (count < sLimit) {
                                                        System.out.print(" Enter your Withdrawal amount  :: ");
                                                        try {
                                                            double withdrawalAmount = scanner.nextDouble();
                                                            senior.setBalance(senior.withdrawal(withdrawalAmount, senior.getBalance(),senior.getCustomerId()));
//                                                            setDatabaseData(senior.getBalance(),senior.getCustomerId());
                                                            count++;
                                                        } catch (InputMismatchException e) {
                                                            System.out.println(" Amount must be a numeric value.");
                                                            return;
                                                        }
                                                    } else {
                                                        System.out.println(" Withdrawal Cancel. You reached Your daily transaction limit... Try after "+1+" " +duration+" later.");
                                                    }
                                                }
                                                case 3 ->
                                                    //System.out.print(" The Current Balance is :: "+senior.balance+"\n");
                                                        System.out.print(" The total amount of " + senior.E_R_I + " % Interest in " + senior.time + " year is :: " + senior.interestCalculator(senior.E_R_I, senior.getBalance(), senior.time) + "\n");
                                                case 4 -> senior.display(senior.getBalance());
                                                case 5 -> senior.showMyDetails();
                                                case 6 -> {
                                                    try {
                                                        senior.storeDetails("Senior");
                                                    } catch (IOException io) {
                                                        System.out.println(" " + io);
                                                    }
                                                    return;
                                                }
                                                default -> System.out.println("Choose Correct Option.");
                                            }
                                        } while (true);
                                    }
                                    else{
                                        System.out.println(" Something went wrong.");
                                    }

                                }catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            else System.out.println(" Invalid  Username or password.");
                        }
                        else System.out.println(" Invalid  Username or password.");
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println(" Choose correct Option");
            }
        } while (true);
    }
}
