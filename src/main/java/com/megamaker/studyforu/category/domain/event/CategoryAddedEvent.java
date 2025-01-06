package com.megamaker.studyforu.category.domain.event;

import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.common.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryAddedEvent extends BaseEvent {
    private Long parentId;
    private String name;
    private Integer level;

    public static CategoryAddedEvent from(CategoryAddRequest request) {
        return new CategoryAddedEvent(request.getParentId(), request.getName(), request.getLevel());
    }
}
