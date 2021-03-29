package simulator.model.network;

import org.neo4j.ogm.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@NodeEntity
public class Intersection {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @Properties(allowCast = true)
    private Map<String, String> simulators = new HashMap<>(); // Long id -> transfer

    private String type;
    private int capacity;

    @Relationship(type = "STREET", direction = Relationship.OUTGOING)
    private Set<Street> outStreets;

    @Relationship(type = "STREET", direction = Relationship.INCOMING)
    private Set<Street> inStreets;

    private Intersection(){
        this.capacity = 1;
    }

    public Intersection(String name) {
        this.name = name;
        this.type = "NONE";
    }

    public void addOutLink(Street street){
        if(outStreets == null){
            outStreets = new HashSet<>();
        }
        outStreets.add(street);
    }

    public void addInLink(Street street){
        if(inStreets == null){
            inStreets = new HashSet<>();
        }
        inStreets.add(street);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Street> getOutStreets() {
        return outStreets;
    }

    public Set<Street> getInStreets() {
        return inStreets;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getSimulators() {
        return simulators;
    }

    public void setSimulators(Map<String, String> simulators) {
        this.simulators = simulators;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}


