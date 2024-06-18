package org.mse.linkstate.controller;

import java.util.List;
import java.util.Map;
import org.mse.linkstate.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routing")
public class RoutingController {

    @Autowired
    private RoutingService routingService;

    @PostMapping("/shortest-path")
    public Map<String, Object> getShortestPath(@RequestBody Map<String, Object> request) {
        List<List<Integer>> adjacencyMatrix = (List<List<Integer>>) request.get("topology");
        int source = (Integer) request.get("source");

        return routingService.calculateShortestPath(adjacencyMatrix, source);
    }
}
