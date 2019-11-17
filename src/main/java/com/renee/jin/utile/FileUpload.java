package com.renee.jin.utile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUpload {
    private static final Logger logger = LoggerFactory.getLogger(FileUpload.class);
    private String removePath ="";
    private String removeFileName;
    private String saveFolder = "";
    private List<String> saveFileNames = new ArrayList<>();  // 저장 이름들
    
    public FileUpload() {}
    public FileUpload ( String removePath ) {
        this.removePath = removePath;
    }
    
    
    public List<String> getSaveFileNames() {
        return saveFileNames;
    }
    public void setSaveFileNames(List<String> saveFileNames) {
        this.saveFileNames = saveFileNames;
    }
    public String getRemoveFileName() {
        return removeFileName;
    }
    public void setRemoveFileName(String removeFileName) {
        this.removeFileName = removeFileName;
    }
    public String getSaveFolder() {
        return saveFolder;
    }
    public void setSaveFolder(String saveFolder) {
        this.saveFolder = saveFolder;
    }
    public static Logger getLogger() {
        return logger;
    }
    public String getRemovePath() {
        return removePath;
    }
    public void setRemovePath(String removePath) {
        this.removePath = removePath;
    }
    
    public HttpServletResponse downloadFiles ( HttpServletResponse response , String fileName, String filePath, HttpServletRequest request) {
    	String downName = null;
        String browser = request.getHeader("User-Agent");
        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;
        File file = new File(filePath);
        try {
            fileName = fileName.replaceAll("/", "").replaceAll("\\", "").replaceAll(".","").replaceAll("&", "");
			//다운로드 취약점 보안
            //파일 인코딩
            if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){//브라우저 확인 파일명 encode  
                 
                downName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
                 
            }else{
                 
                downName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                 
            }
            
            response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");             
            response.setContentType("application/octer-stream");
            response.setHeader("Content-Transfer-Encoding", "binary;");
            fileInputStream = new FileInputStream(file);
            servletOutputStream = response.getOutputStream();
            
            
            byte b [] = new byte[1024];
            int data = 0;
     
            while((data=(fileInputStream.read(b, 0, b.length))) != -1){
                servletOutputStream.write(b, 0, data);
                 
            }
            servletOutputStream.flush();//출력
            
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if(servletOutputStream!=null){
                try{
                    servletOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(fileInputStream!=null){
                try{
                    fileInputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        
        return response;
    }
    
    public void removeFiles (String filePath, String fileName) {
        String[] removeFileNames = fileName.split(",");
        for( int index = 0; index < removeFileNames.length; index++ ) {
            System.out.println(removePath + removeFileNames[index]);
            File deleteFile = new File( filePath + removeFileNames[index] );
            if( deleteFile.exists() ) {
                if( deleteFile.isFile() ) {
                    deleteFile.delete();
                    logger.info(deleteFile.getName() + "파일 제거 완료");
                }
            }
        }
    }
    
    public ArrayList<String> uploadFiles( MultipartHttpServletRequest mRequest, List<MultipartFile> files, Boolean isDev , String localSaveFolder , String serverFolder ,  Boolean isUpdate, String[] allowFiles, Long allowFileSize ) {
    	/* 
		 * Iterator를 통해서 하는 경우는 들어오는 input file 필드자체가 여러개일 때! 사용하는 것! 
		 * multiple를 쓰기 위해서는  List<MultipartFile> files = mRequest.getFiles("test[]"); 를통해서 각각의 파일을 선택해야 한다.
		 *  
		 * */
    	
        ArrayList<String> result = new ArrayList<>();
        String orignalFileName; // 확장자 포함 full name =>  test.txt
        String orignalOnlyeName; // 순수 이름만 test
        String orignalExt;// 확장자 확인
        Long orignalSize;
        Boolean isUploadCheck = false;
        
        // 저장 경로 설정
        if( isDev ) {
            saveFolder = localSaveFolder;
            /*System.out.println("저장경로 테스트!!!!");
            System.out.println(saveFolder);*/
        } else {
            saveFolder = mRequest.getSession().getServletContext().getRealPath("/"+serverFolder)+"/";
            /*System.out.println("저장경로 테스트!!!!");
            System.out.println(saveFolder);*/
        }
        
     // 업데이트일 경우 기존 파일 지움
        if( isUpdate ) {
            if(removeFileName != null && !removeFileName.isEmpty() ) {
                String[] removeFileNames = removeFileName.split(",");
                for( int index = 0; index < removeFileNames.length; index++ ) {
                    System.out.println(removePath + removeFileNames[index]);
                    File deleteFile = new File( removePath + removeFileNames[index] );
                    if( deleteFile.exists() ) {
                        if( deleteFile.isFile() ) {
                            deleteFile.delete();
                            logger.info(deleteFile.getName() + "파일 제거 완료");
                        }
                    }
                }
            }
        }
        
        
       // 폴더 없을 시 폴더부터 생성 
        File mkdir = new File(saveFolder);
        if( !mkdir.exists() ) mkdir.mkdir();
            
      //Iterator<String> iterator = mRequest.getFileNames(); // 꼭 이터레이터로 써야 한다. 파일 이름 여러개가 들어 갈 수도 있으니
      
        /*  
       while( iterator.hasNext() ){
    		String uploadFile = iterator.next();
        	MultipartFile mFile = mRequest.getFile(uploadFile); //우선 받아온 파일 1개
    	}
    	
       */
        /*System.out.println("사이크 : " +  files.size());*/
        for( int indexFiles = 0; indexFiles < files.size(); indexFiles++ ) {
            MultipartFile mFile = files.get(indexFiles); //우선 받아온 파일 1개
            if( !mFile.isEmpty() ) {
                 isUploadCheck = false;
                 orignalFileName  = mFile.getOriginalFilename(); // 확장자 포함 full name =>  test.txt
                 orignalOnlyeName = orignalFileName.substring(0, orignalFileName.lastIndexOf(".")).replaceAll(" ", "_"); // 순수 이름만 test
                 orignalExt       = orignalFileName.substring(orignalFileName.lastIndexOf(".") + 1, orignalFileName.length()); // 확장자 확인
                 orignalSize      = mFile.getSize();
                 System.out.println( "orignalFileName :" + orignalFileName );
                 System.out.println( "orignalExt :" + orignalExt );
                 System.out.println( "orignalSize :" + orignalSize );
                
                //업로드 가능 확장자 및 허용 파일 크기 용량 확인
                for (int l = 0; l < allowFiles.length; l++) {
                    if (orignalExt.equalsIgnoreCase(allowFiles[l])) {
                        if( orignalSize < allowFileSize ) { 
                            isUploadCheck = true;
                            break;
                        } else {
                            result.add(String.valueOf((indexFiles+1)));
                            break;
                        }
                    } 
                }
                
                if( isUploadCheck ) {
                    //파일 업로드 진행 중복된 이름 변경 
                    String replaceName = orignalOnlyeName.replaceAll("\\(", "-").replaceAll("\\)", "");
                    File upLoadFile = new File( saveFolder + replaceName + "." + orignalExt);
                    if( upLoadFile.exists() ) {
                        int k = 1;
                        boolean chk = true;
                        while (chk) {
                            upLoadFile = new File(saveFolder + replaceName + "-" + k + "."+orignalExt);
                            if (!upLoadFile.exists()) { 
                                chk = false;
                            }
                            
                            k++;
                        }
                    }
                    
                    saveFileNames.add(upLoadFile.getName());
                     
                    try {
                        mFile.transferTo( upLoadFile );
                        logger.info(upLoadFile.getName() + "파일 업로드 ");
                    } catch ( Exception e) {
                        logger.info("에러");
                        logger.info(e.toString());
                        result.add("에러 : "+ e.toString());
                        
                    }
                } else {
                    result.add(String.valueOf((indexFiles+1)));
                }
                
            }
        }
        
        return result;
    }
}
