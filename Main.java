import java.sql.SQLOutput;
import java.time.*;
import java.util.*;
import java.util.regex.*;
import java.lang.*;
import java.io.*;
//=================================================LOGIN=================================================
public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<String> product = new ArrayList<>();
    static ArrayList<Integer> quantity = new ArrayList<>();
    static ArrayList<Integer> price = new ArrayList<>();
    static String loggedInUser;

    public static void main(String[] args) {

        while (true){
            System.out.println("===========================================================================");
            System.out.println("Commands:");
            System.out.println("1 - Register");
            System.out.println("2 - Login");
            System.out.println("3 - Exit");
            System.out.println("===========================================================================");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice){
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    System.out.println("Exiting the program.");
                    transactionShow();
                    return;
                default:
                    System.out.println("Invalid input (1,2, or 3 only). Try again.");
            }
        }

    }
    public static void register(){
        String username;
        String password;
        File obj = new File("user.txt");
        String verifyPassword;
        System.out.println("===========================================================================");
        System.out.println("CREATE AN ACCOUNT");
        System.out.println("Username must:");
        System.out.println("• be 5 - 15 characters long");
        System.out.println("• contain at least one uppercase and lowercase letters");
        System.out.println("• contain at least one number");
        System.out.println("Password must:");
        System.out.println("• be 8-20 characters long");
        System.out.println("• contain at least one uppercase and lowercase letters");
        System.out.println("• contain at least one number");
        System.out.println("===========================================================================");
        while(true) {
            System.out.print("Enter username: ");
            username = sc.nextLine();
            if(username.matches("^(?=.*[A-Za-z])(?=.*[0-9]).{5,15}$")){
                break;
            }else{
                System.out.println("Invalid username. Try again.");
            }
        }
        while(true) {
            System.out.print("Enter password: ");
            password = sc.nextLine();
            if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$")){
                break;
            }else{
                System.out.println("Invalid password. Try again.");
            }
        }
        while(true) {
            System.out.print("Verify password: ");
            verifyPassword = sc.nextLine();
            if(verifyPassword.equals(password)){
                System.out.println("Verification Successful.");
                break;
            }else{
                System.out.println("Verification failed. Try again.");
            }
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))){
            writer.write((username + "," + password));
            writer.newLine();
            System.out.println("Account created successfully.");
        }catch(Exception e){
            System.out.println("Failed to write account from file.");
        }
    }

    public static void login(){
        File file = new File("users.txt");
        if(!file.exists()){
            System.out.println("No account has been registered. Try registering first.");
            return;
        }
        System.out.println("LOGIN");
        System.out.print("Enter username: ");
        String usernameLogin = sc.nextLine();
        System.out.print("Enter password: ");
        String passwordLogin = sc.nextLine();

        String authentication = usernameLogin.concat("," + passwordLogin);
        System.out.println(authentication);
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String account;
            boolean found = false;
            while ((account = reader.readLine())!= null){
                if(account.equals(authentication)){
                    System.out.println("Account found. Logging in......");
                    loggedInUser = usernameLogin;
                    cashRegister();
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Account not found. Try again.");
            }
        }catch (Exception e){
            System.out.println("Failed to read from the file.");
        }
    }
//=================================================CASH REGISTER=================================================
public static void cashRegister(){
    boolean running = true;

    while (running) {
        displayCommands();
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                addOrder();
                break;
            case "2":
                editOrder();
                break;
            case "3":
                removeOrder();
                break;
            case "4":
                displayOrderSummary();
                break;
            case "5":
                payOrder();
                while(true){
                    System.out.print("Would you like to continue transaction?(y/n): ");
                    String choice2 = sc.nextLine();
                    if(choice2.equalsIgnoreCase("y")){
                        System.out.println("Transaction Loading....");
                        break;
                    }else if(choice2.equalsIgnoreCase("n")){
                        System.out.println("Terminating transaction....");
                        running = false;
                        break;
                    }else{
                        System.out.println("Invalid input. Try again(y/n only).");
                    }
                }
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }
    System.out.println("===========================================================================");
    System.out.println("\t\t\tTHANK YOU FOR COMING!!");
    System.out.println("===========================================================================");
}


    public static void displayMenu() {
        System.out.println("===========================================================================");
        System.out.println("Menu:");
        System.out.println("1-Cheese Burger--------------------------------55");
        System.out.println("2-Ham Burger-----------------------------------45");
        System.out.println("3-Orange Juice---------------------------------20");
        System.out.println("4-Fried Chicken--------------------------------35");
        System.out.println("5-Rice-----------------------------------------20");
        System.out.println("===========================================================================");
    }

    public static void displayCommands() {
        System.out.println("===========================================================================");
        System.out.println("Commands:");
        System.out.println("1-Add Order");
        System.out.println("2-Edit Order");
        System.out.println("3-Remove Order");
        System.out.println("4-View Order");
        System.out.println("5-Pay Order");
        System.out.println("===========================================================================");
    }

    public static void addOrder() {
        displayMenu();
        System.out.println("Enter the details for each product:");
        System.out.print("Enter product number: ");
        String prodNum = sc.nextLine();

        String prodName = "";
        int prodPrice = 0;

        switch (prodNum) {
            case "1":
                prodName = "Cheese Burger";
                prodPrice = 55;
                break;
            case "2":
                prodName = "Ham Burger";
                prodPrice = 45;
                break;
            case "3":
                prodName = "Orange Juice";
                prodPrice = 20;
                break;
            case "4":
                prodName = "Fried Chicken";
                prodPrice = 35;
                break;
            case "5":
                prodName = "Rice";
                prodPrice = 20;
                break;
            default:
                System.out.println("Invalid product number. Try again.");
                return;
        }

        int prodQuantity = 0;
        while (true){
            try{
                System.out.print("Enter quantity of " + prodName + ": ");
                prodQuantity = Integer.parseInt(sc.nextLine());
                break;
            }catch (NumberFormatException  e){
                System.out.println("Invalid number. try again.");
            }
        }
        product.add(prodName);
        price.add(prodPrice);
        quantity.add(prodQuantity);
        System.out.println("Order added successfully!");
        displayOrderSummary();
    }

    public static void displayOrderSummary() {
        System.out.println("===========================================================================");
        System.out.println("Order Summary:");
        int total = 0;

        for (int i = 0; i < product.size(); i++) {
            int itemTotal = quantity.get(i) * price.get(i);
            total += itemTotal;
            System.out.printf("%s x%d = %d\n", product.get(i), quantity.get(i), itemTotal);
        }

        System.out.println("Total: Php " + total);
        System.out.println("===========================================================================");
    }

    public static void editOrder() {
        displayOrderSummary();
        System.out.print("Enter product name to edit: ");
        String name = sc.nextLine();
        boolean found = false;

        for (int i = 0; i < product.size(); i++) {
            if (product.get(i).equalsIgnoreCase(name)) {
                found = true;
                displayMenu();
                System.out.print("Enter new product number: ");
                String newProdNum = sc.nextLine();

                String newProdName = "";
                int newProdPrice = 0;

                switch (newProdNum) {
                    case "1":
                        newProdName = "Cheese Burger";
                        newProdPrice = 55;
                        break;
                    case "2":
                        newProdName = "Ham Burger";
                        newProdPrice = 45;
                        break;
                    case "3":
                        newProdName = "Orange Juice";
                        newProdPrice = 20;
                        break;
                    case "4":
                        newProdName = "Fried Chicken";
                        newProdPrice = 35;
                        break;
                    case "5":
                        newProdName = "Rice";
                        newProdPrice = 20;
                        break;
                    default:
                        System.out.println("Invalid product number.");
                        return;
                }

                product.set(i, newProdName);
                price.set(i, newProdPrice);

                System.out.print("Enter new quantity: ");
                int newQuantity = sc.nextInt();
                sc.nextLine();
                quantity.set(i, newQuantity);
                displayOrderSummary();
                System.out.println("Order updated!");
                return;
            }
            if(!found){
                System.out.println("Product not found.");
            }
        }
        displayOrderSummary();
    }

    public static void removeOrder() {
        displayOrderSummary();
        System.out.print("Enter product name to remove: ");
        String name = sc.nextLine();
        boolean found = false;

        for (int i = 0; i < product.size(); i++) {
            if (product.get(i).equalsIgnoreCase(name)) {
                found = true;
                product.remove(i);
                quantity.remove(i);
                price.remove(i);
                System.out.println("Product removed!");
                return;
            }
        }

        if (!found) {
            System.out.println("Product not found.");
        }
        displayOrderSummary();
    }

    public static void payOrder() {
        int total = 0;
        for (int i = 0; i < product.size(); i++) {
            total += quantity.get(i) * price.get(i);
        }

        displayOrderSummary();
        System.out.println("Total Amount: Php " + total);
        System.out.print("Enter payment: Php ");
        double payment = sc.nextDouble();
        sc.nextLine();

        if (payment >= total) {
            System.out.printf("\nPayment successful! Change: Php %.2f\n", (payment - total));
            transactionDetails(total);
            product.clear();
            quantity.clear();
            price.clear();
        } else {
            System.out.println("Insufficient payment. Try again.");
        }
    }
    //=================================================TRANSACTION=================================================
    public static void transactionDetailsCreate(){
        try{
            File transaction = new File("transaction.txt");
            if(transaction.createNewFile()){
                System.out.println("File created: " + transaction.getName());
            }
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void transactionDetails(int total){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("transaction.txt", true))){
            writer.write("Transaction Date: " + LocalDateTime.now());
            writer.newLine();
            writer.write("Username: " + loggedInUser);
            writer.newLine();
            writer.write("Items: ");
            for(int i = 0; i < product.size(); i++){
                writer.write(String.format("\n- %s x%d @ Php %d each = Php %d",product.get(i), quantity.get(i), price.get(i), quantity.get(i) * price.get(i)));
            }
            writer.newLine();
            writer.write("Total Amount: Php " + total);
            writer.newLine();
            writer.write("===============================================");
            writer.newLine();
        }catch (IOException e){
            System.out.println("Failed to write to transaction file.");
        }
    }
    public static void transactionShow(){
        File obj= new File("transaction.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader("transaction.txt"))){
            String line;
            while((line = reader.readLine()) !=null){
                System.out.println(line);
            }
        }catch(IOException e){
            System.out.println("Failed to read from the file.");
        }
    }
}
