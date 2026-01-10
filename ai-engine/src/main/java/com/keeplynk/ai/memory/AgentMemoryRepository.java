package com.keeplynk.ai.memory;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;

@ConditionalOnProperty(name = "spring.data.mongodb.uri")
public interface AgentMemoryRepository
extends MongoRepository<AgentMemory, String> {

Optional<AgentMemory> findByTypeAndValue(
String type,
String value
);

Optional<AgentMemory> findByTypeAndAliasesContaining(
String type,
String alias
);

long countByType(String type);
}