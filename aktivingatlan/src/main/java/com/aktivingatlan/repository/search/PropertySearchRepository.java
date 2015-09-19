package com.aktivingatlan.repository.search;

import com.aktivingatlan.domain.Property;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Property entity.
 */
public interface PropertySearchRepository extends ElasticsearchRepository<Property, Long> {
}
