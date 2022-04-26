package com.example.server.Service;

import java.io.IOException;
import java.util.Collection;

import com.example.server.model.Server;

public interface ServerService {
	Server create(Server server);
	Collection<Server> list(int limit);
	Server ping(String ipAddress) throws IOException;
	Server get(Long id);
	Server update(Server server);
	Boolean delete(Long id);

}
