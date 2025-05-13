package org.burgas.walletservice.handler;

import org.burgas.walletservice.dto.Request;
import org.burgas.walletservice.dto.Response;
import org.burgas.walletservice.entity.AbstractEntity;

public interface MapperDataHandler<T extends Request, S extends AbstractEntity, V extends Response> {

    S toEntity(T t);

    V toResponse(S s);
}
