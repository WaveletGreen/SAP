package KHSAP;

import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

import util.JcoUtil;
import util.KHSAPUtil;
import util.KHXMLUtil;

@SuppressWarnings("unused")
public class FuncsCall {
	private static String ENTRIESName = "ENTRIES";
	private static String FunctionName = "RFC_GET_TABLE_ENTRIES";

	/**
	 * 獲取SAP连接
	 * 
	 * @param DestinationName
	 *            连接方式
	 */

	private JCoDestination getDestination(String DestinationName) {
		JCoDestination destination = null;
		try {
			destination = JCoDestinationManager.getDestination(DestinationName);
		} catch (JCoException e) {
			e.printStackTrace();
		}
		return destination;
	}

	/**
	 * 执行方法
	 * 
	 * @param destination
	 * @param FunctionName
	 *            方法名
	 */
	private JCoFunction exeFunction(JCoDestination destination, String FunctionName) {
		JCoFunction function = null;
		try {
			if (destination == null)
				return null;
			function = destination.getRepository().getFunction(FunctionName);
		} catch (JCoException e) {
			e.printStackTrace();
		}
		return function;
	}

	public JCoTable CallSAPFunc(String DestinationName, String FunctionName, String ENTRIESName) {
		JCoDestination destination = null;
		JCoFunction function = null;
		// 获取目的
		destination = getDestination(DestinationName);
		// 执行方法
		function = exeFunction(destination, FunctionName);
		function.getImportParameterList().setValue("TABLE_NAME", "MARA");
		function.getImportParameterList().setValue("MAX_ENTRIES", "20");
		if (destination != null && function != null) {
			try {
				function.execute(destination);
			} catch (JCoException e) {
				e.printStackTrace();
			}
			JCoTable tb = function.getTableParameterList().getTable(ENTRIESName);
			return tb;
		}
		return null;

	}

	public void CallSAPFuncWithTable(String DestinationName, String FunctionName, String ENTRIESName, JCoTable target,
			String... args) {

	}

	public void CallRFCTable() {
		try {
			StepByStepClient.CallRFCTable(FunctionName);
		} catch (JCoException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		FuncsCall call = new FuncsCall();
		call.testParseXML2();
	}

	public void testParseXML2() throws Exception {
		KHXMLUtil xUtil = new KHXMLUtil();
		Map<String, Map<String, String>> result = xUtil
				.autoLoadXMLFunctionNode(new File("src/SAPIntegrationConfig4.xml").toURL(), "BomTOSapSpec", null);
		for (Map<String, String> res : result.values()) {
			System.out.println("------------------------------------");
			for (String r : res.values()) {
				System.out.println(r);
			}
		}
		System.out.println("*****************************************************************");
		for (ArrayList<String> fieldResult : xUtil.getFieldConfigs().values()) {
			System.out.println(xUtil.getFieldConfigs().keySet().size());
			for (int i = 0; i < fieldResult.size(); i++) {
				System.out.println("-----" + fieldResult.get(i));
			}
			System.out.println("==================================");
		}
	}

	public void testParseXML() throws MalformedURLException, DocumentException {
		KHXMLUtil xUtil = new KHXMLUtil();
		Document document = xUtil.parse(new File("src/SAPIntegrationConfig4.xml").toURL());
		Map<String, String> funcResult;
		try {
			funcResult = xUtil.getFunctionElementConfigs(document, "PartTOSap");
			xUtil.getTableElementConfigs(xUtil.getEleFunction(), "T_MARA");
			for (String f : funcResult.keySet()) {
				System.out.println(f + ":");
				System.out.println("\t" + funcResult.get(f));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testUsingTable(JCoTable tb) {
		JcoUtil jcoUtil = new JcoUtil();
		jcoUtil.printJCoTable(tb);
	}

	public void test_CallRFCFunction() {
		try {
			StepByStepClient.CallRFCTable(FunctionName);
		} catch (JCoException e) {
			e.printStackTrace();
		}

	}

	public void getConnectPooled() {
		ConnectPooled pooled = new ConnectPooled();
	}

	public void getCallFunction() {
		FuncsCall call = new FuncsCall();

		JCoTable tb = call.CallSAPFunc(KHSAPUtil.ABAP_AS_POOLED, "RFC_GET_TABLE_ENTRIES", ENTRIESName);

		if (tb.getNumRows() == 0) {
			System.out.println("The result is empty!");
			return;
		}
		for (int i = 0; i < tb.getNumRows(); i++) {
			tb.setRow(i);
			System.out.println(tb.getValue("WA"));
			System.out.println(tb.getFieldCount() + "==================================\n");
		}
		testUsingTable(tb);
	}

}
