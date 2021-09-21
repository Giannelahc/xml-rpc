package com.demo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.parser.DateParser;
import org.apache.xmlrpc.parser.TypeParser;
import org.apache.xmlrpc.serializer.DateSerializer;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.xml.sax.SAXException;

public class MyTypeFactory extends TypeFactoryImpl {
    
    public MyTypeFactory(XmlRpcController pController) {
        super(pController);
    }

    private DateFormat newFormat() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        return f;
    }

    public TypeParser getParser(XmlRpcStreamConfig pConfig, NamespaceContextImpl pContext, String pURI, String pLocalName) {
        if (DateSerializer.DATE_TAG.equals(pLocalName)) {
            return new DateParser(newFormat());
        } else {
            return super.getParser(pConfig, pContext, pURI, pLocalName);
        }
    }

    public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig, Object pObject) throws SAXException {
        if (pObject instanceof Date) {System.out.println(pObject);
            return new DateSerializer(newFormat());
        } else {
            return super.getSerializer(pConfig, pObject);
        }
    }
}
