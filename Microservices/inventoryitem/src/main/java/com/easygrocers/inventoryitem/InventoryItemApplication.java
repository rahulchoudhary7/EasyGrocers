package com.easygrocers.inventoryitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InventoryItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryItemApplication.class, args);
	}

}
