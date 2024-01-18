package com.example.lianxi1.tools;

import com.example.lianxi1.config.HbaseConfig;
import com.example.lianxi1.pojo.Good;
import com.example.lianxi1.pojo.place;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoodsHbase {
    public Connection connection=null;
    public Admin admin=null;
    public GoodsHbase(){
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

    public List<Good> getGoodSum(int id)throws IOException{
        Table table = connection.getTable(TableName.valueOf("brand"));  //hbase表名
        Scan scan = new Scan();
        String goods = "goodname1";  //列族
//        scan.setStartRow(Bytes.toBytes("rowket0"));
//        scan.setStopRow(Bytes.toBytes("rowkey4"));
        scan.addFamily(Bytes.toBytes(goods));
        ResultScanner scanner = table.getScanner(scan);
        List<Good> placelist = new ArrayList<>();
        for(Result result:scanner){
            int id1=Integer.parseInt(new String(result.getValue(Bytes.toBytes(goods),Bytes.toBytes("id"))));
//            System.out.println(new String(result.getValue(Bytes.toBytes(goods),Bytes.toBytes("name")))+": "+new String(result.getValue(Bytes.toBytes(goods),Bytes.toBytes("sum"))));
            Good good = new Good();
            good.setName(new String(result.getValue(Bytes.toBytes(goods),Bytes.toBytes("name"))));
            good.setSum(Double.parseDouble(new String(result.getValue(Bytes.toBytes(goods),Bytes.toBytes("sum")))));
//            if(id1==id) {
//                placelist.add(good);
//            }
            placelist.add(good);
        }
        return placelist;
    }

    public List<place> getall()throws IOException{
        Table table = connection.getTable(TableName.valueOf("location"));
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("place"));
        ResultScanner scanner = table.getScanner(scan);
        List<place> placelist = new ArrayList<>();
        for(Result result:scanner){
            System.out.println(new String(result.getValue(Bytes.toBytes("place"),Bytes.toBytes("city")))+": "+new String(result.getValue(Bytes.toBytes("place"),Bytes.toBytes("sum"))));
            place place1=new place();
            place1.setName(new String(result.getValue(Bytes.toBytes("place"),Bytes.toBytes("city"))));
            place1.setValue(Double.parseDouble(new String(result.getValue(Bytes.toBytes("place"),Bytes.toBytes("sum")))));
            placelist.add(place1);
        }
        return placelist;
    }
    public void close(){
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
}
