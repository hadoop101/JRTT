package xugong.com.test03test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class MyDaoTest {
    @Test
    public void add01() {
       MyDao myDao=new MyDao();
       myDao.add1();
    }

    @Test
    public void add02() {
        MyDao myDao=new MyDao();
        myDao.add2();
    }
}
