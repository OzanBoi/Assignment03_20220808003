/**@author Ozan Ege Çalışır
@since 31.05.2023 **/
import java.util.HashMap;
import java.lang.*;
import java.util.Map;

public class Assignment03_20220808003 {
    public static void main(String[] args) {
        Store s1 = new Store("Migros", "www.migros.com.tr");
        Store s2 = new Store("BIM", "www.bim.com.tr");
        Customer c = new Customer("CSE 102");
        Customer cc = new Customer("Club CSE 102");
        s1.addCustomer(cc);
        Product p = new Product(123456L, "Computer", 1000.00);
        FoodProduct fp = new FoodProduct(456798L, "Snickers", 2, 250, true, true, true, false);
        CleaningProduct cp = new CleaningProduct(31654L, "Mop", 99, false, "Multi-room");
        System.out.println(cp);
        s1.addToInventory(p, 20);
        s2.addToInventory(p, 10);
        s2.addToInventory(fp, 100);
        s1.addToInventory(cp, 28);
        System.out.println(s1.getName() + " has " + s1.getCount() + " products");
        System.out.println(s1.getProductCount(p));
        System.out.println(s1.purchase(p, 2));
        s1.addToInventory(p, 3);
        System.out.println(s1.getProductCount(p));
        System.out.println(s2.getProductCount(p));


        //System.out.println(s1.getProductCount(fp));//results in Exception

       // System.out.println(s2.purchase (fp, 288)); // results in Exception


        c.addToCart(s1, p, 2);
        c.addToCart(s1, fp, 1); // NOTE: This does not stop the program because the Exception is caught
        c.addToCart(s1, cp, 1);
        System.out.println("Total due - " +c.getTotalDue (s1));
        System.out.println("\n\nReceipt: \n" + c.receipt(s1));


        //System.out.println("\n\nReceipt: \n" + c.receipt(s2)); // results in Exception

        //System.out.println("After paying: "+ c.pay(s1, 2000, true)); // results in Exception
        System.out.println("After paying: " + c.pay(s1, 2100, true));

        //System.out.println("Total due "+c.getTotalDue(s1));// results in Exception -
        //System.out.println("\n\nReceipt: \n " +c.receipt(s1)); // results in Exception

        cc.addToCart(s2, fp, 2);
        cc.addToCart(s2, fp, 1);
        System.out.println(cc.receipt(s2));
        cc.addToCart(s2, fp, 10);
        System.out.println(cc.receipt(s2));
        }
}

class Product {
   private Long id;
  private  String name;
   private double price;

    public Product(Long id, String name, double price)throws InvalidPriceException
    ,InvalidAmountException{
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price)throws InvalidPriceException {

        this.price = price;
            throw new InvalidPriceException(price);

    }

    public String toString() {

        return "ID-"+this.id + "-" + this.name  + " @ " + this.price;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product other = (Product) o;
        return Math.abs(price - other.getPrice()) < 0.001;
    }
}

class FoodProduct extends Product {
    int calories;
    boolean dairy;
    boolean eggs;
    boolean peanuts;
    boolean gluten;

    public FoodProduct(Long id, String name,  double price, int calories,
                       boolean dairy, boolean eggs, boolean peanuts, boolean gluten)throws InvalidAmountException{
        super(id, name, price);
        this.calories = calories;
        this.dairy = dairy;
        this.eggs = eggs;
        this.peanuts = peanuts;
        this.gluten = gluten;
    }

    public int getCalories() {

        return calories;
    }

    public void setCalories(int calories)throws InvalidAmountException{
        this.calories = calories;
        if(calories<0){
            throw new InvalidAmountException(calories);
        }
    }

    public boolean containsDairy() {

        return dairy;
    }

    public boolean containsEggs() {

        return eggs;
    }

    public boolean containsPeanuts() {

        return peanuts;
    }

    public boolean containsGluten() {

        return gluten;
    }
}

class CleaningProduct extends Product {
    private boolean liquid;
    private String whereToUse;

    public CleaningProduct(Long id, String name,
                           double price, boolean liquid, String whereToUse) {
        super(id, name, price);
        this.whereToUse = whereToUse;
        this.liquid = liquid;
    }

    public String getWhereToUse() {

        return whereToUse;
    }

    public void setWhereToUse(String whereToUse) {

        this.whereToUse = whereToUse;
    }

    public boolean isLiquid() {

        return liquid;
    }
}

class Customer {
    private String name;
    protected HashMap<Store,HashMap<Product,Integer>> cart;


    public Customer(String name) {
        this.name = name;
        this.cart = new HashMap<>();
    }

    public void addToCart(Store store, Product product, int count)throws InvalidAmountException{

      /*  if(!store.products.containsKey(product))
            throw new ProductNotFoundException(product);


        HashMap<Product,Integer> store_cart=cart.get(store);
        if(!cart.containsKey(store)) {
            HashMap<Product,Integer> map=new HashMap<>();
            cart.put(store,map);
        }
        if(store_cart.containsKey(product)){
            store_cart.put(product,store_cart.get(product)+count);
            store.purchase(product,count);
*/
        try {
            if(!store.products.containsKey(product))
                throw new ProductNotFoundException(product);
            for (Product p : store.products.keySet()) {
                if (p.equals(product)) {
                    if (cart.containsKey(store)) {
                        if (cart.get(store).containsKey(product)) {
                            store.purchase(product, count);
                            cart.get(store).put(product, cart.get(store).get(product) + count);
                        } else {
                            store.purchase(product, count);
                            cart.get(store).put(product, count);
                        }
                    } else {
                        HashMap<Product, Integer> map = new HashMap<>();
                        store.purchase(product, count);
                        cart.put(store, map);
                        map.put(product, count);

                    }
                }
            }
        }catch(ProductNotFoundException exception){
            System.out.println(exception);
        }

        }


    public String receipt(Store store) {
        if(!cart.containsKey(store))
            throw new StoreNotFoundException("Store NOT FOUND");
        String receipt = "Customer receipt for "+store.getName()+"\n";
        HashMap<Product,Integer> store_cart=cart.get(store);
        for(Product product: store_cart.keySet()){
            receipt+= product.getId() +  " - " +  product.getName() + " @ "  + product.getPrice() +
                    " X " + store_cart.get(product)+ " ... " + store_cart.get(product)*product.getPrice() + "\n";
        }
        return (receipt+"-------------------------------------------\nTotal Due - " + getTotalDue(store)+"\n");
    }

    public double getTotalDue(Store store){
        if(!cart.containsKey(store))
            throw new StoreNotFoundException("Store not found.");
        double total=0;
        HashMap<Product,Integer> store_cart=cart.get(store);
        for(Product product:store_cart.keySet()) {
         total+=(product.getPrice()*store_cart.get(product));
        }
        return total;
    }

    public double getPoints(Store store){
        if(!store.points.containsKey(this)){
            throw new StoreNotFoundException("Store not found.");
        }
        return store.getCustomerPoints(this);
    }

    public double pay(Store store, double amount, boolean usePoints) throws InsufficientFundsException {
        if(!cart.containsKey(store)) {
            throw new StoreNotFoundException("Store not found.");
        }
            double priceAfterDiscount = getTotalDue(store);
            if (usePoints) {
                if (store.points.containsKey(this)) {
                    priceAfterDiscount = getTotalDue(store) - this.getPoints(store) * 0.01;
                }
                if (amount < priceAfterDiscount)
                    throw new InsufficientFundsException(priceAfterDiscount, amount);
                if (priceAfterDiscount < 0) {
                    store.points.put(this, (int) Math.abs(priceAfterDiscount) * 100);
                    return amount;
                }

                store.points.put(this, (int) priceAfterDiscount);
                return amount - priceAfterDiscount;
            } else {
                if (amount < getTotalDue(store))
                    throw new InsufficientFundsException(getTotalDue(store), amount);
                else {
                    store.points.put(this, (int) getTotalDue(store));
                    return amount - getTotalDue(store);
                }
            }
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {

        return name;
    }
}

class Store {
    private String name;
    private String website;
    protected Map<Product,Integer> products;
    protected Map<Customer,Integer> points;

    public Store(String name, String website) {
        this.name = name;
        this.website = website;
        this.products = new HashMap<>();
        this.points = new HashMap<>();
    }
    public void addPoints(Customer customer, int point) {
        points.computeIfPresent(customer, (key, val) -> val + point);
    }
    public int getCount() {
        int count=0;
        for(Product product: products.keySet()){
            count++;
        }
        return count;
    }

    public void addCustomer(Customer customer) {
        points.put(customer,0);
    }

    public int getProductCount(Product product) {
        return products.get(product);
    }

    public int getCustomerPoints(Customer customer) {
        return points.get(customer);
    }

    public void removeProduct(Product product) {
       if(!products.containsKey(product))
           throw new ProductNotFoundException(product);

       products.remove(product);
    }

    public void addToInventory(Product product, int amount) {
        if (!products.containsKey(product)) {
            products.put(product, amount);
        } else {
            products.put(product, products.get(product) + amount);
        }
        if(amount<0)
            throw new InvalidAmountException(amount);
    }


    public double purchase (Product product,int amount){
        if(!products.containsKey(product))
                throw new ProductNotFoundException(product);
        int stock_count=products.get(product);
        if(amount>stock_count || amount<0)
            throw new InvalidAmountException(amount);
        products.put(product,stock_count-amount);
        return product.getPrice()*amount;
        }

        public String getName () {

            return name;
        }

        public void setName (String name){

            this.name = name;
        }

        public String getWebsite () {

            return website;
        }

        public void setWebsite (String website){

            this.website = website;
        }

        public int getInventorySize () {

            return products.size();
        }
    }

class StoreNotFoundException extends IllegalArgumentException{
    private String name;
    public StoreNotFoundException(String name){
        this.name=name;
    }
    public String toString(){
        return ("StoreNotFoundException: " + name);
    }
}

class CustomerNotFoundException extends IllegalArgumentException{
    private String phone;
    private Customer customer;
    public CustomerNotFoundException(String phone){
        this.phone = phone;
    }
    public String toString(){
        return ("CustomerNotFoundException: Name -" + customer.getName());
    }
}

class InsufficientFundsException extends RuntimeException{
    private double total;
    private double payment;
    public InsufficientFundsException(double total, double payment){
        this.total = total;
        this.payment = payment;
    }
    public String toString(){
        return ("InsufficientFundsException: " + total + " due, but only "
        + payment + " given");
    }
}

class InvalidAmountException extends RuntimeException{
    private int amount;
    private int quantity;
    public InvalidAmountException(int amount){
        this.amount = amount;
    }
    public InvalidAmountException(int amount, int quantity){
        this.amount = amount;
        this.quantity = quantity;
    }
    public String toString(){
        if(quantity <= 0) {
            return ("InvalidAmountException: " + amount);
        }else {
            return ("InvalidAmountException: " + amount + " was requested, but only " +
                    quantity + " remaining");
        }
    }
}

class InvalidPriceException extends RuntimeException{
    private double price;
    public InvalidPriceException(double price){
        this.price = price;
    }
    public String toString(){
        return("InvalidPriceException: " + price);
    }
}

class ProductNotFoundException extends IllegalArgumentException {

    private Product product;

    public ProductNotFoundException(Product product){
        this.product=product;
    }

    public String toString() {
        return ("ProductNotFoundException: ID - " + product.getId() + " Name - " + product.getName());

    }
}