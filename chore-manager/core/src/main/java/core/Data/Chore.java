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

    /**
     * A constructor for the Chore class that initializes the state of the object.
     *
     * @param choreName The name of the chore
     * @param timeFrom  The start date of the chore
     * @param timeTo    The end date of the chore
     * @param isWeekly  Whether the chore is weekly or not
     * @param points    The points of the chore
     */
    public Chore(String choreName, LocalDate timeFrom, LocalDate timeTo, boolean isWeekly, int points) {
        this.choreName = choreName;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.isWeekly = isWeekly;
        this.points = points;
    }

    /**
     * Outputs the name of the chore.
     *
     * @return The name of the chore
     */
    public String getName() {
        return this.choreName;
    }

    /**
     * Outputs the time the chore is due to start.
     *
     * @return The start date of the chore
     */
    public LocalDate getTimeFrom() {
        return this.timeFrom;
    }

    /**
     * Outputs the time the chore is due to end.
     *
     * @return The end date of the chore
     */
    public LocalDate getTimeTo() {
        return this.timeTo;
    }

    /**
     * Outputs a boolean value indicating whether the chore is weekly or not.
     *
     * @return Whether the chore is weekly or not
     */
    public boolean getIsWeekly() {
        return this.isWeekly;
    }

    /**
     * Outputs the amount of points the chore is worth.
     *
     * @return The points of the chore
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Set the amount of points the chore should be worth.
     *
     * @param points The amount of points the chore should be worth
     * @throws IllegalArgumentException If points is negative
     */
    public void setPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.points = points;
    }

    /**
     * Outputs a {@link JSONObject} representation of the chore's state. The
     * object's variables will be turned into key/value pairs in the JSONObject.
     *
     * @return A {@link JSONObject} representation of the chore
     */
    public JSONObject encodeToJSON() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("choreName", this.choreName);
        map.put("timeFrom", this.timeFrom.toString());
        map.put("timeTo", this.timeTo.toString());
        map.put("isWeekly", this.isWeekly);
        map.put("points", this.points);

        JSONObject json = new JSONObject(map);
        return json;
    }

}
