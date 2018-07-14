package io.francoisbotha.bdgasadmin.data;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class AppInit implements ApplicationRunner{

    public void run(ApplicationArguments args) {
        log.info("Application initialisation tasks");
    }

}
