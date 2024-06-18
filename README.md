# socketServer4

Intra Routing 알고리즘인 Link State 알고리즘을 사용하여 shortest path를 구하는 프로그램

* Intra Routing 알고리즘인 Link State 알고리즘을 사용하여 shortest path를 구하는 C/S 프로그램
* 클라이언트에는 전체 네트워크 토폴로지 정리를 보냄
* 서버는 토폴로지 정보를 이용하여 최단 경로를 구한다.


## Dijkstra 알고리즘 사용

문제가 아래와 같을 경우
<img width="550" alt="스크린샷 2024-06-19 오전 2 49 58" src="https://github.com/Yangdaehan/socketServer4/assets/68599095/d59e0a18-98d5-474a-8987-f5425563d95e">

* 1번 노드에서 2, 3, 4, 5번 노드까지의 최단거리를 모두 구해야 함
* dist[1~5] = [0, ∞, ∞, ∞, ∞]로 초기화 후 시작
* 1번 노드에서 갈 수 있는 노드 = 2, 3, 4
* 1(시작노드) -> 2로 갈 때 드는 비용 ( = dist[1] + (1->2) )과 지금까지 2로가는 최소 비용 (=dist[2])를 비교해서 지금까지 2로가는 최소비용이 더 크다면 dist[2] = dist[1] + (1->2)로 갱신
* 이 방식으로 1번노드에서 갈 수 있는 노드들에 대한 비교를 마치면, dist = [0, 1, 2, 6, ∞]
* 1번 노드를 제외하고, 남은 것중 dist값이 가장 작은 노드 = 2
* 2번 노드는 이제 최단 거리가 확정됨
* 2번 노드에서 갈 수 있는 노드 = 4, 5
* dist[4]와 dist[2] + (2->4)를 비교했을 때, dist[4]가 더 큼
* 이 뜻은 지금까지 4번 노드로 가는 방법보다, 2번 노드를 경유해 4번 노드로 가는 방법이 더 좋다는 뜻 => 값 갱신
* 이 방식으로 2번노드에서 갈 수 있는 노드들에 대한 비교를 마치면, dist = [0, 1, 2, 5, 16]
* 1, 2번 노드를 제외하고, 남은 것 중 dist값이 가장 작은 노드 = 3
* 3번 노드 최단 거리 확정
* 3번 노드에서의 비교를 마치면, dist = [0, 1, 2, 4, 16]
* 4번 노드에서의 비교를 마치면, dist = [0, 1, 2, 4, 12]
* 5번 노드에서의 비교를 마치면, dist = [0, 1, 2, 4, 12]
* 1번 노드에서 1, 2, 3, 4, 5번 노드로 가는데 걸리는 최소 비용 = 0, 1, 2, 4, 12


## 코드 구현
이를 구현하기 위해 아래와 같이 코드를 작성하였습니다.

1. 무한대로 초기화
https://github.com/Yangdaehan/socketServer4/blob/46f8b84b7a59785f5b13ac6b178e30c59d577e88/linkState/src/main/java/org/mse/linkstate/service/RoutingService.java#L18

2. v-1 반복으로 최단 경로를 찾습니다. 아직 처리되지 않은 노드 중 최소 거리를 가진 노드를 선택하고, 만약 u에서 v로 가는 경로가 존재하고, u를 통해 v로 가는 것이 더 짧으면 dist[v]를 업데이트 합니다.
https://github.com/Yangdaehan/socketServer4/blob/46f8b84b7a59785f5b13ac6b178e30c59d577e88/linkState/src/main/java/org/mse/linkstate/service/RoutingService.java#L21-L32

3. 결과를 담을 Map 객체 생성하고 최단 경로 결과를 반환합니다.
https://github.com/Yangdaehan/socketServer4/blob/46f8b84b7a59785f5b13ac6b178e30c59d577e88/linkState/src/main/java/org/mse/linkstate/service/RoutingService.java#L33-L35

4. 아직 처리되지 않은 노드 중 최소 거리를 가진 노드의 인덱스를 반환하는 메서드 'minDistance'
https://github.com/Yangdaehan/socketServer4/blob/46f8b84b7a59785f5b13ac6b178e30c59d577e88/linkState/src/main/java/org/mse/linkstate/service/RoutingService.java#L38-L47


## 실행 결과

postman을 이용해서 시스템을 실행시켰습니다. 

localhost:8080/api/link-state 로 json 형태의 토폴로지를 body에 담아 post 요청 하였습니다.

```c
{
  "topology": [
    [0, 1, 2, 6, 0],
    [1, 0, 0, 4, 15],
    [2, 0, 0, 2, 0],
    [6, 4, 2, 0, 8],
    [0, 15, 0, 8, 0]
  ],
  "source": 0
}
```
이후 결과값이 다음과 같이 나왔습니다.

<img width="1041" alt="스크린샷 2024-06-19 오전 3 10 55" src="https://github.com/Yangdaehan/socketServer4/assets/68599095/822bee56-7ed0-454b-93ee-15ef4e8b379e">

이는 위에서 계산한 결과 값과 같다는 것을 확인할 수 있습니다.
