package KHSAP;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataProvider;

import util.KHXMLUtil;

/**
 * 直接写配置文件在内存中
 * 
 * @author connor
 *
 */
public class ConnectPooled {

	static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
	static {
		// 不配置文件
		Properties connectProperties = new Properties();
		KHXMLUtil util = new KHXMLUtil();
		try {
			util.parse(new File("src/SAPIntegrationConfig4.xml").toURL());
			util.getFunctionElementConfigs(util.getDocument(), "KHYJ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> Organize = util.getFunctionConfigs();
		// 配置服务器地址
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, Organize.get("AppServerHost"));
		// 配置系统编号
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, Organize.get("SystemNumber"));
		// 配置客户端
		connectProperties

				.setProperty(DestinationDataProvider.JCO_CLIENT, Organize.get("Client"));
		// 配置用户名
		connectProperties.setProperty(DestinationDataProvider.JCO_USER,

				Organize.get("User"));

		// 注：密码是区分大小写的，要注意大小写
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,

				Organize.get("Password"));
		// 配置语言
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG, Organize.get("Language"));

		// *********连接池方式与直接不同的是设置了下面两个连接属性

		// JCO_PEAK_LIMIT - 同时可创建的最大活动连接数，0表示无限制，默认为JCO_POOL_CAPACITY的值

		// 如果小于JCO_POOL_CAPACITY的值，则自动设置为该值，在没有设置JCO_POOL_CAPACITY的情况下为0

		connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,

				Organize.get("JCO_PEAK_LIMIT"));

		// JCO_POOL_CAPACITY - 空闲连接数，如果为0，则没有连接池效果，默认为1

		connectProperties.setProperty(

				DestinationDataProvider.JCO_POOL_CAPACITY, Organize.get("PoolSize"));

		createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);

	}

	static void createDataFile(String name, String suffix, Properties properties) {

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
