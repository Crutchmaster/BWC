package com.ric.bill.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * DTO - Компонент формулы платежного поручения
 * @author lev
 *
 */
public class PayordCmpDTO {


	// Здесь важен default конструктор (иначе не примет обратно данные, методом POST)
	public PayordCmpDTO() {
		
	}

	// конструктор
	public PayordCmpDTO(Integer id, String username, Date dtf,
			Integer payordFk, Integer varFk, Integer servFk, Integer orgFk,
			Integer koFk, Integer koExtFk, String koName, String koExtName, 
			String mark, Double summa, String selDays, Integer periodTpFk) {
		super();
		this.id = id;
		this.username = username;
		this.dtf = dtf;
		this.payordFk = payordFk;
		this.varFk = varFk;
		this.servFk = servFk;
		this.orgFk = orgFk;
		this.koFk = koFk;
		this.koExtFk = koExtFk;
		this.koName = koName;
		this.koExtName = koExtName;
		this.mark = mark;
		this.summa = summa;
		this.selDays = selDays;
		this.periodTpFk = periodTpFk;
	}

	private Integer id;

	// Пользователь
	private String username;
	
	// Дата создания
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Krasnoyarsk")
	private Date dtf;
	
	// Платежка
	private Integer payordFk; 
	
	// Вариант сбора информации (например начисление по оборотной ведомости)
	private Integer varFk; 

	// Услуга
	private Integer servFk; 

	// Организация
	private Integer orgFk; 
	
	// Объект (область действия) (например id Города, УК)
	private Integer koFk; 
	
	// Наименование объекта
	private String koName;

	// Доп.Объект (область действия) (например id Дома)
	private Integer koExtFk; 

	// Наименование доп.объекта
	private String koExtName;
	
	// Маркер
	private String mark;

	// Сумма
	private Double summa;

	// Выбранные дни
	private String selDays;
	
	// Fk на тип периода
	private Integer periodTpFk;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDtf() {
		return dtf;
	}

	public void setDtf(Date dtf) {
		this.dtf = dtf;
	}

	public Integer getPayordFk() {
		return payordFk;
	}

	public void setPayordFk(Integer payordFk) {
		this.payordFk = payordFk;
	}

	public Integer getVarFk() {
		return varFk;
	}

	public void setVarFk(Integer varFk) {
		this.varFk = varFk;
	}

	public Integer getServFk() {
		return servFk;
	}

	public void setServFk(Integer servFk) {
		this.servFk = servFk;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getOrgFk() {
		return orgFk;
	}

	public void setOrgFk(Integer orgFk) {
		this.orgFk = orgFk;
	}


	public Integer getKoFk() {
		return koFk;
	}

	public void setKoFk(Integer koFk) {
		this.koFk = koFk;
	}

	public String getKoName() {
		return koName;
	}

	public void setKoName(String nameKo) {
		this.koName = nameKo;
	}

	public Double getSumma() {
		return summa;
	}

	public void setSumma(Double summa) {
		this.summa = summa;
	}

	public Integer getKoExtFk() {
		return koExtFk;
	}

	public void setKoExtFk(Integer koExtFk) {
		this.koExtFk = koExtFk;
	}

	public String getKoExtName() {
		return koExtName;
	}

	public void setKoExtName(String koExtName) {
		this.koExtName = koExtName;
	}

	public String getSelDays() {
		return selDays;
	}

	public void setSelDays(String selDays) {
		this.selDays = selDays;
	}

	public Integer getPeriodTpFk() {
		return periodTpFk;
	}

	public void setPeriodTpFk(Integer periodTpFk) {
		this.periodTpFk = periodTpFk;
	}

}

