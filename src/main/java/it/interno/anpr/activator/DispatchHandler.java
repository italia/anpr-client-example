package it.interno.anpr.activator;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.config.WSTypeHandler;
import it.interno.anpr.security.message.Op1001Handler;
import it.interno.anpr.security.message.Op1002Handler;
import it.interno.anpr.security.message.Op1010Handler;
import it.interno.anpr.security.message.Op1013Handler;
import it.interno.anpr.security.message.Op1014Handler;
import it.interno.anpr.security.message.Op2001Handler;
import it.interno.anpr.security.message.Op2003Handler;
import it.interno.anpr.security.message.Op2009Handler;
import it.interno.anpr.security.message.Op2011Handler;
import it.interno.anpr.security.message.Op3001Handler;
import it.interno.anpr.security.message.Op3002Handler;
import it.interno.anpr.security.message.Op3003Handler;
import it.interno.anpr.security.message.Op3005Handler;
import it.interno.anpr.security.message.Op3007Handler;
import it.interno.anpr.security.message.Op4001Handler;
import it.interno.anpr.security.message.Op4002Handler;
import it.interno.anpr.security.message.Op4003Handler;
import it.interno.anpr.security.message.Op5001Handler;
import it.interno.anpr.security.message.Op5005Handler;
import it.interno.anpr.security.message.Op5008Handler;
import it.interno.anpr.security.message.Op5012Handler;
import it.interno.anpr.security.message.Op6001Handler;
import it.interno.anpr.security.message.Op7001Handler;
import it.interno.anpr.security.message.OpA001Handler;
import it.interno.anpr.security.message.OpA002Handler;
import it.interno.anpr.security.message.OpA006Handler;
import it.interno.anpr.security.message.OpCIE2Handler;
import it.interno.anpr.security.message.OpS001Handler;
import it.interno.anpr.security.message.TestConnHandler;

public class DispatchHandler {

	private ParamHandler param;

	public DispatchHandler(ParamHandler param) {
		this.param = param;
	}
	
	public boolean execute() {
		switch (param.getWsType().getWSFamily()) {
			case WSTypeHandler.TESTCONN:
				TestConnHandler servizioHandler = new TestConnHandler(param);
				return servizioHandler.isResponseValid(servizioHandler.sendRequest());
			case WSTypeHandler.WS1001:
				Op1001Handler servizio1001 = new Op1001Handler(param);
				return servizio1001.isResponseValid(servizio1001.sendRequest());
			case WSTypeHandler.WS1002:
				Op1002Handler servizio1002 = new Op1002Handler(param);
				return servizio1002.isResponseValid(servizio1002.sendRequest());
			case WSTypeHandler.WS1010:
				Op1010Handler servizio1010 = new Op1010Handler(param);
				return servizio1010.isResponseValid(servizio1010.sendRequest());
			case WSTypeHandler.WS1013:
				Op1013Handler servizio1013 = new Op1013Handler(param);
				return servizio1013.isResponseValid(servizio1013.sendRequest());
			case WSTypeHandler.WS1014:
				Op1014Handler servizio1014 = new Op1014Handler(param);
				return servizio1014.isResponseValid(servizio1014.sendRequest());
			case WSTypeHandler.WS2001:
				Op2001Handler servizio2001 = new Op2001Handler(param);
				return servizio2001.isResponseValid(servizio2001.sendRequest());
			case WSTypeHandler.WS2003:
				Op2003Handler servizio2003 = new Op2003Handler(param);
				return servizio2003.isResponseValid(servizio2003.sendRequest());
			case WSTypeHandler.WS2009:
				Op2009Handler servizio2009 = new Op2009Handler(param);
				return servizio2009.isResponseValid(servizio2009.sendRequest());
			case WSTypeHandler.WS2011:
				Op2011Handler servizio2011 = new Op2011Handler(param);
				return servizio2011.isResponseValid(servizio2011.sendRequest());
			case WSTypeHandler.WS3001:
				Op3001Handler servizio3001 = new Op3001Handler(param);
				return servizio3001.isResponseValid(servizio3001.sendRequest());
			case WSTypeHandler.WS3002:
				Op3002Handler servizio3002 = new Op3002Handler(param);
				return servizio3002.isResponseValid(servizio3002.sendRequest());
			case WSTypeHandler.WS3003:
				Op3003Handler servizio3003 = new Op3003Handler(param);
				return servizio3003.isResponseValid(servizio3003.sendRequest());
			case WSTypeHandler.WS3005:
				Op3005Handler servizio3005 = new Op3005Handler(param);
				return servizio3005.isResponseValid(servizio3005.sendRequest());
			case WSTypeHandler.WS3007:
				Op3007Handler servizio3007 = new Op3007Handler(param);
				return servizio3007.isResponseValid(servizio3007.sendRequest());
			case WSTypeHandler.WS4001:
				Op4001Handler servizio4001 = new Op4001Handler(param);
				return servizio4001.isResponseValid(servizio4001.sendRequest());
			case WSTypeHandler.WS4002:
				Op4002Handler servizio4002 = new Op4002Handler(param);
				return servizio4002.isResponseValid(servizio4002.sendRequest());
			case WSTypeHandler.WS4003:
				Op4003Handler servizio4003 = new Op4003Handler(param);
				return servizio4003.isResponseValid(servizio4003.sendRequest());
			case WSTypeHandler.WS5001:
				Op5001Handler servizio5001 = new Op5001Handler(param);
				return servizio5001.isResponseValid(servizio5001.sendRequest());
			case WSTypeHandler.WS5005:
				Op5005Handler servizio5005 = new Op5005Handler(param);
				return servizio5005.isResponseValid(servizio5005.sendRequest());
			case WSTypeHandler.WS5008:
				Op5008Handler servizio5008 = new Op5008Handler(param);
				return servizio5008.isResponseValid(servizio5008.sendRequest());
			case WSTypeHandler.WS5012:
				Op5012Handler servizio5012 = new Op5012Handler(param);
				return servizio5012.isResponseValid(servizio5012.sendRequest());
			case WSTypeHandler.WS6001:
				Op6001Handler servizio6001 = new Op6001Handler(param);
				return servizio6001.isResponseValid(servizio6001.sendRequest());
			case WSTypeHandler.WS7001:
				Op7001Handler servizio7001 = new Op7001Handler(param);
				return servizio7001.isResponseValid(servizio7001.sendRequest());
			case WSTypeHandler.WSA001:
				OpA001Handler servizioA001 = new OpA001Handler(param);
				return servizioA001.isResponseValid(servizioA001.sendRequest());
			case WSTypeHandler.WSA002:
				OpA002Handler servizioA002 = new OpA002Handler(param);
				return servizioA002.isResponseValid(servizioA002.sendRequest());
			case WSTypeHandler.WSA006:
				OpA006Handler servizioA006 = new OpA006Handler(param);
				return servizioA006.isResponseValid(servizioA006.sendRequest());
			case WSTypeHandler.WSS001:
				OpS001Handler servizioS001 = new OpS001Handler(param);
				return servizioS001.isResponseValid(servizioS001.sendRequest());
			case WSTypeHandler.WSCIE2:
				OpCIE2Handler servizioCIE2 = new OpCIE2Handler(param);
				return servizioCIE2.isResponseValid(servizioCIE2.sendRequest());
			default:
				System.out.println("Operation <"+param.getWsType().getWSFamily()+"> non gestita");
				return false;
		}
	}
}
