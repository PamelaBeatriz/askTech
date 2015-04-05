package asktechforum.dominio;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;

import asktechforum.util.Util;

/**
 * Classe que representa uma Pergunta 
 */
public class Pergunta {
	private int idPergunta;
	private String titulo;
	private String descricao;
	private int idUsuario;
	private String strData;
	private String strHora;
	private String tag;
	private ArrayList<String> listTags;
	private Date data;
	private Time hora;
	
	
	public Pergunta() {
		
	}
	
	//GETTERS AND SETTERS
	public int getIdPergunta() {
		return idPergunta;
	}
	public void setIdPergunta(int idPergunta) {
		this.idPergunta = idPergunta;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getStrData() {
		strData = Util.converterDataToString("dd/MM/yyyy", data);
		return strData;
	}

	public void setStrData(String strData){
		
		try {
			this.data = Util.converterStringToDate("dd/MM/yyyy", strData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getStrHora() {
		strHora = Util.converterTimeToString("hh:mm", hora);
		return strHora;
	}

	public void setStrHora(String strHora)  {
		try {
			this.hora = Util.converterStringToTime("hh:mm", strHora);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public java.sql.Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
		this.listTags = this.separaTags(tag);
	}

	public ArrayList<String> getListTags() {
		return listTags;
	}

	public void setListTags(ArrayList<String> listTags) {
		this.listTags = listTags;
	}
	
	/**
	 * Tratamento específico para separar as tags numa lista de tags.
	 * @param tag
	 * @return
	 */
	private ArrayList<String> separaTags(String tag){
		ArrayList<String> retorno = new ArrayList<String>();
		if(tag != null) {
			String[] tagArray = tag.split(" ");
			for(int i=0; i<tagArray.length; i++){
				retorno.add(tagArray[i]);
			}
		}
		return retorno;
	}

}
