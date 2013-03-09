/**    
 * 文件名：XmlFileUtil.java    
 *    
 * 版本信息：1.0    
 * 日期：Sep 30, 2010    
 * Copyright (c) ESRI China (Beijing) Ltd. All rights reserved   
 * 版权所有    
 *    
 */
package com.frss.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * <p>
 * 功能描述: xml 和 xhtml 文件读写的通用方法类
 * </p>
 * <p>
 * 主要方法:
 * <li></li>
 * <li></li>
 * </p>
 * 
 * @author Michel Cho
 * @version 2.0
 * @date Sep 30, 2010 11:44:27 AM
 * @since
 * @see
 *       <p>
 *       其它：
 *       </p>
 *       <p>
 *       修改历史：
 *       </p>
 * 
 */
public class XmlFileUtil {

	private Document doc;// 文件内容对象

	private String filepath;// 文件的路径

	private SAXBuilder builder;

	/**
	 * 创建一个新的实例 XmlFileUtil.
	 * 
	 * @param filepath
	 */
	public XmlFileUtil(String filepath) {
		super();
		this.filepath = filepath;
		builder = new SAXBuilder();
		builder.setValidation(false);
		builder.setEntityResolver(new NoOpEntityResolver());
		this.load();
	}

	public boolean store() {
		boolean b = false;
		if (filepath != null && filepath.length() > 0) {
			XMLOutputter out = new XMLOutputter();
			FileOutputStream fos = null;
			try {
				File file = new File(filepath);
				fos = new FileOutputStream(file);
				out.output(doc, fos);
				b = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return b;
	}
	
	public String getValue(String xPath) {
		return this.getElement(xPath).getText();
	}
	
	public void setValue(String xPath, String value) {
		this.getElement(xPath).setText(value);
	}
	
	public class NoOpEntityResolver implements EntityResolver {
		public InputSource resolveEntity(String publicId, String systemId) {
			String str = "";
			try {
				return new InputSource(new ByteArrayInputStream(str.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}	
	
	// 根据xpath遍历其下所有子节点并返回所有子节点的值
	public List<String> getSubNodesValue(String xPath) {
		List <String> subNodes = new ArrayList<String>();
		subNodes.clear();
		
		Element element = null;
		element = getElement(xPath);
		
		// 获取所有子节点		
		List<Element> children = element.getChildren();
		if(children.size()==0)	// 如果是最后一层，返回空list 
			return subNodes;
		
		Iterator<Element> it = children.iterator();
        while (it.hasNext()) {		// 将所有子节点保存到list中
                Element childElement = it.next();
                subNodes.add(childElement.getValue());
        }
		
		return subNodes;
	}
	
	// 根据xpath遍历其下所有子节点并返回所有子节点的名字
	public List<String> getSubNodesName(String xPath) {
		List <String> subNodes = new ArrayList<String>();
		subNodes.clear();
		
		Element element = null;
		element = getElement(xPath);
		
		// 获取所有子节点		
		List<Element> children = element.getChildren();
		if(children.size()==0)	// 如果是最后一层，返回空list 
			return subNodes;
		
		Iterator<Element> it = children.iterator();
        while (it.hasNext()) {		// 将所有子节点保存到list中
                Element childElement = it.next();
                subNodes.add(childElement.getName());
        }
		
		return subNodes;		
	}
	
	// 根据xpath遍历其下所有子节点并返回attributeName的值
	public List<String> getSubNodes(String xPath, String attributeName) {
		List <String> subNodes = new ArrayList<String>();
		subNodes.clear();
		
		Element element = null;
		element = getElement(xPath);

		List<Element> children = element.getChildren();
		if(children.size()==0)	// 如果是最后一层，返回空list 
			return subNodes;
		
		Iterator<Element> it = children.iterator();
        while (it.hasNext()) {		// 将所有子节点保存到list中
                Element childElement = it.next();
                subNodes.add(childElement.getAttribute(attributeName).getValue());
        }
        
		return subNodes;
	}
	
	// 通过给定的xpath查找element并获取其attributeName的值
	public String findAttributeValueByElementXPath(String xPath,
            String attributeName) {
	    Element e = getElement(xPath);
	    
	    String attr = e.getAttributeValue(attributeName);		// 增加容错处理
	    if(attr==null)
	    	return null;
	
	    return e.getAttribute(attributeName).getValue();
	}
	
	// 查找名字为 elementName 的节点，返回此节点名为 attributeName 属性的值
	public String findAttributeValueByElementName(String elementName,
            String attributeName) {
		Element root = doc.getRootElement();
	    Element e = getElementByName(elementName);
	    // System.out.println(e.getName());
	    return e.getAttribute(attributeName).getValue();
	}	
	
	// 找到所有名为 elementName 的节点，返回一个含有这些节点的 List
	public List<Element> getElementsByName(String elementName, Element root) {
		List<Element> elementList = new ArrayList<Element>();
		
//        System.out.println(root.getName());
        if (root.getName().equals(elementName))
                elementList.add(root);
        else {
                List<Element> childElements = root.getChildren();
                Iterator<Element> it = childElements.iterator();
                while (it.hasNext()) {
                        Element childElement = it.next();
                        getElementsByName(elementName, childElement);
                }
        }
        // System.out.println(elementList.size());
        return elementList;
	}
	
	// 删除名为 elementName 的节点
	public void removeElementByName(String elementName) {
		Element root = doc.getRootElement();
        Element targetElement = getElementByName(elementName);
        targetElement.getParentElement()
                        .removeChildren(targetElement.getName());
	}

	////////////////////////////////////////////////////////////////////////
	// 加载xml文件
	private void load() {
		InputStream in = null;
		try {
			in = new FileInputStream(filepath);
			doc = builder.build(in);
		} catch (Exception ex) {
			ex.printStackTrace();
			doc = null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 通过给定的xpath查找element
	public Element getElement(String xPath) {
		Element element = null;
		try {
			element = (Element) XPath.selectSingleNode(doc.getRootElement(), xPath);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return element;
	}
	
	// 确定此节点是否为叶节点
	private boolean isLastLevel(Element ele) {
		List<Element> children = ele.getChildren();
		if(children.size()==0)	// 如果是最后一层，返回空list 
			return true;
		
		return false;
	}

	// 使用深度优先的算法来循环扫描 XML 文档，直到找到所需要的节点(在使用返回值前判断是否找到?=null)
	private Element getElementByName(String elementName) {
		boolean elementFind = false;		// 是否找到了节点
		Element element = null;
		Element root = doc.getRootElement();	// 根节点
		
        // 如果是根元素，直接返回
        if (root.getName().equals(elementName)) {
                element = root;
                elementFind = true;
                System.out.println("element finded");
                return element;
        } else {
                List<Element> childElements = root.getChildren();
                Iterator<Element> it = childElements.iterator();
                while (it.hasNext()) {
                        // 如果找到元素，则返回
                        if (elementFind)
                                return element;
                        else {
                                Element childElement = (Element) it.next();
                                System.out.println(childElement.getName());
                                element = getElementByName(elementName);
                        }
                }
                
                return element;
        }
	}
}
