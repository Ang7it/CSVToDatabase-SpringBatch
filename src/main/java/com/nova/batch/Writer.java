package com.nova.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nova.model.User;
import com.nova.repository.UserRepository;

@Component
public class Writer implements ItemWriter<User>{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void write(List<? extends User> users) throws Exception {
			users.stream()
			.forEach(user->	userRepository.save(user));
	}
	

}
