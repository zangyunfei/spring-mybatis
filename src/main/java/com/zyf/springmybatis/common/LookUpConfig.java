package com.zyf.springmybatis.common;

public class LookUpConfig {
	public static String CacheDataBasePath;
	public static int CacheMaxMemoryPercent;
	public static int CacheFileSize;
	public static String DBIP;
	public static String DBPORT;
	public static String DBNAME;
	public static String DBUNAME;
	public static String DBPSD;
	public static String xmlPath;
	public static String htmlPath;

	public static boolean init() {
		try {
			AppinfoConfigureFactory facotry = AppinfoConfigureFactory
					.getInstance();
			if (facotry.getText("CacheDataBasePath") != null)
				CacheDataBasePath = facotry.getText("CacheDataBasePath");
			if (facotry.getText("CacheMaxMemoryPercent") != null)
				CacheMaxMemoryPercent = Integer.parseInt(facotry
						.getText("CacheMaxMemoryPercent"));
			if (facotry.getText("CacheFileSize") != null)
				CacheFileSize = Integer.parseInt(facotry
						.getText("CacheFileSize"));

			if (facotry.getText("DBIP") != null)
				DBIP = facotry.getText("DBIP");
			if (facotry.getText("DBPORT") != null)
				DBPORT = facotry.getText("DBPORT");
			if (facotry.getText("DBNAME") != null)
				DBNAME = facotry.getText("DBNAME");
			if (facotry.getText("DBUNAME") != null)
				DBUNAME = facotry.getText("DBUNAME");
			if (facotry.getText("DBPSD") != null)
				DBPSD = facotry.getText("DBPSD");
			if (facotry.getText("xmlPath") != null) {
				xmlPath = facotry.getText("xmlPath");
			}
			if (facotry.getText("htmlPath") != null) {
				htmlPath = facotry.getText("htmlPath");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
