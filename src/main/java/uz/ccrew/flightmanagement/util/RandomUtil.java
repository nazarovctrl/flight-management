package uz.ccrew.flightmanagement.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtil {
    private static final Random RANDOM = new Random();

    public int getRandomSeatNumber() {
        return RANDOM.nextInt(1, 151);
    }
}
