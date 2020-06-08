package com.nova.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.nova.model.User;

@EnableBatchProcessing
@Configuration
public class SpringBatchConfig {
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory,ItemReader<User> itemReader,
			ItemProcessor<User,User> itemProcessor,ItemWriter<User> itemWriter) {
		
		
		
		Step step=stepBuilderFactory.get("ETL-file-load")
				.<User,User>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
		
		
		return jobBuilderFactory.get("ETL-Load")
				.incrementer(new RunIdIncrementer())
				.start(step)
				.build();
	}
	
	@Bean
	public FlatFileItemReader<User> fileItemReader(){
		
		FlatFileItemReader<User> flatFileItemReader=new FlatFileItemReader<User>();
		flatFileItemReader.setResource(new ClassPathResource("user.csv"));
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(0);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}
	
	@Bean
	public LineMapper<User> lineMapper(){
		DefaultLineMapper<User> defaultLineMapper=new DefaultLineMapper<User>();
		DelimitedLineTokenizer delimitedLineTokenizer=new DelimitedLineTokenizer();
		
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		delimitedLineTokenizer.setNames(new String[] {"id","name","club","country"});
		
		BeanWrapperFieldSetMapper<User> beanWrapperFieldSetMapper=new BeanWrapperFieldSetMapper<User>();
		beanWrapperFieldSetMapper.setTargetType(User.class);
		
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		
		return defaultLineMapper;
	}

}
