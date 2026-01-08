package com.keeplynk.ai.controller;

import com.keeplynk.ai.agent.AgentContext;
import com.keeplynk.ai.agent.AgentInput;
import com.keeplynk.ai.decision.AgentDecision;
import com.keeplynk.ai.decision.DecisionEngine;
import com.keeplynk.ai.orchestrator.AgentExecutor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final DecisionEngine decisionEngine;
    private final AgentExecutor agentExecutor;

    public AgentController(
        DecisionEngine decisionEngine,
        AgentExecutor agentExecutor
    ) {
        this.decisionEngine = decisionEngine;
        this.agentExecutor = agentExecutor;
    }

    @PostMapping("/resource/enrich")
    public AgentContext enrichResource(@RequestBody AgentInput input) {

        AgentDecision decision = decisionEngine.decide(input);

        if ("NONE".equals(decision.getAction())) {
            return AgentContext.empty(input);
        }

        AgentContext context = AgentContext.from(input);
        context.addReasoning("DecisionEngine selected action: " + decision.getAction());
        context.addReasoning("Reason: " + decision.getReason());
        
        agentExecutor.runResourceAgent(context);

        context.getMemory().put("confidence", decision.getConfidence());

        return context;
    }
}
