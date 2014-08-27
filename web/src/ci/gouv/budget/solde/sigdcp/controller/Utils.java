package ci.gouv.budget.solde.sigdcp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import lombok.extern.java.Log;

@Log @Singleton
public class Utils {
	
	public static final int DEFAULT_BUFFER_SIZE = 1024 * 1000; // 10KB.
	
	public static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				log.log(Level.WARNING,e.toString(),e);
			}
		}
	}
	
	public static void send(ServletContext servletContext,HttpServletRequest request, HttpServletResponse response,String fileName, int contentLength, InputStream inputStream) throws IOException {
		send(servletContext,request, response, fileName, contentLength, inputStream, true);
	}
	
	public static void send(ServletContext servletContext,HttpServletRequest request, HttpServletResponse response,String fileName, int contentLength, InputStream inputStream,boolean inline) throws IOException {
		send(servletContext,request, response, fileName, contentLength, inputStream, inline,DEFAULT_BUFFER_SIZE);
	}
	
	public static void send(ServletContext servletContext,HttpServletRequest request, HttpServletResponse response,String fileName, int contentLength, InputStream inputStream,boolean inline,int bufferSize) throws IOException {
		// Get content type by filename.
		
		String contentType = servletContext.getMimeType(fileName);

		// If content type is unknown, then set the default value.
		// For all content types, see:
		// http://www.w3schools.com/media/media_mimeref.asp
		// To add new content types, add new mime-mapping entry in web.xml.
		if (contentType == null) {
			log.warning("Unknown content type of file : " + fileName);
			contentType = "application/octet-stream";
		}

		response.reset();
		response.setBufferSize(bufferSize);
		response.setContentType(contentType);
		response.setHeader("Content-Length", String.valueOf(contentLength));
		response.setHeader("Content-Disposition", (inline?"inline":"attachment")+"; filename=\""+ fileName + "\"");

		// Prepare streams.
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open streams.
			input = new BufferedInputStream(inputStream, bufferSize);
			output = new BufferedOutputStream(response.getOutputStream(),bufferSize);
			
			// Write file contents to response.
			/*
			byte[] buffer = new byte[bufferSize];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}*/
			
			IOUtils.copy(input, output);
			
		} finally {
			// Gently close streams.
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
			//close(output);
			//close(input);
		}
	}

}
