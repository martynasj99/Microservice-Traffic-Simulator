CREATE (J1:Intersection { name: "J1", type: "NONE" })
CREATE (J2:Intersection { name: "J2", type: "NONE" })


CREATE
    (J1)-[:STREET { length: 5 }]->(J2)

