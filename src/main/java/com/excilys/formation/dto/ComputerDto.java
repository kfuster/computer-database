package com.excilys.formation.dto;

import java.time.LocalDate;

/**
 * DTO class for computers
 * @author kfuster
 *
 */
public class ComputerDto {
	public int id;
	public String name;
	public LocalDate introduced;
	public LocalDate discontinued;
	public int companyId;
	public String companyName;
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder().append("Computer : [id=")
				.append(id)
				.append(", name=")
				.append(name);
		if(introduced != null) {
				stringBuilder.append(", introduced=")
				.append(introduced);
		}
		if(discontinued != null) {
				stringBuilder.append(", discontinued=")
				.append(discontinued);
		}
		if(companyId != 0 && companyName != null) {
				stringBuilder.append(", Company : [companyId=")
				.append(companyId)
				.append(", companyName=")
				.append(companyName)
				.append("]]");
		}
		 return stringBuilder.toString();
	}	
}
