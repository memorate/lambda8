package com.lambda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@RunWith(MockitoJUnitRunner.class)
public class UsageSample {

    private List<Transaction> transactions;

    @Before
    public void setup() {
        Trader jack = new Trader("Jack", "Alaska");
        Trader tom = new Trader("Tom", "Boston ");
        Trader teddy = new Trader("Teddy", "Columbia");
        Trader ward = new Trader("Ward", "Columbia");
        Trader mack = new Trader("Mack", "Hawaii ");
        Trader richard = new Trader("Richard", "New York ");

        transactions = Arrays.asList(
                new Transaction(jack, 2018, 300),
                new Transaction(tom, 2019, 1000),
                new Transaction(teddy, 2018, 400),
                new Transaction(ward, 2019, 710),
                new Transaction(mack, 2019, 700),
                new Transaction(richard, 2018, 950));
    }

    /**
     * 找出2018年所有的交易并按交易额排序（从低到高）
     */
    @Test
    public void test1() {
        List<Transaction> sortedTransactionList = transactions.stream()
                .filter(t -> t.getYear() == 2018)
                .sorted(comparingInt(Transaction::getValue))
                .collect(toList());
        sortedTransactionList.forEach(System.out::println);
    }

    /**
     * 找出2018年所有的交易并按交易额排序（从高到低）
     */
    @Test
    public void test2() {
        List<Transaction> sortedTransactionList = transactions.stream()
                .filter(t -> t.getYear() == 2018)
                .sorted(comparingInt(Transaction::getValue).reversed())
                .collect(toList());
        sortedTransactionList.forEach(System.out::println);
    }

    /**
     * 交易员都在哪些不同城市工作过
     */
    @Test
    public void test3() {
        List<String> cityNames = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(toList());
        cityNames.forEach(System.out::println);
    }

    /**
     * 查找所有来自Columbia的交易员，并按姓名排序
     */
    @Test
    public void test4() {
        List<Trader> traderList = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Columbia"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        traderList.forEach(System.out::println);
    }

    @Test
    public void test5() {

    }

    @Test
    public void test6() {

    }

    @Test
    public void test7() {

    }

    @Test
    public void test8() {

    }

    @Test
    public void test9() {

    }

    @Test
    public void test10() {

    }
}
