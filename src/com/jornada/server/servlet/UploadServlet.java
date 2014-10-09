package com.jornada.server.servlet;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.shared.UConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.jornada.ConfigJornada;
import com.jornada.server.classes.image.ImageCompressor;

public class UploadServlet extends UploadAction {

	private static final long serialVersionUID = 1L;

	Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
	/**
	 * Maintain a list with received files and their content types.
	 */
	Hashtable<String, File> receivedFiles = new Hashtable<String, File>();

	/**
	 * Override executeAction to save the received files in a custom place and
	 * delete this items from session.
	 */
	public String executeAction(HttpServletRequest request,	List<FileItem> sessionFiles) throws UploadActionException {
		String response = "";
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				try {
					// / Create a new file based on the remote file name in the
					// client
					// String saveName =
					// item.getName().replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+",
					// "_");
					// File file =new File("/tmp/" + saveName);

					// / Create a temporary file placed in /tmp (only works in
					// unix)
					// File file = File.createTempFile("upload-", ".bin", new
					// File("/tmp"));

					// / Create a temporary file placed in the default system
					// temp folder

					// File file = File.createTempFile("upload-",
					// ".bin","c://temp");

					// Date date = new Date();
					// String nomeImagem = Long.toString(date.getTime());

					// File file = new File("images/download/"+item.getName());
					

					
					int begin = item.getName().lastIndexOf(".");
					int end = item.getName().length();
					String sufix = item.getName().substring(begin, end);
					
					String strDestinationFolder = ConfigJornada.getProperty("config.download");
					
					if(isExcelFile(sufix)){
						strDestinationFolder += ConfigJornada.getProperty("config.download.excel");
					}else if(isImageFile(sufix)){
						strDestinationFolder += ConfigJornada.getProperty("config.download.image");
					}else{
					    strDestinationFolder += ConfigJornada.getProperty("config.download.file");
					}
					
					
					File file = File.createTempFile("upload-", sufix, new File(strDestinationFolder));
					file.createNewFile();
					file.getName();

					item.write(file);
					
					
//			        int new_w = 128, new_h = 128;        
//			        BufferedImage imagem = ImageIO.read(new File("images/download/"+file.getName()));        
//			        BufferedImage new_img = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
//			        Graphics2D g = new_img.createGraphics();
//			        g.drawImage(imagem, 0, 0, new_w, new_h, null);
//			        ImageIO.write(new_img, "JPG", new File("images/download/compressed-"+file.getName()+""));   
					
//					ImageCompressor.Thumbnail("images/download/", file.getName(), 0.5f);
//					ImageCompressor imageCompressor = new ImageCompressor();
					if(isImageFile(sufix)){
						ImageCompressor.compressAndSave(strDestinationFolder, file.getName(), 0.1f);						
					}
					
					// Save a list with the received files
					receivedFiles.put(item.getFieldName(), file);
					receivedContentTypes.put(item.getFieldName(),item.getContentType());

					// Send a customized message to the client.
//					response += strDestinationFolder+file.getName();
					if(isExcelFile(sufix)){
						response += strDestinationFolder+file.getName();
					}else if(isImageFile(sufix)){
						response += file.getName();
					}else{
					    response += strDestinationFolder+file.getName();
					}
					

				} catch (Exception e) {
					throw new UploadActionException(e);
				}
			}
		}

		// / Remove files from session because we have a copy of them
		removeSessionFileItems(request);

		// / Send your customized message to the client.
		return response;
	}
	
	private boolean isExcelFile(String sufix){
		if(sufix.equalsIgnoreCase(".xls")){
			return true;
		}else if (sufix.equalsIgnoreCase(".xlsx")){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean isImageFile(String sufix){
		if(sufix.equalsIgnoreCase(".png")){
			return true;
		}else if (sufix.equalsIgnoreCase(".gif")){
			return true;
		}else if(sufix.equalsIgnoreCase(".jpg")){
			return true;
		}			
		else{
			return false;
		}
	}	

	/**
	 * Get the content of an uploaded file.
	 */
	@Override
    public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fieldName = request.getParameter(UConsts.PARAM_SHOW);
		File f = receivedFiles.get(fieldName);
		if (f != null) {
			response.setContentType(receivedContentTypes.get(fieldName));
			FileInputStream is = new FileInputStream(f);
			copyFromInputStreamToOutputStream(is, response.getOutputStream());
		} else {
			renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
		}
	}

	/**
	 * Remove a file when the user sends a delete request.
	 */
	@Override
	public void removeItem(HttpServletRequest request, String fieldName)
			throws UploadActionException {
		File file = receivedFiles.get(fieldName);
		receivedFiles.remove(fieldName);
		receivedContentTypes.remove(fieldName);
		if (file != null) {
			file.delete();
		}
	}

}
