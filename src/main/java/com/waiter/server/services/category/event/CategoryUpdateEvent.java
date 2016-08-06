package com.waiter.server.services.category.event;

import com.waiter.server.services.event.ApplicationEvent;

/**
 * Created by hovsep on 8/5/16.
 */
public class CategoryUpdateEvent implements ApplicationEvent {

    private Long categoryId;

    public CategoryUpdateEvent(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
