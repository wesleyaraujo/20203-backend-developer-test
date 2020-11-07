package br.com.zssn.utils;

import java.util.Random;
import java.util.UUID;

import br.com.zssn.entity.Survivor;

public class TestHelper {
	public static Survivor buildSurvivor() {
		String uuid = UUID.randomUUID().toString();
		return Survivor.builder().name("name-" + uuid).build();
	}

	public static Survivor buildSurvivorWithId() {
		Random random = new Random();
		String uuid = UUID.randomUUID().toString();
		return Survivor.builder().id(random.nextLong()).name("name-" + uuid).build();
	}

}
