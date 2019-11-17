package com.renee.jin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.renee.jin.board.service.SimsimService;
import com.renee.jin.model.BoardBean;
import com.renee.jin.utile.CommonConfig;
import com.renee.jin.utile.FileUpload;
import com.renee.jin.utile.Paging;
import com.renee.jin.utile.RequestSetting;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static CommonConfig cfg         = new CommonConfig();
    private static String returnUrl         = cfg.getSimsimUrl();
    private static String serverFolder      = cfg.getRemoteSimSimSaveFolder();
    private static String localSaveFolder   = cfg.getLocalSimSimSaveFolder();
    private static String[] allowFiles      = cfg.getAllowFile().split(",");
    private static Long allowFileSize       = Long.valueOf( cfg.getAllowFileSize() );
    private static Boolean isDev            = cfg.getDevelopment().equals("1") ? true : false;
	
	@Autowired
    SimsimService sismsim;
	
	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String listPage( Model model ,HttpSession session, HttpServletRequest request, HttpServletResponse response, BoardBean bean) {
		String pageNum = request.getParameter("pageNum") != null ?  request.getParameter("pageNum") : "1";
	    String searchField = request.getParameter("searchField") != null ?  request.getParameter("searchField") : "";
	    String searchString = request.getParameter("searchString") != null ?  request.getParameter("searchString") : "";
        String showGrid = request.getParameter("showGrid") != null ?  request.getParameter("showGrid") : "5";
	    HashMap<String, String> like = new HashMap<>();
	    HashMap<String, String> where = new HashMap<>();
	    HashMap<String, String> whereNot = new HashMap<>();
	    whereNot.put("USEFLAG","3");
	    bean.setWhereNot(whereNot);
	    bean.setWhere(where);
	    
	    switch ( searchField ) {
        case "sTitle":
            like.put("STITLE", searchString);
            bean.setLike(like);
            
            break;
        case "sTitleContents":
            like.put("STITLE", searchString);
            like.put("SCONTENT", searchString);
            bean.setLike(like);
            break;
        case "sWriter":
            like.put("ID", searchString);
            bean.setLike(like);
            break;
        }
	    
	    List<BoardBean> boardBeans = sismsim.getbordList(bean);
	    //총 갯수 구하기
	    Paging paging = new Paging(boardBeans.size(), pageNum,  Integer.parseInt(showGrid), 5 );
	    
	    // 뿌릴꺼만 가지고 오기
	    bean.setLimitStart(paging.getStartRow());
	    bean.setLimitEnd(paging.getPageSize());
	    boardBeans = sismsim.getbordList(bean);
	    model.addAttribute("currnetPage", pageNum);
	    model.addAttribute("searchField", searchField);
	    model.addAttribute("searchString", searchString);
	    model.addAttribute("showGrid", showGrid);
	    model.addAttribute("boadList",boardBeans);
	    model.addAttribute("paging",paging);

	    return "list";
	}
	
	@RequestMapping(value = "detail.do", method = RequestMethod.GET)
    public String detail( Model model ,HttpSession session, HttpServletRequest request, BoardBean bean) {
        HashMap<String, String> where = new HashMap<>();
        String filenames[];
        String seq = request.getParameter("seq").toString();
        where.put("SEQ", seq);
        
        bean.setWhere(where);
        List<BoardBean> boardBeans = sismsim.getbordList(bean);
        if(boardBeans.get(0).getUseFlag()==1) {
	        if( bean.getPw() == "" || bean.getPw() == null || !bean.getPw().equals(boardBeans.get(0).getPw()) ) {
	        	String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
	            String searchField = request.getParameter("searchField");
	            String searchString = request.getParameter("searchString");
	            try {
	            	searchString = URLEncoder.encode(searchString, "UTF-8");
	    		} catch (UnsupportedEncodingException e) {
	    			// TODO Auto-generated catch block
	    		}
	        	return "redirect:getPassword.do?seq="+seq+"&pageNum="+pageNum+"&searchField="+searchField+"&searchString="+searchString;
	        }
        }
        if(boardBeans.get(0).getsFilename() != null) {
            filenames  = boardBeans.get(0).getsFilename().split(",");
            model.addAttribute("filenames",filenames);
        }
        
        boardBeans.get(0).setsContent(boardBeans.get(0).getsContent().replaceAll("&#40;", "(").replaceAll("&#41;", ")").replaceAll("\r\n", "<br>").replaceAll("&#39;", "'").replaceAll("&lt;", "<").replaceAll("&gt;",">").replaceAll("\"", "'"));
       
        
        int readCount = boardBeans.get(0).getReadCount()+1;
        
        BoardBean updateBean = new BoardBean();
        updateBean.setWhere(where);
        updateBean.setReadCount(readCount);
        sismsim.boardUpdate(updateBean);
        
        boardBeans.get(0).setReadCount(readCount);        
        model.addAttribute("detail",boardBeans.get(0));
        
        return "detail";
    }
	
	@RequestMapping(value = "listDelete.do", method = RequestMethod.GET)
    public String listDelete( Model model ,HttpSession session, HttpServletRequest request, BoardBean bean) {
        String seq = request.getParameter("seq").toString();
        BoardBean getOne = getOne(seq);
        if( bean.getPw() == "" || bean.getPw() == null || !bean.getPw().equals(getOne.getPw()) ) {
        	String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
            String searchField = request.getParameter("searchField");
            String searchString = request.getParameter("searchString");
            try {
            	searchString = URLEncoder.encode(searchString, "UTF-8");
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    		}
        	return "redirect:delete.do?seq="+seq+"&pageNum="+pageNum+"&searchField="+searchField+"&searchString="+searchString;
        }
        
        
        //파일 전체 지우기 안함 .. 글을 그냥 숨길것임
        //fileUpload.removeFiles(sFilePath, sFilename);
        
        HashMap<String, String> where = new HashMap<>();
        bean.setUseFlag(3);
        where.put("SEQ", seq);
        bean.setWhere(where);
        
        sismsim.boardUpdate(bean);
        
        return "redirect:/list.do";
    }
	
	@RequestMapping(value = "update_view.do", method = RequestMethod.POST)
    public String update_view_ch( Model model ,HttpSession session, HttpServletRequest request, BoardBean bean) throws Exception {
        String seq = request.getParameter("seq").toString();
        BoardBean getOne = getOne(seq);
        if( bean.getPw() == "" || bean.getPw() == null || !bean.getPw().equals(getOne.getPw()) ) {
        	String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
            String searchField = request.getParameter("searchField");
            String searchString = request.getParameter("searchString");
            try {
            	searchString = URLEncoder.encode(searchString, "UTF-8");
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    		}
        	return "redirect:update.do?seq="+seq+"&pageNum="+pageNum+"&searchField="+searchField+"&searchString="+searchString;
        }
        
        HashMap<String, String> where = new HashMap<>();
        where.put("SEQ", seq);
        bean.setWhere(where);
        List<BoardBean> boardBeans = sismsim.getbordList(bean);
        
        
        boardBeans.get(0).setsContent(boardBeans.get(0).getsContent().replaceAll("&#40;", "(").replaceAll("&#41;", ")").replaceAll("\r\n", "<br>").replaceAll("&#39;", "'").replaceAll("&lt;", "<").replaceAll("&gt;",">").replaceAll("\"", "'"));
        model.addAttribute("detail",boardBeans.get(0));
        model.addAttribute("pageNum",request.getParameter("pageNum"));
        model.addAttribute("searchField",request.getParameter("searchField"));
        model.addAttribute("searchString",request.getParameter("searchString"));
        
        return "update_view";
    }
	
	
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
    public String update( Model model ,HttpSession session, BoardBean bean, MultipartHttpServletRequest mRequest, HttpServletRequest request) {
        FileUpload fileUpload  = new FileUpload();
        HashMap<String, String> where = new HashMap<>();
        String sFilename = "";
        List<MultipartFile> files = mRequest.getFiles("userfile[]");
        String thisSeq = request.getParameter("seqThis");
        String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
        String searchField = request.getParameter("searchField");
        String searchString = request.getParameter("searchString");
        try {
        	searchString = URLEncoder.encode(searchString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BoardBean getOne = getOne(thisSeq);
        
        if( thisSeq != null ) {
            // 수정 할 파일이 있다면 기존 것 지우기 아니면 유지
            if( files.size() > 0 && !files.get(0).isEmpty() ) {
                fileUpload.setRemoveFileName(getOne.getsFilename());
                fileUpload.setRemovePath(getOne.getsFilepath());
                ArrayList<String> upLoadResult = fileUpload.uploadFiles(mRequest, files, isDev, localSaveFolder, serverFolder, true, allowFiles, allowFileSize);

                sFilename  = String.join(",", fileUpload.getSaveFileNames());
                if( !sFilename.equals("") ) {
                    bean.setsFilename(sFilename);
                    bean.setsFilepath(fileUpload.getSaveFolder());
                }
               
                try {
                    if( upLoadResult.size() >0 ) {
                        String resultSet = String.join(",", upLoadResult);
                       // msg = "&msg="+URLEncoder.encode(resultSet+"번째 파일의 용량 및 확장자가 잘못되어 업로드 되지 못했습니다.", "UTF-8");
                    }
                } catch (Exception e) {

                }
                
            }
            where.put("SEQ", thisSeq);
            bean.setsJoinUpdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            bean.setWhere(where);
            sismsim.boardUpdate(bean);
            
        }
        
        return "redirect:detail.do?seq="+thisSeq+"&pageNum="+pageNum+"&searchField="+searchField+"&searchString="+searchString;
    }
	
	@RequestMapping(value = "write.do", method = RequestMethod.POST)
    public String writeSimsim( Model model, BoardBean bean, MultipartHttpServletRequest mRequest) {
	    FileUpload fileUpload  = new FileUpload();
	    String sFilename = "";
	    List<MultipartFile> files = mRequest.getFiles("userfile[]");
	    String pageNum = mRequest.getParameter("pageNum") != null ? mRequest.getParameter("pageNum") : "1";
        String searchField = mRequest.getParameter("searchField");
        String searchString = mRequest.getParameter("searchString");
        try {
        	searchString = URLEncoder.encode(searchString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ArrayList<String> upLoadResult = fileUpload.uploadFiles(mRequest, files, isDev, localSaveFolder, serverFolder, false, allowFiles, allowFileSize);
	    String msg = "";
	    if( files.size() > 0 ) {
	        sFilename  = String.join(",", fileUpload.getSaveFileNames());
	        if( !sFilename.equals("") ) {
	            bean.setsFilename(sFilename);
	            bean.setsFilepath(fileUpload.getSaveFolder());
	        }
	       
	        try {
	            if( upLoadResult.size() >0 ) {
	                String resultSet = String.join(",", upLoadResult);
	                msg = "&msg="+URLEncoder.encode(resultSet+"번째 파일의 용량 및 확장자가 잘못되어 업로드 되지 못했습니다.", "UTF-8");
	            }
			} catch (Exception e) {

			}
	        
	    }
	    
	    int returnSeq = sismsim.boardInsert(bean);
	    
        return "redirect:detail.do?seq="+returnSeq+"&pageNum="+pageNum+"&searchField="+searchField+"&searchString="+searchString;
    }
	@RequestMapping(value = "download.do", method = RequestMethod.GET)
		public void download( HttpServletRequest request, BoardBean bean, HttpServletResponse response ) {
		String seq       = request.getParameter("seq");
		String fileName  = request.getParameter("name");
		String filePath  = "";
		bean = getOne(seq);
		filePath = bean.getsFilepath();
		FileUpload fileUpload = new FileUpload();
		fileUpload.downloadFiles(response, fileName, filePath, request);
	}

	@RequestMapping(value = "{url}", method = RequestMethod.GET)
	public String indexPage( @PathVariable("url") String url ) {
		return url;
	}
	
	private BoardBean getOne ( String seq ) {
	    HashMap<String, String> where = new HashMap<>();
	    BoardBean bean = new BoardBean();
	    bean.setSeq(Integer.parseInt(seq));
	    where.put("SEQ", seq);
	    bean.setWhere(where);
	    List<BoardBean> boardBeans = sismsim.getbordList(bean);
	    return boardBeans.get(0);
	}
}
