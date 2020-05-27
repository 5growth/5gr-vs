 
### Prerequisites

* [JDK] - OpenJDK-8 is preferred
* [Maven] - version 3.3.9 is required
* [PostgresSQL] - as internal DB 



### Required Libraries (should be manually installed)

| Lib | REPOSITORY | INSTALL COMMAND |
| ------ | ------ | ------ |
| NFV IFA LIBS --dev_5growth-- | [README] (https://github.com/nextworks-it/nfv-ifa-libs/tree/dev_5growth) | mvn clean install |
| SLICER IDENTITY MANAGEMENT --master-- | [README](https://github.com/nextworks-it/slicer-identity-mgmt) | mvn clean install |
| NFVO DRIVERS --master-- | <5Growth-VS repo>/NFVO_DRIVERS | mvn clean install |
| SLICER CATALOGUE --5growth-release-- | [README](https://github.com/nextworks-it/slicer-catalogue/tree/5growth-release) | mvn clean install |


### Install 

In the SEBASTIAN folder of the repository:
```
$ mvn clean install 

### Run

$ java -jar SEBASTIAN_CORE/target/SebastianCore-0.0.2-SNAPSHOT.jar

