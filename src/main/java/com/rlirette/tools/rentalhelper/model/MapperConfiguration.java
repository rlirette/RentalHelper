package com.rlirette.tools.rentalhelper.model;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MapperConfiguration {
}
