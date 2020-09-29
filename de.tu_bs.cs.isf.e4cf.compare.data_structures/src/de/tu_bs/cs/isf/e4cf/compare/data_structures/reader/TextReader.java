package de.tu_bs.cs.isf.e4cf.compare.data_structures.reader;

import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;
/***
 * 
 * @author Kamil Rosiak
 *
 */
public class TextReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = {"txt"};
	public final static String NODE_TYPE_TREE = "TEXT";
	public final static String NODE_TYPE_LINE = "LINE";
	public final static String NODE_TYPE_WORD = "WORD";
	public final static String VALUE_TYPE_TEXT = "TEXT";
	
	public TextReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		Tree tree = null;
		if(isFileSupported(element)) {
			String s = FileStreamUtil.readLineByLine(Paths.get(element.getAbsolutePath()));
			//Spiting the input by lines
			String[] lines = s.split("\n");
			//The name is only the file name
			String name = element.getAbsolutePath().substring(element.getAbsolutePath().lastIndexOf("\\") + 1);
			
			tree = new TreeImpl(name,new NodeImpl(NODE_TYPE_TREE));
			for(String line :lines) {
				Node lineNode = new NodeImpl(NODE_TYPE_LINE, tree.getRoot());
				String[] words = line.split(" ");
				for(String word : words) {
					Node wordNode = new NodeImpl(NODE_TYPE_WORD, lineNode);
					wordNode.addAttribute(VALUE_TYPE_TEXT, element.getAbsolutePath(), word);
				}
			}	
		}
		return tree;	
	}
}
