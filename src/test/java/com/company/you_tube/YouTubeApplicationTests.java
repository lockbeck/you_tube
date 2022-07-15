package com.company.you_tube;

import com.company.service.AttachService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YouTubeApplicationTests {
@Autowired
private AttachService attachService;
    @Test
    void contextLoads() {
        attachService.getImageUrl("sdfbfadvdv");
    }

}
