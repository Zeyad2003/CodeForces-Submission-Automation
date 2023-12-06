# codeforces-submit-automation

An API to submit code to [CodeForces](https://codeforces.com) **using Selenium** and **Spring Boot**.

## Java code to test the API

```java
public class test {

    static String code = "YOUR CODE HERE";

    public static void main(String[] args) {


        WebClient client = WebClient.create("http://localhost:8080");
        SubmissionInfo problemData = new SubmissionInfo();
        problemData.solutionCode = code; // code
        problemData.compilerId = 54; // compiler

        ProblemSubmitRes res = client.post()
                .uri("submit/{PROBLEM CODE}") // add your problem code
                .body(BodyInserters.fromValue(problemData))
                .retrieve()
                .bodyToMono(ProblemSubmitRes.class)
                .block();

        System.out.println(res);

    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ProblemSubmitRes {
    String verdict;
    String time;
    String memory;
    String submitTime;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class SubmissionInfo {

    String solutionCode;

    Integer compilerId;
}
```

## Remember To Add test code dependencies

```
Add webclient dependency
Add lombok dependency
```

## Provided End-Points

- localhost:8080/submit/{problemCode} - POST

**Example:**

```
submit/4A
```

## Request Body

```
{
    "code" : "test code 11" ,
    "programTypeId" : 54 
}
```
