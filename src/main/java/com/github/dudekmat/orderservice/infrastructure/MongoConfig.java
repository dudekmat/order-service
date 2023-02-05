package com.github.dudekmat.orderservice.infrastructure;

import com.github.dudekmat.orderservice.shared.IdValueObject;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
class MongoConfig {

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    return new MongoCustomConversions(
        List.of(new ValueObjectIdConverter()));
  }

  private static class ValueObjectIdConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
      return new HashSet<>(List.of(
          new ConvertiblePair(IdValueObject.class, UUID.class)
      ));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      try {
        if (source instanceof IdValueObject) {
          return ((IdValueObject) source).getValue();
        }

        final Class<?> objectType = targetType.getType();
        final Constructor<?> constructor = objectType.getConstructor(sourceType.getType());
        return constructor.newInstance(source);
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException("Could not convert unexpected type " +
            sourceType.getObjectType().getName() + " to " +
            targetType.getObjectType().getName(), e);
      }
    }
  }
}
