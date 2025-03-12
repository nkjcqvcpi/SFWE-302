package edu.baylor.ecs.csi5324.decorator.impl;
import java.util.Base64;

import edu.baylor.ecs.csi5324.decorator.DataSource;
import edu.baylor.ecs.csi5324.decorator.DataSourceDecorator;

public class EncryptionDecorator extends DataSourceDecorator {

    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        super.writeData(encode(data));
    }

    @Override
    public String readData() {
        return decode(super.readData());
    }

    private String encode(String data) {
        byte[] result = data.getBytes();
        for (int i = 0; i < result.length; i++) {
            result[i] += (byte) 1;
        }
        return Base64.getEncoder().encodeToString(result);
    }

    private String decode(String data) {
        // decode data via Base 64
        byte[] result = Base64.getDecoder().decode(data);
    	// use returned byte[] to do the invert of encode loop
        for (int i = 0; i < result.length; i++) {
            result[i] -= (byte) 1;
        }
    	// return as String
        return new String(result);
    }
}