package com.lineplus.featurestore.feature.repository.custom;

import com.lineplus.featurestore.feature.domain.dao.BaseFeatureGroupDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class BaseFeatureGroupCustomRepositoryTest {

    @Autowired
    BaseFeatureGroupCustomRepository baseFeatureGroupCustomRepository;

    List<BaseFeatureGroupDao> baseFeatureGroupDaoList;


    @Test
    public void  findBaseFeatureGroupTest(){
        this.baseFeatureGroupDaoList = this.baseFeatureGroupCustomRepository.findBaseFeatureGroup();
        this.baseFeatureGroupDaoList.forEach(System.out::println);
        Assertions.assertEquals(baseFeatureGroupDaoList.size(), 28);
    }


}