package core.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;

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
        this.chores = chores;
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

        map.put("uuid", this.uuid);
        map.put("name", this.name);
        map.put("chores", this.chores);

        return new JSONObject(map);
    }
}
