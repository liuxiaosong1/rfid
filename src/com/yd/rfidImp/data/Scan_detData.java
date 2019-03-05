package com.yd.rfidImp.data;

import com.yd.util.string.StrUtil;

/**
 * 
 * OrderData 
 * @author mym
 * 2016年11月18日 上午10:46:38
 *
 */
		
public class Scan_detData {
	/**
	 * 运单号(DOC_ID)
	 */
	private String ship_id;
	
	/**
	 * 对应大包号
	 */
	private String pack_id;
	
	/**
	 * rfid类型    (五种:1.发放  2.调拨  3.绑定  4.领用  5.报损)
	 */
	private String scan_type;
	
	/**
	 * rfid号
	 */
	private String rfid;
	
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
	private String prev_site;
	/**
	 * 发出凭证
	 */
	private String car_certificate;

	public String getScan_db_time() {
		return scan_db_time;
	}

	public void setScan_db_time(String scan_db_time) {
		if(StrUtil.isEmpty(scan_db_time)){
			this.scan_db_time = null;
		}else{
			this.scan_db_time = scan_db_time;
		}
	}

	/**
	 * 扫描站点
	 */
	private String scan_site;
	
	/**
	 * 下一站
	 */
	private String next_site;

	/**
	 * 操作人
	 */
	private String opt_code;

	private String type;
	
	
	public String getOpt_code() {
		return opt_code;
	}

	public void setOpt_code(String opt_code) {
		if(StrUtil.isEmpty(opt_code)){
			this.opt_code = null;
		}else{
			this.opt_code = opt_code;
		}
	}

	public String getShip_id() {
		return ship_id;
	}

	public void setShip_id(String ship_id) {
		if(StrUtil.isEmpty(ship_id)){
			this.ship_id = null;
		}else{
			this.ship_id = ship_id;
		}

	}

	

	public String getPack_id() {
		return pack_id;
	}

	public void setPack_id(String pack_id) {
		if(StrUtil.isEmpty(pack_id)){
			this.pack_id = null;
		}else{
			this.pack_id = pack_id;
		}
	}

	public String getScan_type() {
		return scan_type;
	}

	public void setScan_type(String scan_type) {
		if(StrUtil.isEmpty(scan_type)){
			this.scan_type = null;
		}else{
			this.scan_type = scan_type;
		}

	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getScan_time() {
		return scan_time;
	}

	public void setScan_time(String scan_time) {
		if(StrUtil.isEmpty(scan_time)){
			this.scan_time = null;
		}else{
			this.scan_time = scan_time;
		}
	}

	public String getScan_site() {
		return scan_site;
	}

	public void setScan_site(String scan_site) {
		if(StrUtil.isEmpty(scan_site)){
			this.scan_site = null;
		}else{
			this.scan_site = scan_site;
		}

	}

	public String getNext_site() {
		return next_site;
	}

	public void setNext_site(String next_site) {
		if(StrUtil.isEmpty(next_site)){
			this.next_site = null;
		}else{
			this.next_site = next_site;
		}
	}

	public String getPrev_site() {
		return prev_site;
	}

	public void setPrev_site(String prev_site) {
		if(StrUtil.isEmpty(prev_site)){
			this.prev_site = null;
		}else{
			this.prev_site = prev_site;
		}
	}

	public String getCar_certificate() {
		return car_certificate;
	}

	public void setCar_certificate(String car_certificate) {
		if(StrUtil.isEmpty(car_certificate)){
			this.car_certificate = null;
		}else{
			this.car_certificate = car_certificate;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if(StrUtil.isEmpty(type)){
			this.type = null;
		}else{
			this.type = type;
		}

	}

}
