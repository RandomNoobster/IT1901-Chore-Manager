package core.data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Chore class represents a task that can be assigned to a user. It stores information about the
 * chore's name, schedule, and points.
 */
public class Chore {

    private String choreName;
    private LocalDate timeFrom;
    private LocalDate timeTo;
    private boolean isWeekly;
    private int points;
    private String color;
    private boolean checked;
    private int daysIncompleted;
    private String creator;
    private String assignedTo;
    private UUID uuid;

    /**
     * A constructor for the Chore class that initializes the state of the object. This is used for
     * creating a copy of a Chore.
     *
     * @param chore The chore to copy
     */
    public Chore(Chore chore) {
        this(chore.choreName, chore.timeFrom, chore.timeTo, chore.isWeekly, chore.points,
                chore.color, chore.checked, chore.daysIncompleted, chore.creator, chore.assignedTo,
                chore.uuid);
    }

    /**
     * A constructor for the Chore class that initializes the state of the object.
     *
     * @param choreName  The name of the chore
     * @param timeFrom   The start date of the chore
     * @param timeTo     The end date of the chore
     * @param isWeekly   Whether the chore is weekly or not
     * @param points     The points of the chore
     * @param color      The color of the chore
     * @param creator    The creator of the chore
     * @param assignedTo The person the chore is assigned to
     */
    public Chore(String choreName, LocalDate timeFrom, LocalDate timeTo, boolean isWeekly,
            int points, String color, String creator, String assignedTo) {
        this(choreName, timeFrom, timeTo, isWeekly, points, color, false, 0, creator, assignedTo,
                UUID.randomUUID());
    }

    /**
     * A constructor for the Chore class that initializes the state of the object.
     *
     * @param choreName       The name of the chore
     * @param timeFrom        The start date of the chore
     * @param timeTo          The end date of the chore
     * @param isWeekly        Whether the chore is weekly or not
     * @param points          The points of the chore
     * @param color           The color of the chore
     * @param checked         Wheter a chore is done or not
     * @param daysIncompleted How many days since the chores due date
     * @param creator         The creator of the chore
     * @param assignedTo      The person the chore is assigned to
     */

    public Chore(String choreName, LocalDate timeFrom, LocalDate timeTo, boolean isWeekly,
            int points, String color, boolean checked, int daysIncompleted, String creator,
            String assignedTo, UUID uuid) {
        if (uuid == null)
            throw new IllegalArgumentException("UUID cannot be null");

        this.choreName = choreName;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.isWeekly = isWeekly;
        this.points = points;
        this.color = color;
        this.checked = checked;
        this.daysIncompleted = daysIncompleted;
        this.creator = creator;
        this.assignedTo = assignedTo;
        this.uuid = uuid;
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
     * Get offset days.
     *
     * @return Offset days
     */
    private Integer getOffsetDays() {
        int add = this.getDaysIncompleted();
        if (!this.timeFrom.equals(this.timeTo)) {
            if (add % 7 > 0) {
                add = add - (add % 7) + 7;
            }
        }
        return add;
    }

    public LocalDate getOriginalTimeFrom() {
        return this.timeFrom;
    }

    public LocalDate getOriginalTimeTo() {
        return this.timeTo;
    }

    /**
     * Outputs the time the chore is due to start.
     *
     * @return The start date of the chore
     */
    public LocalDate getTimeFrom() {
        return this.timeFrom.plusDays(this.getOffsetDays());
    }

    /**
     * Outputs the time the chore is due to end.
     *
     * @return The end date of the chore
     */
    public LocalDate getTimeTo() {
        return this.timeTo.plusDays(this.getOffsetDays());
    }

    /**
     * Outputs if a chore is checked/done or not.
     *
     * @return Chores checked status
     */
    public Boolean getChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * Outputs how many days the chore is past the due date.
     *
     * @return Days chore has been incompleted past due date
     */
    public Integer getDaysIncompleted() {
        this.updateIncompleted();
        return this.daysIncompleted;
    }

    /**
     * Updates count of incompleted days.
     */
    public void updateIncompleted() {
        if (!this.getChecked() && this.timeTo.isBefore(LocalDate.now())) {
            this.daysIncompleted = (int) ChronoUnit.DAYS.between(this.timeTo, LocalDate.now());
        }
    }

    /**
     * True if task is overdue.
     *
     * @return returns true if a task is overdue
     */
    public boolean overdue() {
        return !this.checked && this.getDaysIncompleted() > 0;
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
     * Outputs the creator of the chores username.
     *
     * @return The creator of the chores username
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * Outputs the color of the chore.
     *
     * @return The color of the chore
     */
    public String getColor() {
        return this.color;
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
     * Outputs the person the chore is assigned to.
     *
     * @return The person the chore is assigned to
     */
    public String getAssignedTo() {
        return this.assignedTo;
    }

    /**
     * Outputs the UUID of the chore.
     *
     * @return The UUID of the chore
     */
    public UUID getUUID() {
        return this.uuid;
    }

    /**
     * Outputs a {@link JSONObject} representation of the chore's state. The object's variables will
     * be turned into key/value pairs in the JSONObject.
     *
     * @return A {@link JSONObject} representation of the chore
     */
    public static JSONObject encodeToJSONObject(Chore chore) {
        if (chore == null)
            throw new IllegalArgumentException("Cannot encode null");

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("uuid", chore.uuid.toString());
        map.put("choreName", chore.choreName);
        map.put("timeFrom", chore.timeFrom.toString());
        map.put("timeTo", chore.timeTo.toString());
        map.put("isWeekly", chore.isWeekly);
        map.put("points", chore.points);
        map.put("color", chore.color);
        map.put("checked", chore.checked);
        map.put("daysIncompleted", chore.daysIncompleted);
        map.put("creator", chore.creator);
        map.put("assignedTo", chore.assignedTo);

        JSONObject json = new JSONObject(map);
        return json;
    }

    /**
     * Decodes a {@link JSONObject} into a {@link Chore} object.
     *
     * @param jsonObject The {@link JSONObject} to decode
     * @return The decoded {@link Chore} object
     * @throws IllegalArgumentException If the {@link JSONObject} is missing required keys
     */
    public static Chore decodeFromJSON(JSONObject jsonObject) throws IllegalArgumentException {
        if (jsonObject == null)
            return null;

        try {
            UUID uuid = UUID.fromString(jsonObject.getString("uuid"));
            String choreName = jsonObject.getString("choreName");
            String timeFromStr = jsonObject.getString("timeFrom");
            String timeToStr = jsonObject.getString("timeTo");
            LocalDate timeFrom = LocalDate.parse(timeFromStr);
            LocalDate timeTo = LocalDate.parse(timeToStr);
            boolean isWeekly = jsonObject.getBoolean("isWeekly");
            int points = jsonObject.getInt("points");
            String color = jsonObject.getString("color");
            boolean checked = jsonObject.getBoolean("checked");
            int daysIncompleted = jsonObject.getInt("daysIncompleted");
            String creator = jsonObject.getString("creator");
            String assignedTo = jsonObject.getString("assignedTo");

            return new Chore(choreName, timeFrom, timeTo, isWeekly, points, color, checked,
                    daysIncompleted, creator, assignedTo, uuid);
        } catch (JSONException e) {
            throw new IllegalArgumentException(
                    "Invalid JSONObject, could not be converted to Chore object");
        }
    }
}
