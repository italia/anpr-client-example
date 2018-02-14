package it.interno.anpr.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnvironmentHandler {

	public static final String VAL = "VAL";
	public static final String TEST = "TEST";
	public static final String PRE = "PRE";
	public static final String FREE = "FREE";
	public static final String PROD = "PROD";

	private static List<String> envList = new ArrayList<String>( Arrays.asList(VAL, TEST, PRE, FREE, PROD));
	private static String env;

	public static String getEnv() {
		return env;
	}

	public static void setEnv(String env) {
		EnvironmentHandler.env = env;
	}

	public static boolean isValid(String env) {
		return envList.contains(env);
	}
}
