package br.edu.utfpr.dv.siacoes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class TemplateDAO<T> {
	public abstract String queryById();


	public T findById(int id) throws SQLException {
		try (Connection conn = ConnectionDAO.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(queryById());) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return this.loadObject(rs);
				} else {
					return null;
				}
			}
		}
	}

	abstract T loadObject(ResultSet rs) throws SQLException;

}
