package com.yd.rfidImp.data;

/**
 * 
 * OrderData 
 * @author mym
 * 2016年11月18日 上午10:46:38
 *
 */
		
public class Scan_SaveData {
	/**
	 * 运单号(DOC_ID)
	 */
	private Integer ship_id;
	
	/**
	 * 对应大包号
	 */
	private Integer pack_id;
	
	/**
	 * rfid类型    (五种:1.发放  2.调拨  3.绑定  4.领用  5.报损)
	 */
	private Integer scan_type;
	
	/**
	 * rfid号
	 */
	private Integer rfid;
	
	/**
	 * 扫面入库时间（DB_TIME）
	 */
	private String scan_db_time;
	
	
	/**
	 * 扫描时间（SCAN_TIME）
	 */
	private String scan_time;

	/**
	 * 上级站点
	 */
	private Integer prev_site;
	/**
	 * 发出凭证
	 */
	private Integer car_certificate;

	/**
	 * 扫描站点
	 */
	private Integer scan_site;
	
	/**
	 * 下一站
	 */
	private Integer next_site;

	/**
	 * 操作人
	 */
	private String opt_code;

	private Integer type;


	public Integer getShip_id() {
		return ship_id;
	}

	public void setShip_id(Integer ship_id) {
		this.ship_id = ship_id;
	}

	public Integer getPack_id() {
		return pack_id;
	}

	public void setPack_id(Integer pack_id) {
		this.pack_id = pack_id;
	}

	public Integer getScan_type() {
		return scan_type;
	}

	public void setScan_type(Integer scan_type) {
		this.scan_type = scan_type;
	}

	public Integer getRfid() {
		return rfid;
	}

	public void setRfid(Integer rfid) {
		this.rfid = rfid;
	}

	public String getScan_db_time() {
		return scan_db_time;
	}

	public void setScan_db_time(String scan_db_time) {
		this.scan_db_time = scan_db_time;
	}

	public String getScan_time() {
		return scan_time;
	}

	public void setScan_time(String scan_time) {
		this.scan_time = scan_time;
	}

	public Integer getPrev_site() {
		return prev_site;
	}

	public void setPrev_site(Integer prev_site) {
		this.prev_site = prev_site;
	}

	public Integer getCar_certificate() {
		return car_certificate;
	}

	public void setCar_certificate(Integer car_certificate) {
		this.car_certificate = car_certificate;
	}

	public Integer getScan_site() {
		return scan_site;
	}

	public void setScan_site(Integer scan_site) {
		this.scan_site = scan_site;
	}

	public Integer getNext_site() {
		return next_site;
	}

	public void setNext_site(Integer next_site) {
		this.next_site = next_site;
	}

	public String getOpt_code() {
		return opt_code;
	}

	public void setOpt_code(String opt_code) {
		this.opt_code = opt_code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
