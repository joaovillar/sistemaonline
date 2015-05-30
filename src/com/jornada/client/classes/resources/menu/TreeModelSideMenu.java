package com.jornada.client.classes.resources.menu;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.jornada.shared.classes.ConteudoProgramatico;
import com.jornada.shared.classes.Curso;
import com.jornada.shared.classes.Disciplina;
import com.jornada.shared.classes.Periodo;
import com.jornada.shared.classes.Topico;

public class TreeModelSideMenu implements TreeViewModel{
	
    
    private final SelectionModel<Object> selectionModelObject;

    
    
  
    
    public TreeModelSideMenu(SelectionModel<Object> selectionModelObject) {

    	this.selectionModelObject = selectionModelObject;
     }


    public <T> NodeInfo<?> getNodeInfo(T value) {

	if (value == null) {
		
		ListDataProvider<String> dataProvider = new ListDataProvider<String>();

		Cell<String> cell = new AbstractCell<String>() {
			@Override
			public void render(Context context, String value, SafeHtmlBuilder sb) {
				if (value != null) {
					sb.appendHtmlConstant("<img style='vertical-align: bottom;' src='images/curso.png'>&nbsp;");
					sb.appendEscaped(value);
				}
			}
		};
		return new DefaultNodeInfo<String>(dataProvider, cell, selectionModelObject, null);

	} else if (value instanceof Curso) {

		ListDataProvider<Periodo> dataProvider = new ListDataProvider<Periodo>(((Curso)value).getListPeriodos());

		Cell<Periodo> cell = new AbstractCell<Periodo>() {
			@Override
			public void render(Context context, Periodo value, SafeHtmlBuilder sb) {
				if (value != null) {
					sb.appendHtmlConstant("<img style='vertical-align: bottom;' src='images/my_projects_folder_16.png'>&nbsp;");
					sb.appendEscaped(value.getNomePeriodo());
				}
			}
		};

		return new DefaultNodeInfo<Periodo>(dataProvider, cell, selectionModelObject, null);

	} 
	else if (value instanceof Periodo) {
		
		ListDataProvider<Disciplina> dataProvider = new ListDataProvider<Disciplina>(((Periodo)value).getListDisciplinas());
		
		Cell<Disciplina> cell = new AbstractCell<Disciplina>() {
			@Override
			public void render(Context context, Disciplina value, SafeHtmlBuilder sb) {
				if (value != null) {
					sb.appendHtmlConstant( "<img style='vertical-align: bottom;' src='images/disciplina.png'>&nbsp;");
					sb.appendEscaped(value.getNome());
				}
			}
			
		};

		return new DefaultNodeInfo<Disciplina>(dataProvider, cell, selectionModelObject, null);

	} 
	else if (value instanceof Disciplina) {
		
		ListDataProvider<ConteudoProgramatico> dataProvider = new ListDataProvider<ConteudoProgramatico>(((Disciplina) value).getListConteudoProgramatico());
		
		Cell<ConteudoProgramatico> cell = new AbstractCell<ConteudoProgramatico>() {
			@Override
			public void render(Context context, ConteudoProgramatico value, SafeHtmlBuilder sb) {
				if (value != null) {
//					sb.appendHtmlConstant("<img style='vertical-align: bottom;' src='images/conteudoprogramatico.png'>&nbsp;"+value.getNumeracao()+"&nbsp;:&nbsp;"+value.getNome());
					sb.appendHtmlConstant("<img style='vertical-align: bottom;' src='images/conteudoprogramatico.png'>&nbsp;");
					sb.appendEscaped(value.getNumeracao()+" : "+value.getNome());
				}
			}
		};
		return new DefaultNodeInfo<ConteudoProgramatico>(dataProvider,cell, selectionModelObject, null);

	} 
	else if (value instanceof ConteudoProgramatico) {
		ListDataProvider<Topico> dataProvider = new ListDataProvider<Topico>(((ConteudoProgramatico) value).getListTopico());
		
		Cell<Topico> cell = new AbstractCell<Topico>() {
			@Override
			public void render(Context context, Topico value, SafeHtmlBuilder sb) {
				if (value != null) {
					sb.appendHtmlConstant("<img  style='vertical-align: bottom;' src='images/type_list_16_16_v1.png'>&nbsp;&nbsp;"+""+value.getNumeracao()+"&nbsp;:&nbsp;"+value.getNome());
//					sb.appendHtmlConstant("<img src='images/type_list_16_16_v1.png'>&nbsp;"+value.getNome());
				}
			}
		};
		return new DefaultNodeInfo<Topico>(dataProvider, cell, selectionModelObject, null);
	}

      return null;
    }


    public boolean isLeaf(Object value) {
      // The leaf nodes are the songs, which are Strings.
      if (value instanceof Topico) {
        return true;
      }
      return false;
    }
    
    
    public static final ProvidesKey<Object> KEY_PROVIDER_OBJECT = new ProvidesKey<Object>() {
        @Override
        public Object getKey(Object object) {
        	
        	if(object instanceof Curso){
        		return object == null ? null :   ((Curso)object).toString() + Integer.toString(((Curso)object).getIdCurso());        		
        	}
        	else if(object instanceof Periodo){
        		return object == null ? null : ((Periodo)object).toString() + Integer.toString(((Periodo)object).getIdPeriodo());        		
        	}
        	else if(object instanceof Disciplina){
        		return object == null ? null : ((Disciplina)object).toString() + Integer.toString(((Disciplina)object).getIdDisciplina());        		
        	}
        	else if(object instanceof ConteudoProgramatico){
        		return object == null ? null : ((ConteudoProgramatico)object).toString() + Integer.toString(((ConteudoProgramatico)object).getIdConteudoProgramatico());        		
        	}
        	else if(object instanceof Topico){
        		return object == null ? null : ((Topico)object).toString() + Integer.toString(((Topico)object).getIdTopico());        		
        	}
//        	else{
        		return null;
//        	}
          
        }
      };
	

}
