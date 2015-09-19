package com.aktivingatlan.repository.search;

import com.aktivingatlan.domain.Ownership;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Ownership entity.
 */
public interface OwnershipSearchRepository extends ElasticsearchRepository<Ownership, Long> {
}
