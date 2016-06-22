package br.com.lifeundercontroll.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
	public RequestWrapper(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);
	}

	private byte[] bytes = null;

	public ServletInputStream getInputStream() throws IOException {
		if (bytes == null) {
			InputStream in = super.getRequest().getInputStream();
			bytes = new byte[super.getRequest().getContentLength()];
			for (int r, offset = 0; (r = in.read(bytes, offset, bytes.length - offset)) > -1;) {
				offset += r;
			}
		}
		
		final InputStream in = new ByteArrayInputStream(bytes);
		return new ServletInputStream() {
			public int read() throws IOException {
				return in.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {}
		};
	}

	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
}
