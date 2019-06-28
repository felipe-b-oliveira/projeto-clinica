package conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.dao.ConsultaDAO;
import modelo.dominio.Consulta;

@FacesConverter(forClass=Consulta.class)
public class ConsultaConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Integer id = null;
		try {
			id = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			id = null;
		}
		
		if (id != null)
		{
			ConsultaDAO dao = new ConsultaDAO();
			
			Consulta consult = dao.lerPorId(id);
			return consult;
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value instanceof Consulta)
		{
			Consulta consult = (Consulta) value;
			return consult.toString();
		}
		
		return null;
	}

}
