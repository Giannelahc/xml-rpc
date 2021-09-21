package com.demo.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcClientRequestImpl;
import org.apache.xmlrpc.client.XmlRpcSunHttpTransport;
import org.apache.xmlrpc.client.XmlRpcSunHttpTransportFactory;
import org.springframework.http.MediaType;
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
        client.setConfig(config);
        client.setTypeFactory(new MyTypeFactory(client));
        XmlRpcSunHttpTransport http = (XmlRpcSunHttpTransport) new XmlRpcSunHttpTransportFactory(client).getTransport();       

        
        Calendar fecha1 = Calendar.getInstance();
        
        fecha1.set(Calendar.YEAR, 2016);
        fecha1.set(Calendar.MONTH, 10);
        fecha1.set(Calendar.DATE, 17);
        fecha1.set(Calendar.HOUR, 0);
        fecha1.set(Calendar.MINUTE, 0);
        fecha1.set(Calendar.SECOND, 0);
        
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        System.out.println(f.format(fecha1.getTime()));
        
        Map<String, Object> request = new HashMap<>();
        request.put("1", new String("0008866376"));
        request.put("2", new String("TEST_Tutuka"));
        request.put("3", new String("156554700000004"));
        request.put("4", new String("4857467"));
        request.put("5", fecha1.getTime());
        request.put("6", new String("4252E91C4890A9BDB7ED04C2673A89ED53F4C6D5"));
        System.out.println(request);
        Vector v = new Vector();
        v.add(request);

        System.out.println(v);
        HashMap o = (HashMap) http.sendRequest(new XmlRpcClientRequestImpl(config, "LinkCard", v));
        return "response";
    }
}
