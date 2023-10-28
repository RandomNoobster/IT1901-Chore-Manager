package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import core.State;
import core.data.Chore;
import core.data.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Pair;

public class Leaderboard {

    @FXML
    private ListView leaderboard;

    @FXML
    public void initialize() {
        this.displayLeaderboard();
    }

    public void displayLeaderboard() {

        this.leaderboard.getItems().clear();
        List<Pair<String, Integer>> pointsList = new ArrayList<>();

        for (Person p : State.getInstance().getCurrentCollective().getPersonsList()) {
            int points = 0;
            for (Chore c : p.getChores()) {
                if (c.getChecked()) {
                    points += c.getPoints();
                }
            }
            pointsList.add(new Pair(p.getDisplayName(), points));
        }

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
