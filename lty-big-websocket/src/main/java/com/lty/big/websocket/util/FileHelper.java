package com.lty.big.websocket.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.lty.big.websocket.modle.StaionPsg;
import com.lty.big.websocket.modle.StaionPsg.Spsg;

public class FileHelper {
	private static final String path = "d:\\jsonTemplate\\";

	public static String readJson(String filename) {
		filename = path + filename;
		try {
			return byteToString(toByteArray(filename));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) throws IOException {
		String msg = FileHelper.readJson("201-2.txt");
		System.out.println(msg);
		StaionPsg staionPsg = JSON.parseObject(msg, StaionPsg.class);
		String msg1 = FileHelper.readJson("站台客流.csv");
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[] arry = msg1.split("\\r\\n");
		for (String s : arry) {
			if (s.indexOf("站台总客流") != -1) {
				continue;
			}
			String[] st = s.split(",");
			String stationname = st[0];
			map.put(st[0], Integer.parseInt(st[3]));
			// System.out.println(st[0] + "-" + st[3]);
		}
		Random r = new Random();
		for (Spsg s : staionPsg.getData()) {
			Integer a = map.get(s.getStationName());
			if (a == null) {
				a = 3;
			}
			if (a == 1) {
				s.setPsg((300 * 3) + r.nextInt(300));
			}
			if (a == 2) {
				s.setPsg(300 * 2 + r.nextInt(100));
			}
			if (a == 3) {
				s.setPsg(300 + r.nextInt(100));
			}
			if (a == 4) {
				s.setPsg(100 + r.nextInt(100));
			}

		}

		Collections.sort(staionPsg.getData(), new Comparator<Spsg>() {

			@Override
			public int compare(Spsg o1, Spsg o2) {
				if (o1.getPsg() > o2.getPsg()) {
					return -1;
				}
				if (o1.getPsg() < o2.getPsg()) {
					return 1;
				}
				return 0;
			}

		});

		System.out.println(JSON.toJSONString(staionPsg));

	}

	/**
	 * 將文件转为字节
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filename) throws IOException {

		File f = new File(filename);
		if (!f.exists()) {
			throw new FileNotFoundException(filename);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}

	}

	/**
	 * 目的:byte[] 转为String
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToString(byte[] b) {
		try {
			return new String(b, "UTF-8");
			//return new String(b, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new String(b);
		}
	}
}
