config=/home/jbrenes/workspace/configs/functional_split.properties
port=5006
jar=target/SebastianCore-0.0.2-SNAPSHOT.jar
java -agentlib:jdwp=transport=dt_socket,server=y,address=${port},suspend=n  -jar ${jar} --spring.config.location=$config
