package core.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked") // There is no way to parameterize the JSONArray
public class Person {
    private UUID uuid;
    private String name;
    private List<Chore> chores = new ArrayList<>();

    public Person(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public Person(String name, List<Chore> chores) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.chores = new ArrayList<Chore>(chores);
    }

    public Person(String name, UUID uuid, List<Chore> chores) {
        this.uuid = uuid;
        this.name = name;
        this.chores = new ArrayList<Chore>(chores);
    }

    public String getName() {
        return this.name;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void addChore(Chore chore) {
        this.chores.add(chore);
    }

    public List<Chore> getChores() {
        return this.chores;
    }

    public JSONObject encodeToJSON() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("uuid", this.uuid.toString());
        map.put("name", this.name);

        JSONArray choresJSON = new JSONArray();
        for (Chore chore : this.chores) {
            choresJSON.add(chore.encodeToJSON());
        }

        map.put("chores", choresJSON);

        return new JSONObject(map);
    }

    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Person)) {
            return false;
        }
        return this.uuid.equals(((Person) arg0).getUUID());
    }
}
