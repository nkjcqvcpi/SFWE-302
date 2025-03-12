package edu.baylor.ecs.csi5324.decorator.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import edu.baylor.ecs.csi5324.decorator.DataSource;
import edu.baylor.ecs.csi5324.decorator.DataSourceDecorator;

public class CompressionDecorator extends DataSourceDecorator {
	private int compLevel = 6;

	public CompressionDecorator(DataSource source) {
		super(source);
	}

	public int getCompressionLevel() {
		return compLevel;
	}

	public void setCompressionLevel(int value) {
		compLevel = value;
	}

	@Override
	public void writeData(String data) {
		super.writeData(compress(data));
	}

	@Override
	public String readData() {
		return decompress(super.readData());
	}

	private String compress(String stringData) {
		byte[] data = stringData.getBytes();
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
			DeflaterOutputStream dos = new DeflaterOutputStream(bout, new Deflater(compLevel));
			dos.write(data);
			dos.close();
			bout.close();
			return Base64.getEncoder().encodeToString(bout.toByteArray());
		} catch (IOException ex) {
			return null;
		}
	}

	private String decompress(String stringData) {
		// use decoder
		byte[] data = Base64.getDecoder().decode(stringData);
		// convert byte data via inflater to ByteArrayOutputStream
		try (ByteArrayInputStream in = new ByteArrayInputStream(data);
			 InflaterInputStream iin = new InflaterInputStream(in);
			 ByteArrayOutputStream bout = new ByteArrayOutputStream(512)){
			int b;
			while ((b = iin.read()) != -1) {
				bout.write(b);
			}
			return bout.toString();
		} catch (IOException ex) {
			return null;
		}
	}
}