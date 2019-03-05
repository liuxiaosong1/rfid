package com.yd.main;

import com.yd.rfidImp.dao.imp.DataDaoImpl;
import com.yd.rfidImp.data.Scan_detData;
import com.yd.util.csv.CsvReader;
import com.yd.util.redis.clients.RedisPool;
import com.yd.util.redis.clients.jedis.Jedis;
import com.yd.util.redis.clients.jedis.JedisPool;
import com.yd.util.string.StrUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
	// 时间格式
	private static SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMddHHmmss");

	// 日期格式
	private static SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyyMMdd");

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static CsvReader reader;

	//private static Map<String, String > dataPackMap = new HashMap<String,String>();

	private static Map<String,Integer> normalMap= new HashMap<String,Integer>();


	// 存放需所有明细数据的List
	private static List<Scan_detData> list = new ArrayList<Scan_detData>();
	//存放需调拨明细数据的List
	private static List<Scan_detData> dblist = new ArrayList<Scan_detData>();
	//存放普通集包明细数据的List
	private static List<Scan_detData> normalList = new ArrayList<Scan_detData>();
	//存放发放明细数据的List
	private static List<Scan_detData> giveList = new ArrayList<Scan_detData>();
	//存放中转发出明细数据的List
	private static List<Scan_detData> sendlist = new ArrayList<Scan_detData>();
	//存放环保袋集包明细数据的List
	private static List<Scan_detData> packagelist = new ArrayList<Scan_detData>();
	//存放入库扫描明细数据的List
	private static List<Scan_detData> inlist = new ArrayList<Scan_detData>();
	//存放报损明细数据的List
	private static List<Scan_detData> excplist = new ArrayList<Scan_detData>();

	private static JedisPool pool = null;
	private static Jedis jedis = null;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		pool = RedisPool.getPool();
		jedis = pool.getResource();

		// 默认文件地址
		//String dtaRoot = "E:/mym/TB/";
		String dtaRoot = "C:/Users/liuxiaosong/Documents/WXWork/1688853029385361/Cache/File/2019-02/";
		//String dtaRoot = "/etl/dta/";

		// 默认启动时间
		//String time = "20170601000000";
		String time = "20190226151000";

		// 获取输入参数
		if(args.length >= 2) {
			dtaRoot = args[0];
			time = args[1];
		}

		// 打印最终地址和时间
		System.out.println(dtaRoot);
		System.out.println(time);

		int idx = getIndex(time);//根据时间获取文件号
        //获取日期
		String date = dayFormatter.format(timeFormatter.parse(time));

		//处理扫描明细
		dealScanData(dtaRoot,date,idx);
		//获取统计时间
		String sum_time = dateFormatter.format(timeFormatter.parse(time));
		//保存所有扫描明细
		saveAllScanData(list,sum_time);
		//保存报损扫描明细
		saveExcpScanData(excplist,sum_time);
		//保存调拨扫描明细
		saveDbScanData(dblist,sum_time);
        //保存普通集包扫描明细
		saveNormalScanData(normalList,sum_time);
		//保存发放扫描明细
		saveGiveScanData(giveList,sum_time);
       //保存入库扫描明细
		saveInScanData(inlist,sum_time);
		//保存集包扫描明细
		savePackageScanData(packagelist,sum_time);
		//保存中转发出扫描明细
		saveSendScanData(sendlist,sum_time);
		list.clear();
		excplist.clear();
		dblist.clear();
		normalList.clear();
		giveList.clear();
		inlist.clear();
		sendlist.clear();
		normalMap.clear();
		System.out.println("success : " + time);
		System.exit(0)	;


	}

	/**
	 * 保存中转发出扫描明细
	 * @param sendlist
	 * @param sum_time
	 */
	private static void saveSendScanData(List<Scan_detData> sendlist, String sum_time) {
		DataDaoImpl.saveSendScanData(sendlist,sum_time);
	}

	/**
	 * 保存集包扫描明细
	 * @param packagelist
	 * @param sum_time
	 */
	private static void savePackageScanData(List<Scan_detData> packagelist, String sum_time) {
		DataDaoImpl.savePackageScanData(packagelist,sum_time);
	}

	/**
	 * 保存入库扫描明细
	 * @param inlist
	 * @param sum_time
	 */
	private static void saveInScanData(List<Scan_detData> inlist, String sum_time) {
		DataDaoImpl.saveInScanData(inlist,sum_time);
	}

	/**
	 * 保存发放扫描明细
	 * @param giveList
	 * @param sum_time
	 */
	private static void saveGiveScanData(List<Scan_detData> giveList, String sum_time) {
		DataDaoImpl.saveGiveScanData(giveList,sum_time);
	}

	/**
	 * 保存普通集包扫描明细
	 * @param normalList
	 * @param sum_time
	 */
	private static void saveNormalScanData(List<Scan_detData> normalList, String sum_time) {
		DataDaoImpl.saveNormalDatas(normalList,sum_time);
	}

	/**
	 * 调拨明细保存
	 * @param dblist
	 * @param sum_time
	 */
	private static void saveDbScanData(List<Scan_detData> dblist, String sum_time) {
		DataDaoImpl.saveDbDatas(dblist,sum_time);
	}

	/**
	 * 报损明细保存
	 * @param excplist
	 * @param sum_time
	 */
	private static void saveExcpScanData(List<Scan_detData> excplist, String sum_time) {
		DataDaoImpl.saveExcpDatas(excplist,sum_time);
	}

	/**
	 * 保存扫描明细
	 * @param allList
	 * @param sum_time
	 */
	private static void saveAllScanData(List<Scan_detData> allList, String sum_time) {
		DataDaoImpl.saveDatas(allList,sum_time);
	}

	/**
	 * 处理扫描明细
	 * @param dtaRoot
	 * @param date
	 * @param idx
	 */
	private static void dealScanData(String dtaRoot, String date, int idx) {
		try {
			//.flg文件路径
			String flagPath;

			//.csv文件路径
			String path;
			String fileName=getFileName(date,idx);
			//path = dtaRoot  + "/test/tb_scan" + "01" + ".csv";
			path = dtaRoot  +date+ "/tb_scan_" + fileName + ".csv";
			boolean flag = true;
			while (flag) {
				// 检测当前批次的扫描文件有没有生成
				if (new File(path).exists()) {
					getScanFromCsv(path);
					flag = false;
					System.out.println("OVER " + path + " :" + list.size());
				} else {
					try {
						System.out.println(path + " : Scan_det Sleep !");
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取扫描文件内容
	 * @param path
	 */
	private static void getScanFromCsv(String path) {
		// 读取CSV文件
		String[] dataArray = null;
		//巴枪扫描   64     装车扫描	97       中转发出	18       普通中转集包	 03/3
		//卸车扫描	96     汽运称重	29       到件收入	17
		//航空称重	44
		String scan_type;
		// 1.发放 2.调拨扫描 3.领用扫描 4.集包扫描  5.报损 6.入库扫描 7.盘点扫描
		String rfid_type;
		//明细对象
		Scan_detData scan_detData;
		try {
			reader = new CsvReader(path, ',', Charset.forName("UTF-8"));
			while (reader.readRecord()) {
				dataArray = reader.getValues();
				if (null != dataArray && !"".equals(dataArray)) {
					scan_type=reader.get(45);
					rfid_type=reader.get(29);
					scan_detData=new Scan_detData();
					scan_detData.setScan_db_time(reader.get(51));
					scan_detData.setScan_time(reader.get(46));
					scan_detData.setScan_site(reader.get(31));
					scan_detData.setOpt_code(reader.get(43));
					if("64".equals(scan_type)){//巴枪扫描
						scan_detData.setRfid(reader.get(30));
						if("1".equals(rfid_type)){//发放
							scan_detData.setNext_site(reader.get(34));
							scan_detData.setScan_type("1");
							list.add(scan_detData);
							giveList.add(scan_detData);
						}else if("2".equals(rfid_type)){//调拨扫描
							scan_detData.setScan_type("2");
							scan_detData.setNext_site(reader.get(34));
							scan_detData.setCar_certificate(reader.get(53));
							list.add(scan_detData);
							dblist.add(scan_detData);
						}else if("3".equals(rfid_type)){//领用扫描
							scan_detData.setScan_type("3");
							scan_detData.setNext_site(reader.get(34));
							scan_detData.setPrev_site(reader.get(33));
							list.add(scan_detData);
						}else if("4".equals(rfid_type)){//集包扫描
							scan_detData.setScan_type("4");
							scan_detData.setPack_id(reader.get(4));
							scan_detData.setShip_id(reader.get(3));
							scan_detData.setNext_site(reader.get(34));
							list.add(scan_detData);
							packagelist.add(scan_detData);
							//dataPackMap.put(reader.get(4),reader.get(30));
							RedisPool.set(reader.get(4),reader.get(30));
							RedisPool.setExpire(reader.get(4),RedisPool.timeout);//设置失效时间
						}else if("5".equals(rfid_type)){//报损
							scan_detData.setScan_type("5");
							scan_detData.setType(reader.get(54));
							list.add(scan_detData);
							excplist.add(scan_detData);
						}else if("6".equals(rfid_type)){//入库扫描
							scan_detData.setScan_type("6");
							scan_detData.setPrev_site(reader.get(33));
							if(!StrUtil.isEmpty(reader.get(53))){
								scan_detData.setCar_certificate(reader.get(53));
								dblist.add(scan_detData);
							}else{
								inlist.add(scan_detData);
							}
							list.add(scan_detData);
						}else if("7".equals(rfid_type)){//盘点扫描
							scan_detData.setScan_type("11");
						}
					}else if("03".equals(scan_type)||"3".equals(scan_type)){//普通集包
						String pack=reader.get(4);
						if(StrUtil.isEmpty(pack)){
							continue;
						}
						if(pack.startsWith("90")||pack.startsWith("94")||pack.startsWith("99")){
							if(!normalMap.containsKey(pack)){
								scan_detData.setPack_id(pack);
								normalList.add(scan_detData);
								normalMap.put(pack ,0);
							}

						}
					}else if("97".equals(scan_type)){//装车扫描  大包号在运单号字段中，以90/94/99开头
						String pack=reader.get(3);
						if(StrUtil.isEmpty(pack)){
							continue;
						}
                        if(pack.startsWith("90")||pack.startsWith("94")||pack.startsWith("99")){
							//String rfid=dataPackMap.get(pack);
							String rfid=jedis.get(pack);
							if(!StrUtil.isEmpty(rfid)) {
								scan_detData.setRfid(rfid);
								scan_detData.setScan_type("9");
								scan_detData.setNext_site(reader.get(34));
								scan_detData.setPack_id(pack);
								scan_detData.setCar_certificate(reader.get(53));
								list.add(scan_detData);
							}
					   }
					}else if("18".equals(scan_type)){//中转发出
						String pack=reader.get(3);
						if(StrUtil.isEmpty(pack)){
							continue;
						}
						if(pack.startsWith("90")||pack.startsWith("94")||pack.startsWith("99")){
							//String rfid=dataPackMap.get(pack);
							String rfid=jedis.get(pack);
							if(!StrUtil.isEmpty(rfid)){
								scan_detData.setRfid(rfid);
								scan_detData.setScan_type("10");
								scan_detData.setNext_site(reader.get(34));
								scan_detData.setPack_id(pack);
								sendlist.add(scan_detData);
								list.add(scan_detData);
							}
						}
					}else if("96".equals(scan_type)){//卸车扫描
						String pack=reader.get(3);
						if(StrUtil.isEmpty(pack)){
							continue;
						}
						if(pack.startsWith("90")||pack.startsWith("94")||pack.startsWith("99")){
							//String rfid=dataPackMap.get(pack);
							String rfid=jedis.get(pack);
                          if(!StrUtil.isEmpty(rfid)){
							  scan_detData.setRfid(rfid);
							  scan_detData.setScan_type("7");
							  scan_detData.setPrev_site(reader.get(33));
							  scan_detData.setCar_certificate(reader.get(53));
							  scan_detData.setPack_id(pack);
							  list.add(scan_detData);
						  }
						}
					}else if("29".equals(scan_type)){//汽运称重
						String pack=reader.get(3);
						if(StrUtil.isEmpty(pack)){
							continue;
						}
						if(pack.startsWith("90")||pack.startsWith("94")||pack.startsWith("99")){
							//String rfid=dataPackMap.get(pack);
							String rfid=jedis.get(pack);
							if(!StrUtil.isEmpty(rfid)){
								scan_detData.setRfid(rfid);
								scan_detData.setScan_type("8");
								scan_detData.setPrev_site(reader.get(33));
								scan_detData.setPack_id(pack);
								list.add(scan_detData);
							}
						}
					}
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(reader != null) {
				reader.close();
			}
		}
	}

	/**
	 * 根据文件号获取文件名称
	 * @param date
	 * @param idx
	 * @return
	 */
	private static String getFileName(String date, int idx) {
		String str = String.format("%04d", idx * 5);
		date=date+str+"00";
		System.out.println(date);
		return date;
	}

	/**
	 * 根据时间获取文件号
	 * @param dateStr
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static int getIndex(String dateStr) {
		int index = -1;
		try{
			Date date = timeFormatter.parse(dateStr);
			index = date.getHours() * 12 + date.getMinutes() / 5;
		} catch(ParseException pe) {
		}
		return index;
	}

}