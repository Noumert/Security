package com.example.demo.model;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Noumert on 21.08.2021.
 */
public interface EntityDtoConverter<E, D> {

    D convertEntityToDto(E entity);

    E convertDtoToEntity(D dto);

    List<D> convertEntityListToDtoList(List<E> entities);

    List<E> convertDtoListToEntityList(List<D> dtos);

    Page<D> convertEntityPageToDtoPage(Page<E> entities);

    Page<E> convertDtoPageToEntityPage(Page<D> dtos);
}
