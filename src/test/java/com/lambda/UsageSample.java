package com.lambda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@RunWith(MockitoJUnitRunner.class)
public class UsageSample {

    /**
     * Predicate<T>               T->boolean
     * Consumer<T>                T->void
     * Function<T,R>              T->R
     * Supplier<T>               ()->T
     * UnaryOperator<T>          T->T
     * BinaryOperator<T>        (T,T)->T
     * BiPredicate<L,R>         (L,R)->boolean
     * BiConsumer<T,U>          (T,U)->void
     * BiFunction<T,U,R>        (T,U)->R
     */

    private List<Transaction> transactions;
    private List<Dish> menu;

    @Before
    public void setup() {
        Trader jack = new Trader("Jack", "Alaska");
        Trader tom = new Trader("Tom", "Boston");
        Trader teddy = new Trader("Teddy", "Cambridge");
        Trader teddy1 = new Trader("Teddy", "Cambridge");
        Trader ward = new Trader("Ward", "Cambridge");
        Trader mack = new Trader("Mack", "Hawaii");
        Trader richard = new Trader("Richard", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(jack, 2018, 300),
                new Transaction(tom, 2019, 1000),
                new Transaction(teddy, 2018, 400),
                new Transaction(teddy1, 2018, 400),
                new Transaction(ward, 2019, 710),
                new Transaction(mack, 2019, 700),
                new Transaction(richard, 2018, 950));

        menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
    }

    /**
     * 找出2018年所有的交易，并按交易额排序（从低到高）
     *
     * 输出：
     * Transaction{trader=Trader{name='Jack', city='Alaska'}, year=2018, value=300}
     * Transaction{trader=Trader{name='Teddy', city='Cambridge'}, year=2018, value=400}
     * Transaction{trader=Trader{name='Richard', city='Cambridge'}, year=2018, value=950}
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
     * 找出2018年所有的交易，并按交易额排序（从高到低）
     *
     * 输出：
     * Transaction{trader=Trader{name='Richard', city='Cambridge'}, year=2018, value=950}
     * Transaction{trader=Trader{name='Teddy', city='Cambridge'}, year=2018, value=400}
     * Transaction{trader=Trader{name='Jack', city='Alaska'}, year=2018, value=300}
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
     *
     * 输出：
     * Alaska
     * Boston
     * Cambridge
     * Hawaii
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
     * 查找所有来自Cambridge的交易员，并按姓名排序
     *
     * 输出：
     * Trader{name='Richard', city='Cambridge'}
     * Trader{name='Teddy', city='Cambridge'}
     * Trader{name='Ward', city='Cambridge'}
     */
    @Test
    public void test4() {
        List<Trader> traderList = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()                 //类的distinct()是根据equals()方法工作的，因此需覆写equals()方法（覆写equals()需同时覆写hashCode()）
                .sorted(comparing(Trader::getName))
                .collect(toList());
        traderList.forEach(System.out::println);
    }

    @Test
    public void test4_01() {
        List<Trader> traderList = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .filter(Trader.distinctByKey(Trader::getName))       //不使用distinct()进行去重
                .sorted(comparing(Trader::getName))
                .collect(toList());
        traderList.forEach(System.out::println);
    }

    /**
     * 返回所有交易员的姓名字符串，按字母顺序排序
     *
     * 输出：
     * Jack
     * Mack
     * Richard
     * Teddy
     * Tom
     * Ward
     */
    @Test
    public void test5() {
        List<String> nameList = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted(String::compareTo)
                .collect(toList());
        nameList.forEach(System.out::println);
    }

    /**
     * 有没有交易员是在波士顿工作的
     *
     * 输出：
     * true
     */
    @Test
    public void test6() {
        boolean existInBoston = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Boston"));
        System.out.println(existInBoston);
    }

    /**
     * 打印生活在剑桥的交易员的所有交易额
     *
     * 输出：
     * 400
     * 710
     * 950
     */
    @Test
    public void test7() {
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    /**
     * 计算所有交易额的总和
     *
     * 输出：
     * 4060
     */
    @Test
    public void test8() {
        int sum = transactions.stream()
                .mapToInt(Transaction::getValue)
                .sum();
        System.out.println(sum);
    }

    /**
     * 所有交易中，最高的交易额是多少
     *
     * 输出：
     * 1000
     * 1000
     */
    @Test
    public void test9() {
        //方式一
        transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)               //存在装箱问题，将int类型的value封装成Integer
                .ifPresent(System.out::println);

        //方式二（推荐）
        transactions.stream()
                .mapToInt(Transaction::getValue)       //将流中元素特化为int，避免装箱问题
                .max()
                .ifPresent(System.out::println);
    }

    /**
     * 找到交易额最小的交易
     *
     * 输出：
     * Transaction{trader=Trader{name='Jack', city='Alaska'}, year=2018, value=300}
     * Transaction{trader=Trader{name='Jack', city='Alaska'}, year=2018, value=300}
     */
    @Test
    public void test10() {
        //方式一
        transactions.stream()
                .reduce((x, y) -> x.getValue() < y.getValue() ? x : y)
                .ifPresent(System.out::println);

        //方式二（推荐）
        transactions.stream()
                .min(comparingInt(Transaction::getValue))
                .ifPresent(System.out::println);
    }

    /**
     * 生成1--100之间所有的偶数
     *
     * 输出：
     * [2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50,
     * 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90, 92, 94, 96, 98, 100]
     */
    @Test
    public void test11() {
        List<Integer> collect = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0)
                .boxed()
                .collect(toList());
        System.out.println(collect);
    }

    /**
     * 生成1--100之间所有的质数
     *
     * 输出：
     * total size: 25
     * [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]
     */
    @Test
    public void test12() {
        //方式一
        List<Integer> collect = IntStream.rangeClosed(2, 100)    //1不是质数
                .filter(candidate -> IntStream.range(2, candidate).noneMatch(i -> candidate % i == 0))
                .boxed()
                .collect(toList());
        System.out.println("total size: " + collect.size());
        System.out.println(collect);

        //方式二（推荐）
        List<Integer> collect1 = IntStream.rangeClosed(2, 100)
                .filter(candidate -> IntStream.rangeClosed(2, (int) Math.sqrt(candidate)).noneMatch(i -> candidate % i == 0))  //优化算法
                .boxed()
                .collect(toList());
        System.out.println("total size: " + collect1.size());
        System.out.println(collect1);
    }

    /**
     * 生成斐波那契数列的前20个
     *
     * 输出：
     * total size: 20
     * [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181]
     */
    @Test
    public void test13() {
        List<Integer> collect = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(t -> t[0])
                .collect(toList());
        System.out.println("total size: " + collect.size());
        System.out.println(collect);
    }

    /**
     *
     */
    @Test
    public void test14() {

    }

    /**
     *
     */
    @Test
    public void test15() {

    }
}
