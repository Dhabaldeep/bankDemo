package java_5sem;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
//import static java_5sem.normalAccount.getTime;
 class bankAccount{
     protected double balance;
     protected double minimumBalance=1500;
     protected String name,address,gender,phoneNo, customerId;
     double B_R_I = 3.5;
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
    //    parameterized constructor
     bankAccount(String name, String address, String gender, String phoneNo){
         this.name=name;
         this.address=address;
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
         this.phoneNo="+91 "+phoneNo;
         balance=5000;
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
         System.out.print( "\n Name:: "+name );
         System.out.print( "\n Gender :: "+gender );
         System.out.print( "\n Phone No :: "+phoneNo);
         System.out.print( "\n Address :: "+address+"\n");
     }
     protected void storeDetails(int age, String accountType) throws IOException {
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
                     + accountType + "\nCustomer Id : "+customerId+"\nName : "
                     + name + "\nAge : " + age + "\nGender : " + gender + "\nPhone No : "
                     + phoneNo + "\nAddress : " + address + "\nAccount Balance : "
                     + balance+"\n"+date+"\n------------------------------");
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
    double E_R_I = B_R_I+0.05;
    int time=1;
    childrenAccount(String name, String address, String gender, String phoneNo) {
        super(name, address, gender,phoneNo);
//        balance=super.balance+1000;    //create separate balance for children class
        super.balance=super.balance+1000;
        super.customerId =generateId("C");
    }
}

//NormalAccount Class
class adultAccount extends bankAccount{
//    double balance=500;
    double E_R_I = B_R_I+0.03;
    int time=1;
    adultAccount(String name, String address, String gender, String phoneNo) {
        super(name, address, gender,phoneNo);
//        balance=super.balance+500;    //create separate balance for children class
        super.balance=super.balance+500;
        super.customerId=generateId("A");
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
    double E_R_I = B_R_I+1.0;
    int time=1;
    seniorAccount(String name, String address, String gender, String phoneNo) {
        super(name, address, gender,phoneNo);
//        balance=super.balance+2000;    //create separate balance for children class
        super.balance=super.balance+2000;
        super.customerId=generateId("S");
    }
}
//Main Class
public class bankDemo {
    public static int count = 0;
    public static void mainMenu(){
        System.out.println( "\n :**: MAIN MENU :**: ");
        System.out.println( " 1. Depositions");
        System.out.println( " 2. Withdrawal");
        System.out.println( " 3. Rate Of Interest");
        System.out.println( " 4. Display Balance");
        System.out.println( " 5. Show Customer Details");
        System.out.println( " 6. EXIT");
        System.out.print( " Enter your option :: ");
    }
    public static void main(String[] args) {
        Calendar cl1 = adultAccount.getTime(1);
        Scanner scanner = new Scanner(System.in);
        System.out.print(" Enter Your Name :: ");
        String name = scanner.nextLine();
        int age = 0;
        try {
            System.out.print(" Enter Your Age :: ");
            age = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(" Age must be a numeric value.");
            System.exit(1);
        }
        Scanner scanner1 = new Scanner(System.in);
        System.out.print(" Enter Your Gender (M/F/O) :: ");
        String gender = scanner1.nextLine();
        String phoneNo = null;
        try {
            System.out.print(" Enter your Phone No :: ");
            long phoneNo1 = scanner.nextLong();
            phoneNo=String.valueOf(phoneNo1);
            if(phoneNo.length()<10){
                throw new StringIndexOutOfBoundsException();
            }else if(phoneNo.length()>10) {
                throw new StringIndexOutOfBoundsException();
            }
        }
        catch(InputMismatchException e) {
            System.out.println("Phone no must be a numeric");
            return;
        }
        catch(Exception e) {
            System.out.println("Please put a valid Phone No..");
            return;
        }
        System.out.print(" Enter Your Address :: ");
        String address = scanner1.nextLine();

        if (age <= 18) {
            childrenAccount child = new childrenAccount(name, address, gender,phoneNo);
            int option;
            do {
                mainMenu();
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.print(" Enter your Deposit amount  :: ");
                        try {
                            double depositAmount = scanner.nextDouble();
                            child.balance = child.deposit(depositAmount);
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
                            child.balance = child.withdrawal(withdrawalAmount, child.balance);
                        } catch (InputMismatchException e) {
                            System.out.println("Amount must be a numeric value.");
//                            System.exit(1);
                            return;
                        }
                    }
                    case 3 ->
                            System.out.print(" The total amount of " + child.E_R_I + " % Interest in " + child.time + " year is :: " + child.interestCalculator(child.E_R_I, child.balance, child.time) + "\n");
                    case 4 -> child.display(child.balance);
                    case 5 -> child.showMyDetails();
                    default ->{
                        try {
                            child.storeDetails(age,"Children");
                        } catch (IOException e) {
                            System.out.println(" "+e);
                        }
//                        System.exit(1);
                        return;
                    }
                }
            } while (true);
        }

        else if (age < 60) {
            adultAccount adult = new adultAccount(name, address, gender, phoneNo);
            int option;
            do {
                mainMenu();
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.print(" Enter your Deposit amount  :: ");
                        try {
                            double depositAmount = scanner.nextDouble();
                            adult.balance = adult.deposit(depositAmount);
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
                                adult.balance = adult.withdrawal(withdrawalAmount, adult.balance);
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
                            System.out.print(" The total amount of " + adult.E_R_I + " % Interest in " + adult.time + " year is :: " + adult.interestCalculator(adult.E_R_I, adult.balance, adult.time) + "\n");
                    case 4 -> adult.display(adult.balance);
                    case 5 -> adult.showMyDetails();
                    default -> {
                        try {
                            adult.storeDetails(age, "Adult");
                        } catch (IOException e) {
                            System.out.println(" " + e);
                        }
//                        System.exit(1);
                        return;
                    }
                }
            } while (true);
        }

        else if (age >= 60) {
            seniorAccount senior = new seniorAccount(name, address, gender, phoneNo);
            int option;
            do {
                mainMenu();
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.print(" Enter your amount  :: ");
                        try{
                            double depositAmount = scanner.nextInt();
                            senior.balance = senior.deposit(depositAmount);
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
                            senior.balance = senior.withdrawal(withdrawalAmount, senior.balance);
                        } catch (InputMismatchException e) {
                            System.out.println("Amount must be a numeric value.");
//                            System.exit(1);
                            return;
                        }
                    }
                    case 3 ->
                        //System.out.print(" The Current Balance is :: "+senior.balance+"\n");
                            System.out.print(" The total amount of " + senior.E_R_I + " % Interest in " + senior.time + " year is :: " + senior.interestCalculator(senior.E_R_I, senior.balance, senior.time) + "\n");
                    case 4 -> senior.display(senior.balance);
                    case 5 -> senior.showMyDetails();
                    default ->{
                        try {
                            senior.storeDetails(age,"Senior");
                        } catch (IOException io) {
                            System.out.println(" "+io);
                        }
//                        System.exit(1);
                        return;
                    }
                }
            } while (true);
        }
    }
}
