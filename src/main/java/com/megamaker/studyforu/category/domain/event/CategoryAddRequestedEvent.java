package com.megamaker.studyforu.category.domain.event;

import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.common.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryAddRequestedEvent extends BaseEvent {
    private String categoryName;
    private Integer parentLevel;
    private String description;
    private String userInfo;

    public static CategoryAddRequestedEvent from(CategoryAddRequest request, String description, String userInfo) {
        return new CategoryAddRequestedEvent(request.getName(), request.getParentLevel(), description, userInfo);
    }
}
