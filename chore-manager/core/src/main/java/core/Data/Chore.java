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

    public Chore(String choreName, LocalDate timeFrom, LocalDate timeTo, boolean isWeekly, int points) {
        this.choreName = choreName;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.isWeekly = isWeekly;
        this.points = points;
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

    public int getPoints() {
        return this.points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public JSONObject encodeToJSON() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("choreName", this.choreName);
        map.put("timeFrom", this.timeFrom);
        map.put("timeTo", this.timeTo);
        map.put("isWeekly", this.isWeekly);
        map.put("points", this.points);

        JSONObject json = new JSONObject(map);
        return json;
    }

}
