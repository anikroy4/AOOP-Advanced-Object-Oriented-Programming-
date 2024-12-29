package Answer1;
import java.util.*;


class Item {
    private String name;
    private int quantity;


    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return name + " (" + quantity + ")";
    }
}


class Shop {
    private List<Item> inventory;
    private List<String> log;


    public Shop() {
        inventory = new ArrayList<>();
        log = new ArrayList<>();
        // Sample inventory
        inventory.add(new Item("Apple", 50));
        inventory.add(new Item("Banana", 30));
        inventory.add(new Item("Orange", 20));
    }


    public List<Item> getInventory() {
        return inventory;
    }


    public void addTransaction(String transaction) {
        log.add(transaction);
    }


    public List<String> getLog() {
        return log;
    }


    public void showInventory() {
        System.out.println("Shop Inventory:");
        for (Item item : inventory) {
            System.out.println(item);
        }
    }
}


class Customer {
    private String name;
    private List<Item> inventory;


    public Customer(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
    }


    public String getName() {
        return name;
    }


    public List<Item> getInventory() {
        return inventory;
    }


    public void showInventory() {
        System.out.println(name + "'s Inventory:");
        for (Item item : inventory) {
            System.out.println(item);
        }
    }


    public void addItem(Item item) {
        inventory.add(item);
    }
}


public class ShopPortal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Shop shop = new Shop();
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        Customer customer = new Customer(customerName);


        boolean running = true;


        while (running) {
            System.out.println("Options:");
            System.out.println("1. Buy");
            System.out.println("2. Sell");
            System.out.println("3. Showlog");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline


            switch (option) {
                case 1:
                    shop.showInventory();
                    System.out.print("Enter item name to buy: ");
                    String itemName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    buyItem(shop, customer, itemName, quantity);
                    break;
                case 2:
                    customer.showInventory();
                    System.out.print("Enter item name to sell: ");
                    itemName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    quantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    sellItem(shop, customer, itemName, quantity);
                    break;
                case 3:
                    showLog(shop);
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }


        scanner.close();
    }


    private static void buyItem(Shop shop, Customer customer, String itemName, int quantity) {
        for (Item item : shop.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName) && item.getQuantity() >= quantity) {
                item.setQuantity(item.getQuantity() - quantity);
                customer.addItem(new Item(itemName, quantity));
                shop.addTransaction(customer.getName() + " bought " + quantity + " " + itemName);
                System.out.println("Transaction successful!");
                return;
            }
        }
        System.out.println("Item not available or insufficient quantity.");
    }


    private static void sellItem(Shop shop, Customer customer, String itemName, int quantity) {
        for (Item item : customer.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName) && item.getQuantity() >= quantity) {
                item.setQuantity(item.getQuantity() - quantity);
                if (item.getQuantity() == 0) {
                    customer.getInventory().remove(item);
                }
                for (Item shopItem : shop.getInventory()) {
                    if (shopItem.getName().equalsIgnoreCase(itemName)) {
                        shopItem.setQuantity(shopItem.getQuantity() + quantity);
                        shop.addTransaction(customer.getName() + " sold " + quantity + " " + itemName);
                        System.out.println("Transaction successful!");
                        return;
                    }
                }
                shop.getInventory().add(new Item(itemName, quantity));
                shop.addTransaction(customer.getName() + " sold " + quantity + " " + itemName);
                System.out.println("Transaction successful!");
                return;
            }
        }
        System.out.println("Item not available or insufficient quantity.");
    }


    private static void showLog(Shop shop) {
        System.out.println("Transaction Log:");
        for (String log : shop.getLog()) {
            System.out.println(log);


        }}
}
