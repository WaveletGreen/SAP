package KHSAP;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataProvider;

import util.KHXMLUtil;

/**
 * ֱ��д�����ļ����ڴ���
 * 
 * @author connor
 *
 */
public class ConnectPooled {

	static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
	static {
		// �������ļ�
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
		// ���÷�������ַ
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, Organize.get("AppServerHost"));
		// ����ϵͳ���
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, Organize.get("SystemNumber"));
		// ���ÿͻ���
		connectProperties

				.setProperty(DestinationDataProvider.JCO_CLIENT, Organize.get("Client"));
		// �����û���
		connectProperties.setProperty(DestinationDataProvider.JCO_USER,

				Organize.get("User"));

		// ע�����������ִ�Сд�ģ�Ҫע���Сд
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,

				Organize.get("Password"));
		// ��������
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG, Organize.get("Language"));

		// *********���ӳط�ʽ��ֱ�Ӳ�ͬ��������������������������

		// JCO_PEAK_LIMIT - ͬʱ�ɴ����������������0��ʾ�����ƣ�Ĭ��ΪJCO_POOL_CAPACITY��ֵ

		// ���С��JCO_POOL_CAPACITY��ֵ�����Զ�����Ϊ��ֵ����û������JCO_POOL_CAPACITY�������Ϊ0

		connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,

				Organize.get("JCO_PEAK_LIMIT"));

		// JCO_POOL_CAPACITY - ���������������Ϊ0����û�����ӳ�Ч����Ĭ��Ϊ1

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
