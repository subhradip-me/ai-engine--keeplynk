package com.keeplynk.ai.skill;

import com.keeplynk.ai.agent.AgentContext;
import com.keeplynk.ai.llm.LlmClient;
import com.keeplynk.ai.memory.MemoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// Category / Folder

@Component
@Order(4)
public class CategorySkill implements Skill {

    private final LlmClient llmClient;
    private final MemoryService memoryService;

    public CategorySkill(LlmClient llmClient, @Autowired(required = false) MemoryService memoryService) {
        this.llmClient = llmClient;
        this.memoryService = memoryService;
    }

    @Override
    public void apply(AgentContext context) {
        context.addReasoning("CategorySkill started");

        String prompt = """
            Categorize the following URL into ONE category/folder name.

            URL: %s
            Persona: %s

            Rules:
            - Choose ONE category
            - Use simple, clear category names
            - Output category name only
            """.formatted(context.getUrl(), context.getPersona());

        String rawCategory = llmClient.generate(prompt);

        String finalCategory =
                memoryService != null ? memoryService.reuseOrCreateCategory(rawCategory) : rawCategory;

        context.getMemory().put("category", finalCategory);

        context.addReasoning(
            "CategorySkill reused category: " + finalCategory
        );
    }
}

