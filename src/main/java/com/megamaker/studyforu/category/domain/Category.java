package com.megamaker.studyforu.category.domain;

import com.megamaker.studyforu.category.domain.dto.AddRequestUserInfo;
import com.megamaker.studyforu.category.domain.dto.CategoryAddRequest;
import com.megamaker.studyforu.category.domain.event.CategoryAddRequestedEvent;
import com.megamaker.studyforu.category.exception.EmptyCategoryException;
import com.megamaker.studyforu.common.event.Events;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Builder
public class Category {
    private final Long id;
    private final Long parentId;
    private final Integer level;
    private final String name;

    public static void addRequest(CategoryAddRequest request, AddRequestUserInfo userInfo,
                                  List<Category> categoryList, CategoryAddKeyStore categoryAddKeyStore) {
        if (categoryList.isEmpty()) throw new EmptyCategoryException("카테고리 목록이 비었습니다");

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

        String randomId = UUID.randomUUID().toString();
        categoryAddKeyStore.save(randomId);  // id 임시 저장소에 랜덤 id 저장
        CategoryAddRequestedEvent event = CategoryAddRequestedEvent.from(request, description,
                userInfo.getUsername() + "(" + userInfo.getUserId() + ")",
                randomId);

        Events.publish(event);  // 이벤트 발행
    }
}
