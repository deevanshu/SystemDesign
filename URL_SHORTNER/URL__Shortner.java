package URL_SHORTNER;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class URL__Shortner {

    private static final String BASE_HOST = "http://short.url/";
    private static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHAR_SET.length(); // 62

    private AtomicLong counter = new AtomicLong(1); // Counter to ensure uniqueness
    
    private ConcurrentHashMap<String, String> shortToLongMap = new ConcurrentHashMap<>(); // Thread safe & no ConcurrentModificationException
    private ConcurrentHashMap<String, String> longToShortMap = new ConcurrentHashMap<>();

    private static final int FIXED_LENGTH = 7;
    
    // Shorten long URL
    public String shortenURL(String longUrl) {
        // Return if already shortened
        if (longToShortMap.containsKey(longUrl)) {
            return BASE_HOST + longToShortMap.get(longUrl);
        }

        long count = counter.getAndIncrement(); // atomic and thread-safe
        String shortKey = encode(count);

        shortToLongMap.put(shortKey, longUrl);
        longToShortMap.put(longUrl, shortKey);

        return BASE_HOST + shortKey;
    }

    // Expand short URL to original
    public String expandURL(String shortUrl) {
        String shortKey = shortUrl.replace(BASE_HOST, "");
        return shortToLongMap.get(shortKey);
    }

    // Encode a number into base62
    private String encode(Long counter2) {
        StringBuilder sb = new StringBuilder();
        while (counter2 > 0) {
            int remainder = (int)(counter2 % BASE); // find remainder than digit at that index(remaindr)
            sb.append(CHAR_SET.charAt(remainder)); 
            counter2 /= BASE; // divide by 62 then for answer of division perform again
        }
    //    String encoded = sb.reverse().toString();
        return String.format("%" + FIXED_LENGTH + "s", sb).replace(' ', '0'); // Pads the string to 6 characters, right-aligning(by default) the content, 
       //                               and filling the left side with spaces. i.e. ex.  '    bcd'  then ' ' -> 0 i.e. '0000bcd'
       
    }

    // Optional: Decode base62 back to number (for debugging)
    private long decode(String str) {
        long num = 0;
        for (char c : str.toCharArray()) {
            num = num * BASE + CHAR_SET.indexOf(c);
        }
        return num;
    }

    // Test the URL shortener
    public static void main(String[] args) {
    	URL__Shortner shortener = new URL__Shortner();

        String longUrl1 = "https://www.example.com/articles/how-to-code-java-url-shortener";
        String longUrl2 = "https://www.stackoverflow.com/questions/ask";

        String shortUrl1 = shortener.shortenURL(longUrl1);
        String shortUrl2 = shortener.shortenURL(longUrl2);

        System.out.println("Shortened: " + shortUrl1);
        System.out.println("Original: " + shortener.expandURL(shortUrl1));
        System.out.println();

        System.out.println("Shortened: " + shortUrl2);
        System.out.println("Original: " + shortener.expandURL(shortUrl2));
    }
}
