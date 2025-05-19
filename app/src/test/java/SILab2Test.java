import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SILab2Test {

    @Test
    public void testStatementCoverage() {
        // Test 1: allItems == null
        RuntimeException ex1 = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, "1234567890123456"));
        assertTrue(ex1.getMessage().contains("can't be null"));

        // Test 2: invalid item name
        List<Item> items2 = List.of(new Item(null, 100, 0, 1));
        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items2, "1234567890123456"));
        assertEquals("Invalid item!", ex2.getMessage());

        // Test 3: invalid character in card
        List<Item> items3 = List.of(new Item("A", 100, 0, 1));
        RuntimeException ex3 = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items3, "1234-5678-9012-3456"));
        assertEquals("Invalid character in card number!", ex3.getMessage());

        // Test 4: valid item with/without discount
        List<Item> items4 = List.of(
                new Item("A", 100, 0.1, 2),
                new Item("B", 50, 0, 3)
        );
        double result = SILab2.checkCart(items4, "1234567890123456");
        assertEquals(330.0, result);
    }

    @Test
    public void testMultipleCondition() {
        // All 8 combinations
        // 1. F F F => No penalty
        List<Item> items1 = List.of(new Item("A", 100, 0, 1));
        double sum1 = SILab2.checkCart(items1, "1234567890123456");
        assertEquals(100.0, sum1);

        // 2. F F T => Penalty
        List<Item> items2 = List.of(new Item("A", 100, 0, 11));
        double sum2 = SILab2.checkCart(items2, "1234567890123456");
        assertEquals(100.0 * 11 - 30, sum2);

        // 3. F T F => Penalty
        List<Item> items3 = List.of(new Item("A", 100, 0.1, 1));
        double sum3 = SILab2.checkCart(items3, "1234567890123456");
        assertEquals(100 * 0.9 - 30, sum3);

        // 4. F T T => Penalty
        List<Item> items4 = List.of(new Item("A", 100, 0.2, 12));
        double sum4 = SILab2.checkCart(items4, "1234567890123456");
        assertEquals(100 * 0.8 * 12 - 30, sum4);

        // 5. T F F => Penalty
        List<Item> items5 = List.of(new Item("A", 400, 0, 1));
        double sum5 = SILab2.checkCart(items5, "1234567890123456");
        assertEquals(400 - 30, sum5);

        // 6. T F T => Penalty
        List<Item> items6 = List.of(new Item("A", 400, 0, 15));
        double sum6 = SILab2.checkCart(items6, "1234567890123456");
        assertEquals(400 * 15 - 30, sum6);

        // 7. T T F => Penalty
        List<Item> items7 = List.of(new Item("A", 400, 0.2, 1));
        double sum7 = SILab2.checkCart(items7, "1234567890123456");
        assertEquals(400 * 0.8 - 30, sum7);

        // 8. T T T => Penalty
        List<Item> items8 = List.of(new Item("A", 400, 0.1, 12));
        double sum8 = SILab2.checkCart(items8, "1234567890123456");
        assertEquals(400 * 0.9 * 12 - 30, sum8);
    }
}
