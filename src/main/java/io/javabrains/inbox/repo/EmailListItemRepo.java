package io.javabrains.inbox.repo;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import io.javabrains.inbox.model.EmailListItem;
import io.javabrains.inbox.model.EmailListItemKey;

@Repository
public interface EmailListItemRepo extends CassandraRepository<EmailListItem, EmailListItemKey>
{
	public List<EmailListItem> findAllByKey_UseridAndKey_Label(String userId, String Label);
}
