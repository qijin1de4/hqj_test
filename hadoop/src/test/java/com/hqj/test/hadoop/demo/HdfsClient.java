package com.hqj.test.hadoop.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsClient {

    private FileSystem init() throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URI("hdfs://ubun1:8020");
        final Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "2");
        return FileSystem.get(uri, configuration, "hqj");
    }

    @Test
    public void makeDir() throws URISyntaxException, IOException, InterruptedException {
        try(FileSystem fs = init()) {
            fs.mkdirs(new Path("/xiyou/huaguoshan"));
        }
    }

    @Test
    public void put() throws URISyntaxException, IOException, InterruptedException {
       try(FileSystem fs = init()) {
           fs.copyFromLocalFile(false, true, new Path("/tmp/huaguoshan/sunwukong"), new Path("hdfs://ubun1:8020/xiyou/huaguoshan"));
       }
    }
}
