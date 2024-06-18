package org.mse.linkstate.service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RoutingService {

    public Map<String, Object> calculateShortestPath(List<List<Integer>> graph, int src) {
        int V = graph.size();
        int[] dist = new int[V];
        boolean[] sptSet = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;

            for (int v = 0; v < V; v++) {
                if (!sptSet[v] && graph.get(u).get(v) != 0 &&
                    dist[u] != Integer.MAX_VALUE &&
                    dist[u] + graph.get(u).get(v) < dist[v]) {
                    dist[v] = dist[u] + graph.get(u).get(v);
                }
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("distances", dist);
        return response;
    }

    private int minDistance(int[] dist, boolean[] sptSet) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < dist.length; v++) {
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }
}
