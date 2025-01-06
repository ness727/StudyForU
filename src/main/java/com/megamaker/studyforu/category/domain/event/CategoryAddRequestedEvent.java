package com.megamaker.studyforu.category.domain.event;

import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.common.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryAddRequestedEvent extends BaseEvent {
    private String categoryName;
    private Long parentId;
    private Integer parentLevel;
    private String description;
    private String userInfo;
    private String key;

    public static CategoryAddRequestedEvent from(CategoryAddRequest request, String description, String userInfo, String randomId) {
        return new CategoryAddRequestedEvent(request.getName(), request.getParentId(), request.getParentLevel(), description, userInfo, randomId);
    }
}
