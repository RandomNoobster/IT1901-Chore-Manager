package ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import core.data.Chore;
import core.data.RestrictedPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Pair;
import ui.dataAccessLayer.DataAccess;

/**
 * The Leaderboard class handles displaying the leaderboard in the UI.
 */
public class Leaderboard {

    private DataAccess dataAccess;

    @FXML
    private ListView<String> leaderboard;

    @FXML
    public void initialize() {
        this.dataAccess = App.getDataAccess();
        this.displayLeaderboard();
    }

    /**
     * Displays the leaderboard in the UI.
     */
    public void displayLeaderboard() {

        this.leaderboard.getItems().clear();
        List<Pair<String, Integer>> pointsList = new ArrayList<>();
        HashMap<String, Integer> points = new HashMap<String, Integer>();

        Collection<RestrictedPerson> persons = this.dataAccess.getPersons().values();

        for (RestrictedPerson person : persons) {
            points.put(person.getUsername(), 0);
        }

        List<Chore> chores = this.dataAccess.getChores();
        for (Chore chore : chores) {
            if (chore.getChecked()) {
                Integer prevPoints = points.getOrDefault(chore.getAssignedTo(), 0);
                points.put(chore.getAssignedTo(), prevPoints + chore.getPoints());
            }
        }

        pointsList = points.entrySet().stream()
                .map(e -> new Pair<String, Integer>(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        Collections.sort(pointsList, (p1, p2) -> Integer.compare(p2.getValue(), p1.getValue()));

        ObservableList<String> items = FXCollections.observableArrayList(pointsList.stream()
                .map(p -> p.getKey() + " - " + p.getValue()).collect(Collectors.toList()));

        this.leaderboard.setItems(items);
    }

    @FXML
    public void toMain() {
        App.switchScene("App");
    }
}
