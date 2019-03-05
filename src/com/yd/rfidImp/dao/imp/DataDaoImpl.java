package com.yd.rfidImp.dao.imp;


import com.yd.rfidImp.data.Scan_detData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * ClassName: DataDaoImpl
 * date: 2019-2-20 上午10:47:21
 *
 * @author lxs
 */
public class DataDaoImpl {
    private static String JDBC_DRIVER ;

    private static String DB_URL ;

    private static String DB_USER;

    private static String DB_PSWD;

    static {
        Properties pro = new Properties();
        try {
             pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
            JDBC_DRIVER = pro.getProperty("yd.mysql.db.driver");
            DB_URL = pro.getProperty("yd.mysql.db.url");
            DB_USER = pro.getProperty("yd.mysql.db.username");
            DB_PSWD = pro.getProperty("yd.mysql.db.passwd");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存扫描明细
     * @param list
     * @param sum_time
     */
    public static void saveDatas(List<Scan_detData> list,String sum_time) {
        Connection conn = null;
        PreparedStatement pst = null;
        //CallableStatement cstmt = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(" replace into rfid.rfid_scan_det (rfid,scan_site,scan_db_time,scan_type,pack_id,next_site,car_certificate,opt_code,scan_time,prev_site,ship_id,sum_time) values (?,?,?,?,?,?,?,?,?,?,?,?) ");
            for(Scan_detData scan:list){
                pst.setString(1, scan.getRfid());
                pst.setString(2, scan.getScan_site());
                pst.setString(3, scan.getScan_db_time());
                pst.setString(4, scan.getScan_type());
                pst.setString(5, scan.getPack_id());
                pst.setString(6, scan.getNext_site());
                pst.setString(7, scan.getCar_certificate());
                pst.setString(8, scan.getOpt_code());
                pst.setString(9, scan.getScan_time());
                pst.setString(10, scan.getPrev_site());
                pst.setString(11, scan.getShip_id());
                pst.setString(12, sum_time);
                pst.addBatch();
                System.out.println(":"+scan.getRfid()+":"+scan.getScan_site()+":"+scan.getScan_db_time());
            }
            pst.executeBatch();
            conn.commit();
            pst.clearBatch();
            System.out.println("size:"+list.size()+" rfid_scan_det  OK!");

        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存报损数据
     * @param list
     * @param sum_time
     */
    public static void saveExcpDatas(List<Scan_detData> list, String sum_time) {
        Connection conn = null;
        PreparedStatement pst = null;
        //CallableStatement cstmt = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(" replace into rfid.rfid_scan_det_excp (rfid,scan_site,scan_db_time,type,sum_time,opt_code) values (?,?,?,?,?,?) ");
            for(Scan_detData scan:list){
                pst.setString(1, scan.getRfid());
                pst.setString(2, scan.getScan_site());
                pst.setString(3, scan.getScan_db_time());
                pst.setString(4, scan.getType());
                pst.setString(5, sum_time);
                pst.setString(6, scan.getOpt_code());
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            pst.clearBatch();
            System.out.println("size:"+list.size()+ "rfid_scan_det_excp  OK!");

        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 调拨明细保存
     * @param list
     * @param sum_time
     */
    public static void saveDbDatas(List<Scan_detData> list, String sum_time) {
        Connection conn = null;
        PreparedStatement pst = null;
        //CallableStatement cstmt = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(" replace into rfid.rfid_scan_det_tran (rfid,scan_site,scan_db_time,type,sum_time,next_site,prev_site,car_certificate) values (?,?,?,?,?,?,?,?) ");
            for(Scan_detData scan:list){
                pst.setString(1, scan.getRfid());
                pst.setString(2, scan.getScan_site());
                pst.setString(3, scan.getScan_db_time());
                pst.setString(4, scan.getScan_type());
                pst.setString(5, sum_time);
                pst.setString(6, scan.getNext_site());
                pst.setString(7, scan.getPrev_site());
                pst.setString(8, scan.getCar_certificate());
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            pst.clearBatch();
            System.out.println("size:"+list.size()+ "rfid_scan_det_tran  OK!");

        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存普通集包明细
     * @param normalList
     * @param sum_time
     */
    public static void saveNormalDatas(List<Scan_detData> normalList, String sum_time) {
        Connection conn = null;
        PreparedStatement pst = null;
        //CallableStatement cstmt = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(" replace into rfid.rfid_scan_package_normal (pack_id,scan_site,scan_time,sum_time) values (?,?,?,?) ");
            for(Scan_detData scan:normalList){

                pst.setString(1, scan.getPack_id());
                pst.setString(2, scan.getScan_site());
                pst.setString(3, scan.getScan_db_time());
                pst.setString(4, sum_time);
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            pst.clearBatch();
            System.out.println("size:"+normalList.size()+"rfid_scan_package_normal  OK!");

        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存发放明细
     * @param giveList
     * @param sum_time
     */
    public static void saveGiveScanData(List<Scan_detData> giveList, String sum_time) {
        Connection conn = null;
        PreparedStatement pst = null;
        //CallableStatement cstmt = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(" replace into rfid.rfid_scan_det_give (rfid,scan_site,scan_db_time,next_site,sum_time) values (?,?,?,?,?) ");
            for(Scan_detData scan:giveList){
                pst.setString(1, scan.getRfid());
                pst.setString(2, scan.getScan_site());
                pst.setString(3, scan.getScan_db_time());
                pst.setString(4, scan.getNext_site());
                pst.setString(5, sum_time);
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            pst.clearBatch();
            System.out.println("size:"+giveList.size()+"rfid_scan_det_give  OK!");

        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存入库扫描明细
     * @param list
     * @param sum_time
     */
    public static void saveInScanData(List<Scan_detData> list, String sum_time) {
        Connection conn = null;
        PreparedStatement pst = null;
        //CallableStatement cstmt = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(" replace into rfid.rfid_scan_det_in (rfid,scan_site,scan_db_time,car_certificate,prev_site,sum_time) values (?,?,?,?,?,?) ");
            for(Scan_detData scan:list){
                pst.setString(1, scan.getRfid());
                pst.setString(2, scan.getScan_site());
                pst.setString(3, scan.getScan_db_time());
                pst.setString(4, scan.getCar_certificate());
                pst.setString(5, scan.getPrev_site());
                pst.setString(6, sum_time);
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            pst.clearBatch();
            System.out.println("size:"+list.size()+"rfid_scan_det_in  OK!");

        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存集包扫描明细
     * @param list
     * @param sum_time
     */
    public static void savePackageScanData(List<Scan_detData> list, String sum_time) {
        Connection conn = null;
        PreparedStatement pst = null;
        //CallableStatement cstmt = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(" replace into rfid.rfid_scan_package_rfid (rfid,scan_site,scan_db_time,pack_id,next_site,ship_id,sum_time) values (?,?,?,?,?,?,?) ");
            for(Scan_detData scan:list){
                pst.setString(1, scan.getRfid());
                pst.setString(2, scan.getScan_site());
                pst.setString(3, scan.getScan_db_time());
                pst.setString(4, scan.getPack_id());
                pst.setString(5, scan.getNext_site());
                pst.setString(6, scan.getShip_id());
                pst.setString(7, sum_time);
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            pst.clearBatch();
            System.out.println("size:"+list.size()+"rfid_scan_package_rfid  OK!");

        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存中转发出扫描明细
     * @param list
     * @param sum_time
     */
    public static void saveSendScanData(List<Scan_detData> list, String sum_time) {
        Connection conn = null;
        PreparedStatement pst = null;
        //CallableStatement cstmt = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(" replace into rfid.rfid_scan_det_send (rfid,scan_site,scan_db_time,pack_id,next_site,sum_time) values (?,?,?,?,?,?) ");
            for(Scan_detData scan:list){
                pst.setString(1, scan.getRfid());
                pst.setString(2, scan.getScan_site());
                pst.setString(3, scan.getScan_db_time());
                pst.setString(4, scan.getPack_id());
                pst.setString(5, scan.getNext_site());
                pst.setString(6, sum_time);
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            pst.clearBatch();
            System.out.println("size:"+list.size()+"rfid_scan_det_send  OK!");

        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
