package qiaohuang.tdt.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import qiaohuang.tdt.util.ArticleReader;
import qiaohuang.tdt.util.ToString;





/**
 * @author qiaohuang 
 *
 */

public class Article {
	
	private String title, content, url, source,category;// category and source may be null
	private Calendar calendar;
	private HashMap<String,WordInfo> words;
	private ArrayList<String> wordList;
	private double sim;//similarity between this article and its related topic
	

	public Article(){
		words = new HashMap<String,WordInfo>();
		wordList = new ArrayList<String>();
	}
	
	public void segmentTerms(){
		
		try{
			List<Term> parse = NlpAnalysis.parse(this.content);
			for(Term token:parse){		
				
				//Filter stop words, noisy words, punctuation and so on...
				String word = ArticleReader.wordFilter.filterWord(token);
				if(word==null)
					continue;         
	            
				//update term frequency
	            if(words.containsKey(word)){
	            	WordInfo wordInfo = words.get(word);
	            	wordInfo.setTf(wordInfo.getTf()+1);
	            }
	            else{          
	            	WordInfo wordInfo = new WordInfo(word);
	            	wordInfo.setTf(1);
	            	words.put(word,wordInfo);
	            	wordList.add(word);
	            }
			}
		}
		catch (Exception e){
			System.out.println("illegal article! segmentation failed!");
			System.out.println("title: "+this.title);
			System.out.println("content: "+this.content);
			
		}
		
		
	}
	
	public void printInfo(BufferedWriter writer) throws IOException{
		
		writer.write(ToString.toString(calendar)+"\t");
		writer.write(title+"\t");
		if(category!=null)
			writer.write(category+"\t");
		if(source!=null)
			writer.write(source+"\t");
		writer.newLine();
		
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	
	public HashMap<String, WordInfo> getWords() {
		return words;
	}

	public ArrayList<String> getWordList() {
		return wordList;
	}

	public double getSim() {
		return sim;
	}

	public void setSim(double sim) {
		this.sim = sim;
	}

	

	
	

}
