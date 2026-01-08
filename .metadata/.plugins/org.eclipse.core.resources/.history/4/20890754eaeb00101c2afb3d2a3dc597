package com.keeplynk.ai.memory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class MemoryService {

    private final AgentMemoryRepository repo;

    public MemoryService(AgentMemoryRepository repo) {
        this.repo = repo;
    }

    public String reuseOrCreate(String rawTag) {

        String normalized = TagNormalizer.normalize(rawTag);

        // 1️⃣ Exact match
        Optional<AgentMemory> exact =
            repo.findByTypeAndValue("TAG", normalized);

        if (exact.isPresent()) {
            increment(exact.get());
            return exact.get().getValue();
        }

        // 2️⃣ Alias match
        Optional<AgentMemory> alias =
            repo.findByTypeAndAliasesContaining("TAG", normalized);

        if (alias.isPresent()) {
            increment(alias.get());
            return alias.get().getValue();
        }

        // 3️⃣ Create new
        AgentMemory mem = new AgentMemory();
        mem.setType("TAG");
        mem.setValue(normalized);
        mem.setAliases(List.of(rawTag.toLowerCase()));
        mem.setUsageCount(1);
        mem.setCreatedAt(Instant.now());
        mem.setLastUsedAt(Instant.now());

        repo.save(mem);
        return normalized;
    }
    
    public String reuseOrCreateCategory(String rawCategory) {

        if (rawCategory == null || rawCategory.isBlank()) {
            return "General";
        }

        String normalized = rawCategory.trim();

        Optional<AgentMemory> exact =
                repo.findByTypeAndValue("CATEGORY", normalized);

        if (exact.isPresent()) {
            increment(exact.get());
            return exact.get().getValue();
        }

        Optional<AgentMemory> alias =
                repo.findByTypeAndAliasesContaining("CATEGORY", normalized);

        if (alias.isPresent()) {
            increment(alias.get());
            return alias.get().getValue();
        }

        // ⚠️ Guardrail: limit category creation
        long categoryCount = repo.countByType("CATEGORY");
        if (categoryCount >= 12) {
            return "General";
        }

        AgentMemory mem = new AgentMemory();
        mem.setType("CATEGORY");
        mem.setValue(normalized);
        mem.setAliases(List.of(rawCategory));
        mem.setUsageCount(1);
        mem.setCreatedAt(Instant.now());
        mem.setLastUsedAt(Instant.now());

        repo.save(mem);
        return normalized;
    }


    private void increment(AgentMemory mem) {
        mem.setUsageCount(mem.getUsageCount() + 1);
        mem.setLastUsedAt(Instant.now());
        repo.save(mem);
    }
}
