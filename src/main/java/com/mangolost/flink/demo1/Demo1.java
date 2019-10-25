package com.mangolost.flink.demo1;

import com.mangolost.flink.WordWithCount;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Demo1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo1.class);

    private static final String FILE_PATH = "demo1.txt";

    public static void main(String[] args) {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> text = env.readTextFile(FILE_PATH);
        //用 WordWithCount 保存单词和次数信息
        DataSet<WordWithCount> counts = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String s, Collector<WordWithCount> collector) {
                String[] tokens = s.toLowerCase().split("\\s+");
                for (String token : tokens) {
                    if (StringUtils.isNoneBlank(token)) {
                        collector.collect(new WordWithCount(token, 1));
                    }
                }
            }
        }).groupBy(WordWithCount::getWord)
                .reduce((ReduceFunction<WordWithCount>) (wc, t1) -> new WordWithCount(wc.getWord(), wc.getCount() + t1.getCount()));
        try {
            List<WordWithCount> list = counts.collect();
            for (WordWithCount wc : list) {
                LOGGER.info(wc.toString());
            }
        } catch (Exception e) {
            LOGGER.error("error: ", e);
        }
    }

}
