package com.jornada.server.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jornada.ConfigJornada;
import com.jornada.server.classes.password.BCrypt;
import com.jornada.server.classes.utility.MpUtilServer;
import com.jornada.server.database.ConnectionManager;
import com.jornada.shared.classes.TipoUsuario;
import com.jornada.shared.classes.Usuario;
import com.jornada.shared.classes.list.UsuarioErroImportar;

public class UsuarioServer{

	public static String DB_INSERT = 
			"INSERT INTO usuario " +
			"(" +
			"primeiro_nome," +
			"sobre_nome," +
			"cpf," +
			"data_nascimento," +
			"id_tipo_usuario," +
			"email," +
			"telefone_celular," +
			"telefone_residencial," +
			"telefone_comercial," +
			"login," +
			"senha," +
			"endereco," +
			"numero_residencia," +
			"bairro," +
			"cidade," +
			"unidade_federativa," +
			"cep," +
			"data_matricula," +
			"rg," +
			"sexo," +
			"empresa_onde_trabalha," +
			"cargo," +
			"resp_academico," +
			"resp_financeiro," +
			"registro_matricula," +
			"tipo_pais," +
			"situacao_responsaveis," +
			"situacao_responsaveis_outros" +			
			") " +
			"VALUES " +
			"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	
	public static String DB_UPDATE = 
			"UPDATE usuario set " +
			"primeiro_nome=?, " +
			"sobre_nome=?, " +
			"cpf=?, " +
			"data_nascimento=?, " +
			"id_tipo_usuario=?, " +
			"email=?, " +
			"telefone_celular=?, " +
			"telefone_residencial=?, " +
			"telefone_comercial=?, " +
			"login=?, " +			
			"endereco=?, " +
			"numero_residencia=?, " +
			"bairro=?, " +
			"cidade=?, " +
			"unidade_federativa=?, " +
			"cep=?, " +
			"data_matricula=?, " +
			"rg=?, " +
			"sexo=?, " +
			"empresa_onde_trabalha=?, " +
			"cargo=?, " +
			"resp_academico=?, " +
			"resp_financeiro=?, " +
			"registro_matricula=?, " +
			"tipo_pais=?, " +
			"situacao_responsaveis=?, " +
			"situacao_responsaveis_outros=? " +			
			"where id_usuario=?";
	
	
	
	public static String DB_UPDATE_IDIOMA = "UPDATE usuario set id_idioma=? where id_usuario=?";
	public static String DB_UPDATE_SENHA = "UPDATE usuario set senha=?, primeiro_login=false where id_usuario=?";
	public static String DB_SELECT_ILIKE = "SELECT * FROM usuario where (primeiro_nome ilike ?) order by primeiro_nome asc";
	public static String DB_SELECT_DB_FIELD_ILIKE = "select * from usuario, tipo_usuario where (<change> ilike ?) and usuario.id_tipo_usuario = tipo_usuario.id_tipo_usuario order by primeiro_nome asc";
	public static String DB_SELECT_ILIKE_TIPO_USUARIO = "SELECT * FROM usuario where id_tipo_usuario = ? and (primeiro_nome ilike ? or sobre_nome ilike ?) order by primeiro_nome asc";
	public static String DB_SELECT_USUARIO_PELO_TIPO_USUARIO = "SELECT * FROM usuario where id_tipo_usuario = ? order by primeiro_nome asc";
	public static String DB_SELECT_ALL = "SELECT * FROM usuario order by primeiro_nome asc;";
//	public static String DB_SELECT_USUARIO_ID = "SELECT * FROM usuario where id_usuario=?;";
	public static String DB_SELECT_USUARIO_ID = "select * from usuario, tipo_usuario where (id_usuario = ?) and usuario.id_tipo_usuario = tipo_usuario.id_tipo_usuario order by primeiro_nome asc";
	public static String DB_SELECT_USUARIO_LOGIN = "SELECT * FROM usuario, tipo_usuario where login=? and usuario.id_tipo_usuario = tipo_usuario.id_tipo_usuario;";
	public static String DB_DELETE = "delete from usuario where id_usuario=?";
	public static String DB_SELECT_ALL_TIPO_USUARIOS = "SELECT * FROM tipo_usuario where is_visible=true order by nome_tipo_usuario asc;";
	public static String DB_SELECT_TIPO_USUARIOS_POR_NOME = "SELECT * FROM tipo_usuario where nome_tipo_usuario=? ;";
	public static String DB_SELECT_TIPO_USUARIOS_POR_ID = "SELECT * FROM tipo_usuario where id_tipo_usuario=? ;";
	public static String DB_SELECT_ILIKE_FILTRADO_POR_CURSO = 
			"select * from usuario where id_usuario in "+
			"( select id_usuario from rel_curso_usuario where id_curso=? ) "+
			"and (primeiro_nome ilike ? or sobre_nome ilike ?) "+
			"order by primeiro_nome, sobre_nome";		
	public static String DB_SELECT_FILTRADO_POR_CURSO = 
			"select * from usuario where id_usuario in "+
			"( select id_usuario from rel_curso_usuario where id_curso=? ) "+
			"order by primeiro_nome, sobre_nome";	
	
	public static String DB_SELECT_ALUNO_FILTRADO_POR_CURSO_AMBIENTE_PAI =
	"select * from usuario where id_usuario in "+ 
	"( "+
	"	select id_usuario from rel_curso_usuario where id_curso=? and id_usuario in "+
	"	( "+
	"		select id_usuario_aluno from rel_pai_aluno where id_usuario_pais=? "+
	"	) "+
	") order by primeiro_nome, sobre_nome ";
	
	public static String DB_SELECT_USUARIO_PELO_TIPO_USUARIO_AMBIENTE_PAI = 
			"SELECT * FROM usuario where id_tipo_usuario = ? and id_usuario in "+
			"( "+
			"	select id_usuario_aluno from rel_pai_aluno where id_usuario_pais=? "+
			") "+
			"order by primeiro_nome asc ";			
	
	
	public static String DB_SELECT_FILTRADO_POR_OCORRENCIA = 
			"select * from usuario where id_usuario in "+
			"( select id_usuario from rel_usuario_ocorrencia where id_ocorrencia = ? ) "+
			"order by primeiro_nome, sobre_nome";		
	
	public static String DB_DELETE_REL_PAI_ALUNO = "delete from rel_pai_aluno where id_usuario_aluno=?";
	public static String DB_INSERT_REL_PAI_ALUNO = "INSERT INTO rel_pai_aluno (id_usuario_pais, id_usuario_aluno) VALUES (?,?)";	
	
	
	public static String DB_SELECT_ALL_PAIS_DO_ALUNO = 
			"select * from usuario where id_usuario in "+
			"(  "+
			"	select id_usuario_pais from rel_pai_aluno where id_usuario_aluno=? "+ 
			")  "+
			"order by primeiro_nome, sobre_nome ";	
	

	public UsuarioServer(){
		
	}
	
	public static String AdicionarUsuario(Usuario usuario) {

		String success = "false";

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			Date date = new Date();

			if (usuario.getDataNascimento() == null) {
				usuario.setDataNascimento(date);
			}
			if (usuario.getDataMatricula() == null) {
				usuario.setDataMatricula(date);
			}
			
			String senhaHashed = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());

			int count = 0;
			PreparedStatement insert = connection.prepareStatement(UsuarioServer.DB_INSERT);
			insert.setString(++count, usuario.getPrimeiroNome());
			insert.setString(++count, usuario.getSobreNome());
			insert.setString(++count, usuario.getCpf());
			insert.setDate(++count, (usuario.getDataNascimento()==null)?null:new java.sql.Date(usuario.getDataNascimento().getTime()));
			insert.setInt(++count, usuario.getIdTipoUsuario());
			insert.setString(++count, usuario.getEmail());
			insert.setString(++count, usuario.getTelefoneCelular());
			insert.setString(++count, usuario.getTelefoneResidencial());
			insert.setString(++count, usuario.getTelefoneComercial());
			insert.setString(++count, usuario.getLogin());
			insert.setString(++count, senhaHashed);
			insert.setString(++count, usuario.getEndereco());
			insert.setString(++count, usuario.getNumeroResidencia());
			insert.setString(++count, usuario.getBairro());
			insert.setString(++count, usuario.getCidade());
			insert.setString(++count, usuario.getUnidadeFederativa());
			insert.setString(++count, usuario.getCep());
			insert.setDate(++count, (usuario.getDataMatricula()==null)?null:new java.sql.Date(usuario.getDataMatricula().getTime()));
			insert.setString(++count, usuario.getRg());
			insert.setString(++count, usuario.getSexo());
			insert.setString(++count, usuario.getEmpresaOndeTrabalha());
			insert.setString(++count, usuario.getCargo());
			insert.setBoolean(++count, usuario.isRespAcademico());
			insert.setBoolean(++count, usuario.isRespFinanceiro());
			insert.setString(++count, usuario.getRegistroMatricula());
			insert.setString(++count, usuario.getTipoPais());
			insert.setString(++count, usuario.getSituacaoResponsaveis());
			insert.setString(++count, usuario.getSituacaoResponsaveisOutros());

			int numberUpdate = insert.executeUpdate();

			if (numberUpdate == 1) {
				success = "true";
			}

		} catch (SQLException sqlex) {
			success = sqlex.getMessage();
			System.err.println(success);
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return success;
	}
	
	public static String updateUsuarioRow(Usuario usuario){
		String success="false";

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();
			
			int count = 0;
			PreparedStatement update = connection.prepareStatement(UsuarioServer.DB_UPDATE);
			update.setString(++count, usuario.getPrimeiroNome());
			update.setString(++count, usuario.getSobreNome());
			update.setString(++count, usuario.getCpf());
			update.setDate(++count, new java.sql.Date(usuario.getDataNascimento().getTime()));
			update.setInt(++count, usuario.getIdTipoUsuario());
			update.setString(++count, usuario.getEmail());
			update.setString(++count, usuario.getTelefoneCelular());
			update.setString(++count, usuario.getTelefoneResidencial());
			update.setString(++count, usuario.getTelefoneComercial());
			update.setString(++count, usuario.getLogin());
//			update.setString(++count, usuario.getSenha());
			update.setString(++count, usuario.getEndereco());
			update.setString(++count, usuario.getNumeroResidencia());
			update.setString(++count, usuario.getBairro());
			update.setString(++count, usuario.getCidade());
			update.setString(++count, usuario.getUnidadeFederativa());
			update.setString(++count, usuario.getCep());
			update.setDate(++count, (usuario.getDataMatricula()==null)?null:new java.sql.Date(usuario.getDataMatricula().getTime()));
			update.setString(++count, usuario.getRg());
			update.setString(++count, usuario.getSexo());
			update.setString(++count, usuario.getEmpresaOndeTrabalha());
			update.setString(++count, usuario.getCargo());
			update.setBoolean(++count, usuario.isRespAcademico());
			update.setBoolean(++count, usuario.isRespFinanceiro());
			update.setString(++count, usuario.getRegistroMatricula());
			update.setString(++count, usuario.getTipoPais());
			update.setString(++count, usuario.getSituacaoResponsaveis());
			update.setString(++count, usuario.getSituacaoResponsaveisOutros());
						
			update.setInt(++count, usuario.getIdUsuario());

			int numberUpdate = update.executeUpdate();


			if (numberUpdate == 1) {
				success = "true";
			}


		} catch (SQLException sqlex) {			
			success = sqlex.getMessage();
			System.err.println(success);
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return success;
	}	
		
	public static boolean atualizarIdioma(int idUsuario, int idIdioma){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();
			
			int count = 0;
			PreparedStatement update = connection.prepareStatement(DB_UPDATE_IDIOMA);
			update.setInt(++count, idIdioma);
			update.setInt(++count, idUsuario);

			int numberUpdate = update.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
			}


		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return success;
	}	
		
	public static boolean atualizarSenha(int idUsuario, String senha){
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();
			
			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
			
			int count = 0;
			PreparedStatement update = connection.prepareStatement(DB_UPDATE_SENHA);
			update.setString(++count, senhaHashed);
			update.setInt(++count, idUsuario);

			int numberUpdate = update.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
			}


		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return success;
	}	
		
	public static boolean deleteUsuarioRow(int id_usuario){
		
		boolean success=false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			int count = 0;
			PreparedStatement deleteCurso = connection.prepareStatement(UsuarioServer.DB_DELETE);
			deleteCurso.setInt(++count, id_usuario);

			int numberUpdate = deleteCurso.executeUpdate();


			if (numberUpdate == 1) {
				success = true;
			}


		} catch (SQLException sqlex) {
			success=false;
			System.err.println(sqlex.getMessage());			
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		return success;
	}		
	
	public static ArrayList<UsuarioErroImportar> importarUsuariosUsandoExcel(String strFileName){
	    Workbook wb;

//	    String strMessage;
	    
	    ArrayList<UsuarioErroImportar> arrayUsuarioError = new ArrayList<UsuarioErroImportar>();
	    
	    int SHEET_ALUNO = 0;
	    int SHEET_COORDENADOR = 1;
	    int SHEET_PAIS = 2;
	    int SHEET_PROFESSOR = 3;

	    
		try {

			wb = new XSSFWorkbook("excel/download/"+strFileName);
			
			arrayUsuarioError.addAll(importarAlunos(wb.getSheetAt(SHEET_ALUNO)));
			arrayUsuarioError.addAll(importarCoordenador(wb.getSheetAt(SHEET_COORDENADOR)));
			arrayUsuarioError.addAll(importarPais(wb.getSheetAt(SHEET_PAIS)));
			arrayUsuarioError.addAll(importarProfessor(wb.getSheetAt(SHEET_PROFESSOR)));

			
		} catch (Exception ex) {
			System.out.println("Error Read Excel:"+ex.getMessage());
//			strMessage="erro";
		}
		
		return arrayUsuarioError;
	}
	
	private static String normalizeCellType(Cell cell) {
		
		 switch (cell.getCellType()) {
		 case Cell.CELL_TYPE_STRING: return cell.getStringCellValue(); 
		 case Cell.CELL_TYPE_NUMERIC:
			 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			 java.util.Date d =  cell.getDateCellValue();
			 String strDate = df.format(d);
			 System.out.println("date is :- "+ strDate );
			 return strDate;
		 }
		return "";
	}
	
	
	public static ArrayList<UsuarioErroImportar> importarAlunos(Sheet sheet){
		
		 ArrayList<UsuarioErroImportar> arrayAlunoError = new ArrayList<UsuarioErroImportar>();
		 
	    Row row;
	    
	    System.out.println("Aluno=====================");
		System.out.println("FirstRowNum:"+sheet.getFirstRowNum());		
		System.out.println("PhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
		
		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
			
			String strStatusAddUsuario= "true";
			String strCamposCorretos = "";
			
			row = sheet.getRow(i);
			

			
			int cv=0;
			Cell Registro_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Data_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			String strDataMatricula = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
			
			Cell Registro_Aluno = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Primeiro_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Sobre_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Email = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Usuario = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Senha = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Data_Nascimento = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			String strDataNascimento = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
			Cell Sexo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Endereco = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Num_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Bairro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Cidade = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Estado = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Cep = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Celular = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Comercial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell CPF = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell RG = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Situacao_dos_Pais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Situacao_dos_Pais_Outros = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);			
			
			
			Registro_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
//			Data_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
			Registro_Aluno.setCellType(Cell.CELL_TYPE_STRING);	
			Primeiro_Nome.setCellType(Cell.CELL_TYPE_STRING);	
			Sobre_Nome.setCellType(Cell.CELL_TYPE_STRING);	
			Email.setCellType(Cell.CELL_TYPE_STRING);	
			Usuario.setCellType(Cell.CELL_TYPE_STRING);	
			Senha.setCellType(Cell.CELL_TYPE_STRING);	
//			Data_Nascimento.setCellType(Cell.CELL_TYPE_STRING);	
			Sexo.setCellType(Cell.CELL_TYPE_STRING);	
			Endereco.setCellType(Cell.CELL_TYPE_STRING);	
			Num_Residencial.setCellType(Cell.CELL_TYPE_STRING);	
			Bairro.setCellType(Cell.CELL_TYPE_STRING);
			Cidade.setCellType(Cell.CELL_TYPE_STRING);
			Estado.setCellType(Cell.CELL_TYPE_STRING);
			Cep.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Celular.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Residencial.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Comercial.setCellType(Cell.CELL_TYPE_STRING);
			CPF.setCellType(Cell.CELL_TYPE_STRING);
			RG.setCellType(Cell.CELL_TYPE_STRING);
			Situacao_dos_Pais.setCellType(Cell.CELL_TYPE_STRING);
			Situacao_dos_Pais_Outros.setCellType(Cell.CELL_TYPE_STRING);
			
			Usuario usuario = new Usuario();
			
			String senha="";				
			if((Senha==null)|| (Senha.getStringCellValue().isEmpty())){
				senha = ConfigJornada.getProperty("config.senha.inicial");
			}
			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
			
			
			if(Primeiro_Nome.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Primeiro Nome é obrigatório || ";
			}
			if(Sobre_Nome.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Sobre Nome é obrigatório || ";
			}
			if(Email.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Email é obrigatório || ";
			}
			if(Usuario.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Usuario é obrigatório || ";
			}			
						
			Date dataMatricula = MpUtilServer.convertStringToDate(strDataMatricula,"dd/MM/yyyy");
			String strValidateDataMatricula = MpUtilServer.isThisDateValid(strDataMatricula, "dd/MM/yyyy");
			if(!strValidateDataMatricula.equals("true")){
				strCamposCorretos+="Data Matricula "+strValidateDataMatricula +" || ";
			}
			
			Date dataNascimento = MpUtilServer.convertStringToDate(strDataNascimento,"dd/MM/yyyy");
			String strValidateDataNascimento = MpUtilServer.isThisDateValid(strDataNascimento, "dd/MM/yyyy");
			if(!strValidateDataNascimento.equals("true")){
				strCamposCorretos+="Data Matricula "+strValidateDataNascimento +" || ";
			}

			TipoUsuario tipoUsuario = UsuarioServer.getTipoUsuarioPorId(TipoUsuario.ALUNO); 
			
			usuario.setIdTipoUsuario(tipoUsuario.getIdTipoUsuario());
			usuario.setTipoUsuario(tipoUsuario);
			usuario.setRegistroMatricula( (Registro_Matricula==null) ? "" : Registro_Aluno.getStringCellValue() );	
			usuario.setDataMatricula(dataMatricula);//(Formato : 25/12/2010)
			usuario.setRegistroAluno( (Registro_Aluno==null) ? "" : Registro_Aluno.getStringCellValue() );	
			usuario.setPrimeiroNome( (Primeiro_Nome==null) ? "" : Primeiro_Nome.getStringCellValue() );	
			usuario.setSobreNome( (Sobre_Nome==null) ? "" : Sobre_Nome.getStringCellValue() );	
			usuario.setEmail( (Email==null) ? "" : Email.getStringCellValue() );	
			usuario.setLogin( (Usuario==null) ? "" : Usuario.getStringCellValue() );	
			usuario.setSenha(senhaHashed);	
			usuario.setDataNascimento(dataNascimento);// (Formato : 25/12/2010)	
			usuario.setSexo( (Sexo==null) ? "" : Sexo.getStringCellValue() );	
			usuario.setEndereco( (Endereco==null) ? "" : Endereco.getStringCellValue() );	
			usuario.setNumeroResidencia( (Num_Residencial==null) ? "" : Num_Residencial.getStringCellValue() );	
			usuario.setBairro( (Bairro==null) ? "" : Bairro.getStringCellValue() );
			usuario.setCidade( (Cidade==null) ? "" : Cidade.getStringCellValue() );
			usuario.setUnidadeFederativa( (Estado==null) ? "" : Estado.getStringCellValue());
			usuario.setCep( (Cep==null) ? "" : Cep.getStringCellValue() );
			usuario.setTelefoneCelular( (Tel_Celular==null) ? "" : Tel_Celular.getStringCellValue() );
			usuario.setTelefoneResidencial( (Tel_Residencial==null) ? "" : Tel_Residencial.getStringCellValue() );
			usuario.setTelefoneComercial( (Tel_Comercial==null) ? "" : Tel_Comercial.getStringCellValue() );
			usuario.setCpf( (CPF==null) ? "" : CPF.getStringCellValue() );
			usuario.setRg( (RG==null) ? "" : RG.getStringCellValue() );
			usuario.setSituacaoResponsaveis( (Situacao_dos_Pais==null) ? "" : Situacao_dos_Pais.getStringCellValue() );
			usuario.setSituacaoResponsaveisOutros( (Situacao_dos_Pais_Outros==null) ? "" : Situacao_dos_Pais_Outros.getStringCellValue() );			
			
//			arrayUsuario.add(usuario);
			

			if (strCamposCorretos.isEmpty()) {
				strStatusAddUsuario = UsuarioServer.AdicionarUsuario(usuario);
			}else{
				strStatusAddUsuario = strCamposCorretos;
			}
			
			if(!strStatusAddUsuario.equals("true")){				
				
				UsuarioErroImportar usuarioErroImportar = new UsuarioErroImportar();
				
				usuarioErroImportar.setIdTipoUsuario(usuario.getIdTipoUsuario());
				usuarioErroImportar.setTipoUsuario(usuario.getTipoUsuario());
				usuarioErroImportar.setPrimeiroNome(usuario.getPrimeiroNome());
				usuarioErroImportar.setSobreNome(usuario.getSobreNome());
				usuarioErroImportar.setEmail(usuario.getEmail());
				usuarioErroImportar.setLogin(usuario.getLogin());
				usuarioErroImportar.setErroImportar(strStatusAddUsuario);
				
				arrayAlunoError.add(usuarioErroImportar);

			}

		}
		
		return arrayAlunoError;

	}
	
	public static ArrayList<UsuarioErroImportar> importarCoordenador(Sheet sheet){
	    

		
		ArrayList<UsuarioErroImportar> arrayUsuarioError = new ArrayList<UsuarioErroImportar>();
		 
	    Row row;
		
	    System.out.println("Coordenador=====================");
		System.out.println("FirstRowNum:"+sheet.getFirstRowNum());		
		System.out.println("PhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
		
		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
			
			String strStatusAddUsuario= "true";
			String strCamposCorretos = "";
			
			row = sheet.getRow(i);
			int cv=0;
//			Cell Registro_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Data_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Registro_Aluno = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Primeiro_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Sobre_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Email = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Usuario = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Senha = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Data_Nascimento = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			String strDataNascimento = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
			Cell Sexo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Endereco = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Num_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Bairro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Cidade = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Estado = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Cep = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Celular = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Comercial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell CPF = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell RG = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Situacao_dos_Pais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Situacao_dos_Pais_Outros = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);			
			
			
//			Registro_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
//			Data_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
//			Registro_Aluno.setCellType(Cell.CELL_TYPE_STRING);	
			Primeiro_Nome.setCellType(Cell.CELL_TYPE_STRING);	
			Sobre_Nome.setCellType(Cell.CELL_TYPE_STRING);	
			Email.setCellType(Cell.CELL_TYPE_STRING);	
			Usuario.setCellType(Cell.CELL_TYPE_STRING);	
			Senha.setCellType(Cell.CELL_TYPE_STRING);	
//			Data_Nascimento.setCellType(Cell.CELL_TYPE_STRING);	
			Sexo.setCellType(Cell.CELL_TYPE_STRING);	
			Endereco.setCellType(Cell.CELL_TYPE_STRING);	
			Num_Residencial.setCellType(Cell.CELL_TYPE_STRING);	
			Bairro.setCellType(Cell.CELL_TYPE_STRING);
			Cidade.setCellType(Cell.CELL_TYPE_STRING);
			Estado.setCellType(Cell.CELL_TYPE_STRING);
			Cep.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Celular.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Residencial.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Comercial.setCellType(Cell.CELL_TYPE_STRING);
			CPF.setCellType(Cell.CELL_TYPE_STRING);
			RG.setCellType(Cell.CELL_TYPE_STRING);
//			Situacao_dos_Pais.setCellType(Cell.CELL_TYPE_STRING);
//			Situacao_dos_Pais_Outros.setCellType(Cell.CELL_TYPE_STRING);
			
			Usuario usuario = new Usuario();
			
			String senha="";				
			if((Senha==null)|| (Senha.getStringCellValue().isEmpty())){
				senha = ConfigJornada.getProperty("config.senha.inicial");
			}
			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
			
			
			if(Primeiro_Nome.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Primeiro Nome é obrigatório || ";
			}
			if(Sobre_Nome.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Sobre Nome é obrigatório || ";
			}
			if(Email.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Email é obrigatório || ";
			}
			if(Usuario.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Usuario é obrigatório || ";
			}			
			


//			String strDataMatricula = Data_Matricula.getStringCellValue();
//			Date dataMatricula = MpUtilServer.convertStringToDate(strDataMatricula,"dd/MM/yyyy");
//			String strValidateDataMatricula = MpUtilServer.isThisDateValid(strDataMatricula, "dd/MM/yyyy");
//			if(!strValidateDataMatricula.equals("true")){
//				strCamposCorretos+="Data Matricula "+strValidateDataMatricula +" || ";
//			}
			
			
			
			Date dataNascimento = MpUtilServer.convertStringToDate(strDataNascimento,"dd/MM/yyyy");
			String strValidateDataNascimento = MpUtilServer.isThisDateValid(strDataNascimento, "dd/MM/yyyy");
			if(!strValidateDataNascimento.equals("true")){
				strCamposCorretos+="Data Matricula "+strValidateDataNascimento +" || ";
			}
			
			TipoUsuario tipoUsuario = UsuarioServer.getTipoUsuarioPorId(TipoUsuario.COORDENADOR); 
			
			usuario.setIdTipoUsuario(tipoUsuario.getIdTipoUsuario());
			usuario.setTipoUsuario(tipoUsuario);
//			usuario.setRegistroMatricula( (Registro_Matricula==null) ? "" : Registro_Aluno.getStringCellValue() );	
//			usuario.setDataMatricula(dataMatricula);//(Formato : 25/12/2010)
//			usuario.setRegistroAluno( (Registro_Aluno==null) ? "" : Registro_Aluno.getStringCellValue() );	
			usuario.setPrimeiroNome( (Primeiro_Nome==null) ? "" : Primeiro_Nome.getStringCellValue() );	
			usuario.setSobreNome( (Sobre_Nome==null) ? "" : Sobre_Nome.getStringCellValue() );	
			usuario.setEmail( (Email==null) ? "" : Email.getStringCellValue() );	
			usuario.setLogin( (Usuario==null) ? "" : Usuario.getStringCellValue() );	
			usuario.setSenha(senhaHashed);	
			usuario.setDataNascimento(dataNascimento);// (Formato : 25/12/2010)	
			usuario.setSexo( (Sexo==null) ? "" : Sexo.getStringCellValue() );	
			usuario.setEndereco( (Endereco==null) ? "" : Endereco.getStringCellValue() );	
			usuario.setNumeroResidencia( (Num_Residencial==null) ? "" : Num_Residencial.getStringCellValue() );	
			usuario.setBairro( (Bairro==null) ? "" : Bairro.getStringCellValue() );
			usuario.setCidade( (Cidade==null) ? "" : Cidade.getStringCellValue() );
			usuario.setUnidadeFederativa( (Estado==null) ? "" : Estado.getStringCellValue());
			usuario.setCep( (Cep==null) ? "" : Cep.getStringCellValue() );
			usuario.setTelefoneCelular( (Tel_Celular==null) ? "" : Tel_Celular.getStringCellValue() );
			usuario.setTelefoneResidencial( (Tel_Residencial==null) ? "" : Tel_Residencial.getStringCellValue() );
			usuario.setTelefoneComercial( (Tel_Comercial==null) ? "" : Tel_Comercial.getStringCellValue() );
			usuario.setCpf( (CPF==null) ? "" : CPF.getStringCellValue() );
			usuario.setRg( (RG==null) ? "" : RG.getStringCellValue() );
//			usuario.setSituacaoResponsaveis( (Situacao_dos_Pais==null) ? "" : Situacao_dos_Pais.getStringCellValue() );
//			usuario.setSituacaoResponsaveisOutros( (Situacao_dos_Pais_Outros==null) ? "" : Situacao_dos_Pais_Outros.getStringCellValue() );			
			
			if (strCamposCorretos.isEmpty()) {
				strStatusAddUsuario = UsuarioServer.AdicionarUsuario(usuario);
			}else{
				strStatusAddUsuario = strCamposCorretos;
			}
			
			if(!strStatusAddUsuario.equals("true")){				
				
				UsuarioErroImportar usuarioErroImportar = new UsuarioErroImportar();
				
				usuarioErroImportar.setIdTipoUsuario(usuario.getIdTipoUsuario());
				usuarioErroImportar.setTipoUsuario(usuario.getTipoUsuario());
				usuarioErroImportar.setPrimeiroNome(usuario.getPrimeiroNome());
				usuarioErroImportar.setSobreNome(usuario.getSobreNome());
				usuarioErroImportar.setEmail(usuario.getEmail());
				usuarioErroImportar.setLogin(usuario.getLogin());
				usuarioErroImportar.setErroImportar(strStatusAddUsuario);
				
				arrayUsuarioError.add(usuarioErroImportar);

			}

		}
		
		return arrayUsuarioError;

	}

	public static ArrayList<UsuarioErroImportar> importarPais(Sheet sheet){
	    

		
		ArrayList<UsuarioErroImportar> arrayUsuarioError = new ArrayList<UsuarioErroImportar>();
		 
	    Row row;
		
	    System.out.println("Pais=====================");
		System.out.println("FirstRowNum:"+sheet.getFirstRowNum());		
		System.out.println("PhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
		
		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
			
			String strStatusAddUsuario= "true";
			String strCamposCorretos = "";
			
			row = sheet.getRow(i);
			int cv=0;
//			Cell Registro_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Data_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Registro_Aluno = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Primeiro_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Sobre_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Email = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Usuario = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Senha = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Data_Nascimento = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			String strDataNascimento = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
			Cell Sexo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell TipoPais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Endereco = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Num_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Bairro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Cidade = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Estado = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Cep = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Celular = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Comercial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell CPF = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell RG = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Empresa = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Cargo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell RespAcademico = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell RespFinanceiro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Situacao_dos_Pais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Situacao_dos_Pais_Outros = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);			
			
			
//			Registro_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
//			Data_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
//			Registro_Aluno.setCellType(Cell.CELL_TYPE_STRING);	
			Primeiro_Nome.setCellType(Cell.CELL_TYPE_STRING);	
			Sobre_Nome.setCellType(Cell.CELL_TYPE_STRING);	
			Email.setCellType(Cell.CELL_TYPE_STRING);	
			Usuario.setCellType(Cell.CELL_TYPE_STRING);	
			Senha.setCellType(Cell.CELL_TYPE_STRING);	
//			Data_Nascimento.setCellType(Cell.CELL_TYPE_STRING);	
			Sexo.setCellType(Cell.CELL_TYPE_STRING);	
			TipoPais.setCellType(Cell.CELL_TYPE_STRING);
			Endereco.setCellType(Cell.CELL_TYPE_STRING);	
			Num_Residencial.setCellType(Cell.CELL_TYPE_STRING);	
			Bairro.setCellType(Cell.CELL_TYPE_STRING);
			Cidade.setCellType(Cell.CELL_TYPE_STRING);
			Estado.setCellType(Cell.CELL_TYPE_STRING);
			Cep.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Celular.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Residencial.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Comercial.setCellType(Cell.CELL_TYPE_STRING);
			CPF.setCellType(Cell.CELL_TYPE_STRING);
			RG.setCellType(Cell.CELL_TYPE_STRING);
			Empresa.setCellType(Cell.CELL_TYPE_STRING);
			Cargo.setCellType(Cell.CELL_TYPE_STRING);
			RespAcademico.setCellType(Cell.CELL_TYPE_STRING);
			RespFinanceiro.setCellType(Cell.CELL_TYPE_STRING);
			
//			Situacao_dos_Pais.setCellType(Cell.CELL_TYPE_STRING);
//			Situacao_dos_Pais_Outros.setCellType(Cell.CELL_TYPE_STRING);
			
			Usuario usuario = new Usuario();
			
			String senha="";				
			if((Senha==null)|| (Senha.getStringCellValue().isEmpty())){
				senha = ConfigJornada.getProperty("config.senha.inicial");
			}
			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
			
			
			if(Primeiro_Nome.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Primeiro Nome é obrigatório || ";
			}
			if(Sobre_Nome.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Sobre Nome é obrigatório || ";
			}
			if(Email.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Email é obrigatório || ";
			}
			if(Usuario.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Usuario é obrigatório || ";
			}
			

			boolean isRespAcademico=false;
			if(RespAcademico.getStringCellValue().equals("Sim")){
				isRespAcademico=true;
			}

			boolean isRespFinanceiro=false;
			if(RespFinanceiro.getStringCellValue().equals("Sim")){
				isRespFinanceiro=true;
			}

//			String strDataMatricula = Data_Matricula.getStringCellValue();
//			Date dataMatricula = MpUtilServer.convertStringToDate(strDataMatricula,"dd/MM/yyyy");
//			String strValidateDataMatricula = MpUtilServer.isThisDateValid(strDataMatricula, "dd/MM/yyyy");
//			if(!strValidateDataMatricula.equals("true")){
//				strCamposCorretos+="Data Matricula "+strValidateDataMatricula +" || ";
//			}
			
			
			Date dataNascimento = MpUtilServer.convertStringToDate(strDataNascimento,"dd/MM/yyyy");
			String strValidateDataNascimento = MpUtilServer.isThisDateValid(strDataNascimento, "dd/MM/yyyy");
			if(!strValidateDataNascimento.equals("true")){
				strCamposCorretos+="Data Matricula "+strValidateDataNascimento +" || ";
			}
			
			TipoUsuario tipoUsuario = UsuarioServer.getTipoUsuarioPorId(TipoUsuario.PAIS); 
			
			usuario.setIdTipoUsuario(tipoUsuario.getIdTipoUsuario());
			usuario.setTipoUsuario(tipoUsuario);
//			usuario.setRegistroMatricula( (Registro_Matricula==null) ? "" : Registro_Aluno.getStringCellValue() );	
//			usuario.setDataMatricula(dataMatricula);//(Formato : 25/12/2010)
//			usuario.setRegistroAluno( (Registro_Aluno==null) ? "" : Registro_Aluno.getStringCellValue() );	
			usuario.setPrimeiroNome( (Primeiro_Nome==null) ? "" : Primeiro_Nome.getStringCellValue() );	
			usuario.setSobreNome( (Sobre_Nome==null) ? "" : Sobre_Nome.getStringCellValue() );	
			usuario.setEmail( (Email==null) ? "" : Email.getStringCellValue() );	
			usuario.setLogin( (Usuario==null) ? "" : Usuario.getStringCellValue() );	
			usuario.setSenha(senhaHashed);	
			usuario.setDataNascimento(dataNascimento);// (Formato : 25/12/2010)	
			usuario.setSexo( (Sexo==null) ? "" : Sexo.getStringCellValue() );	
			usuario.setTipoPais( (TipoPais==null) ? "" : TipoPais.getStringCellValue() );
			usuario.setEndereco( (Endereco==null) ? "" : Endereco.getStringCellValue() );	
			usuario.setNumeroResidencia( (Num_Residencial==null) ? "" : Num_Residencial.getStringCellValue() );	
			usuario.setBairro( (Bairro==null) ? "" : Bairro.getStringCellValue() );
			usuario.setCidade( (Cidade==null) ? "" : Cidade.getStringCellValue() );
			usuario.setUnidadeFederativa( (Estado==null) ? "" : Estado.getStringCellValue());
			usuario.setCep( (Cep==null) ? "" : Cep.getStringCellValue() );
			usuario.setTelefoneCelular( (Tel_Celular==null) ? "" : Tel_Celular.getStringCellValue() );
			usuario.setTelefoneResidencial( (Tel_Residencial==null) ? "" : Tel_Residencial.getStringCellValue() );
			usuario.setTelefoneComercial( (Tel_Comercial==null) ? "" : Tel_Comercial.getStringCellValue() );
			usuario.setCpf( (CPF==null) ? "" : CPF.getStringCellValue() );
			usuario.setRg( (RG==null) ? "" : RG.getStringCellValue() );
			usuario.setEmpresaOndeTrabalha( (Empresa==null) ? "" : Empresa.getStringCellValue() );
			usuario.setCargo( (Cargo==null)? "" : Cargo.getStringCellValue() );
			usuario.setRespAcademico(isRespAcademico);
			usuario.setRespFinanceiro(isRespFinanceiro);
			
//			usuario.setSituacaoResponsaveis( (Situacao_dos_Pais==null) ? "" : Situacao_dos_Pais.getStringCellValue() );
//			usuario.setSituacaoResponsaveisOutros( (Situacao_dos_Pais_Outros==null) ? "" : Situacao_dos_Pais_Outros.getStringCellValue() );			
			
			if (strCamposCorretos.isEmpty()) {
				strStatusAddUsuario = UsuarioServer.AdicionarUsuario(usuario);
			}else{
				strStatusAddUsuario = strCamposCorretos;
			}
			
			if(!strStatusAddUsuario.equals("true")){				
				
				UsuarioErroImportar usuarioErroImportar = new UsuarioErroImportar();
				
				usuarioErroImportar.setIdTipoUsuario(usuario.getIdTipoUsuario());
				usuarioErroImportar.setTipoUsuario(usuario.getTipoUsuario());
				usuarioErroImportar.setPrimeiroNome(usuario.getPrimeiroNome());
				usuarioErroImportar.setSobreNome(usuario.getSobreNome());
				usuarioErroImportar.setEmail(usuario.getEmail());
				usuarioErroImportar.setLogin(usuario.getLogin());
				usuarioErroImportar.setErroImportar(strStatusAddUsuario);
				
				arrayUsuarioError.add(usuarioErroImportar);

			}

		}
		
		return arrayUsuarioError;

	}

	public static ArrayList<UsuarioErroImportar> importarProfessor(Sheet sheet){
	    
		
		
		ArrayList<UsuarioErroImportar> arrayUsuarioError = new ArrayList<UsuarioErroImportar>();
		 
	    Row row;
		
	    System.out.println("Professor=====================");
	    System.out.println("FirstRowNum:"+sheet.getFirstRowNum());		
		System.out.println("PhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
		
		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
			
			String strStatusAddUsuario= "true";
			String strCamposCorretos = "";
			
			row = sheet.getRow(i);
			int cv=0;
//			Cell Registro_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Data_Matricula = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Registro_Aluno = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Primeiro_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Sobre_Nome = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Email = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Usuario = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Senha = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Data_Nascimento = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			String strDataNascimento = normalizeCellType(row.getCell(cv++, Row.CREATE_NULL_AS_BLANK));
			Cell Sexo = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Endereco = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Num_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Bairro = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Cidade = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Estado = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Cep = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Celular = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Residencial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell Tel_Comercial = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell CPF = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
			Cell RG = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Situacao_dos_Pais = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);
//			Cell Situacao_dos_Pais_Outros = row.getCell(cv++, Row.CREATE_NULL_AS_BLANK);			
			
			
//			Registro_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
//			Data_Matricula.setCellType(Cell.CELL_TYPE_STRING);	
//			Registro_Aluno.setCellType(Cell.CELL_TYPE_STRING);	
			Primeiro_Nome.setCellType(Cell.CELL_TYPE_STRING);	
			Sobre_Nome.setCellType(Cell.CELL_TYPE_STRING);	
			Email.setCellType(Cell.CELL_TYPE_STRING);	
			Usuario.setCellType(Cell.CELL_TYPE_STRING);	
			Senha.setCellType(Cell.CELL_TYPE_STRING);	
//			Data_Nascimento.setCellType(Cell.CELL_TYPE_STRING);	
			Sexo.setCellType(Cell.CELL_TYPE_STRING);	
			Endereco.setCellType(Cell.CELL_TYPE_STRING);	
			Num_Residencial.setCellType(Cell.CELL_TYPE_STRING);	
			Bairro.setCellType(Cell.CELL_TYPE_STRING);
			Cidade.setCellType(Cell.CELL_TYPE_STRING);
			Estado.setCellType(Cell.CELL_TYPE_STRING);
			Cep.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Celular.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Residencial.setCellType(Cell.CELL_TYPE_STRING);
			Tel_Comercial.setCellType(Cell.CELL_TYPE_STRING);
			CPF.setCellType(Cell.CELL_TYPE_STRING);
			RG.setCellType(Cell.CELL_TYPE_STRING);
//			Situacao_dos_Pais.setCellType(Cell.CELL_TYPE_STRING);
//			Situacao_dos_Pais_Outros.setCellType(Cell.CELL_TYPE_STRING);
			
			Usuario usuario = new Usuario();
			
			String senha="";				
			if((Senha==null)|| (Senha.getStringCellValue().isEmpty())){
				senha = ConfigJornada.getProperty("config.senha.inicial");
			}
			String senhaHashed = BCrypt.hashpw(senha, BCrypt.gensalt());
			
			
			if(Primeiro_Nome.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Primeiro Nome é obrigatório || ";
			}
			if(Sobre_Nome.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Sobre Nome é obrigatório || ";
			}
			if(Email.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Email é obrigatório || ";
			}
			if(Usuario.getStringCellValue().isEmpty()){
				strCamposCorretos+="Campo Usuario é obrigatório || ";
			}			
			


//			String strDataMatricula = Data_Matricula.getStringCellValue();
//			Date dataMatricula = MpUtilServer.convertStringToDate(strDataMatricula,"dd/MM/yyyy");
//			String strValidateDataMatricula = MpUtilServer.isThisDateValid(strDataMatricula, "dd/MM/yyyy");
//			if(!strValidateDataMatricula.equals("true")){
//				strCamposCorretos+="Data Matricula "+strValidateDataMatricula +" || ";
//			}
			
			Date dataNascimento = MpUtilServer.convertStringToDate(strDataNascimento,"dd/MM/yyyy");
			String strValidateDataNascimento = MpUtilServer.isThisDateValid(strDataNascimento, "dd/MM/yyyy");
			if(!strValidateDataNascimento.equals("true")){
				strCamposCorretos+="Data Matricula "+strValidateDataNascimento +" || ";
			}
			
			TipoUsuario tipoUsuario = UsuarioServer.getTipoUsuarioPorId(TipoUsuario.COORDENADOR); 
			
			usuario.setIdTipoUsuario(tipoUsuario.getIdTipoUsuario());
			usuario.setTipoUsuario(tipoUsuario);
//			usuario.setRegistroMatricula( (Registro_Matricula==null) ? "" : Registro_Aluno.getStringCellValue() );	
//			usuario.setDataMatricula(dataMatricula);//(Formato : 25/12/2010)
//			usuario.setRegistroAluno( (Registro_Aluno==null) ? "" : Registro_Aluno.getStringCellValue() );	
			usuario.setPrimeiroNome( (Primeiro_Nome==null) ? "" : Primeiro_Nome.getStringCellValue() );	
			usuario.setSobreNome( (Sobre_Nome==null) ? "" : Sobre_Nome.getStringCellValue() );	
			usuario.setEmail( (Email==null) ? "" : Email.getStringCellValue() );	
			usuario.setLogin( (Usuario==null) ? "" : Usuario.getStringCellValue() );	
			usuario.setSenha(senhaHashed);	
			usuario.setDataNascimento(dataNascimento);// (Formato : 25/12/2010)	
			usuario.setSexo( (Sexo==null) ? "" : Sexo.getStringCellValue() );	
			usuario.setEndereco( (Endereco==null) ? "" : Endereco.getStringCellValue() );	
			usuario.setNumeroResidencia( (Num_Residencial==null) ? "" : Num_Residencial.getStringCellValue() );	
			usuario.setBairro( (Bairro==null) ? "" : Bairro.getStringCellValue() );
			usuario.setCidade( (Cidade==null) ? "" : Cidade.getStringCellValue() );
			usuario.setUnidadeFederativa( (Estado==null) ? "" : Estado.getStringCellValue());
			usuario.setCep( (Cep==null) ? "" : Cep.getStringCellValue() );
			usuario.setTelefoneCelular( (Tel_Celular==null) ? "" : Tel_Celular.getStringCellValue() );
			usuario.setTelefoneResidencial( (Tel_Residencial==null) ? "" : Tel_Residencial.getStringCellValue() );
			usuario.setTelefoneComercial( (Tel_Comercial==null) ? "" : Tel_Comercial.getStringCellValue() );
			usuario.setCpf( (CPF==null) ? "" : CPF.getStringCellValue() );
			usuario.setRg( (RG==null) ? "" : RG.getStringCellValue() );
//			usuario.setSituacaoResponsaveis( (Situacao_dos_Pais==null) ? "" : Situacao_dos_Pais.getStringCellValue() );
//			usuario.setSituacaoResponsaveisOutros( (Situacao_dos_Pais_Outros==null) ? "" : Situacao_dos_Pais_Outros.getStringCellValue() );			
			
			if (strCamposCorretos.isEmpty()) {
				strStatusAddUsuario = UsuarioServer.AdicionarUsuario(usuario);
			}else{
				strStatusAddUsuario = strCamposCorretos;
			}
			
			if(!strStatusAddUsuario.equals("true")){				
				
				UsuarioErroImportar usuarioErroImportar = new UsuarioErroImportar();
				
				usuarioErroImportar.setIdTipoUsuario(usuario.getIdTipoUsuario());
				usuarioErroImportar.setTipoUsuario(usuario.getTipoUsuario());
				usuarioErroImportar.setPrimeiroNome(usuario.getPrimeiroNome());
				usuarioErroImportar.setSobreNome(usuario.getSobreNome());
				usuarioErroImportar.setEmail(usuario.getEmail());
				usuarioErroImportar.setLogin(usuario.getLogin());
				usuarioErroImportar.setErroImportar(strStatusAddUsuario);
				
				arrayUsuarioError.add(usuarioErroImportar);

			}

		}
		
		return arrayUsuarioError;

	}

	
	public static ArrayList<Usuario> getUsuarios() {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ALL);
			
			
			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	

	public static ArrayList<Usuario> getUsuarios(String strFilter) {

		ArrayList<Usuario> userList = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ILIKE);
			
			int count=0;
			ps.setString(++count, strFilter);
			
			userList = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return userList;

	}	
	
	public static ArrayList<Usuario> getUsuarios(String strDBField, String strFilter) {

		ArrayList<Usuario> userList = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();

		try 
		{
			
			String strQuery = UsuarioServer.DB_SELECT_DB_FIELD_ILIKE;
			strQuery = strQuery.replace("<change>", strDBField);

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(strQuery);
			
			int count=0;
//			ps.setString(++count, strDBField);
			ps.setString(++count, strFilter);
			
			userList = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return userList;

	}		
	
//	public static Usuario getUsuarioPeloLogin(String strLogin) {
//
//		ArrayList<Usuario> userList = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
//
//		try 
//		{
//
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
//			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_LOGIN);
//			
//			int count=0;
//			ps.setString(++count, strLogin);
//			
//			userList = getUserParameters(ps.executeQuery());
//
//		} catch (SQLException sqlex) {
//			System.err.println(sqlex.getMessage());
//		} finally {
////			dataBase.close();
//		}
//
//		return userList.get(0);
//
//	}		
	
	public static Usuario getUsuarioPeloLogin(String strLogin) {

		ArrayList<Usuario> userList = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();

		Connection connection = ConnectionManager.getConnection();
		try 
		{
	
			
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_LOGIN);
			
			int count=0;
			ps.setString(++count, strLogin);
			
			userList = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(connection);
//			dataBase.close();
		}

		return userList.get(0);

	}		
	
	public static Usuario getUsuarioPeloId(int idUsuario) {

		ArrayList<Usuario> userList = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_ID);
			
			int count=0;
			ps.setInt(++count, idUsuario);
			
			userList = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}
		
		if(userList.size()>0){
			return userList.get(0);
		}
		else
		{
			return null;
		}


	}		
	
	public static ArrayList<Usuario> getAlunosPorCurso(int idCurso, String strFiltroUsuario) {
		
		ArrayList<Usuario> userList = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
			
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ILIKE_FILTRADO_POR_CURSO);
			
			int count=0;
			ps.setInt(++count, idCurso);
			ps.setString(++count, strFiltroUsuario);
			ps.setString(++count, strFiltroUsuario);
			
			userList = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return userList;

	}		
		
	public static ArrayList<Usuario> getAlunosPorCurso(int idCurso) {
		
		ArrayList<Usuario> userList = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
			
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_FILTRADO_POR_CURSO);
			
			int count=0;
			ps.setInt(++count, idCurso);
			
			userList = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return userList;

	}		
	
	public static ArrayList<Usuario> getUsuariosPorCursoAmbientePai(Usuario usuarioPai, int idCurso) {
		
		ArrayList<Usuario> userList = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
			
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ALUNO_FILTRADO_POR_CURSO_AMBIENTE_PAI);
			
			int count=0;
			ps.setInt(++count, idCurso);
			ps.setInt(++count, usuarioPai.getIdUsuario());
			
			userList = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return userList;

	}		
	
	public static ArrayList<Usuario> getUsuariosPorOcorrencia(int idOcorrencia) {
		
		ArrayList<Usuario> userList = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
			
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_FILTRADO_POR_OCORRENCIA);
			
			int count=0;
			ps.setInt(++count, idOcorrencia);
			
			userList = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return userList;

	}				
	
	public static ArrayList<Usuario> getUsuariosPorTipoUsuario(int id_tipo_usuario, String strFilter) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ILIKE_TIPO_USUARIO);
			
			int count=0;
			ps.setInt(++count, id_tipo_usuario);
			ps.setString(++count, strFilter);
			ps.setString(++count, strFilter);			
			
			data = getUserParameters(ps.executeQuery());
			
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
	public static ArrayList<Usuario> getUsuariosPorTipoUsuario(int id_tipo_usuario) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_PELO_TIPO_USUARIO);
			
			int count=0;
			ps.setInt(++count, id_tipo_usuario);
				
			
			data = getUserParameters(ps.executeQuery());
			
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}		
	
	
	public static ArrayList<Usuario> getFilhoDoPaiAmbientePais(Usuario usuarioPai) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_USUARIO_PELO_TIPO_USUARIO_AMBIENTE_PAI);
			
			int count=0;
			ps.setInt(++count, TipoUsuario.ALUNO);
			ps.setInt(++count, usuarioPai.getIdUsuario());
				
			
			data = getUserParameters(ps.executeQuery());
			
		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}			
	
	public static ArrayList<TipoUsuario> getTipoUsuarios() {

		ArrayList<TipoUsuario> data = new ArrayList<TipoUsuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{

//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();

			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ALL_TIPO_USUARIOS);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				TipoUsuario current = new TipoUsuario();

				current.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				current.setNomeTipoUsuario(rs.getString("nome_tipo_usuario"));

				data.add(current);
			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}	
	
	public static TipoUsuario getTipoUsuario(String nomeTipoUsuario) {

		TipoUsuario current=null;

		Connection connection = ConnectionManager.getConnection();
		try 
		{

			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_TIPO_USUARIOS_POR_NOME);

			ps.setString(1, nomeTipoUsuario);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				 current = new TipoUsuario();

				current.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				current.setNomeTipoUsuario(rs.getString("nome_tipo_usuario"));

			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(connection);
		}

		return current;

	}	
	
	public static TipoUsuario getTipoUsuarioPorId(int idTipoUsuario) {

		TipoUsuario current=null;

		Connection connection = ConnectionManager.getConnection();
		try 
		{

			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_TIPO_USUARIOS_POR_ID);

			ps.setInt(1, idTipoUsuario);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				 current = new TipoUsuario();

				current.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
				current.setNomeTipoUsuario(rs.getString("nome_tipo_usuario"));

			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			ConnectionManager.closeConnection(connection);
		}

		return current;

	}
		
	public static ArrayList<Usuario> getUserParameters(ResultSet rs){

		ArrayList<Usuario> data = new ArrayList<Usuario>();
		
		try{
		
		while (rs.next()) 
		{
			Usuario usuario = new Usuario();			
			usuario.setIdUsuario(rs.getInt("id_usuario"));
			usuario.setPrimeiroNome(rs.getString("primeiro_nome"));
			usuario.setSobreNome(rs.getString("sobre_nome"));
			usuario.setCpf(rs.getString("cpf"));
			usuario.setDataNascimento(rs.getDate("data_nascimento"));
			usuario.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
			usuario.setEmail(rs.getString("email"));
			usuario.setTelefoneCelular(rs.getString("telefone_celular"));
			usuario.setTelefoneResidencial(rs.getString("telefone_residencial"));
			usuario.setTelefoneComercial(rs.getString("telefone_comercial"));
			usuario.setLogin(rs.getString("login"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setPrimeiroLogin(rs.getBoolean("primeiro_login"));
			usuario.setEndereco(rs.getString("endereco"));
			usuario.setNumeroResidencia(rs.getString("numero_residencia"));
			usuario.setBairro(rs.getString("bairro"));
			usuario.setCidade(rs.getString("cidade")); 
			usuario.setUnidadeFederativa(rs.getString("unidade_federativa"));   
			usuario.setCep(rs.getString("cep"));  
			usuario.setDataMatricula(rs.getDate("data_matricula"));  
			usuario.setRg(rs.getString("rg"));   
			usuario.setSexo(rs.getString("sexo"));   
			usuario.setEmpresaOndeTrabalha(rs.getString("empresa_onde_trabalha"));   
			usuario.setCargo(rs.getString("cargo"));   
			usuario.setRespAcademico(rs.getBoolean("resp_academico"));   
			usuario.setRespFinanceiro(rs.getBoolean("resp_financeiro"));   
			usuario.setRegistroMatricula(rs.getString("registro_matricula"));   
			usuario.setTipoPais(rs.getString("tipo_pais"));   
			usuario.setSituacaoResponsaveis(rs.getString("situacao_responsaveis"));   
			usuario.setSituacaoResponsaveisOutros(rs.getString("situacao_responsaveis_outros"));   

			
			
			usuario.setIdIdioma((rs.getInt("id_idioma")==0)? 1 : rs.getInt("id_idioma"));
			usuario.getTipoUsuario().setIdTipoUsuario(usuario.getIdTipoUsuario());
			try {
				usuario.getTipoUsuario().setNomeTipoUsuario((rs.getString("nome_tipo_usuario") == null) ? null: rs.getString("nome_tipo_usuario"));
			}catch (Exception ex) {
				usuario.getTipoUsuario().setNomeTipoUsuario(null);
			}
			
			try{
				usuario.setIdioma(IdiomaServer.getIdioma(usuario.getIdIdioma()));
			}catch (Exception ex) {
				usuario.setIdioma(null);
			}

			data.add(usuario);
		}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}
		
		return data;

	}
	
	public static boolean associarPaisAoAluno(int id_aluno, ArrayList<String> list_id_pais){

		boolean success = false;

//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try {
//			dataBase.createConnection();
//			Connection connection = dataBase.getConnection();

			deleteAssociacaoPaisAoAluno(connection, id_aluno);
			insertAssociacaoPaisAoAluno(connection, id_aluno, list_id_pais);
			success=true;

		} catch (Exception ex) {
			success = false;
			System.err.println(ex.getMessage());
		} finally {
			// dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return success;
	}		
	
	private static int deleteAssociacaoPaisAoAluno(Connection conn, int id_aluno){
		
		int count = 0;
		int deleted = 0;

		try {
			
			Connection connection = conn;

			PreparedStatement psDelete = connection.prepareStatement(UsuarioServer.DB_DELETE_REL_PAI_ALUNO);
			psDelete.setInt(++count, id_aluno);
			deleted = psDelete.executeUpdate();

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
			
		}
		
		return deleted;
		
	}	
	
	private static int insertAssociacaoPaisAoAluno(Connection conn, int id_aluno, ArrayList<String> list_id_pais){
		
		int count = 0;
		int intAddedItems = 0;

		try {
			
			Connection connection = conn;
			
			//"INSERT INTO rel_pai_aluno (id_usuario_pais, id_usuario_aluno) VALUES (?,?)";	

			for(int i=0;i<list_id_pais.size();i++){
				count=0;
				int id_usuario_pais = Integer.parseInt(list_id_pais.get(i));
				PreparedStatement psInsert = connection.prepareStatement(UsuarioServer.DB_INSERT_REL_PAI_ALUNO);
				psInsert.setInt(++count, id_usuario_pais);				
				psInsert.setInt(++count, id_aluno);	
				intAddedItems=psInsert.executeUpdate();					

			}

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
			// dataBase.close();
		}
		
		return intAddedItems;
		
	}		
		
	public static ArrayList<Usuario> getTodosOsPaisDoAluno(int id_aluno) {

		ArrayList<Usuario> data = new ArrayList<Usuario>();
//		JornadaDataBase dataBase = new JornadaDataBase();
		Connection connection = ConnectionManager.getConnection();
		try 
		{
//			dataBase.createConnection();			
//			Connection connection = dataBase.getConnection();
			PreparedStatement ps = connection.prepareStatement(UsuarioServer.DB_SELECT_ALL_PAIS_DO_ALUNO);
			
			int count=0;
			ps.setInt(++count, id_aluno);

			data = getUserParameters(ps.executeQuery());

		} catch (SQLException sqlex) {
			System.err.println(sqlex.getMessage());
		} finally {
//			dataBase.close();
			ConnectionManager.closeConnection(connection);
		}

		return data;

	}		

	

}

