import java.util.ArrayList;

public class ford_fulkerson {
    public int capacity[][] = {
            {0, 3, 4, 5, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 3, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 7, 0},
            {0, 0, 0, 0, 0, 0, 0, 3},
            {0, 0, 0, 0, 0, 0, 0, 5},
            {0, 0, 0, 0, 0, 1, 0, 4},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };
    public String node_name[] = {
            "S", "A", "B", "C", "D", "E", "F", "T"
    };
    public int flow[][] = new int[8][8];
    public ArrayList<Integer> path = new ArrayList<>();
    public boolean visited[] = new boolean[8];
    public int graph[][] = {
            {1, 2, 3},
            {4, 5},
            {5},
            {6},
            {7},
            {7},
            {5, 7},
            {}
    };
    public int connected[][] = new int[8][8];
    public int total_flow = 0;
    public static void main(String[] args) {
        ford_fulkerson f = new ford_fulkerson();
        f.setLink(f.graph, 0);
        f.dfs(0);
        for (int i=0; i<f.flow.length;i++){
            for (int j=0;j<f.flow[0].length;j++){
                System.out.print(f.flow[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < f.flow.length; i++){
            f.total_flow += f.flow[i][7];
        }
        System.out.println(f.total_flow);
    }

    public void dfs(int start){
        visited[start] = true;
        path.add(start);
        if (node_name[start].equals("T")){          // 정점에 도착하면
            for (int i = 0; i < path.size(); i++){
                System.out.print(node_name[path.get(i)] + " "); // 경로 출력
            }
            System.out.println();
            int min_flow = Integer.MAX_VALUE;

            for (int i = 0; i < path.size() - 1; i++){
                if (min_flow > capacity[path.get(i)][path.get(i+1)] - flow[path.get(i)][path.get(i+1)]) {
                    min_flow = capacity[path.get(i)][path.get(i+1)] - flow[path.get(i)][path.get(i+1)];
                }  // 경로안의 간선들의 최소 유량 값을 찾는다.
            }
            for (int i = 0; i < path.size() - 1; i++){
                flow[path.get(i)][path.get(i+1)] += min_flow; // 정방향 간선엔 유량을 양수로 넣어준다.
                flow[path.get(i+1)][path.get(i)] -= min_flow; // 역방향 간선엔 유량을 음수로 넣어준다.
            }
            return;
        }

        for (int i = 0; i < connected[start].length; i++){
            if(!visited[i] && (connected[start][i] == 1) && capacity[start][i] - flow[start][i] > 0){ // 정점끼리 이어져있고, 남아있는 유량이 0 이상이면,
                dfs(i);
                path.remove(path.size() - 1);
                visited[i] = false;
            }

        }

    }
    public void setLink(int[][] graph, int index){
        if(index >= graph.length){
            return;
        }
        for (int i = 0; i < graph[index].length; i++){
            int n = graph[index][i];
            connected[index][n] = 1; // 정점끼리 연결되어 있다면 1, 연결되어 있지 않다면 0
            connected[n][index] = 1;
        }
        setLink(graph, index + 1);
    }

}


