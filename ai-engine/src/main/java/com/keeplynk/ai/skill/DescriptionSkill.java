package com.keeplynk.ai.skill;

import com.keeplynk.ai.agent.AgentContext;
import com.keeplynk.ai.llm.LlmClient;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DescriptionSkill implements Skill {

    private final LlmClient llmClient;

    public DescriptionSkill(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    @Override
    public void apply(AgentContext context) {
        context.addReasoning("DescriptionSkill started");
        
        String prompt = """
        		Generate a brief, informative description for the following URL.

        		URL: %s
        		Persona: %s

        		Rules:
        		- Max 30 words
        		- Describe what the resource is about or its purpose
        		- Be specific and informative
        		- No emojis or special characters
        		- Output description only, no additional text
        		""".formatted(context.getUrl(), context.getPersona());
        

        String description = llmClient.generate(prompt);
        
        context.getMemory().put("description", description);
        
        context.addReasoning("DescriptionSkill generated description");
    }
}
