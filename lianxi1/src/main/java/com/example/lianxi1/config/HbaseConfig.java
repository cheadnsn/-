package com.example.lianxi1.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class HbaseConfig {
    public Configuration conf(){
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.rootdir", "hdfs://master:9000/hbase");
        configuration.set("hbase.zookeeper.quorum","master01:2181,slave01:2181");
        return configuration;
    }
}
