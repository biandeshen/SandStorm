package top.sandstorm.sandstorm.common.connector.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import top.sandstorm.sandstorm.common.api.UserAPI;
import top.sandstorm.sandstorm.common.entity.User;
import top.sandstorm.sandstorm.common.rest.Result;
import top.sandstorm.sandstorm.common.service.UserService;

import static org.mockito.Mockito.*;

public class UserAPITest {
    @Mock
    UserService userService;
    @InjectMocks
    UserAPI userAPI;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd() throws Exception {
        Result result = userAPI.add(new User());
        Assert.assertEquals(new Result(), result);
    }

    @Test
    public void testDelete() throws Exception {
        Result result = userAPI.delete(Integer.valueOf(0));
        Assert.assertEquals(new Result(), result);
    }

    @Test
    public void testUpdate() throws Exception {
        Result result = userAPI.update(new User());
        Assert.assertEquals(new Result(), result);
    }

    @Test
    public void testDetail() throws Exception {
        when(userService.findById(anyInt())).thenReturn(new User());

        Result result = userAPI.detail(Integer.valueOf(0));
        Assert.assertEquals(new Result(), result);
    }

    @Test
    public void testList() throws Exception {
        when(userService.findAll(anyInt(), anyInt())).thenReturn(null);

        Result result = userAPI.list(Integer.valueOf(1), Integer.valueOf(10));
        Assert.assertEquals(new Result(), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme