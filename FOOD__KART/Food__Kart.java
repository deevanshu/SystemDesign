package FOOD__KART;
import java.util.*;
/*
🎯 1️⃣ Requirements (Simplified)

System must:
Onboard restaurant (menu + capacity)
Update price

Place order (based on strategy)

Reject if:
Item not available
Capacity exceeded

Dispatch order
Track served items

Process commands sorted by timestamp

(Bonus) Show dispatched orders

Strategy Pattern for Restaurant Selection
*/
public class Food__Kart {

    public static void main(String[] args) {

        RestaurantService restaurantService = new RestaurantService();
        OrderService orderService = new OrderService(restaurantService, new LowestPriceStrategy());

        // Onboard Restaurants
        restaurantService.addRestaurant("R1", "Dominos", 2,
                Map.of("item1", 100, "item2", 200));
        restaurantService.addRestaurant("R2", "KFC", 1,
                Map.of("item1", 90, "item3", 150));

        // Commands (random order)
        List<Command> commands = List.of(
                new Command(5, () -> orderService.placeOrder("order2", List.of("item1", "item2"))),
                new Command(2, () -> restaurantService.updatePrice("R2", "item1", 50)),
                new Command(8, () -> orderService.dispatchOrder("order2")),
                new Command(3, () -> orderService.placeOrder("order1", List.of("item1")))
        );
        // Sort by timestamp & then run the commands
        commands.stream()
                .sorted(Comparator.comparingInt(c -> c.timestamp))
                .forEach(c -> c.action.run());

        orderService.showDispatchedOrders();
    }
}
class Command {
    int timestamp;
    Runnable action;  // Store what to execute later

    Command(int timestamp, Runnable action) {
        this.timestamp = timestamp;
        this.action = action;
    }
}
class Restaurant {

    String id;
    String name;
    int capacity;
    int currentOrders = 0;

    Map<String, Integer> menu = new HashMap<>();
    Map<String, Integer> servedItems = new HashMap<>();

    Restaurant(String id, String name, int capacity, Map<String, Integer> menu) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.menu.putAll(menu);
    }

    boolean canAcceptOrder(List<String> items) {
        if (currentOrders >= capacity) return false;

        for (String item : items) {
            if (!menu.containsKey(item)) return false;
        }
        return true;
    }

    int calculatePrice(List<String> items) {
        return items.stream().mapToInt(menu::get).sum();
    }

    void acceptOrder(List<String> items) {
        currentOrders++;
        items.forEach(item ->
                servedItems.put(item, servedItems.getOrDefault(item, 0) + 1));
    }

    void dispatchOrder() {
        currentOrders--;
    }
}
class Order {

    String orderId;
    String restaurantId;
    List<String> items;
    int totalPrice;
    OrderStatus status;

    Order(String orderId, String restaurantId, List<String> items, int totalPrice) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.ACCEPTED;
    }
}
enum OrderStatus {
    ACCEPTED, DISPATCHED, REJECTED
}
interface RestaurantStrategy {
    Restaurant findRestaurant(List<String> items, Collection<Restaurant> restaurants);
}
class LowestPriceStrategy implements RestaurantStrategy {

    @Override
    public Restaurant findRestaurant(List<String> items, Collection<Restaurant> restaurants) {

        Restaurant best = null;
        int minPrice = Integer.MAX_VALUE;

        for (Restaurant r : restaurants) {
            if (r.canAcceptOrder(items)) {
                int price = r.calculatePrice(items);
                if (price < minPrice) {
                    minPrice = price;
                    best = r;
                }
            }
        }
        return best;
    }
}
class RestaurantService {

    Map<String, Restaurant> restaurants = new HashMap<>();

    void addRestaurant(String id, String name, int capacity, Map<String, Integer> menu) {
        restaurants.put(id, new Restaurant(id, name, capacity, menu));
    }

    void updatePrice(String restaurantId, String item, int price) {
        Restaurant r = restaurants.get(restaurantId);
        if (r != null) {
            r.menu.put(item, price);
        }
    }

    Collection<Restaurant> getAllRestaurants() {
        return restaurants.values();
    }

    Restaurant getRestaurant(String id) {
        return restaurants.get(id);
    }
}
class OrderService {

    Map<String, Order> orders = new HashMap<>();

    RestaurantService restaurantService;
    RestaurantStrategy strategy;

    OrderService(RestaurantService restaurantService, RestaurantStrategy strategy) {
        this.restaurantService = restaurantService;
        this.strategy = strategy;
    }

    void placeOrder(String orderId, List<String> items) {

        Restaurant restaurant =
                strategy.findRestaurant(items, restaurantService.getAllRestaurants());

        if (restaurant == null) {
            System.out.println("Order Rejected: " + orderId);
            orders.put(orderId, new Order(orderId, null, items, 0));
            orders.get(orderId).status = OrderStatus.REJECTED;
            return;
        }

        int totalPrice = restaurant.calculatePrice(items);
        restaurant.acceptOrder(items); // Restaurent accepted the order

        Order order = new Order(orderId, restaurant.id, items, totalPrice); // create new order with status Accepted
        orders.put(orderId, order);

        System.out.println("Order Accepted: " + orderId + " by " + restaurant.name);
    }

    void dispatchOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order != null && order.status == OrderStatus.ACCEPTED) {

            Restaurant restaurant =
                    restaurantService.getRestaurant(order.restaurantId);

            restaurant.dispatchOrder();
            order.status = OrderStatus.DISPATCHED;  // Mark status as dispatched

            System.out.println("Order Dispatched: " + orderId);
        }
    }

    void showDispatchedOrders() {
        System.out.println("\nDispatched Orders:");
        orders.values().stream()
                .filter(o -> o.status == OrderStatus.DISPATCHED)
                .forEach(o -> System.out.println(o.orderId));
    }
}

