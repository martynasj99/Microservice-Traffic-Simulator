package simulator.model.network;

import org.neo4j.ogm.annotation.*;


import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Intersection{

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String type;

    @Relationship(type = "STREET", direction = Relationship.OUTGOING)
    private Set<Street> outStreets;

    @Relationship(type = "STREET", direction = Relationship.INCOMING)
    private Set<Street> inStreets;

    private Intersection(){}

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
}


