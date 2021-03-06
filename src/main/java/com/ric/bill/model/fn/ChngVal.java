package com.ric.bill.model.fn;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.ric.bill.Simple;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Par;
import com.ric.bill.model.mt.Meter;
import com.ric.bill.model.mt.Vol;
import com.ric.bill.model.oralv.Ko;

/**
 * Детали перерасчета - объемы по счетчикам
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CHNG_VAL", schema="FN")
public class ChngVal implements java.io.Serializable, Simple {


	public ChngVal() {
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG_LSK", referencedColumnName="ID", updatable = false)
	private ChngLsk chngLsk;
	
	// приращение к старому объему
	@Column(name = "VAL")
	private Double val;

	// Тип значения перерасчета (дни, %, кВт, ...)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_VAL_TP", referencedColumnName="ID")
	private Lst valTp; 
	
	// даты начала и окончания действия значения перерасчета
    @Column(name = "DT1_VAL", updatable = false, nullable = true)
    private Date dtVal1;

    @Column(name = "DT2_VAL", updatable = false, nullable = true)
    private Date dtVal2;

	// Физический счетчик, участвующий в перерасчете
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER", referencedColumnName="ID")
	private Meter meter; 
    
	// Объем физического счетчика, участвующего в перерасчете
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER_VOL", referencedColumnName="ID")
	private Vol vol; 
    
	// Объект по которому меняется параметр в перерасчете
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="ID", updatable = false, insertable = false)
	private Ko ko;
    
    // Изменяемый параметр по перерасчету
    @ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name="FK_HFP", referencedColumnName="ID")
	@BatchSize(size = 50)
	private Par par; 
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ChngLsk getChngLsk() {
		return chngLsk;
	}

	public void setChngLsk(ChngLsk chngLsk) {
		this.chngLsk = chngLsk;
	}

	public Lst getValTp() {
		return valTp;
	}

	public void setValTp(Lst valTp) {
		this.valTp = valTp;
	}

	public Meter getMeter() {
		return meter;
	}

	public void setMeter(Meter meter) {
		this.meter = meter;
	}

	public Date getDtVal1() {
		return dtVal1;
	}

	public Vol getVol() {
		return vol;
	}

	public void setVol(Vol vol) {
		this.vol = vol;
	}

	public void setDtVal1(Date dtVal1) {
		this.dtVal1 = dtVal1;
	}

	public Date getDtVal2() {
		return dtVal2;
	}

	public void setDtVal2(Date dtVal2) {
		this.dtVal2 = dtVal2;
	}

	public Double getVal() {
		return val;
	}

	public void setVal(Double val) {
		this.val = val;
	}

	public Ko getKo() {
		return ko;
	}

	public void setKo(Ko ko) {
		this.ko = ko;
	}

	public Par getPar() {
		return par;
	}

	public void setPar(Par par) {
		this.par = par;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof ChngVal))
	        return false;

	    ChngVal other = (ChngVal)o;

	    if (getId() == other.getId()) return true;
	    if (getId() == null) return false;

	    // equivalence by id
	    return getId().equals(other.getId());
	}

	public int hashCode() {
	    if (getId() != null) {
	        return getId().hashCode();
	    } else {
	        return super.hashCode();
	    }
	}
	
}

