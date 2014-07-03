package com.zyf.springmybatis.common;

import java.io.File;
import java.util.SortedMap;

import org.apache.log4j.Logger;

import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.zyf.springmybatis.bean.ChannelInfo;

public class CacheDataBase {

	public static Logger log = Logger.getLogger("Cache");
	public static Environment env;
	public SortedMap map;

	public String cacheName;
	public Database db;
	private ClassCatalog catalog;
	private Class key;
	private Class value;

	public CacheDataBase(String cacheName, Class key, Class value) {
		this.cacheName = cacheName;
		this.key = key;
		this.value = value;
	}

	/** 构建Database： 指定数据库名字，如果指定名字的数据库不存在，则自动创建。 */
	public boolean openCache() {
		try {
			if (env == null)
				env = CacheDataBase.createEnvironment();
			DatabaseConfig dbConfig = new DatabaseConfig();
			dbConfig.setAllowCreate(true);
			/** 是否采用延时操作 **/
			dbConfig.setDeferredWrite(true);
			// catalog is needed for serial bindings (java serialization)
			Database catalogDb = env.openDatabase(null, "catalog", dbConfig);
			catalog = new StoredClassCatalog(catalogDb);
			// use Integer tuple binding for key entries
			TupleBinding keyBinding = TupleBinding.getPrimitiveBinding(key);
			// use String serial binding for data entries
			SerialBinding dataBinding = new SerialBinding<ChannelInfo>(catalog,
					value);

			db = env.openDatabase(null, cacheName, dbConfig);
			this.map = new StoredSortedMap(db, keyBinding, dataBinding, true);
			log.info("Open Cache Success[" + cacheName + "];Count:"
					+ db.count());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal(cacheName + " Open Failure");
			return false;
		}
	}

	private static Environment createEnvironment() {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		/*** 设置日志文件大小 ***/
		envConfig.setConfigParam("je.log.fileMax", LookUpConfig.CacheFileSize
				* 1024 * 1024 + "");
		/*** 占用虚拟机内从为50% ***/
		envConfig.setConfigParam("je.maxMemoryPercent",
				LookUpConfig.CacheMaxMemoryPercent + "");
		/***
		 * 仅在阻塞/死锁探测时检查timeout-->锁可能超时直到DB检查锁表--> 对于生命周期特别长的事务或长时间持有锁的操作,
		 * 可能在事务或操作完成之前time out 仅当应用只在短时间里持有锁时才采用该机制
		 **/
		// envConfig.setLockTimeout(10, TimeUnit.SECONDS);
		/*** 设这为共享缓存，如果使用多个environment可以充分提高性能 ***/
		envConfig.setSharedCache(true);
		envConfig.setLocking(false);
		/** 设置是否使用事物 ***/
		// envConfig.setTransactional(true);
		/* 缓存中保持打开文件句柄的数目** */
		envConfig.setConfigParam("je.log.fileCacheSize", "1024");
		/*** 设置清理线程数 ***/
		envConfig.setConfigParam("je.cleaner.threads", "8");
		/*** 驱逐缓存最大线程数，清理不频繁使用的值 **/
		envConfig.setConfigParam("je.evictor.maxThreads", "8");
		/***
		 * envConfig.setConfigParam("je.evictor.maxThreads","200"); 并发数=锁表数*
		 * envConfig.setConfigParam("je.lock.nLockTables","5");
		 * 缓存中保持打开文件句柄的数目***
		 * envConfig.setConfigParam("je.log.fileCacheSize","100000"); 默认一次读多少***
		 * envConfig.setConfigParam("je.log.faultReadSize","1024"); /*
		 * envConfig.setConfigParam("je.checkpointer.bytesInterval","1024");
		 * envConfig.setConfigParam("je.evictor.criticalPercentage","5");
		 */
		envConfig.setAllowCreate(true);
		File dir = new File(LookUpConfig.CacheDataBasePath);
		if (!dir.exists()) {// 如果指定的目录不存在，则自动创建
			if (dir.mkdirs())
				log.info("Create Dirs：" + dir.getAbsolutePath() + " Success");
			else {
				log.fatal("Create Dirs：" + dir.getAbsolutePath() + " Failure");
				return null;
			}
		}
		Environment env = new Environment(dir, envConfig);
		return env;
	}

	public void closeDataBase() throws Exception {

		if (catalog != null) {
			catalog.close();
			catalog = null;
		}
		if (db != null) {
			db.close();
			db = null;
		}
	}

	public static void closeEnv() {
		if (env != null) {
			env.close();
			env = null;
		}

	}
}
