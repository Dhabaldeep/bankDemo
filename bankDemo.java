package java_5sem;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
//import static java_5sem.normalAccount.getTime;
class bankAccount{
    private double balance;
    protected double minimumBalance=1500;
    private String name,address,gender,customerId;
    protected String countryCode="+91 ";
    private long phoneNo;
    private int age;
    private final double B_R_I;
    {
        B_R_I = 3.5;
    }
    bankAccount(){
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
        if(Objects.equals(gender, "M")||Objects.equals(gender, "m")){
            this.gender="Male";
        }
        else if(Objects.equals(gender, "F")||Objects.equals(gender, "f")){
            this.gender="Female";
        }
        else if(Objects.equals(gender, "O")||Objects.equals(gender, "o")){
            this.gender="Other";
        }
        else{
            this.gender="Invalid";
        }
    }
    protected long getPhoneNo(){
        return phoneNo;
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


    //    deposit method
    protected double deposit(double balance){
        this.balance = this.balance+balance;
        return this.balance;
    }
    //     withdrawal method
    protected double withdrawal(double balance, double accountBalance){
        double availableBalance = this.balance-balance;
        if(balance<accountBalance){
            if (availableBalance<=minimumBalance && balance>availableBalance){
                System.out.println("  Your minimum balance should be greater than "+minimumBalance+", after withdraw "+balance+" but your balance is "+availableBalance+" Withdraw canceled.");
            }
            else{
                this.balance = this.balance-balance;
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
        System.out.print( "\n Phone No :: "+countryCode+getPhoneNo());
        System.out.print( "\n Address :: "+getAddress()+"\n");
    }
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
                    + accountType + "\nCustomer Id : "+getCustomerId()+"\nName : "
                    + getName() + "\nAge : " + getAge() + "\nGender : " + getGender() + "\nPhone No : "
                    +countryCode+ getPhoneNo() + "\nAddress : " + getAddress() + "\nAccount Balance : "
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
                System.out.println(""+io);
            }
        }
    }
    public double interestCalculator(double E_R_I, double balance, int time){
        return (balance*E_R_I*time)/100+balance;
    }
}

//Children Account Class
class childrenAccount extends bankAccount{
    //    double balance=1000;
    double E_R_I = getB_R_I()+0.05;
    int time=1;
    childrenAccount(){
        deposit(1000);
        setCustomerId(generateId("C"));
    }
}

//NormalAccount Class
class adultAccount extends bankAccount{
    //    double balance=500;
    double E_R_I = getB_R_I()+0.03;
    int time=1;
    adultAccount(){
        deposit(500);
        setCustomerId(generateId("A"));
    }
    public static Calendar getTime(int add) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,add);
        return cal;
    }
}
//Senior citizen Account Class
class seniorAccount extends bankAccount{
    //     double balance=2000;
    double E_R_I = getB_R_I()+1.0;
    int time=1;
    seniorAccount(){
        deposit(2000);
        setCustomerId(generateId("S"));
    }
}

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
    protected static String getName(){
        System.out.print(" Enter Your Name :: ");
        return scanner.nextLine();
    }
    protected static int age = 0;
    protected static int getAge(){
        try {
            System.out.print(" Enter Your Age :: ");
            age = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(" Age must be a numeric value.");
            System.out.println(" Renter the Age again.");
            scanner.nextLine();
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
            System.out.println("Phone no must be a numeric.");
            System.out.println("Renter the Phone No again.");
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
public class bankDemo extends displayAcceptBankDetails{
    private static int count = 0;
    public static void main(String[] args) {
        Calendar cl1 = adultAccount.getTime(1);
        Scanner scanner = new Scanner(System.in);
        String name=getName();
        int age=getAge();
        String gender=getGender();
        long phoneNo = getPhoneNo();
        String address=getAddress();
        if (age >= 5 && age <= 18) {
            childrenAccount child = new childrenAccount();
            child.setName(name);
            child.setAddress(address);
            child.setAge(age);
            child.setGender(gender);
            child.setPhoneNo(phoneNo);
            int option;
            do {
                mainMenu();
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.print(" Enter your Deposit amount  :: ");
                        try {
                            double depositAmount = scanner.nextDouble();
                            child.setBalance(child.deposit(depositAmount));
                        } catch (InputMismatchException e) {
                            System.out.println("Amount must be a numeric value.");
//                             System.exit(1);
                            return;
                        }
                    }
                    case 2 -> {
                        System.out.print(" Enter your Withdrawal amount  :: ");
                        try {
                            double withdrawalAmount = scanner.nextDouble();
                            child.setBalance(child.withdrawal(withdrawalAmount, child.getBalance()));
                        } catch (InputMismatchException e) {
                            System.out.println("Amount must be a numeric value.");
//                            System.exit(1);
                            return;
                        }
                    }
                    case 3 ->
                            System.out.print(" The total amount of " + child.E_R_I + " % Interest in " + child.time + " year is :: " + child.interestCalculator(child.E_R_I, child.getBalance(), child.time) + "\n");
                    case 4 -> child.display(child.getBalance());
                    case 5 -> child.showMyDetails();
                    default ->{
                        try {
                            child.storeDetails("Children");
                        } catch (IOException e) {
                            System.out.println(" "+e);
                        }
//                        System.exit(1);
                        return;
                    }
                }
            } while (true);
        }

        else if (age < 61) {
            adultAccount adult = new adultAccount();
            adult.setName(name);
            adult.setAddress(address);
            adult.setAge(age);
            adult.setGender(gender);
            adult.setPhoneNo(phoneNo);
            int option;
            do {
                mainMenu();
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.print(" Enter your Deposit amount  :: ");
                        try {
                            double depositAmount = scanner.nextDouble();
                            adult.setBalance(adult.deposit(depositAmount));
                        } catch (InputMismatchException e) {
                            System.out.println("Amount must be a numeric value.");
//                            System.exit(1);
                            return;
                        }
                    }
                    case 2 -> {
                        Calendar cl = adultAccount.getTime(0);
                        if (cl.equals(cl1) || (cl.after(cl1))) {
                            count = 0;
                            cl1 = adultAccount.getTime(1);
                        }
                        if (count < 4) {
                            System.out.print(" Enter your Withdrawal amount  :: ");
                            try {
                                double withdrawalAmount = scanner.nextDouble();
                                adult.setBalance(adult.withdrawal(withdrawalAmount, adult.getBalance()));
                                count++;
                            } catch (InputMismatchException e) {
                                System.out.println("Amount must be a numeric value.");
//                                System.exit(1);
                                return;
                            }
                        } else {
                            System.out.println(" Withdrawal Cancel. You reached Your daily transaction limit...");
                        }
                    }
                    case 3 ->
                            System.out.print(" The total amount of " + adult.E_R_I + " % Interest in " + adult.time + " year is :: " + adult.interestCalculator(adult.E_R_I, adult.getBalance(), adult.time) + "\n");
                    case 4 -> adult.display(adult.getBalance());
                    case 5 -> adult.showMyDetails();
                    default -> {
                        try {
                            adult.storeDetails("Adult");
                        } catch (IOException e) {
                            System.out.println(" " + e);
                        }
//                        System.exit(1);
                        return;
                    }
                }
            } while (true);
        }

        else if (age<120) {
            seniorAccount senior = new seniorAccount();
            senior.setName(name);
            senior.setAddress(address);
            senior.setAge(age);
            senior.setGender(gender);
            senior.setPhoneNo(phoneNo);
            int option;
            do {
                mainMenu();
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.print(" Enter your amount  :: ");
                        try{
                            double depositAmount = scanner.nextInt();
                            senior.setBalance(senior.deposit(depositAmount));
                        } catch (InputMismatchException e) {
                            System.out.println("Amount must be a numeric value.");
//                            System.exit(1);
                            return;
                        }
                    }
                    case 2 -> {
                        System.out.print(" Enter your amount  :: ");
                        try{
                            double withdrawalAmount = scanner.nextInt();
                            senior.setBalance(senior.withdrawal(withdrawalAmount, senior.getBalance()));
                        } catch (InputMismatchException e) {
                            System.out.println("Amount must be a numeric value.");
//                            System.exit(1);
                            return;
                        }
                    }
                    case 3 ->
                        //System.out.print(" The Current Balance is :: "+senior.balance+"\n");
                            System.out.print(" The total amount of " + senior.E_R_I + " % Interest in " + senior.time + " year is :: " + senior.interestCalculator(senior.E_R_I, senior.getBalance(), senior.time) + "\n");
                    case 4 -> senior.display(senior.getBalance());
                    case 5 -> senior.showMyDetails();
                    default ->{
                        try {
                            senior.storeDetails("Senior");
                        } catch (IOException io) {
                            System.out.println(" "+io);
                        }
//                        System.exit(1);
                        return;
                    }
                }
            } while (true);
        }
        else{
            System.out.println("You are too old..");
        }
    }
}
