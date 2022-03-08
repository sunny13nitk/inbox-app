package io.javabrains.inbox.repo;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import io.javabrains.inbox.model.FolderByUser;

@Repository
public interface FolderByUserRepo extends CassandraRepository<FolderByUser, String>
{
	List<FolderByUser> findAllByUserid(String userId);
}
