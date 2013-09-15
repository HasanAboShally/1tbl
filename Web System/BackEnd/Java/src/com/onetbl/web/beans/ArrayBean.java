package com.onetbl.web.beans;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;


@XmlRootElement
@JsonAutoDetect
public class ArrayBean {
	@JsonProperty
	private long[] longArr;

	public long[] getLongArr() {
		return longArr;
	}

	public void setLongArr(long[] longArr) {
		this.longArr = longArr;
	}
	
}
