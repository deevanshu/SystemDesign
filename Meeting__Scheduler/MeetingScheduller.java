package Meeting__Scheduler;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

//1: Users & Actions
// Employees (Users) should be able to:
// Book a room for a specific time.
// Reschedule a meeting if needed.
// View their booking status.
// 
//2: Admins should be able to:
// CRUD operations
// Add new meeting rooms.
// View all available rooms.
// 
// 3: Meeting Rooms
// There are multiple meeting rooms.
// Each room has:
// A name
// A capacity
// A calendar of bookings (time slots)
// A room can’t be double-booked — no overlapping bookings are allowed.
// 
// 4:TimeSlot
// Bookings happen within a defined time range (start & end).
// The system checks if the room is already booked during that time.
// If it's free → booking is confirmed.
// If not → booking fails.
// 
// 5:Booking Logic
// The system will try to find the first available room (FCFS strategy).
// Once found, it locks that room for the requested time.
// Creates a Booking record with:
// User details
// Room details
// Start and end time
// Booking status
// 
// 6:Concurrency Control
// Since many users might try to book at the same time:
// We make sure that no two users book the same room at the same time.
// This is handled using locks on each room — so that one booking at a time is processed.
// 
// Summary
// So, in simple terms:
// Users can book/reschedule a meeting room.
// Admins can manage room data.
// Room bookings cannot overlap.
// The system ensures thread-safe booking.
// All booking history is maintained.
// Core of the system revolves around time-based availability checking and conflict resolution.
// 
 
//Synchronized singleton access
//✅ 1. Design Patterns Used
//1. Singleton Pattern
//Used in: RoomManager and BookingService , Ensure that only one instance of these service classes exists in the application.
//
//2. Strategy Pattern (optional for extension)
//Used in: Room assignment logic (can be plugged in for different booking strategies) , Allow dynamic selection of room booking strategy (e.g., FCFS, capacity-based, location-based)
//
//3. Observer Pattern (OPTIONAL - for notification)
//Could be used in future:
//To notify users when their meeting is booked/rescheduled/cancelled
//
//Concurrency & Thread Safety
//ReentrantLock in Room ensures atomic access to that room during booking
//
//synchronized on Singleton getInstance() prevents race conditions
//4. Scalability & Extensibility

class Room {
    String id;
    String name;
    int capacity;
    List<Booking> bookings;
  //  ReentrantLock lockObj;
    
    Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.bookings = new ArrayList<>();
   //     this.lockObj = new ReentrantLock();
    }
    public boolean isAvailable(TimeSlot slot) {
        for (Booking b : bookings) {
            if (b.status == BookingStatus.CONFIRMED && b.slot.overlaps(slot)) {
                return false;
            }
        }
        return true;
    }
    public void addBooking(Booking b) {
        this.bookings.add(b);
    }
}
class TimeSlot {
	 LocalDateTime start;
	 LocalDateTime end;
	 
	 public TimeSlot(LocalDateTime start, LocalDateTime end) {
	     this.start = start;
	     this.end = end;
	 }
	 public boolean overlaps(TimeSlot other) {
	     return this.start.isBefore(other.end) && other.start.isBefore(this.end);
	 }
	}
//ENUMS
enum BookingStatus {
PENDING, CONFIRMED, CANCELLED
}

class Booking {
    String id;
    Room room;
    User user;
    TimeSlot slot;
    BookingStatus status;
    Booking(String id, Room room, User user, TimeSlot slot) {
        this.id = id;
        this.room = room;
        this.user = user;
        this.slot = slot;
        this.status = BookingStatus.PENDING;
    }
}
class User {
    String id;
    String name;
    String email;

    User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
// SINGLETON SERVICE
class RoomManager {
    private static RoomManager instance = null;
    private final Map<String, Room> rooms = new LinkedHashMap<>();  // Room Id & Room   map

    private RoomManager() {}

    public static RoomManager getInstance() {
        if (instance == null) {
            instance = new RoomManager();
        }
        return instance;
    }

    public void addRoom(Room room) {
        rooms.put(room.id, room);
    }

    public Room getRoom(String id) {
        return rooms.get(id);
    }

    public Collection<Room> getAllRooms() {
        return rooms.values();
    }
}

class BookingService { // Singleton SERVICE
    private static BookingService instance = null;
    private final Map<String, Booking> bookings = new ConcurrentHashMap<>(); // booking Id  & booking map , synchronized for thread safe

    private BookingService() {}

    public static BookingService getInstance() {
        if (instance == null) {
            instance = new BookingService();
        }
        return instance;
    }

    public Booking bookRoom(User user, TimeSlot slot , int capacity) {
        for (Room room : RoomManager.getInstance().getAllRooms()) {
        	synchronized(room) {
  //          room.lockObj.lock();  // room.lockObj is a ReentrantLock instance, and room.lockObj.lock() acquires the lock by calling the lock() method on a ReentrantLock object
 //           try {
                if (room.isAvailable(slot) && room.capacity >= capacity) {
                    Booking booking = new Booking(UUID.randomUUID().toString(), room, user, slot);
                    booking.status = BookingStatus.CONFIRMED;
                    room.addBooking(booking);
                    this.bookings.put(booking.id, booking);
                    return booking;
                }
  //          } finally {
  //              room.lockObj.unlock();
  //          }
        	}
        }
        return null;
    }

    public List<Booking> getUserBookings(String userId) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings.values()) {
            if (b.user.id.equals(userId)) {
                result.add(b);
            }
        }
        return result;
    }
}

// MAIN
public class MeetingScheduller {
    public static void main(String[] args) {
        RoomManager roomManager = RoomManager.getInstance();
        BookingService bookingService = BookingService.getInstance();

        roomManager.addRoom(new Room("R1", "Conf Room 1", 10));
        roomManager.addRoom(new Room("R2", "Conf Room 2", 15));

        User user1 = new User("U1", "Alice", "alice@example.com");
        User user2 = new User("U2", "Bob", "bob@example.com");

        TimeSlot slot1 = new TimeSlot(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        TimeSlot slot2 = new TimeSlot(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));

        Booking b1 = bookingService.bookRoom(user1, slot1 , 9);
        Booking b2 = bookingService.bookRoom(user2, slot2 , 8);

        if (b1 != null) {
            System.out.println("Booking confirmed for Alice in room: " + b1.room.name);
        } else {
            System.out.println("No room available for Alice");
        }

        if (b2 != null) {
            System.out.println("Booking confirmed for Bob in room: " + b2.room.name);
        } else {
            System.out.println("No room available for Bob");
        }
    }
}