package util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

public class GenerateBean {
	private List<String> funcProperties;
	private KHXMLUtil xmlUtil = new KHXMLUtil();
	private Document document;
	private Map<String, ArrayList<String>> table=new HashMap<String,ArrayList<String>>();
	public GenerateBean(URL url) throws DocumentException {
		super();
		document = xmlUtil.parse(url);
	}

	public void parse(String functionName) throws Exception {
		if ("".equals(functionName) || null == functionName)
			xmlUtil.getFunctionElementConfigs(document, functionName);
		// Element funcEle=xmlUtil.getEleFunction();
		// for (Attribute attr : funcEle.attributes()) {
		// funcProperties.add(attr.getName());
		// }
		Element root = document.getRootElement();
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			Element e = it.next();
			if (functionName.equals(e.attribute("Name").getValue()))
				parseNode(e);
		}
	}

	private Element parseNode(Element node) {
		System.out.println(node.attribute("Name").getValue());
		switch (node.getName()) {
		case "Function":
			parseIsFunction(node);
			break;
		case "Table":
			parseIsTable(node);
			break;
		case "FieldConfig":
			parseIsFieldConfig(node);

			break;
		default:
			break;
		}

		return null;

	}

	private void parseIsFieldConfig(Element node) {

	}

	private void parseIsTable(Element node) {

	}

	private void parseIsFunction(Element node) {
		for (Iterator<Element> it = node.elementIterator(); it.hasNext();) {
			Element e = it.next();
				parseNode(e);
		}
	}


	
	private void generate(ArrayList<String> params){
		
	}
	
	
	public static void main(String[] args) throws Exception {

		GenerateBean bean = new GenerateBean(new File("src/SAPIntegrationConfig4.xml").toURL());
		bean.parse("BomTOSapSpec");

	}
}
