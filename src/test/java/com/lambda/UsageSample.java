package com.lambda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

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
        Trader ward = new Trader("Ward", "Columbia ");
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
}
