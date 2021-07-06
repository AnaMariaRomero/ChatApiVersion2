package net.apimessages.pd2.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomLanguage {

	public static List<String> lenguageUser = new ArrayList<String>(Arrays.asList("Chino","Español","Inglés","Japonés"));
	public static Random random = new Random();
	
	public static List<String> getLenguages(int lengthList){
		List<String> lenguagesRandom = new ArrayList<String>();
		while(lengthList > 0) {
			lenguagesRandom.add(lenguageUser.get(random.nextInt(lenguageUser.size())));
			lengthList -=1;
		}
		System.out.print(lenguagesRandom.toString());
		return lenguagesRandom;
	}
	
	public static Map<String,Integer> getMapOfProm(Integer size){
		List<String> generated = getLenguages(size);
		List<String> generated2 = new ArrayList<String>();
		Map<String, Integer> resume = new HashMap <String, Integer>();
		Integer count = 0;
		for (int i=0; i<size;i++) {
			if(!generated2.contains(generated.get(i))) {
				count = getCount(generated.get(i),generated);
				resume.put(generated.get(i),(count*100/size));
				generated2.add(generated.get(i));
			}
			count = 0;
		}
		return resume;
	}
	
	public static Map<String,Integer> getMapOfPromUsers(Integer size){
		List<String> generated = getLenguages(size);
		List<String> generated2 = new ArrayList<String>();
		Map<String, Integer> resume = new HashMap <String, Integer>();
		Integer count = 0;
		for (int i=0; i<size;i++) {
			if(!generated2.contains(generated.get(i))) {
				count = getCount(generated.get(i),generated);
				resume.put(generated.get(i),(count*100/size));
				generated2.add(generated.get(i));
			}
			count = 0;
		}
		return resume;
	}

	private static Integer getCount(String string, List<String> lenguages) {		
		Integer number = 0;
		for (int i=0; i<lenguages.size();i++) {
			if(lenguages.get(i).equals(string)) {
				number+=1;
			}
		}
		return number;
	}
}
