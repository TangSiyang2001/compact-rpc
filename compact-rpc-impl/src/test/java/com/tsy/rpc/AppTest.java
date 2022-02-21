package com.tsy.rpc;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        List<Integer> nums = new ArrayList<>();
        nums.add(999);
        nums.add(233);
        final List<InetSocketAddress> localhost = nums.stream().map(num -> new InetSocketAddress("localhost", num)).collect(Collectors.toList());
        localhost.forEach(System.out::println);
    }
}
