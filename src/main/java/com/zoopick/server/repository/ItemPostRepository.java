package com.zoopick.server.repository;

import com.zoopick.server.dto.item.ItemPostFilter;
import com.zoopick.server.entity.ItemCategory;
import com.zoopick.server.entity.ItemColor;
import com.zoopick.server.entity.ItemPost;
import com.zoopick.server.entity.ItemStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemPostRepository extends JpaRepository<ItemPost, Long>, JpaSpecificationExecutor<ItemPost> {
    static Specification<ItemPost> hasStatus(ItemStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null)
                return null;
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    static Specification<ItemPost> hasCategory(ItemCategory category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null)
                return null;
            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    static Specification<ItemPost> hasColor(ItemColor color) {
        return (root, query, criteriaBuilder) -> {
            if (color == null)
                return null;
            return criteriaBuilder.equal(root.get("color"), color);
        };
    }

    static Specification<ItemPost> applyFilter(ItemPostFilter filter) {
        return Specification.where(hasStatus(filter.getStatus()))
                .and(hasCategory(filter.getCategory()))
                .and(hasColor(filter.getColor()));
    }
}
