
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class CustomizableFinder extends DomainEntity {

	private int	resultNumber;
	private int	timeCache;


	//Getters and Setters
	@Range(min = 10, max = 100)
	public int getResultNumber() {
		return this.resultNumber;
	}

	public void setResultNumber(final int resultNumber) {
		this.resultNumber = resultNumber;
	}
	@Range(min = 1, max = 24)
	public int getTimeCache() {
		return this.timeCache;
	}

	public void setTimeCache(final int timeCache) {
		this.timeCache = timeCache;
	}

}
