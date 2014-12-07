package com.jornada.client.classes.listBoxes.ambiente.coordenador.usuario;

import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.shared.classes.TipoUsuario;

public class MpListBoxStatusUsuario extends MpSelection {
    
//    private static MpListBoxStatusUsuario uniqueInstance;
//    
//    public static MpListBoxStatusUsuario getInstance(int idTipoUsuario){
//        
//        if(uniqueInstance==null){
//            uniqueInstance= new MpListBoxStatusUsuario();
//            uniqueInstance.showItems(idTipoUsuario);            
//        }else{
//            uniqueInstance.clear();
//            uniqueInstance.showItems(idTipoUsuario);     
//        }
//        
//        return uniqueInstance;
//    }
    
   
    public void showItems(int idTipoUsuario){
        clear();
        if(TipoUsuario.ALUNO==idTipoUsuario){
            addItem("Ativo(a)", "1");
            addItem("Transferido(a)", "2");
            addItem("Concluído(a)", "3");            
        }else if(TipoUsuario.COORDENADOR==idTipoUsuario){
            addItem("Ativo(a)", "4");
            addItem("Desativado(a)", "5");           
        }else if(TipoUsuario.PROFESSOR==idTipoUsuario){
            addItem("Ativo(a)", "6");
            addItem("Desativado(a)", "7");       
        }else if(TipoUsuario.PAIS==idTipoUsuario){
            addItem("Ativo(a)", "8");
            addItem("Desativado(a)", "9");    
        }else if(TipoUsuario.ADMINISTRADOR==idTipoUsuario){
            addItem("Ativo(a)", "10");
            addItem("Desativado(a)", "11");      
        }      
        
    }

}
