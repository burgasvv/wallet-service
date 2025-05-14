package org.burgas.walletservice.handler;

import org.burgas.walletservice.dto.Request;
import org.burgas.walletservice.dto.Response;
import org.burgas.walletservice.entity.AbstractEntity;

/**
 * Интерфейс-обработчик для указания взаимодействия между запросами, сущностями и ответами
 * @param <T> параметр обобщения запросов
 * @param <S> параметр обобщения сущностей
 * @param <V> параметр обобщения ответов
 */
public interface MapperDataHandler<T extends Request, S extends AbstractEntity, V extends Response> {

    S toEntity(T t);

    V toResponse(S s);
}
