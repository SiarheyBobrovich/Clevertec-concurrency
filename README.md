# Clevertec test task concurrency

<h3>dependencies</h3>
<pre><pre>
    implementation 'org.projectlombok:lombok:1.18.26'
    annotationProcessor 'org.projectlombok:lombok:1.18.26'
</pre>    
    testImplementation 'org.junit.jupiter:junit-jupiter:5.9.1'
    testImplementation 'org.mockito:mockito-core:5.2.0'
    testImplementation 'org.mockito:mockito-junit-jupiter:5.2.0'
</pre>

## Client
- sendRequests()
  - make requests and send it to a server
- getSum()
  - waits for all responses from the server and returns the sum of all responses

## Server
- process()
    - accept a request, gets the index of the request and stores it to the list
- getResult()
  - returns a List of request's indexes

### Test coverage 86%
