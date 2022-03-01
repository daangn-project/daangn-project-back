package daangnmarket.daangn.project.apiTest;


import daangnmarket.daangn.project.controller.CommunityController;
import daangnmarket.daangn.project.repository.CommunityRepository;
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
    private CommunityController communityController;

    @Autowired
    private CommunityRepository communityPostRepository;


    @Test
    @Transactional
    public void createCommunityPost(){

    }
}
