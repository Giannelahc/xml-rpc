package com.demo.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcClientRequestImpl;
import org.apache.xmlrpc.client.XmlRpcSunHttpTransport;
import org.apache.xmlrpc.client.XmlRpcTransport;
import org.apache.xmlrpc.client.XmlRpcTransportFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/txn-admin")
public class RegistroClienteController
{

    @PostMapping(value = "/registro")
    public String registro() throws MalformedURLException, XmlRpcException, ParseException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://apidev.tutuka.com/card/v1/XmlRpc.cfm"));
        //config.setEnabledForExceptions(true);
        //config.setReplyTimeout(3 * 1000);
        XmlRpcClient client = new XmlRpcClient();
        
        client.setTypeFactory(new MyTypeFactory(client));
        
        final XmlRpcTransportFactory transportFactory = new XmlRpcTransportFactory()
        {
            public XmlRpcTransport getTransport()
            {
                return new MessageLoggingTransport(client);
            }
        };
        
        //client.setTransportFactory(transportFactory);
        
        client.setConfig(config);
        
        XmlRpcSunHttpTransport http = 
                (XmlRpcSunHttpTransport) transportFactory.getTransport();       

        
        
        Calendar fecha1 = Calendar.getInstance();
        
        fecha1.set(Calendar.YEAR, 2016);
        fecha1.set(Calendar.MONTH, 9);
        fecha1.set(Calendar.DATE, 17);
        fecha1.set(Calendar.HOUR_OF_DAY, 0);
        fecha1.set(Calendar.MINUTE, 0);
        fecha1.set(Calendar.SECOND, 0);
        
        System.out.println(fecha1.getTime());
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        System.out.println(f.format(fecha1.getTime()));
        
        Object[] params = {
                new String("0008866376"),
                new String("TEST_Tutuka"),
                new String("156554700000004"),
                new String("4857467"),
                fecha1.getTime(),
                new String("4252E91C4890A9BDB7ED04C2673A89ED53F4C6D5")
        };

        HashMap o = (HashMap) http.sendRequest(new XmlRpcClientRequestImpl(config, "LinkCard", 
                params));
        System.out.println(o);
        return "response";
    }
}
