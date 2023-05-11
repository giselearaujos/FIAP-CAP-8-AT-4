package br.com.traveller.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.traveller.beans.Cidade;


public class CidadeDAO implements DAO<Cidade> {
	
	private DataSource dataSource;

	public CidadeDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}
	
	@Override
	public Cidade read(Cidade object) {
		try {
			String SQL = "select * from TB_TIN_CIDADE "
					+ "   where cidade = ? ";
			PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL);
			stm.setString(1, object.getCidade());
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				Cidade cidade = new Cidade();
				cidade.setId(rs.getInt("ID"));
				cidade.setCidade(rs.getString("CIDADE"));
				cidade.setEstado(rs.getString("ESTADO"));
				cidade.setSiglaEstado(rs.getString("SIGLA_ESTADO"));
				cidade.setPais(rs.getString("PAIS"));
				cidade.setSiglaPais(rs.getString("SIGLA_PAIS"));
				return cidade;
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("CidadeDAO.READ = " + ex.getMessage());
		}
		return null;
	}

	@Override
	public void create(Cidade object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Cidade object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Cidade object) {
		// TODO Auto-generated method stub
		
	}

}
