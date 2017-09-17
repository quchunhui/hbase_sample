package api;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class create_table_sample1 {
    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.6.3,192.168.6.4,192.168.6.5");
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        HTableDescriptor desc = new HTableDescriptor(TableName.valueOf("TEST1"));
        desc.setMemStoreFlushSize(2097152L);          //2M(默认128M)
        desc.setMaxFileSize(10485760L);               //10M(默认10G)

        HColumnDescriptor family1 = new HColumnDescriptor(constants.COLUMN_FAMILY_DF.getBytes());
        family1.setTimeToLive(2 * 60 * 60 * 24);     //过期时间
        family1.setMaxVersions(2);                   //版本数
        desc.addFamily(family1);
        HColumnDescriptor family2 = new HColumnDescriptor(constants.COLUMN_FAMILY_EX.getBytes());
        family2.setTimeToLive(3 * 60 * 60 * 24);     //过期时间
        family2.setMaxVersions(3);                   //版本数
        desc.addFamily(family2);

        admin.createTable(desc);
        admin.close();
        connection.close();
    }
}