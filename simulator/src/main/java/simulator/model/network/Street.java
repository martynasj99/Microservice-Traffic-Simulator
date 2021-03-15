package simulator.model.network;

import org.neo4j.ogm.annotation.*;
import simulator.model.Traffic;
import simulator.model.network.Intersection;

@RelationshipEntity(type = "STREET")
public class Street{

    @Id @GeneratedValue private Long relationshipId;
    @Property private String streetName;
    @Property private int length;
    @Property private int maxSpeed;
    @StartNode private Intersection source;
    @EndNode private Intersection target;

    public Street() {
    }

    public Street(String streetName, int length, int maxSpeed, Intersection source, Intersection target) {
        this.streetName = streetName;
        this.length = length;
        this.maxSpeed = maxSpeed;
        this.source = source;
        this.target = target;
        source.addOutLink(this);
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
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

    public Long getSource() {
        return source.getId();
    }

    public void setSource(Intersection source) {
        this.source = source;
    }

    public Long getTarget() {
        return target.getId();
    }

    public void setTarget(Intersection target) {
        this.target = target;
    }

}