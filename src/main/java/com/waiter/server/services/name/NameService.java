package com.waiter.server.services.name;

import com.waiter.server.commons.entities.Name;
import org.springframework.stereotype.Service;

/**
 * @author shahenpoghosyan
 */
@Service
public interface NameService {
    Name create(Name name);
}
