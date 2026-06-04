package com.service.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceDAO implements ServiceDAO_interface {

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/WeBondDB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("JNDI DataSource lookup failed: jdbc/WeBondDB", e);
		}
	}

	private static final String INSERT_STMT = "INSERT INTO service "
			+ "(SERVICE_TYPE_ID, MEMBER_ID, SERVICE_NAME, DESCRIPTION, HOURLY_RATE, STATUS, CREATED_AT) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String GET_ALL_STMT = "SELECT SERVICE_ID, SERVICE_TYPE_ID, MEMBER_ID, SERVICE_NAME, DESCRIPTION, HOURLY_RATE, STATUS, CREATED_AT "
			+ "FROM service " + "ORDER BY SERVICE_ID";

	private static final String GET_ONE_STMT = "SELECT SERVICE_ID, SERVICE_TYPE_ID, MEMBER_ID, SERVICE_NAME, DESCRIPTION, HOURLY_RATE, STATUS, CREATED_AT "
			+ "FROM service " + "WHERE SERVICE_ID = ?";

	private static final String DELETE = "DELETE FROM service " + "WHERE SERVICE_ID = ?";

	private static final String UPDATE = "UPDATE service SET " + "SERVICE_TYPE_ID = ?, " + "MEMBER_ID = ?, "
			+ "SERVICE_NAME = ?, " + "DESCRIPTION = ?, " + "HOURLY_RATE = ?, " + "STATUS = ? " + "WHERE SERVICE_ID = ?";

	@Override
	public void insert(ServiceVO svc) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT_STMT, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, svc.getServiceTypeId());
			pstmt.setInt(2, svc.getMemberId());
			pstmt.setString(3, svc.getServiceName());
			pstmt.setString(4, svc.getDescription());
			pstmt.setInt(5, svc.getHourlyRate());
			pstmt.setByte(6, svc.getStatus());

			if (svc.getCreatedAt() != null) {
				pstmt.setTimestamp(7, Timestamp.valueOf(svc.getCreatedAt()));
			} else {
				pstmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
			}

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				svc.setServiceId(rs.getInt(1));
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
	}

	@Override
	public void delete(Integer PK) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, PK);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occurred. " + se.getMessage());
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
	public void update(ServiceVO svc) {
	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        con = ds.getConnection();

	        pstmt = con.prepareStatement(UPDATE);

	        pstmt.setInt(1, svc.getServiceTypeId());
	        pstmt.setInt(2, svc.getMemberId());
	        pstmt.setString(3, svc.getServiceName());
	        pstmt.setString(4, svc.getDescription());
	        pstmt.setInt(5, svc.getHourlyRate());
	        pstmt.setByte(6, svc.getStatus());

	        // WHERE SERVICE_ID = ?
	        pstmt.setInt(7, svc.getServiceId());

	        pstmt.executeUpdate();

	    } catch (SQLException se) {
	        throw new RuntimeException("A database error occurred. " + se.getMessage());
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
	public ServiceVO getOne(Integer PK) {
	    ServiceVO svc = null;
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        con = ds.getConnection();

	        pstmt = con.prepareStatement(GET_ONE_STMT);

	        pstmt.setInt(1, PK);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            svc = new ServiceVO();

	            svc.setServiceId(rs.getInt("SERVICE_ID"));
	            svc.setServiceTypeId(rs.getInt("SERVICE_TYPE_ID"));
	            svc.setMemberId(rs.getInt("MEMBER_ID"));
	            svc.setServiceName(rs.getString("SERVICE_NAME"));
	            svc.setDescription(rs.getString("DESCRIPTION"));
	            svc.setHourlyRate(rs.getInt("HOURLY_RATE"));
	            svc.setStatus(rs.getByte("STATUS"));

	            Timestamp createdAt = rs.getTimestamp("CREATED_AT");
	            if (createdAt != null) {
	                svc.setCreatedAt(createdAt.toLocalDateTime());
	            }
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

	    return svc;
	}

	@Override
	public List<ServiceVO> getAll() {
	    List<ServiceVO> list = new ArrayList<ServiceVO>();
	    ServiceVO svc = null;

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        con = ds.getConnection();

	        pstmt = con.prepareStatement(GET_ALL_STMT);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            svc = new ServiceVO();

	            svc.setServiceId(rs.getInt("SERVICE_ID"));
	            svc.setServiceTypeId(rs.getInt("SERVICE_TYPE_ID"));
	            svc.setMemberId(rs.getInt("MEMBER_ID"));
	            svc.setServiceName(rs.getString("SERVICE_NAME"));
	            svc.setDescription(rs.getString("DESCRIPTION"));
	            svc.setHourlyRate(rs.getInt("HOURLY_RATE"));
	            svc.setStatus(rs.getByte("STATUS"));

	            Timestamp createdAt = rs.getTimestamp("CREATED_AT");
	            if (createdAt != null) {
	                svc.setCreatedAt(createdAt.toLocalDateTime());
	            }

	            list.add(svc);
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

	@Override
	public List<ServiceVO> getByServiceTypeId(Integer serviceTypeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
