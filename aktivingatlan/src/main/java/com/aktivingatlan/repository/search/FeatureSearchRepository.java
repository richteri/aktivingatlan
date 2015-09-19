package com.aktivingatlan.repository.search;

import com.aktivingatlan.domain.Feature;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Feature entity.
 */
public interface FeatureSearchRepository extends ElasticsearchRepository<Feature, Long> {
}
