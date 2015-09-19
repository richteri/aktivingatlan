package com.aktivingatlan.repository.search;

import com.aktivingatlan.domain.Apartment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Apartment entity.
 */
public interface ApartmentSearchRepository extends ElasticsearchRepository<Apartment, Long> {
}
