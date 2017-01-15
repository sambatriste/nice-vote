package com.nablarch.example;

import com.nablarch.example.entity.Theme;
import nablarch.common.dao.EntityList;
import nablarch.common.dao.UniversalDao;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by kawasaki on 2017/01/15.
 */
public class HogeTest {

    @ClassRule
    public static SystemRepositoryResource systemRepositoryResource = new SystemRepositoryResource();

    @Rule
    public DatabaseResource databaseResource = new DatabaseResource();

    @Test
    public void test() {

        EntityList<Theme> all = UniversalDao.findAll(Theme.class);
        for (Theme theme : all) {
            System.out.println("theme.getTitle() = " + theme.getTitle());
        }




    }

}
