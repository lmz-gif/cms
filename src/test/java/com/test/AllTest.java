package com.test;
/**
  * @author lmz
  * @date 2019年10月15日 上午8:09:29
  * 
  */

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lmz.utils.FileUtils;

public class AllTest {
	@Test
	public void test1() throws IOException {
		String str = FileUtils.readFileByLine("C:\\Users\\Administrator\\Desktop\\aa.txt");
		str = str.replace("\r\n", "");
		HashMap<Character,Integer> hashMap = new HashMap<>();
		char[] charArray = str.toCharArray();
		for (char c : charArray) {
			if (c!=' ') {
				if (hashMap.get(c)!=null) {
					hashMap.replace(c, hashMap.get(c)+1);
				}else {
					hashMap.put(c, 1);
				}	
			}
		}
//		Set<Entry<Character, Integer>> entrySet = hashMap.entrySet();
//		for (Entry<Character, Integer> entry : entrySet) {
//			System.out.println(entry);
//		}
		
		Set<Character> set = hashMap.keySet();
		for (Character character : set) {
			System.out.println(character+"="+hashMap.get(character));
		}
	}
	
	@After
	public void test3() {
		System.out.println("____________________________");
	}
	@Before
	public void test5() {
		System.out.println("**************************");
	}
	@BeforeClass
	public static void test6() {
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}
	
	@Test
	public void test4() {
		System.out.println(8>>1);
		System.out.println(10>>1);
		System.out.println(1<<30);
		Assert.assertEquals("bb", "aa");
	}
	
	
	
	
	
	@Test
	public void test2() throws IOException {
		String str = FileUtils.readFileByLine("C:\\Users\\Administrator\\Desktop\\aa.txt");
		str = str.replace("\r\n", " ");
		HashMap<String,Integer> hashMap = new HashMap<>();
		String[] split = str.split("\\W");
		for (String string : split) {
			if (string.length()>0) {
				
				if (hashMap.get(string)!=null) {
					hashMap.replace(string, hashMap.get(string)+1);
				}else {
					hashMap.put(string, 1);
				}
			}
		}
		
//		Set<Entry<Character, Integer>> entrySet = hashMap.entrySet();
//		for (Entry<Character, Integer> entry : entrySet) {
//			System.out.println(entry);
//		}
		
		Set<String> set = hashMap.keySet();
		System.out.println("------------------------三次以上---------------------------");
		for (String string : set) {
			if (hashMap.get(string)>2) {
				
				System.out.println(string+"="+hashMap.get(string));
			}
		}
		System.out.println("------------------------全部---------------------------");
		for (String string : set) {
				
				System.out.println(string+"="+hashMap.get(string));
			
		}
	}
}
