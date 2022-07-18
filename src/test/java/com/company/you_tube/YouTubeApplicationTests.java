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
        String imageUrl = attachService.getImageUrl("ee405052-e34f-4c84-92fe-8423300c2941");
        System.out.println(imageUrl);
    }

}
