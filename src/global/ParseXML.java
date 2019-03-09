package global;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import heap.Heapfile;
import heap.NodeTuple;
import heap.Scan;
import heap.Tuple;


public class ParseXML {
	public static String path = "/Users/sidmadan/Documents/cse510/xml_sample_data.xml";	
	public static final int min = Integer.MIN_VALUE;
	
	public static NodeTuple convertElementToNode(Element element, int level) {
		NodeTuple n = new NodeTuple();
		Intervaltype interval = new Intervaltype();
		interval.setStart(min);
		interval.setEnd(min);
		n.setNodeTag(element);
		n.setNodeIntLabel(interval);
		n.setLevel(level);
		return n;
	}
	
    public static List<NodeTuple> parse(String path) throws Exception {
        int counter = -100000;
        int level;
        Stack<NodeTuple> stack = new Stack<>();
        List<NodeTuple> nodes = new ArrayList<NodeTuple>();
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new FileInputStream(new File(path)));
        
        Element root = doc.getDocumentElement();
        
        stack.add(convertElementToNode(root, 0));
        NodeTuple node;
        Intervaltype interval;
        while(!stack.isEmpty()) {
    	   node = stack.pop();
    	  
    	  interval = node.getNodeIntLabel();
    	  level = node.getLevel();
    	  if(interval.getStart() == min) {
	    	  interval.setStart(counter++);
	    	  
	    	  node.setNodeIntLabel(interval);
	    	  stack.push(node);
	    	  NodeList entries = node.getNodeTag().getChildNodes();
    	    
	          for (int i=entries.getLength() -1; i >= 0; i--) {
	        	  if(entries.item(i).getNodeType() == Node.ELEMENT_NODE){
	                    Element element = (Element) entries.item(i);
	    	            stack.push(convertElementToNode(element, level + 1 ));
	    	            
	        	  } else if( entries.item(i).hasAttributes() ) {
	        		  NamedNodeMap namedNodeMap   = entries.item(i).getAttributes();
	        		  for(i = 0 ; i < namedNodeMap.getLength() ; i++) {
	        	          Element element = (Element) namedNodeMap.item(i);
		    	         stack.push(convertElementToNode(element, level + 1 ));
	        		  }
	        	  } 
	          }
	          
    	  } else {
    		  
    		  interval = node.getNodeIntLabel();
    		  interval.setEnd(counter++);
    		  node.setNodeIntLabel(interval);
    		  if(node.getNodeTag().getNodeType() == Node.ELEMENT_NODE){
//    			  System.out.println("Found element " + node.getNodeTag().getTagName() + " "
//    					  +  node.getNodeIntLabel().getStart() + " " + node.getLevel() + " " + node.getNodeIntLabel().getEnd());
    			  nodes.add(node);
    		  } else  if(node.getNodeTag().getNodeType() == Node.ELEMENT_NODE){
//    			  System.out.println("Found element " + node.getNodeTag().getTagName() + " "
//				  +  node.getNodeIntLabel().getStart() + " " + node.getLevel() + " " + node.getNodeIntLabel().getEnd());
    			  	nodes.add(node);
    		  }
    			  

    		  
    		  // todo: for saving tuple 
    		  
    		  
    	  }
        }
        return nodes;
//            NodeList children = element.getChildNodes();
            
//            for (int childNo = 0; childNo < (children.getLength()); childNo++) {
//            	//Element child = (Element) children.item(childNo);
//            	System.out.println(children.item(childNo).getNodeName());
//            }

            /*
             Node childNode = childNodes.item(childNo);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) childNode;

                Log.d(elem.getNodeValue(), "Zed");
            }
            */

    }
    
    
    
    public static void main(String[] args) {
    	System.out.println("runignsgisibgissnigs");
    	try {
    		parse(path);
    	}  catch(Exception e) {
	    	  e.printStackTrace();
	      }
    }

}
