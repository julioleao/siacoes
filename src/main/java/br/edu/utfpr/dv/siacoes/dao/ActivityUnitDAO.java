package br.edu.utfpr.dv.siacoes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import br.edu.utfpr.dv.siacoes.model.ActivityUnit;

public class ActivityUnitDAO extends TemplateDAO<ActivityUnit> {

	@Override
	public String queryById() {
		return ("SELECT * FROM activityunit WHERE idActivityUnit=?");
	}

	public List<ActivityUnit> listAll() throws SQLException {
		try (Connection conn = ConnectionDAO.getInstance().getConnection(); Statement stmt = conn.createStatement();) {
			try (ResultSet rs = stmt.executeQuery("SELECT * FROM activityunit ORDER BY description")) {

				List<ActivityUnit> list = new ArrayList<ActivityUnit>();

				while (rs.next()) {
					list.add(this.loadObject(rs));
				}
				return list;
			}
		}
	}

	public int save(int idUser, ActivityUnit unit) throws SQLException {
		boolean insert = (unit.getIdActivityUnit() == 0);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionDAO.getInstance().getConnection();

			if (insert) {
				stmt = conn.prepareStatement(
						"INSERT INTO activityunit(description, fillAmount, amountDescription) VALUES(?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
			} else {
				stmt = conn.prepareStatement(
						"UPDATE activityunit SET description=?, fillAmount=?, amountDescription=? WHERE idActivityUnit=?");
			}

			stmt.setString(1, unit.getDescription());
			stmt.setInt(2, (unit.isFillAmount() ? 1 : 0));
			stmt.setString(3, unit.getAmountDescription());

			if (!insert) {
				stmt.setInt(4, unit.getIdActivityUnit());
			}

			stmt.execute();

			if (insert) {
				rs = stmt.getGeneratedKeys();

				if (rs.next()) {
					unit.setIdActivityUnit(rs.getInt(1));
				}

				new UpdateEvent(conn).registerInsert(idUser, unit);
			} else {
				new UpdateEvent(conn).registerUpdate(idUser, unit);
			}

			return unit.getIdActivityUnit();
		} finally {
			if ((rs != null) && !rs.isClosed())
				rs.close();
			if ((stmt != null) && !stmt.isClosed())
				stmt.close();
			if ((conn != null) && !conn.isClosed())
				conn.close();
		}
	}

	@Override
	ActivityUnit loadObject(ResultSet rs) throws SQLException {
		ActivityUnit unit = new ActivityUnit();

		unit.setIdActivityUnit(rs.getInt("idActivityUnit"));
		unit.setDescription(rs.getString("Description"));
		unit.setFillAmount(rs.getInt("fillAmount") == 1);
		unit.setAmountDescription(rs.getString("amountDescription"));

		return unit;
	}
}
