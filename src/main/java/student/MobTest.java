package student;

import com.spertus.jacquard.common.GradedTest;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.regex.*;

import static org.junit.jupiter.api.Assertions.*;

public class MobTest {

    @Test
    @GradedTest(name = "Mob getters work", points = 5.0, includeOutput = false)
    public void testMobGetters() {
        Mob mob = new Mob("squid", 100, Mob.Behavior.Hostile, 1, 10);
        assertEquals("squid", mob.getType());
        assertEquals(100, mob.getMaxHearts());
        assertEquals(Mob.Behavior.Hostile, mob.getBehavior());
        assertEquals(Mob.Status.Healthy, mob.getStatus());
        assertEquals(1, mob.getMinDamage());
        assertEquals(10, mob.getMaxDamage());
        assertEquals(100, mob.getNumHearts());
        mob.takeDamage(1);
        assertEquals(Mob.Status.Injured, mob.getStatus());
    }
}
