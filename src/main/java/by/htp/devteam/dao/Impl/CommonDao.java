package by.htp.devteam.dao.Impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

abstract class CommonDao {
		
	protected void close(PreparedStatement ps) {
		if ( ps != null ) {
			try {
				ps.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}	
	
	protected void close(Statement st) {
		if ( st != null ) {
			try {
				st.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
}
