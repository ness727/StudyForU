package com.megamaker.studyforu.category.domain;

import com.megamaker.studyforu.category.domain.dto.AddRequestUserInfo;
import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.category.domain.event.CategoryAddRequestedEvent;
import com.megamaker.studyforu.common.event.Events;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Builder
public class Category {
    private final Long id;
    private final Long parentId;
    private final Integer level;
    private final String name;

    public static void addRequest(CategoryAddRequest request, AddRequestUserInfo userInfo, List<Category> categoryList) {
        /*
            계층 구조 description 조립
            ex) 생활(0) -> 잡화(0) -> 그릇[추가 요청]
         */
        List<String> categoryStringList = new ArrayList<>(
                categoryList.stream()
                        .map(category -> category.getName() + "(" + category.getLevel() + ")")
                        .toList()
        );
        categoryStringList.add(request.getName() + "[추가 요청]");
        String description = String.join(" -> ", categoryStringList);

        CategoryAddRequestedEvent event = CategoryAddRequestedEvent.from(request, description,
                userInfo.getUsername() + "(" + userInfo.getUserId() + ")");
        Events.publish(event);
    }
}
