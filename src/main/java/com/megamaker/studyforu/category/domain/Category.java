package com.megamaker.studyforu.category.domain;

import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.category.domain.event.CategoryAddedEvent;
import com.megamaker.studyforu.common.event.Events;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
public class Category {
    private final Long id;
    private final Long parentId;
    private final Integer level;
    private final String name;

    public static void request(CategoryAddRequest request) {
        CategoryAddedEvent event = CategoryAddedEvent.from(request);
        Events.publish(event);
    }
}
