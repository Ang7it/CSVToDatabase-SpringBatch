package com.nova.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nova.model.User;

@Component
public class Processor implements ItemProcessor<User, User>{

	@Override
	public User process(User user) throws Exception {
		return user;
	}
	
}
