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
			//�������ļ�
	      Properties connectProperties = new Properties();
	      //���÷�������ַ
	      connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,

	            "192.168.90.104");
	      //����ϵͳ���
	      connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
	      //���ÿͻ���
	      connectProperties

	            .setProperty(DestinationDataProvider.JCO_CLIENT, "800");
	      //�����û���
	      connectProperties.setProperty(DestinationDataProvider.JCO_USER,

	            "khplmcs");

	      // ע�����������ִ�Сд�ģ�Ҫע���Сд
	      connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,

	            "112233");
	      //��������
	      connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "zh");

	      // *********���ӳط�ʽ��ֱ�Ӳ�ͬ��������������������������

	      // JCO_PEAK_LIMIT - ͬʱ�ɴ����������������0��ʾ�����ƣ�Ĭ��ΪJCO_POOL_CAPACITY��ֵ

	      // ���С��JCO_POOL_CAPACITY��ֵ�����Զ�����Ϊ��ֵ����û������JCO_POOL_CAPACITY�������Ϊ0

	      connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,

	            "10");

	      // JCO_POOL_CAPACITY - ���������������Ϊ0����û�����ӳ�Ч����Ĭ��Ϊ1

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
