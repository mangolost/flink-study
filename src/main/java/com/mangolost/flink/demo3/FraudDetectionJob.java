package com.mangolost.flink.demo3;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.source.TransactionSource;

/**
 *
 */
public class FraudDetectionJob {

    public static void main(String[] args) throws Exception {

        //设置执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //数据源头
        DataStream<Transaction> transactions = env
                .addSource(new TransactionSource())
                .name("transactions");

        DataStream<Alert> alerts = transactions
                .keyBy(Transaction::getAccountId) //对流进行分区
                .process(new FraudDetector()) //对流绑定了一个操作，这个操作将会对流上的每一个消息调用所定义好的函数
                .name("fraud-detector");

        alerts.addSink(new AlertSink()) //sink 会将 DataStream 写出到外部系统
              .name("send-alerts");

        env.execute("Fraud Detection"); //给任务传递一个任务名参数，并开始运行任务
    }

}
