package com.example.lianxi1.controller;
import com.example.lianxi1.config.HbaseConfig;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TestDemo {


    public static Connection connection=null;
    public static Admin admin=null;
    static {
        try {
            HbaseConfig hbaseConfig = new HbaseConfig();
            //2、创建连接对象
            connection= ConnectionFactory.createConnection(hbaseConfig.conf());
            //3、创建Admin对象
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取表中的某行数据
    public static void get()throws IOException{
        Table table = connection.getTable(TableName.valueOf("location"));
        Get get = new Get(Bytes.toBytes("rowkey1"));
        get.addColumn(Bytes.toBytes("place"),Bytes.toBytes("city"));
        Result result = table.get(get);
        byte[] byte1 = result.getValue(Bytes.toBytes("place"),Bytes.toBytes("city"));
        get.addColumn(Bytes.toBytes("place"),Bytes.toBytes("sum"));
        Result result1 = table.get(get);
        byte[] byte2 = result1.getValue(Bytes.toBytes("place"),Bytes.toBytes("sum"));
        System.out.println(new String(byte1)+": "+new String(byte2));
        table.close();
    }
    //批量删除hbase中的数据
    public static void delete()throws IOException{
        Table table = connection.getTable(TableName.valueOf("location"));
        List<Delete> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            String str="rowkey"+i;
            Delete delete = new Delete(Bytes.toBytes(str));
            list.add(delete);
        }
        table.delete(list);
        table.close();
    }
    //向hbase中批量插入数据
    public static void put()throws IOException{
        Table table = connection.getTable(TableName.valueOf("brand"));
        String[] str = {"金龙鱼","鲁花","福临门","十月稻田","柴火大院","三只松鼠","良品铺子","百草味","Member mark","比比赞","欧莱雅","珀莱雅","雅诗兰黛","兰蔻","赫莲娜","圣罗兰","肌肤之钥","彩棠","娜斯","玫珂菲","海尔","美的","小天鹅","TCL","小米","斯维诗","汤臣倍健","益节","健安喜","澳佳宝","卡诗","维达","护舒宝","海飞丝","欧莱雅","网易严选","鲜朗","皇家","麦富迪","蓝氏"};
        Double[] doubles = {7.6,4.4,3.6,2.6,2.0,9.0,7.9,7.4,1.4,1.3,26.1,21.5,18.5,17.7,11.7,3.2,2.7,2.2,2.1,1.7,77.1,70.2,27.4,19.5,16.1,4.5,2.9,1.5,1.1,1.0,3.6,2.5,2.2,2.0,1.8,1.2,1.2,1.0,0.7,0.6};
        String[] str2 = new String[doubles.length];
        int[] intid = {1,1,1,1,1,2,2,2,2,2,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5,6,6,6,6,6,7,7,7,7,7,8,8,8,8,8};
        String[] str11=new String[intid.length];
        for(int i=0;i<str2.length;i++){
            str2[i]=Double.toString(doubles[i]);
            str11[i]=Integer.toString(intid[i]);
        }
        List<Put> list = new ArrayList<>();
        for(int i=0;i<str.length;i++){
            String str1="rowkey"+i;
            String str3="goodname1";
            Put put  = new Put(Bytes.toBytes(str1));
            put.addColumn(Bytes.toBytes(str3), Bytes.toBytes("id"), Bytes.toBytes(str11[i]));
            put.addColumn(Bytes.toBytes(str3), Bytes.toBytes("name"), Bytes.toBytes(str[i]));
            put.addColumn(Bytes.toBytes(str3), Bytes.toBytes("sum"), Bytes.toBytes(str2[i]));
            list.add(put);
        }
        table.put(list);
        table.close();
    }
    public static void createTableTest() throws IOException {
        TableName tableName = TableName.valueOf("location");
        // 1.	判断表是否存在
        if (admin.tableExists(tableName)) {
            return;
        }
        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName);
        // 3.	使用ColumnFamilyDescriptorBuilder.newBuilder构建列蔟描述构建器
        // 创建列蔟也需要有列蔟的描述器，需要用一个构建起来构建ColumnFamilyDescriptor
        // 经常会使用到一个工具类：Bytes（hbase包下的Bytes工具类）
        // 这个工具类可以将字符串、long、double类型转换成byte[]数组
        // 也可以将byte[]数组转换为指定类型
        ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("C1"));
        // 4.	构建列蔟描述，构建表描述
        ColumnFamilyDescriptor cfDes = columnFamilyDescriptorBuilder.build();
        // 建立表和列蔟的关联
        tableDescriptorBuilder.setColumnFamily(cfDes);
        TableDescriptor tableDescriptor = tableDescriptorBuilder.build();
        // 5.	创建表
        admin.createTable(tableDescriptor);
    }
    //判断表是否存在
    public static boolean isTableExist(String tableName) throws IOException {
        boolean exists = admin.tableExists(TableName.valueOf(tableName));
        return exists;
    }

    public static void close(){
        if (admin!=null){
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (connection!=null){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(isTableExist("a"));
    }
}