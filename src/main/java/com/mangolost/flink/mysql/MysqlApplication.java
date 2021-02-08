package com.mangolost.flink.mysql;

import com.mangolost.flink.mysql.entity.EmployeeAddDto;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.util.ArrayList;
import java.util.List;

import static org.apache.flink.table.api.Expressions.$;

/**
 *
 */
public class MysqlApplication {

    /**
     *
     * @return
     */
    private static List<EmployeeAddDto> genData1() {
        List<EmployeeAddDto> list = new ArrayList<>();
        list.add(new EmployeeAddDto("51038300", "abc", 33, "开发", "大专", "cc"));
        list.add(new EmployeeAddDto("51038301", "xyz", 22, "测试", "本科", "cd"));
        return list;
    }

    /**
     *
     */
    public static void func1() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        List<EmployeeAddDto> employeeList = genData1();
        DataStream<EmployeeAddDto> employeeDataStream = env
                .fromCollection(employeeList)
                .name("employeeStream");

        String sql = "CREATE TABLE t_employee (\n" +
                "                `employee_no` string,\n" +
                "                `employee_name` string,\n" +
                "                `age` int,\n" +
                "                `position` string,\n" +
                "                `degree` string,\n" +
                "                `remark` string\n" +
                "        ) WITH (\n" +
                "                'connector' = 'jdbc',\n" +
                "                'url' = 'jdbc:mysql://localhost:3306/db_test?useSSL=false',\n" +
                "                'table-name' = 't_employee',\n" +
                "                'username' = 'root',\n" +
                "                'password' = 'bstek'\n" +
                "        )";

        tableEnv.executeSql(sql);

        Table table1 = tableEnv.fromDataStream(employeeDataStream,
                $("employeeNo").as("employee_no"),
                $("employeeName").as("employee_name"),
                $("age"),
                $("position"),
                $("degree"),
                $("remark"));

        TableResult tableResult = table1.executeInsert("t_employee");
        System.out.println(tableResult.getJobClient().get().getJobStatus());
    }

    /**
     *
     */
    public static void func2() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        String createSourceTableSql = "CREATE TABLE t_employee_source (\n" +
                "                `employee_no` string,\n" +
                "                `employee_name` string,\n" +
                "                `age` int,\n" +
                "                `position` string,\n" +
                "                `degree` string,\n" +
                "                `remark` string\n" +
                "        ) WITH (\n" +
                "               'connector' = 'kafka',\n" + // 使用 kafka connector
                "               'topic' = 'employee',\n" +  // kafka topic
                "               'properties.bootstrap.servers' = 'myaliyun:9092',\n" +
                "               'properties.group.id' = 'testGroup',\n" +
                "               'scan.startup.mode' = 'group-offsets',\n" +
                "               'format' = 'json'\n" + // 数据源格式为 json
                "        )";
        tableEnv.executeSql(createSourceTableSql);

        String createTargetTableSql = "CREATE TABLE t_employee_target (\n" +
                "                `employee_no` string,\n" +
                "                `employee_name` string,\n" +
                "                `age` int,\n" +
                "                `position` string,\n" +
                "                `degree` string,\n" +
                "                `remark` string\n" +
                "        ) WITH (\n" +
                "                'connector' = 'jdbc',\n" +
                "                'url' = 'jdbc:mysql://localhost:3306/db_test?useSSL=false',\n" +
                "                'table-name' = 't_employee',\n" +
                "                'username' = 'root',\n" +
                "                'password' = 'bstek'\n" +
                "        )";
        tableEnv.executeSql(createTargetTableSql);

        TableResult tableResult = tableEnv.executeSql("insert into t_employee_target select * from t_employee_source");
        System.out.println(tableResult.getJobClient().get().getJobStatus());
    }

    public static void main(String[] args) {

//        func1();

        func2();

    }

}
