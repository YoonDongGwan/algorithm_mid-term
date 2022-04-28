# 포드 풀커슨 알고리즘(Ford-Fulkerson Algorithm)
포드 풀커슨 알고리즘이란, **유량이 흐르는 네트워크**에서 종점까지 도달할 때, **최대 유량**을 구하는 탐욕 알고리즘이다.  
1956년, L. R. Ford Jr. 와 D. R. Fulkerson 에 의해 구현되어 Ford-Fulkerson 알고리즘이라 부른다.  

# 용어
* *S* : 출발지(Source)  
* *T* : 도착지(Sink)  
* *c(a, b)* : 정점 a와 b 사이의 간선의 용량(capacity)  
* *f(a, b)* : 정점 a와 b 사이에 흐르고 있는 유량(flow)

# 규칙
1. 간선에 흐르는 유량은 용량을 초과해서 흐를 수 없다.
2. 정점 *a*에서 정점 *b*에서 흐르는 유량 *f*가 있다면, *b*에서 *a*로 *-f*만큼 흐른다고 볼 수 있다.
3. 한 정점으로 들어오는 유량과 그 정점에서 나가는 유량은 같다.

# 풀이
1. Source에서 Sink 가는 경로 하나를 찾는다.
* 포드-풀커슨 알고리즘에서는 DFS로 경로를 찾는다. BFS를 사용하는 것은 에드먼즈-카프 알고리즘이다.
2. 그 경로안에 있는 간선 중, 남아있는 용량이 가장 적은 수를 찾아 각 간선에 유량을 흘려보낸다.
* 여기서 남아있는 용량이란 c(a, b) - f(a, b)를 의미한다
3. 더 이상 경로를 찾을 수 없을 때까지 1~2 과정을 반복한다.

이러한 과정을 통해 종점에 도달하는 유량을 구할 수 있게 된다.  

하지만, 위 과정엔 한 가지 고려하지 않은 점이 있다.  

경로를 찾는 과정에서, 결국 찾는 경로들 사이에도 순서가 생기게 된다. 예를 들어, 아래의 그래프가 있다고 가정해보자.

<img width="443" alt="스크린샷 2022-04-26 오후 5 43 07" src="https://user-images.githubusercontent.com/39906922/165260046-c85ad5a9-770c-4b31-bea1-e7e90432345d.png">

이 그래프에서 DFS로 경로를 찾게 된다면, 아래의 순서와 같이 경로를 찾아낼 것이다. (B가 T보다 먼저인 경우)
1. S -> A -> B -> T
2. S -> A -> T
3. S -> B -> T  

먼저 찾아지는 경로에서 간선의 용량을 다 사용했다면, 이후에 찾아지는 경로에서는 당연히 유량을 흘러 보낼 수 없다.  

여기서 고려해야 하는 점은 만약 **먼저 찾아지는 경로보다 후에 찾아지는 경로가 종점까지 도달하는 유량을 더 늘릴 수 있다면?** 이다.  

만약 경로 S -> A -> B -> T 에서 S -> A의 간선의 용량을 다 사용했다면, 이 후에 찾은 경로 S -> A -> T 가 더 높은 유량을 흘러보낼 수 있다하더라도, S -> A -> T 로는 유량을 흘러보낼 수 없다.  

이 문제를 해결하기 위해 `규칙 2. 정점 *a*에서 정점 *b*에서 흐르는 유량 *f*가 있다면, *b*에서 *a*로 *-f*만큼 흐른다고 볼 수 있다.`를 이용한다.

위 그래프에서 A 와 B 사이에는 A -> B 라는 방향으로 f만큼 흐르는 유량이 있었지만, 이번엔 B -> A 의 방향으로 -f만큼 흐르는 유량이 있음을 가정하고 경로를 탐색한다.  

그러면 S -> B -> A -> T 라는 경로가 하나 새로 생기고, B -> A 라는 경로는 사실 존재하지 않지만, A -> B 로 흐르는 유량을 감소시킬 수 있게 되고, 그로 인해 감소된 만큼 다른 곳으로 유량이 흐를 수 있게 된다.  

예를 들면, 원래는 S -> A 의 용량이 꽉 차 A -> T 로 유량을 흘러보낼 수 없었지만, A -> B 로 흐르는 유량이 감소되어 A -> T로 흐를 수 있게 된다.  

즉, 유량이 반대로도 흐름을 고려하여 풀이 1, 2, 3 을 거치면, 포드-풀커슨 알고리즘의 목표인 종점에서의 최대 유량을 알 수 있게 된다.

# 실행 결과
## 조건
<img width="629" alt="스크린샷 2022-04-27 오후 5 10 30" src="https://user-images.githubusercontent.com/39906922/165472349-6d651116-94e0-4261-9a1e-2cc4922a22a8.png">
<img width="345" alt="스크린샷 2022-04-27 오후 5 13 08" src="https://user-images.githubusercontent.com/39906922/165472935-4d9681e8-22e4-43c2-8e2a-657750bc97b3.png">
<img width="543" alt="스크린샷 2022-04-27 오후 5 13 21" src="https://user-images.githubusercontent.com/39906922/165472972-ce71c033-e8f3-4591-8f7d-fb3c6b5839e2.png">

## 수행 과정
### 경로 S -> A -> D -> T
<img width="631" alt="1" src="https://user-images.githubusercontent.com/39906922/165476327-9556c361-f691-4a4f-a210-6326ed343a75.png">

### 경로 S -> B -> D -> T
<img width="631" alt="2" src="https://user-images.githubusercontent.com/39906922/165476672-c8c2578d-3baa-4ae6-92da-9f332aef8dfb.png">

### 경로 S -> B -> E -> T
<img width="631" alt="3" src="https://user-images.githubusercontent.com/39906922/165476784-caba0bd2-bc2f-492a-8342-2800172e3c20.png">

### 경로 S -> B -> F -> T
<img width="631" alt="4" src="https://user-images.githubusercontent.com/39906922/165476871-55a72899-26d9-458b-b5f9-a8db760d51a8.png">

### 경로 S -> C -> F -> T
<img width="631" alt="5" src="https://user-images.githubusercontent.com/39906922/165476947-db83c3e2-fca9-4b27-9348-39cbc5052ace.png">


## 결과
<img width="264" alt="스크린샷 2022-04-27 오후 5 15 18" src="https://user-images.githubusercontent.com/39906922/165473233-dd894291-60c8-4e30-abf6-d6bdfb962246.png">
<img width="631" alt="스크린샷 2022-04-27 오후 5 23 29" src="https://user-images.githubusercontent.com/39906922/165474909-82f71512-63d1-4a3c-937e-731dc0fecf6b.png">

**위 예제의 최대 유량은 13이다.**

# 시간 복잡도 및 성능 비교
최대 유량이 f라면, 각 경로에 흐를 수 있는 최소 유량은 1이므로, 최대 f개의 경로를 탐색한다. 하나의 경로를 탐색하는 데에는 최대 V개의 정점과 최대 E개의 간선을 거친다.  
따라서, 시간 복잡도는 O((V+E)f) 이다.  

포드-풀커슨 알고리즘은 최초의 네트워크 유량 알고리즘인만큼 아까 보았던, 경로 문제나 시간 복잡도면에서 성능이 떨어지는 것이 사실이다.  

이를 개선하기 위해 경로를 탐색할 때 BFS로 탐색하는 에드먼즈-카프 알고리즘(Edmonds-Karp Algorithm)이 존재하고, 수많은 네트워크 유량 문제를 풀기 위한 알고리즘이 존재한다.

아래는 Max Flow Algorithm(최대 유량 알고리즘)의 성능을 비교한 표이다. 
<img width="562" alt="스크린샷 2022-04-28 오후 1 41 06" src="https://user-images.githubusercontent.com/39906922/165678262-b1c30c72-37ae-4c8a-b821-f7976e449cd5.png">
##### 출처 : EDMONDS-KARP AND DINIC’S ALGORITHMS FOR MAXIMUM FLOW https://www.topcoder.com/thrive/articles/edmonds-karp-and-dinics-algorithms-for-maximum-flow

위 표를 보면, 에드먼즈-카프 알고리즘의 수행시간이 포드-풀커슨에 비해 비약적으로 발전했음을 알 수 있고, 포드-풀커슨 알고리즘은 그 중 가장 낮은 성능을 가진 것으로 보여진다.  

그 차이는 정점(Vertex)의 개수가 많아질수록 더 현저하게 나타난다. 하지만, 최대 유량과 간선에 개수에 따라 포드-풀커슨 알고리즘이 에드먼즈-카프 알고리즘보다 나은 성능을 보일 때도 있다고 한다.

때문에 일반적으로 우리가 코딩테스트에 사용하거나 작은 문제를 해결할 때에는 포드-풀커슨 알고리즘을 사용하는 편이라고 한다.
