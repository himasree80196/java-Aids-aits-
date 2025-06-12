import java.util.*;

public class ManagementSystem{

    static HashMap<String, Integer> menu = new HashMap<>();
    static HashMap<String, Integer> itemCost = new HashMap<>();
    static ArrayList<String> menuItems = new ArrayList<>();
    static HashMap<String, Integer> inventory = new HashMap<>();
    static HashMap<String, List<String>> itemIngredients = new HashMap<>();
    static ArrayList<String> feedbackList = new ArrayList<>();
    static ArrayList<String> currentOrder = new ArrayList<>();
    static HashSet<String> customers = new HashSet<>();

    static int totalBill = 0;
    static boolean isBillPaid = false;
    static String currentCustomer = "";
    static int totalEarnings = 0;
    static int totalCosts = 0;
    static int employeeCount = 5;

    static boolean[] tables = new boolean[10];

    static HashMap<String, Integer> prepTimeMap = new HashMap<>();
    static Stack<Order> orderStack = new Stack<>();

    static class Order {
        String itemName;
        int prepTime;

        Order(String itemName, int prepTime) {
            this.itemName = itemName;
            this.prepTime = prepTime;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializeMenu();
        initializeInventory();
        initializePrepTimes();
        initializeItemCosts();
        initializeItemIngredients();

        while (true) {
            System.out.println("\nWelcome! Are you a:");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int role = sc.nextInt();
            sc.nextLine();

            if (role == 1) {
                handleCustomer(sc);
            } else if (role == 2) {
                handleAdmin(sc);
            } else {
                System.out.println("System closed.");
                break;
            }
        }

        sc.close();
    }

    // -------------------- Customer Logic --------------------
    public static void handleCustomer(Scanner sc) {
        int choice;
        boolean exit = false;

        System.out.print("Enter your name: ");
        currentCustomer = sc.nextLine();
        customers.add(currentCustomer);

        while (!exit) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View Menu");
            System.out.println("2. Place Order");
            System.out.println("3. Reserve Table");
            System.out.println("4. View & Pay Bill");
            System.out.println("5. Submit Feedback");
            System.out.println("6. Process Orders");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewMenu();
                case 2 -> placeOrder(sc);
                case 3 -> reserveTable(sc);
                case 4 -> viewBillAndPay(sc);
                case 5 -> submitFeedback(sc);
                case 6 -> processOrders();
                case 7 -> {
                    if (!isBillPaid && totalBill > 0) {
                        System.out.println("You must clear your bill before exiting.");
                    } else {
                        System.out.println("Thank you, " + currentCustomer + "! Visit Again.");
                        exit = true;
                    }
                }
                default -> System.out.println("Invalid choice!");
            }
        }

        currentCustomer = "";
    }

    public static void viewMenu() {
        System.out.println("\n--- Menu ---");
        for (String item : menuItems) {
            System.out.println(item + " - Rs." + menu.get(item));
        }
    }

    public static void placeOrder(Scanner sc) {
        System.out.print("Enter item to order: ");
        String item = sc.nextLine();
        int index = Collections.binarySearch(menuItems, item);

        if (index >= 0) {
            if (!hasSufficientIngredients(item)) {
                System.out.println("Sorry, not enough ingredients to prepare " + item);
                return;
            }

            currentOrder.add(item);
            totalBill += menu.get(item);
            totalCosts += itemCost.getOrDefault(item, 0);
            int time = prepTimeMap.getOrDefault(item, 10);
            orderStack.push(new Order(item, time));
            reduceInventory(item);
            System.out.println(item + " added to order. Prep time: " + time + " mins.");
        } else {
            System.out.println("Item not found!");
        }
    }

    public static void reserveTable(Scanner sc) {
        System.out.print("Enter table number (0-9): ");
        int tableNo = sc.nextInt();
        if (tableNo < 0 || tableNo >= tables.length) {
            System.out.println("Invalid table number!");
        } else if (tables[tableNo]) {
            System.out.println("Table already reserved!");
        } else {
            tables[tableNo] = true;
            System.out.println("Table " + tableNo + " reserved successfully.");
        }
    }

    public static void viewBillAndPay(Scanner sc) {
        System.out.println("\n--- Bill Summary ---");
        for (String item : currentOrder) {
            System.out.println(item + " - Rs." + menu.get(item));
        }
        System.out.println("Total: Rs." + totalBill);

        System.out.print("Do you want to pay the bill now? (yes/no): ");
        String pay = sc.nextLine().trim().toLowerCase();

        if (pay.equals("yes")) {
            isBillPaid = true;
            totalEarnings += totalBill;
            System.out.println("Bill paid successfully.");
        } else {
            System.out.println("You must pay the bill before leaving.");
        }
    }

    public static void submitFeedback(Scanner sc) {
        System.out.print("Enter your feedback: ");
        String fb = sc.nextLine();
        feedbackList.add(fb);
        System.out.println("Thanks for your feedback!");
    }

    public static void processOrders() {
        System.out.println("\n--- Order Processing ---");

        if (!isBillPaid){
            System.out.println("Cannot process orders. Bill was not paid!");
            return;
        }

        int totalTime = 0;

        if (orderStack.isEmpty()) {
            System.out.println("No orders to process.");
            return;
        }

        while (!orderStack.isEmpty()) {
            Order o = orderStack.pop();
            System.out.println("Preparing: " + o.itemName + " (Time: " + o.prepTime + " mins)");
            totalTime += o.prepTime;
        }

        if (totalTime > 0) {
            System.out.println("Total preparation time: " + totalTime + " mins.");
        }

        currentOrder.clear();
        totalBill = 0;
        isBillPaid = false;
    }

    // -------------------- Admin Logic --------------------
    public static void handleAdmin(Scanner sc) {
        int choice;

        while (true) {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. View Inventory");
            System.out.println("2. Total Customers");
            System.out.println("3. Total Employees");
            System.out.println("4. View Profit/Loss");
            System.out.println("5. Exit Admin");
            System.out.println("6. Update Inventory");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewInventory();
                case 2 -> System.out.println("Total customers: " + customers.size());
                case 3 -> System.out.println("Total employees: " + employeeCount);
                case 4 -> viewProfitLoss();
                case 5 -> { return; }
                case 6 -> updateInventory(sc);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    public static void viewInventory() {
        System.out.println("\n--- Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " units");
        }
    }

    public static void viewProfitLoss() {
        int profit = totalEarnings - totalCosts;
        System.out.println("Total Earnings: Rs." + totalEarnings);
        System.out.println("Total Costs: Rs." + totalCosts);
        System.out.println((profit >= 0 ? "Profit" : "Loss") + ": Rs." + Math.abs(profit));
    }

    public static void updateInventory(Scanner sc) {
        System.out.print("Enter inventory item name: ");
        String item = sc.nextLine();

        System.out.print("Enter quantity to add: ");
        int quantity = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (inventory.containsKey(item)) {
            inventory.put(item, inventory.get(item) + quantity);
            System.out.println("Updated " + item + " quantity to " + inventory.get(item));
        } else {
            inventory.put(item, quantity);
            System.out.println("Added new inventory item: " + item + " with quantity " + quantity);
        }
    }

    // -------------------- Init --------------------
    public static void initializeMenu() {
        menu.put("Pizza", 250);
        menu.put("Burger", 120);
        menu.put("Pasta", 200);
        menu.put("Fries", 100);
        menu.put("Coke", 50);
        menuItems.addAll(menu.keySet());
        Collections.sort(menuItems);
    }

    public static void initializePrepTimes() {
        prepTimeMap.put("Pizza", 15);
        prepTimeMap.put("Burger", 10);
        prepTimeMap.put("Pasta", 12);
        prepTimeMap.put("Fries", 8);
        prepTimeMap.put("Coke", 2);
    }

    public static void initializeInventory() {
        inventory.put("Cheese", 50);
        inventory.put("Buns", 100);
        inventory.put("Sauce", 70);
        inventory.put("Potato", 60);
        inventory.put("Soda", 80);
    }

    public static void initializeItemCosts() {
        itemCost.put("Pizza", 150);
        itemCost.put("Burger", 60);
        itemCost.put("Pasta", 100);
        itemCost.put("Fries", 50);
        itemCost.put("Coke", 20);
    }

    public static void initializeItemIngredients() {
        itemIngredients.put("Pizza", Arrays.asList("Cheese", "Sauce"));
        itemIngredients.put("Burger", Arrays.asList("Buns", "Cheese"));
        itemIngredients.put("Pasta", Arrays.asList("Sauce", "Cheese"));
        itemIngredients.put("Fries", Arrays.asList("Potato"));
        itemIngredients.put("Coke", Arrays.asList("Soda"));
    }

    public static boolean hasSufficientIngredients(String item) {
        List<String> ingredients = itemIngredients.get(item);
        if (ingredients == null) return false;
        for (String ing : ingredients) {
            if (inventory.getOrDefault(ing, 0) <= 0) return false;
        }
        return true;
    }

    public static void reduceInventory(String item) {
        List<String> ingredients = itemIngredients.get(item);
        if (ingredients == null) return;
        for (String ing : ingredients) {
            inventory.put(ing, inventory.get(ing) - 1);
        }
    }
}
