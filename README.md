This POC is created to validate use of Hazlecast to synchronisze responses from multiple socket connections.


Instructions to execute POC

1. Execute java program TestSocketServer

2. Build war for hazelcast-socket REST service . and deploy on Tomcat.

Test URLs
Take Payment
http://localhost:8080/hazelcast-socket/rest/payment/<reservaion id>/<wait-time-socket response>
http://localhost:8080/hazelcast-socket/rest/payment/123111/20000

Cancel Payment 

http://localhost:8080/hazelcast-socket/rest/cancel/<reservaion id>/<wait-time-socket response>
http://localhost:8080/hazelcast-socket/rest/cancel/123111/100

TODO :Add Test Scenarios 
