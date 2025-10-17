# ABOUT THIS REPOSITORY
### Purpose of the following repository

The following repository is for educational purposes. It explores the use of the following annotation:
### @RepositoryRestResource(path = "products")
This annotation is integrated in the Spring Data REST dependency which ca be added to the pom.xml as follows:
    
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-rest</artifactId>
		 </dependency>

By annotating the repository interface, which extends CrudRepository<Class, Long>, a complete CRUD is automatically created without the need to implement a Service.
In addition, an H2 database and Liquibase have been used to manage database change control.

### H2 and Liquibase dependencies:
    <dependency>
        <groupId>com.h2database</groupId>
    	<artifactId>h2</artifactId>
    	<scope>runtime</scope>
	</dependency>
	<dependency>
    	<groupId>org.liquibase</groupId>
    	<artifactId>liquibase-core</artifactId>
	</dependency>
