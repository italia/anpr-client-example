package it.interno.anpr.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WSTypeHandler {

	public static final String TESTCONN = "TESTCONN";

	public static final String WS1001 = "WS1001";
	public static final String WS1002 = "WS1002";
	public static final String WS1010 = "WS1010";
	public static final String WS1013 = "WS1013";
	public static final String WS1014 = "WS1014";

	public static final String WS2001 = "WS2001";
	public static final String WS2003 = "WS2003";
	public static final String WS2009 = "WS2009";
	public static final String WS2011 = "WS2011";

	public static final String WS3001 = "WS3001";
	public static final String WS3002 = "WS3002";
	public static final String WS3003 = "WS3003";
	public static final String WS3005 = "WS3005";
	public static final String WS3007 = "WS3007";

	public static final String WS4001 = "WS4001";
	public static final String WS4002 = "WS4002";
	public static final String WS4003 = "WS4003";

	public static final String WS5001 = "WS5001";
	public static final String WS5005 = "WS5005";
	public static final String WS5007 = "WS5007";
	public static final String WS5008 = "WS5008";
	public static final String WS5009 = "WS5009";
	public static final String WS5012 = "WS5012";

	public static final String WS6001 = "WS6001";

	public static final String WS7001 = "WS7001";

	public static final String WSA001 = "WSA001";
	public static final String WSA002 = "WSA002";
	public static final String WSA006 = "WSA006";

	public static final String WSS001 = "WSS001";

	public static final String WSCIE2 = "WSCIE2";

	public static final String URI_TESTCONN = "/ANPRTestConn/TestConn";
	public static final String URI_WS1000 = "/ANPR1000ServiziIscrizione/AnprService1000";
	public static final String URI_WS2000 = "/ANPR2000ServiziCancellazione/AnprService2000";
	public static final String URI_WS3000 = "/ANPR3000ServiziConsultazione/AnprService3000";
	public static final String URI_WS4000 = "/ANPR4000ServiziEstrazione/AnprService4000";
	public static final String URI_WS5000 = "/ANPR5000ServiziMutazione/AnprService5000";
	public static final String URI_WS6001 = "/ANPR6001ServizioCertificazione/AnprService6001";
	public static final String URI_WS7001 = "/ANPR7001ServizioScaricoTabelle/AnprService7001";
	public static final String URI_WSA000 = "/ANPRA000ServiziAire/AnprServiceA000";
	public static final String URI_WSS001 = "/ANPRS001ServizioSubentro/AnprServiceS001";
	public static final String URI_WSCIE0 = "/ANPRCIE/AnprServiziCIE";

	private List<String> WSType = new ArrayList<String>(Arrays.asList(TESTCONN, WS1001, WS1002, WS1010, WS1013, WS1014,
			WS2001, WS2003, WS2009, WS2011, WS3001, WS3002, WS3003, WS3005, WS3007, WS4001, WS4002, WS4003, WS5001,
			WS5005, WS5007, WS5008, WS5009, WS5012, WS6001, WS7001, WSA001, WSA002, WSA006, WSS001, WSCIE2));
	private String WSFamily;

	public String getWSFamily() {
		return WSFamily;
	}

	public void setWSFamily(String WSFamily) throws Exception {
		if (WSType.contains(WSFamily))
			this.WSFamily = WSFamily;
		else
			throw new Exception("Famiglia del Servizio " + WSFamily + " non ammessa");
	}

	public String getUri() {
		switch (WSFamily) {
		case TESTCONN:
			return URI_TESTCONN;
		case WS1001:
			return URI_WS1000;
		case WS1002:
			return URI_WS1000;
		case WS1010:
			return URI_WS1000;
		case WS1013:
			return URI_WS1000;
		case WS1014:
			return URI_WS1000;
		case WS2001:
			return URI_WS2000;
		case WS2003:
			return URI_WS2000;
		case WS2009:
			return URI_WS2000;
		case WS2011:
			return URI_WS2000;
		case WS3001:
			return URI_WS3000;
		case WS3002:
			return URI_WS3000;
		case WS3003:
			return URI_WS3000;
		case WS3005:
			return URI_WS3000;
		case WS3007:
			return URI_WS3000;
		case WS4001:
			return URI_WS4000;
		case WS4002:
			return URI_WS4000;
		case WS4003:
			return URI_WS4000;
		case WS5001:
			return URI_WS5000;
		case WS5005:
			return URI_WS5000;
		case WS5007:
			return URI_WS5000;
		case WS5008:
			return URI_WS5000;
		case WS5009:
			return URI_WS5000;
		case WS5012:
			return URI_WS5000;
		case WS6001:
			return URI_WS6001;
		case WS7001:
			return URI_WS7001;
		case WSA001:
			return URI_WSA000;
		case WSA002:
			return URI_WSA000;
		case WSA006:
			return URI_WSA000;
		case WSS001:
			return URI_WSS001;
		case WSCIE2:
			return URI_WSCIE0;
		default:
			return "";
		}
	}

	public boolean isValid(String WSOperation) {
		return WSType.contains(WSOperation);
	}

}
