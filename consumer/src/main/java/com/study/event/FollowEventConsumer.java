package com.study.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class FollowEventConsumer {

    @Bean("follow")
    public Consumer<FollowEvent> follow() {
        return follow -> log.info(follow.toString());
    }
}
