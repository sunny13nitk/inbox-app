package io.javabrains.inbox.repo;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import io.javabrains.inbox.model.Email;

@Repository
public interface EmailRepo extends CassandraRepository<Email, UUID>
{

}
