package generator;

import java.util.Random;

public class IdGenerator {
    public static String generateId(int size) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        char randomChar;
        String baned = "\\/?:*\"<>|";
        for (int i = 0; i < size; i++) {
            do {
                randomChar = (char) (random.nextInt(91) + 35);
            } while (baned.contains(String.valueOf(randomChar)));
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
