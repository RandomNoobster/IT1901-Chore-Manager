package core.Data;

import java.time.LocalDate;
import java.util.HashMap;

import org.json.simple.JSONObject;

/**
 * The Chore class represents a task that can be assigned to a user.
 * It stores information about the chore's name, schedule, and points.
 */
public class Chore {

    private String choreName;
    private LocalDate timeFrom;
    private LocalDate timeTo;
    private boolean isWeekly;
    private int points;
    private String color;

    public Chore(String choreName, LocalDate timeFrom, LocalDate timeTo, boolean isWeekly, int points, String color) {
        this.choreName = choreName;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.isWeekly = isWeekly;
        this.points = points;
        this.color = color;
    }

    public String getName() {
        return this.choreName;
    }

    public LocalDate getTimeFrom() {
        return this.timeFrom;
    }

    public LocalDate getTimeTo() {
        return this.timeTo;
    }

    public boolean getIsWeekly() {
        return this.isWeekly;
    }

    public String getColor() {
        return this.color;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.points = points;
    }

    public JSONObject encodeToJSON() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("choreName", this.choreName);
        map.put("timeFrom", this.timeFrom.toString());
        map.put("timeTo", this.timeTo.toString());
        map.put("isWeekly", this.isWeekly);
        map.put("points", this.points);
        map.put("color", this.color);

        JSONObject json = new JSONObject(map);
        return json;
    }

}
