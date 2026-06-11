package com.servicetype.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceTypeDAO implements ServiceTypeDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/WeBondDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO service_type (TYPE_NAME,`DESCRIPTION`,TYPE_MODE,DEFAULT_IMAGE_URL)VALUES (?,?,?,?);";
	private static final String GET_ALL_STMT = "SELECT * FROM service_type";
	private static final String GET_ONE_STMT = "SELECT * FROM service_type WHERE SERVICE_TYPE_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM service_type where SERVICE_TYPE_ID = ?";
	private static final String UPDATE_STMT = "UPDATE service_type SET TYPE_NAME=?, `DESCRIPTION`=?, TYPE_MODE=?, DEFAULT_IMAGE_URL=? WHERE SERVICE_TYPE_ID=?";
	@Override
	public void insert(ServiceTypeVO svcType) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, svcType.getTypeName());
			pstmt.setString(2, svcType.getDescrip());
			pstmt.setInt(3, svcType.getTypeMode());
			pstmt.setString(4, svcType.getImgURL());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				svcType.setSvcTypeID(rs.getInt(1));
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	public void delete(Integer PK) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, PK);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(ServiceTypeVO serviceType) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, serviceType.getTypeName());
			pstmt.setString(2, serviceType.getDescrip());
			pstmt.setInt(3, serviceType.getTypeMode());
			pstmt.setString(4, serviceType.getImgURL());
			// wehre id
			pstmt.setInt(5, serviceType.getSvcTypeID());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public ServiceTypeVO findByPK(Integer serviceTypeId) {

		ServiceTypeVO serviceTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, serviceTypeId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				serviceTypeVO = new ServiceTypeVO();
				serviceTypeVO.setSvcTypeID(rs.getInt("SERVICE_TYPE_ID"));
				serviceTypeVO.setTypeName(rs.getString("TYPE_NAME"));
				serviceTypeVO.setDescrip(rs.getString("DESCRIPTION"));
				serviceTypeVO.setTypeMode(rs.getInt("TYPE_MODE"));
				serviceTypeVO.setImgURL(rs.getString("DEFAULT_IMAGE_URL"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return serviceTypeVO;
	}

	@Override
	public List<ServiceTypeVO> getAll() {

		List<ServiceTypeVO> list = new ArrayList<ServiceTypeVO>();
		ServiceTypeVO serviceTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				serviceTypeVO = new ServiceTypeVO();
				serviceTypeVO.setSvcTypeID(rs.getInt("SERVICE_TYPE_ID"));
				serviceTypeVO.setTypeName(rs.getString("TYPE_NAME"));
				serviceTypeVO.setDescrip(rs.getString("DESCRIPTION"));
				serviceTypeVO.setTypeMode(rs.getInt("TYPE_MODE"));
				serviceTypeVO.setImgURL(rs.getString("DEFAULT_IMAGE_URL"));

				list.add(serviceTypeVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occurred. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return list;
	}
}