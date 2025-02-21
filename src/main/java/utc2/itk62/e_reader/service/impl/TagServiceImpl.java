package utc2.itk62.e_reader.service.impl;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.BookCollection;
import utc2.itk62.e_reader.domain.entity.BookTag;
import utc2.itk62.e_reader.domain.entity.Tag;
import utc2.itk62.e_reader.domain.model.TagFilter;
import utc2.itk62.e_reader.repository.TagRepository;
import utc2.itk62.e_reader.service.TagService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public Tag createTag(String name) {
        Tag tag = Tag.builder().name(name).build();
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        tag.ifPresent(tagRepository::delete);
    }

    @Override
    public List<Tag> getAllTag(TagFilter tagFilter, Pagination pagination) {
        Specification<Tag> spec = Specification.where(null);

        if (tagFilter != null) {
            if (!CollectionUtils.isEmpty(tagFilter.getIds())) {
                spec = spec.and(((root, query, cb) -> root.get("id").in(tagFilter.getIds())));
            }
            if (tagFilter.getName() != null) {
                String namePattern = "%" + tagFilter.getName() + "%";
                spec = spec.and(((root, query, cb) -> cb.like(root.get("name"), namePattern)));
            }
            if (tagFilter.getBookIdNe() != null) {
                spec = spec.and((root, query, cb) -> {
                    Subquery<Long> subquery = query.subquery(Long.class);
                    Root<BookTag> bookTagRoot = subquery.from(BookTag.class);
                    subquery.select(bookTagRoot.get("tagId"))
                            .where(cb.equal(bookTagRoot.get("bookId"), tagFilter.getBookIdNe()));
                    return cb.in(root.get("id")).value(subquery).not();
                });
            }
        }

        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        Page<Tag> tagPage = tagRepository.findAll(spec, pageable);
        pagination.setTotal(tagPage.getTotalElements());
        return tagPage.toList();
    }
}
