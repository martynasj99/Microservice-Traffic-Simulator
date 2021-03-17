1) Run: docker run --publish=7474:7474 --publish=7687:7687 neo4j:4.0.8
2) Go to http://192.168.99.101:7474/
3) Enter neo4j and neo4j as the username and password
4)
Username: neo4j
Password: mts

docker run \
-p 7474:7474 -p 7687:7687 \
-v $PWD/data:/data -v $PWD/plugins:/plugins \
--name neo4j-apoc \
-e NEO4J_apoc_export_file_enabled=true \
-e NEO4J_apoc_import_file_enabled=true \
-e NEO4J_apoc_import_file_use__neo4j__config=true \
-e NEO4JLABS_PLUGINS=\[\"apoc\"\] \
neo4j:4.0