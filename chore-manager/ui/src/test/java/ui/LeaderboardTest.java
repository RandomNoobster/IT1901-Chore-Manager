package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import core.data.Chore;
import core.data.Person;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Test that the chore popup works as expected.
 */
public class LeaderboardTest extends BaseTestClass {

    private static final String fxmlFileName = "Leaderboard.fxml";
    private ListView<String> leaderboard;

    @Override
    protected String getFileName() {
        return fxmlFileName;
    }

    @BeforeAll
    private void boot() {
        Chore chore = new Chore("Vaske", LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 2), 0,
                "#000000", testPerson.getUsername(), testPerson.getUsername());
        chore.setChecked(true);
        dataAccess.addChore(chore, testPerson);

        Person p1 = new Person("James", testCollective.getJoinCode());
        dataAccess.addPerson(p1, testCollective.getJoinCode());

        Chore chore2 = new Chore("Chore 2", LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 2), 1,
                "#000000", p1.getUsername(), p1.getUsername());
        chore2.setChecked(true);
        dataAccess.addChore(chore2, p1);
    }

    /**
     * Test that the leaderboard is displayed correctly.
     */
    @Test
    public void testContent() {
        this.leaderboard = this.lookup("#leaderboard").query();

        ObservableList<String> items = this.leaderboard.getItems();
        assertTrue(items.get(0).equals("James - 1"));
        assertTrue(items.get(1).equals("Test - 0"));
    }
}
