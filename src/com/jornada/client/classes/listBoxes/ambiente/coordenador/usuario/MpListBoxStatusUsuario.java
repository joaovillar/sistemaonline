package com.jornada.client.classes.listBoxes.ambiente.coordenador.usuario;

import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.shared.classes.TipoStatusUsuario;
import com.jornada.shared.classes.TipoUsuario;

public class MpListBoxStatusUsuario extends MpSelection {
    
   
    public void showItems(int idTipoUsuario){
        clear();
        if(TipoUsuario.ALUNO==idTipoUsuario){
            addItem("Ativo(a)", Integer.toString(TipoStatusUsuario.ALUNO_ATIVO));
            addItem("Transferido(a)", Integer.toString(TipoStatusUsuario.ALUNO_TRANSFERIDO));
            addItem("Concluído(a)", Integer.toString(TipoStatusUsuario.ALUNO_CONCLUIDO));            
        }else if(TipoUsuario.COORDENADOR==idTipoUsuario){
            addItem("Ativo(a)", Integer.toString(TipoStatusUsuario.COORDENADOR_ATIVO));
            addItem("Desativado(a)", Integer.toString(TipoStatusUsuario.COORDENADOR_DESATIVADO));           
        }else if(TipoUsuario.PROFESSOR==idTipoUsuario){
            addItem("Ativo(a)", Integer.toString(TipoStatusUsuario.PROFESSOR_ATIVO));
            addItem("Desativado(a)", Integer.toString(TipoStatusUsuario.PROFESSOR_DESATIVADO));       
        }else if(TipoUsuario.PAIS==idTipoUsuario){
            addItem("Ativo(a)", Integer.toString(TipoStatusUsuario.PAIS_ATIVO));
            addItem("Desativado(a)", Integer.toString(TipoStatusUsuario.PAIS_DESATIVADO));    
        }else if(TipoUsuario.ADMINISTRADOR==idTipoUsuario){
            addItem("Ativo(a)", Integer.toString(TipoStatusUsuario.ADMINISTRADOR_ATIVO));
            addItem("Desativado(a)", Integer.toString(TipoStatusUsuario.ADMINISTRADOR_DESATIVADO));      
        }      
        
    }

}
