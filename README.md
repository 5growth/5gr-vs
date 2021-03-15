 
### Prerequisites

* [JDK] - OpenJDK-8 is preferred
* [Maven] - version 3.3.9 is required
* [PostgresSQL] - as internal DB 



### Required Libraries (should be manually installed)

| Lib | REPOSITORY/FOLDER | INSTALL COMMAND |
| ------ | ------ | ------ |
| NFV IFA LIBS --dev_5growth-- | [README] (https://github.com/nextworks-it/nfv-ifa-libs/tree/dev_5growth) | mvn clean install |
| SLICER IDENTITY MANAGEMENT --master-- | [README](https://github.com/nextworks-it/slicer-identity-mgmt) | mvn clean install |
| NFVO DRIVERS | NFVO_DRIVERS | mvn clean install |
| SLICER CATALOGUE --5growth-release-- | [README](https://github.com/nextworks-it/slicer-catalogue/tree/5growth-release) | mvn clean install |


### Install 

In the SEBASTIAN folder of the repository:
```
$ mvn clean install 

### Run

$ java -jar SEBASTIAN_CORE/target/SebastianCore-0.0.2-SNAPSHOT.jar
```

-------------------
### Project information
Call: H2020-ICT-2019. Topic: ICT-19-2019. Type of action: RIA. Duration: 30 Months. Start date: 1/6/2019
![5GROWTH logo](https://5g-ppp.eu/wp-content/uploads/2019/06/5Growth_rgb_horizontal.png)

<p align="center">
<img src="https://upload.wikimedia.org/wikipedia/commons/b/b7/Flag_of_Europe.svg" width="200px" />
</p>
