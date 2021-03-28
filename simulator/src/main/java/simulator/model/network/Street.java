package simulator.model.network;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "STREET")
public class Street{

    @Id @GeneratedValue private Long relationshipId;
    @Property private String name;
    @Property private int length;
    @Property private int maxSpeed;
    @StartNode private Intersection source;
    @EndNode private Intersection target;

    public Street() {
    }

    public Street(String streetName, int length, int maxSpeed, Intersection source, Intersection target) {
        this.name = streetName;
        this.length = length;
        this.maxSpeed = maxSpeed;
        this.source = source;
        this.target = target;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getSource() {
        return source.getName();
    }

    public void setSource(Intersection source) {
        this.source = source;
    }

    public String getTarget() {
        return target.getName();
    }

    public void setTarget(Intersection target) {
        this.target = target;
    }
}