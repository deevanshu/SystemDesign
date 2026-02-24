package Student_Counselling_Mgmt_System;
import java.util.*;
/*
Student ↔ CounsellingSession → One-to-Many
Counselor ↔ CounsellingSession → One-to-Many
Admin → manages both Student and Counselor
Feedback → associated with one CounsellingSession

+------------------+        +------------------+
|     Student      |        |     Counselor    |
+------------------+        +------------------+
| studentId        |        | counselorId      |
| name             |        | name             |
| email            |        | specialization   |
+------------------+        +------------------+
| bookSession()    |<>----->| viewSchedule()   |
| viewSessions()   |        | addFeedback()    |
+------------------+        +------------------+

              |
              V
      +----------------------+
      |   CounsellingSession |
      +----------------------+
      | sessionId            |
      | dateTime             |
      | status, feedback     |
      +----------------------+
      | markCompleted()      |
      | reschedule()         |
      +----------------------+

Singleton → DatabaseConnection and ReportGenerator
Factory Pattern → UserFactory to create Admin, Student, or Counselor
Observer Pattern → NotificationService that observes session updates
Strategy Pattern → SessionSchedulingStrategy (manual vs auto scheduling)

*/

enum SessionStatus {
    PENDING,
    CONFIRMED,
    COMPLETED,
    CANCELLED
}

/* ===================== MODELS ===================== */

/* ============================== STRATEGY PATTERN ============================== */

interface SessionSchedulingStrategy {
    Date scheduleSession(Student student, Counselor counselor);
}

class ManualSchedulingStrategy implements SessionSchedulingStrategy {
	@Override
	public Date scheduleSession(Student student, Counselor counselor) {
        System.out.println("📅 Manual Scheduling in progress...");
        return new Date(); // date decided manually
    }
}

class AutoSchedulingStrategy implements SessionSchedulingStrategy {
	@Override
	public Date scheduleSession(Student student, Counselor counselor) {
        System.out.println("⚙️ Auto-scheduling next available slot...");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1); // tomorrow's slot
        return cal.getTime();
    }
}

/* ============================== OBSERVER PATTERN ============================== */

interface Observer {
    void update(String message);
}

class NotificationService implements Observer {
    private static NotificationService instance = new NotificationService();

    private NotificationService() {}

    public static NotificationService getInstance() {
        return instance;
    }

    @Override
    public void update(String message) {
        System.out.println("🔔 Notification: " + message);
    }
}

/* ============================== SINGLETON DATABASE & REPORT ============================== */

class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("🔗 Database connected successfully.");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void executeQuery(String query) { // Only simulating DB action
        System.out.println("Executing DB Query: " + query);
    }
}

class ReportGenerator {
    private static ReportGenerator instance = new ReportGenerator();

    private ReportGenerator() {}

    public static ReportGenerator getInstance() {
        return instance;
    }

    public void generateReport(List<CounsellingSession> sessions) {
        System.out.println("\n📊 Counselling Summary Report:");
        for (CounsellingSession s : sessions) System.out.println(s); // [SessionID 1] Student: Rahul -> Counselor: Dr. Smith (General Counselling) | Sat Oct 25 12:40:33 IST 2025 | Status: COMPLETED | Feedback: Rahul shows strong motivation.

    }
}

/* ============================== FACTORY PATTERN ============================== */

abstract class User {
    protected int id;
    protected String name;
    protected String email;
}

class Student extends User {
    private List<CounsellingSession> sessions = new ArrayList<>();

    public Student(int id, String name, String email) {
        this.id = id; this.name = name; this.email = email;
    }
    public CounsellingSession requestSession(Counselor c, SessionSchedulingStrategy strategy, CounsellingCenter center) {
        Date scheduledDate = strategy.scheduleSession(this, c);
        CounsellingSession s = new CounsellingSession(this, c, scheduledDate);
        sessions.add(s);
        center.addSession(s);
        NotificationService.getInstance().update(name + " booked a session with " + c.getName());
        return s;
    }

    public String toString() { return "Student: " + name; }
}

class Counselor extends User {
    private String specialization;
    private List<CounsellingSession> assignedSessions = new ArrayList<>();

    public Counselor(int id, String name, String specialization) {
        this.id = id; this.name = name; this.specialization = specialization;
    }

    public void giveFeedback(CounsellingSession s, String feedback) {
        s.setFeedback(feedback);
        s.setStatus(SessionStatus.COMPLETED);
        NotificationService.getInstance().update("Feedback added for " + s.getStudent().name + ": " + feedback);
    }

    public String getName() { return name; }
    public String toString() { return "Counselor: " + name + " (" + specialization + ")"; }
}

class Admin extends User {
    public Admin(int id, String name, String email) {
        this.id = id; this.name = name; this.email = email;
    }

    public void assignCounselor(Student s, Counselor c, CounsellingSession session) {
        session.setStatus(SessionStatus.CONFIRMED);
        NotificationService.getInstance().update("Admin assigned " + c.getName() + " to " + s.name);
        DatabaseConnection.getInstance().executeQuery("INSERT INTO sessions VALUES(...)"); // simulated DB action
    }

    public void generateReports(CounsellingCenter center) {
        ReportGenerator.getInstance().generateReport(center.getAllSessions());
    }

    public String toString() { return "Admin: " + name; }
}

/* Factory class */
class UserFactory {
    public static User createUser(String role, int id, String name, String email) {
        switch(role.toLowerCase()) {
            case "student": return new Student(id, name, email);
            case "counselor": return new Counselor(id, name, "General Counselling");
            case "admin": return new Admin(id, name, email);
            default: throw new IllegalArgumentException("Invalid user role: " + role);
        }
    }
}

/* ============================== COUNSELLING DOMAIN ============================== */

class CounsellingSession {
    private static int counter = 1;
    private int id;
    private Student student;
    private Counselor counselor;
    private Date date;
    private String feedback;
    private SessionStatus status;

    public CounsellingSession(Student s, Counselor c, Date d) {
        this.id = counter++;
        this.student = s;
        this.counselor = c;
        this.date = d;
        this.status = SessionStatus.PENDING;
    }

    public void setFeedback(String feedback) { this.feedback = feedback; }
    public void setStatus(SessionStatus s) { this.status = s; }

    public Student getStudent() { return student; }

    public String toString() {
        return "[SessionID " + id + "] " + student + " -> " + counselor + 
               " | " + date + " | Status: " + status + 
               (feedback != null ? " | Feedback: " + feedback : "");
    }
}

/* ============================== MAIN CONTROLLER ============================== */

class CounsellingCenter {
    private List<CounsellingSession> sessions = new ArrayList<>();

    public void addSession(CounsellingSession s) { sessions.add(s); }
    public List<CounsellingSession> getAllSessions() { return sessions; }
}

/* ============================== MAIN DEMO ============================== */

public class CounsellingDemo {
    public static void main(String[] args) {
        System.out.println("===== STUDENT COUNSELLING SYSTEM v2 =====");

        DatabaseConnection.getInstance(); // singleton init

        CounsellingCenter center = new CounsellingCenter();

        Admin admin = (Admin) UserFactory.createUser("admin", 1, "Alice", "alice@admin.com");
        Student s1 = (Student) UserFactory.createUser("student", 101, "Rahul", "rahul@gmail.com");
        Counselor c1 = (Counselor) UserFactory.createUser("counselor", 201, "Dr. Smith", "smith@uni.com");

        // Session scheduling with Strategy pattern
        CounsellingSession session1 = s1.requestSession(c1, new AutoSchedulingStrategy(), center);
        admin.assignCounselor(s1, c1, session1);
        c1.giveFeedback(session1, "Rahul shows strong motivation.");

        admin.generateReports(center);
    }
}
/*
===== STUDENT COUNSELLING SYSTEM v2 =====
🔗 Database connected successfully.
⚙️ Auto-scheduling next available slot...
🔔 Notification: Rahul booked a session with Dr. Smith
🔔 Notification: Admin assigned Dr. Smith to Rahul
Executing DB Query: INSERT INTO sessions VALUES(...)
🔔 Notification: Feedback added for Rahul: Rahul shows strong motivation.

📊 Counselling Summary Report:
[SessionID 1] Student: Rahul -> Counselor: Dr. Smith (General Counselling) | Sat Oct 25 12:40:33 IST 2025 | Status: COMPLETED | Feedback: Rahul shows strong motivation.
*/