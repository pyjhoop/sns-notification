package com.study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRepositoryMemoryImplTest {
    private final NotificationRepositoryMemoryImpl sut = new NotificationRepositoryMemoryImpl();

    Notification notification;
    @BeforeEach
    void setUp() {
         notification = new Notification("1", 2L, NotificationType.LIKE, Instant.now(), Instant.now().plus(90, ChronoUnit.DAYS));
    }

    @Test
    void test_save() {
        sut.save(notification);
        Optional<Notification> saved = sut.findById(notification.getId());

        assertTrue(saved.isPresent());
    }

    @Test
    void test_find_by_id() {
        sut.save(notification);
        Optional<Notification> saved = sut.findById(notification.getId());

        Notification found = saved.orElseThrow();
        assertEquals("1", notification.getId());
        assertEquals(NotificationType.LIKE, notification.getType());
    }

    @Test
    void test_delete_by_id() {
        sut.save(notification);
        sut.deleteById("1");

        Optional<Notification> saved = sut.findById(notification.getId());
        assertFalse(saved.isPresent());
    }

}