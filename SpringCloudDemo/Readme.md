You can use command "mvn clean install" to compile eurekaserver and helloclient under their folder.
Start eurekaserver and helloclient by run "mvn spring-boot:run" under these two folders. Or you can start by the jar in target folder(java -jar <jarName>).

For helloserver, you can use "mvn clean install" to compile and start it by "mvn spring-boot:run". Then change the com.example.demo.HelloServerController#index to throw exception and server.port to 5557 in application.yml. Then compile and start again. So we have two instance of helloserver with different port.

Use http://localhost:5555 to check if these server are up.

Use http://localhost:5560/hello1 ~ hello5 to check hystrix feature.
Use http://localhost:5560/hystrix to check hystrix dashboard feature. Fill with http://localhost:5560/actuator/hystrix.stream.

Use http://localhost:5556/limit to check rate limit by guava.


For redislimit,

you need start you redis server. There exists redis connection configuration in application.yml. You need to change to you own configuration. Then compile and start redislimit server.
Use http://localhost:5569/test to check rate limit by redis. The default limit configuration is 3 times in 30 seconds.