package daangnmarket.daangn.project.apiTest;


import daangnmarket.daangn.project.controller.CommunityPostController;
import daangnmarket.daangn.project.repository.CommunityPostRepository;
import daangnmarket.daangn.project.service.CommunityPostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class CommunityPostApiTest {
    @Autowired
    private CommunityPostService communityPostService;

    @Autowired
    private CommunityPostController communityPostController;

    @Autowired
    private CommunityPostRepository communityPostRepository;


    @Test
    @Transactional
    public void createCommunityPost(){

    }
}
