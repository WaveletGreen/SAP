package util;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class KHXMLUtil {
	private Document document;
	private SAXReader reader;
	private Map<String, String> FunctionConfigs = new LinkedHashMap<String, String>();
	private Map<String, String> TableConfigs = new LinkedHashMap<String, String>();
	private Map<Integer, ArrayList<String>> FieldConfigs = new LinkedHashMap<Integer, ArrayList<String>>();
	private Integer index = 0;
	private Element eleFunction;
	private Element eleTable;

	public Document getDocument() {
		return document;
	}

	public Element getEleFunction() {
		return eleFunction;
	}

	public Element getEleTable() {
		return eleTable;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public SAXReader getReader() {
		return reader;
	}

	public void setReader(SAXReader reader) {
		this.reader = reader;
	}

	public Map<String, String> getFunctionConfigs() {
		return FunctionConfigs;
	}

	public Map<String, String> getTableConfigs() {
		return TableConfigs;
	}

	public Map<Integer, ArrayList<String>> getFieldConfigs() {
		return FieldConfigs;
	}

	public KHXMLUtil() {
		super();
	}

	public Document parse(URL url) throws DocumentException {
		reader = new SAXReader();
		this.document = reader.read(url);
		return document;
	}

	public Map<String, String> getFunctionElementConfigs(Document document, String functionName) throws Exception {
		Map<String, String> functionResult = new HashMap<String, String>();
		if ("".equals(functionName) || null == functionName) {
			System.err.println("FunctionName不能为空");
			return null;
		}
		Element root = document.getRootElement();
		String name = null;
		// iterate through child elements of root
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			Element element = it.next();
			Attribute a = element.attribute("Name");
			name = a.getValue();
			if ((null != name || !"".equals(name)) && functionName.equals(a.getValue())) {
				functionResult.clear();
				for (Attribute attr : element.attributes()) {
					functionResult.put(attr.getName(), attr.getValue());
				}
				this.eleFunction = element;
				this.FunctionConfigs = functionResult;
				return functionResult;
			}

		}
		if ((null == name || "".equals(name))) {
			throw new Exception("没有找到目标Function");
		}
		return null;

	}

	public Map<String, String> getTableElementConfigs(Element function, String tableName) throws Exception {
		String name = null;
		Map<String, String> tableResult = new HashMap<String, String>();
		for (Iterator<Element> it = function.elementIterator(); it.hasNext();) {
			Element table = it.next();
			Attribute a = table.attribute("Name");
			name = a.getValue();
			if ((null != tableName || !"".equals(table)) && (!"".equals(name) || null == name)
					&& tableName.equals(name)) {
				tableResult.clear();
				for (Attribute attr : table.attributes()) {
					tableResult.put(attr.getName(), attr.getValue());
				}
				this.eleTable = table;
				this.TableConfigs = tableResult;
				return tableResult;
			}
		}
		if ((null == name || "".equals(name))) {
			throw new Exception("没有找到目标Table");
		}
		return null;
	}

	public void searchFieldConfigs(Element element) throws DocumentException {

		searchFieldConfigs(element, false);
	}

	/* 获取到Table下面的所有FieldConfig */
	public void searchFieldConfigs(Element element, boolean isAuto) throws DocumentException {

		if (!isAuto) {
			index = 0;
			FieldConfigs.clear();
		}
		if (null == element) {
			System.err.println("提供的Table节点为空，请提供这个节点");
			return;
		}
		sunBar(element);
	}

	private void sunBar(Element element) throws DocumentException {
		index++;
		List<Attribute> attrList = element.attributes();
		ArrayList<String> fieldPros = new ArrayList<String>();
		for (Attribute attribute : attrList) {
			fieldPros.add(attribute.getValue());
		}
		FieldConfigs.put(index, fieldPros);
		for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
			Element ele = it.next();
			sunBar(ele);
		}

	}

	public Map<String, Map<String, String>> autoLoadXMLFunctionNode(URL url, String functionName, String tableName)
			throws Exception {

		Map<String, Map<String, String>> result = new LinkedHashMap<String, Map<String, String>>();
		this.parse(url);
		this.getFunctionElementConfigs(document, functionName);
		result.put(functionName, this.FunctionConfigs);
		if (null == tableName || "".equals(tableName)) {
			for (Iterator<Element> it = this.eleFunction.elementIterator(); it.hasNext();) {
				Element table = it.next();
				Attribute a = table.attribute("Name");
				result.put(a.getValue(), getTableElementConfigs(this.eleFunction, a.getValue()));

				searchFieldConfigs(table, true);
			}
		} else {
			result.put(tableName, this.getTableElementConfigs(this.eleFunction, tableName));
		}
		return result;
	}

	public void free() {
	}
}
