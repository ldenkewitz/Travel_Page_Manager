package com.lde.travel.manager.entities.converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
  public Date convertToDatabaseColumn(LocalDate attribute) {
		return Date.from(attribute.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  @Override
  public LocalDate convertToEntityAttribute(Date date) {
  	return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }


}
