package  Hotel_Booking;
import java.util.Date;
import java.util.List;

public class Hotel {  //  1 hotel have 1 Address  , 1 hotel many Rooms ,   1 hotel many Comment

	long hotelId; //(Prim key);
	String name;
	Address address;
	List<Room> rooms;
	double rating;
	List<Comment> comments;
}
class Address{
	long latitude;
	long longitude;
	String address;
	long zipcode;
	String state;
	String country;
}
class Room {
	long roomId; // (Prim Key)
	long HotelId; // (Foreign Key);
	RoomType roomtype;
	int price;
	Status status;
}
enum RoomType{

	Premium,
	Deluxe;
}
enum Status{

	AVAILABLE , BOOKED , UNDERMAINTAINNCE;
}
class Comment{  
	long hotelId; // (Foreign Key);
	String email;
	String username;
	String description; 	
}

class User{  // 1 User have 1 Reservation 

	long UserId ; // (Prim key)
	String userName;
	String email;
	int phnNumber;
	String passWord;
}

class  ReservationDetails {  // 1 Reservation many Room , 1 reservation 1 Payment

	long reservationId ; // (Prim key)
	long userId;         // (Foreign key)
	long hotelId;        // (Foreign key)
	List<Room> room;
	Payment payment;
	int guestList;
	int balanceAmount;
	Date startDate;
	Date endDate;
	int numberOfDate;
}
class Payment{

	int paymentId ; // (Prim key)
	int paymentAmount;
	String paymentMode;
	PaymentStatus status;
}

enum PaymentStatus{

	SUCCESS, FAILURE;
}


// API 's 

// SearchHotels(Location location , Date date);
// makeReservation(Hotel hotel , User user ,  Date startDate , DateEndDate );
// sendNotificationToUser(ReservationDetails resDetails);

// Design  Patterns -> Builder 

// Locking : depending upon on isolation level , db side locking , optimistic locking 







