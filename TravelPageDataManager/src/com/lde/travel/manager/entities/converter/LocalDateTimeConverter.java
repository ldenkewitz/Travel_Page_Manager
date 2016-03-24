package com.lde.travel.manager.entities.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
	
	@Override
  public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
      return Timestamp.from(attribute.atZone(ZoneId.systemDefault()).toInstant());
  }

  @Override
  public LocalDateTime convertToEntityAttribute(Timestamp ts) {
  	return ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }


}
