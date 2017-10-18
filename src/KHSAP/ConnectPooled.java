package KHSAP;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class ConnectPooled {
	
	static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
	static {
			//不配置文件
	      Properties connectProperties = new Properties();
	      //配置服务器地址
	      connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,

	            "192.168.90.104");
	      //配置系统编号
	      connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
	      //配置客户端
	      connectProperties

	            .setProperty(DestinationDataProvider.JCO_CLIENT, "800");
	      //配置用户名
	      connectProperties.setProperty(DestinationDataProvider.JCO_USER,

	            "khplmcs");

	      // 注：密码是区分大小写的，要注意大小写
	      connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,

	            "112233");
	      //配置语言
	      connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "zh");

	      // *********连接池方式与直接不同的是设置了下面两个连接属性

	      // JCO_PEAK_LIMIT - 同时可创建的最大活动连接数，0表示无限制，默认为JCO_POOL_CAPACITY的值

	      // 如果小于JCO_POOL_CAPACITY的值，则自动设置为该值，在没有设置JCO_POOL_CAPACITY的情况下为0

	      connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,

	            "10");

	      // JCO_POOL_CAPACITY - 空闲连接数，如果为0，则没有连接池效果，默认为1

	      connectProperties.setProperty(

	            DestinationDataProvider.JCO_POOL_CAPACITY, "5");

	      createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);

	   }
	   static void createDataFile(String name, String suffix, Properties properties) 
	   {

		      File cfg = new File(name + "." + suffix);

		      if (!cfg.exists()) {

		         try {

		            FileOutputStream fos = new FileOutputStream(cfg, false);

		            properties.store(fos, "KH QAS TEST !");

		            fos.close();

		         } catch (Exception e) {

		            e.printStackTrace();

		         }

		      }

		}	
}
