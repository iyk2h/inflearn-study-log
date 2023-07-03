package me.study;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    public void getterSetter() {
        Member member = new Member();
        member.setName("test");
        Assert.assertEquals(member.getName(), "test");
    }
}