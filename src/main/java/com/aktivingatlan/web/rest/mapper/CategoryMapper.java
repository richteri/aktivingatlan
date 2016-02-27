package com.aktivingatlan.web.rest.mapper;

import com.aktivingatlan.domain.*;
import com.aktivingatlan.web.rest.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.nameHu", target = "parentNameHu")
    @Mapping(source = "parent.nameEn", target = "parentNameEn")
    @Mapping(source = "parent.nameDe", target = "parentNameDe")
    CategoryDTO categoryToCategoryDTO(Category category);

    @Mapping(source = "parentId", target = "parent")
    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    default Category categoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
