package com.lambda;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UsageSampleTest {

    private List<Transaction> transactions;

    @Before
    public void setup() {
        Trader jack = new Trader("Jack", "Alaska");
        Trader tom = new Trader("Tom", "Boston ");
        Trader teddy = new Trader("Teddy", "Columbia");
        Trader ward = new Trader("Ward", "Detroit ");
        Trader mack = new Trader("Mack", "Hawaii ");
        Trader richard = new Trader("Richard", "New York ");

        transactions = Arrays.asList(
                new Transaction(jack, 2018, 300),
                new Transaction(tom, 2019, 1000),
                new Transaction(teddy, 2018, 400),
                new Transaction(ward, 2019, 710),
                new Transaction(mack, 2019, 700),
                new Transaction(richard, 2019, 950));
    }
}
