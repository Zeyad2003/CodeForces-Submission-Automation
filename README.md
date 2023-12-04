# codeforces-submit-automation

This a try to automate codeforces submission using an endpoint

# Java code to Test

```java
public class Test {
    static String code = "YOUR CODE";

    public static void main(String[] args) throws InterruptedException, IOException {


        WebClient client = WebClient.create("http://localhost:8080");
        problemData problemData = new problemData();
        problemData.code = code; // code
        problemData.programTypeId = 54; // compiler

        ProblemSubmitRes res = client.post()
                .uri("api/submit/4A") // add your problem code 
                .body(BodyInserters.fromValue(problemData))
                .retrieve()
                .bodyToMono(ProblemSubmitRes.class)
                .block();

        System.out.println(res);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class ProblemSubmitRes {
        String verdict;
        String time;
        String memory;
        Date submitTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class problemData {
        String code;
        int programTypeId;
    }
}
```

# Remember To Add test code dependency

```
Add webclient dependency
Add lombok dependency
```

# EndPoint

**Post**  /api/submit/{problemCode}

```
/api/submit/4A
```

# Request Body

```
{
    "code" : "test code 11" ,
    "programTypeId" : 54 
}
```
