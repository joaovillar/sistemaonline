package com.jornada.server.classes;

import java.util.ArrayList;

import com.jornada.shared.classes.Avaliacao;
import com.jornada.shared.classes.ConteudoProgramatico;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Usuario;

public class HierarquiaCursoServer {

	
	public static ArrayList<Curso> getHierarquiaCursos(ArrayList<Curso> listCursos){
		
		for(Curso curso : listCursos){			
			
			//Getting Periodos
			//curso.setListPeriodos(PeriodoServer.getPeriodos(curso.getIdCurso()));
			curso.getListPeriodos().addAll(PeriodoServer.getPeriodos(curso.getIdCurso()));
			curso.getListAlunos().addAll(UsuarioServer.getAlunosPorCurso(curso.getIdCurso()));
			                        
			
			//Getting Disciplinas
			for(Periodo periodo : curso.getListPeriodos()){				
				//periodo.setListDisciplinas(DisciplinaServer.getDisciplinas(periodo.getIdPeriodo()));
				periodo.getListDisciplinas().addAll(DisciplinaServer.getDisciplinas(periodo.getIdPeriodo()));
				
				//Getting ConteudoProgramatico
				for(Disciplina disciplina : periodo.getListDisciplinas()){
					disciplina.setListConteudoProgramatico(ConteudoProgramaticoServer.getConteudoProgramaticos(disciplina.getIdDisciplina()));
					disciplina.setProfessor(UsuarioServer.getUsuarioPeloId(disciplina.getIdUsuario()));

					//Getting Topico e Avaliacao
					for(ConteudoProgramatico conteudo : disciplina.getListConteudoProgramatico()){
						conteudo.setListTopico(TopicoServer.getTopicos(conteudo.getIdConteudoProgramatico()));
						conteudo.setListAvaliacao(AvaliacaoServer.getAvaliacao(conteudo.getIdConteudoProgramatico()));
						//Getting Nota e TipoAvaliacao
						for(Avaliacao avaliacao : conteudo.getListAvaliacao()){
							avaliacao.setListNota(NotaServer.getNotas(avaliacao.getIdAvaliacao()));
							avaliacao.setTipoAvaliacao(AvaliacaoServer.getTipoAvaliacao(avaliacao.getIdTipoAvaliacao()));
						}
					}
				}				
			}
		}
		
		return listCursos;		
	}
	
	public static ArrayList<Curso> getHierarquiaCursos(){		
		return getHierarquiaCursos(CursoServer.getCursos());		
	}	
	
    public static ArrayList<Curso> getHierarquiaCursos(String strFilter){       
        return getHierarquiaCursos(CursoServer.getCursos(strFilter));        
    }   	
	
	public static Curso getHierarquiaCurso(int idCurso){	
		ArrayList<Curso> listCurso = new ArrayList<Curso>();
		listCurso.add(CursoServer.getCurso(idCurso));
		Curso curso = getHierarquiaCursos(listCurso).get(0);
		return curso;	
	}	
	
	public static ArrayList<Curso> getHierarquiaCursosAmbientePais(Usuario usuarioPais){		
		return getHierarquiaCursos(CursoServer.getCursosPorPaiAmbientePais(usuarioPais));		
	}	
	
	public static ArrayList<Curso> getHierarquiaCursosAmbienteAluno(Usuario usuarioAluno){		
		return getHierarquiaCursos(CursoServer.getCursosPorAlunoAmbienteAluno(usuarioAluno));		
	}
	
	public static ArrayList<Curso> getHierarquiaCursosAmbienteProfessor(Usuario usuarioProfessor){		
		return getHierarquiaCursos(CursoServer.getCursosAmbienteProfessor(usuarioProfessor));		
	}		
	
}
