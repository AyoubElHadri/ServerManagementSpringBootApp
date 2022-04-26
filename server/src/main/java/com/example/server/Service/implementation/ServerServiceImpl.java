package com.example.server.Service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.server.Service.ServerService;
import com.example.server.enumeration.Status;
import com.example.server.model.Server;
import com.example.server.repository.ServerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService{
	private final ServerRepository serverRepository;

	@Override
	public Server ping(String ipAddress) throws IOException {
		log.info("Pinging server IP: {}", ipAddress);
		Server server = serverRepository.findByIpAddress(ipAddress);
		InetAddress address = InetAddress.getByName(ipAddress);
		server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		serverRepository.save(server);
		return server;
	}

	@Override
	public Server create(Server server) {
		log.info("Saving new server: {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		return serverRepository.save(server);
	}


	@Override
	public Collection<Server> list(int limit) {
		log.info("Fetching all servers");
		return serverRepository.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		log.info("Fetching server by Id : {}", id);
		return serverRepository.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("Updating server :{}", server.getName());
		return serverRepository.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting server : {}", id);
		serverRepository.deleteById(id);
		return Boolean.TRUE;
	}
	private String setServerImageUrl() {
		String [] imageNames = {"server1", "server2", "server3", "server4"};
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/"+ imageNames[new Random().nextInt(4)]).toUriString();
	}


}
