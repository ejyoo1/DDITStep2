package kr.or.ddit.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * FileUpload API를 사용하는 HttpServletRequestWrapper 클래스
 * (HttpServletRequest에 기반한 API를 사용하면서 멀티파트처리 기능이 추가되었음.)
 * mac : "/Users/macween/upload_files";
 */
public class FileUploadRequestWrapper extends HttpServletRequestWrapper {
    public static final String UPLOAD_DIRECTORY = "d:/D_Other/upload_files"; // 업로드 경로 설정
    private boolean multipart = false; // 멀티파트여부

    // 데이터 타입 별 Map 멤버변수 설정
    private Map<String, String[]> parameterMap;	// 폼필드(파라미터) 데이터를 담기 위한 맵 
    private Map<String, Object> fileItemMap; // fileItem객체를 담기위한 맵

    /**
     * 생성자
     * @param request
     * @throws FileUploadException
     */
    public FileUploadRequestWrapper(HttpServletRequest request)
    throws FileUploadException{
        this(request, -1, -1, -1, null); // 파일 설정 정보를 -1로 미리 설정 (43번 라인 호출)
    }

    /**
     * 생성자
     * @param request 기본 요청 객체
     * @param memoryThreshold 메모리 임계크기(이 크기가 넘어가면 레파지토리 위치에 임시파일로 저장됨)
     * @param fileSizeMax 파일 1개당 최대크기
     * @param maxRequestSize 요청 파일 최대 크기
     * @param repositoryPath 임시파일 저장경로
     * @throws FileUploadException
     */
    public FileUploadRequestWrapper(HttpServletRequest request,
        int memoryThreshold, long fileSizeMax, long maxRequestSize, String repositoryPath) throws FileUploadException {
        super(request); // 필수로 super를 호출해야 함.

        parsing(request, memoryThreshold, fileSizeMax, maxRequestSize, repositoryPath); // start
    }

    /**
     * 멀티파트 데이터를 파싱하여 두개의 맵에 나누어 담는다.
     * @param request
     * @param memoryThreshold 메모리 임계크기(이 크기가 넘어가면 레파지토리 위치에 임시파일로 저장됨)
     * @param fileSizeMax 파일 1개당 최대 크기
     * @param maxRequestSize 요청 파일 최대 크기
     * @param repositoryPath 임시파일 저장경로
     * @throws FileUploadException
     * 
     * 파일이면 파일의 수대로 fileItemMap 에 담김
     * 폼 관련 데이타는 parameterMap 에 담김(key 값으로)
     */
    private void parsing(HttpServletRequest request,
        int memoryThreshold, long fileSizeMax, long maxRequestSize, String repositoryPath) throws FileUploadException { // 여기서 부터 시작
    	// 실제 업로드 시작 ==> 파싱 시작 (멀티파트? Y:파싱시작  N: 파싱안함)
        if (ServletFileUpload.isMultipartContent(request)) { // request 내 서블릿 파일의 인코딩 타입이 멀티파트인지
            multipart = true; // 멀티파트임을 설정함.

            parameterMap = new HashMap<>();
            fileItemMap = new HashMap<>();

            DiskFileItemFactory factory = new DiskFileItemFactory();
            if (memoryThreshold != -1) {
            	factory.setSizeThreshold(memoryThreshold);
            }
            if (repositoryPath != null) {// 저장 경로가 설정되지 않았으면...
            	factory.setRepository(new File(repositoryPath));
            }else {
            	factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            }

            ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
            servletFileUpload.setFileSizeMax(fileSizeMax);
            servletFileUpload.setSizeMax(maxRequestSize);
            // 여기까지 servletFileUpload 객체 초기화 작업 끝!

            // 실제 파싱하는 부분 시작!
            List<FileItem> list = servletFileUpload.parseRequest(request); 
            for (int i = 0 ; i < list.size() ; i++) {
                FileItem fileItem = (FileItem) list.get(i); // 파일 아이템 하나 꺼내서
                String name = fileItem.getFieldName(); // 필드명 가져오기

                if (fileItem.isFormField()) { // 폼필드인 경우... (필드인지 파일인지? Y : 폼필드임(파일이 아님) N : 파일인경우 )
                    String value = "";
					try {
						// 폼필드의 값이 한글인 경우에는 해당 문자열을 적절히 변환을 해주어야 한다. (둘중하나 선택)
						//value = new String(fileItem.getString().getBytes("8859_1"), "UTF-8");
						value = fileItem.getString("UTF-8"); // 폼 이름 가져옴
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
                    String[] values = (String[]) parameterMap.get(name); // 기존에 동일한 이름으로 등록된것이 있는지 확인만 하기 위해 생성
                    if (values == null) { // 처음 만드는 경우... (값을 세팅한적이 없는 경우)
                        values = new String[] { value }; // value를 넣어 초기화
                    } else { // 기존에 이미 존재하는 경우... (이전에 값을 세팅한 적이 있는 경우)
                        String[] tempValues = new String[values.length + 1];  // 길이 + 1 한 값 생성
                        System.arraycopy(values, 0, tempValues, 0, values.length); // 
                        tempValues[tempValues.length - 1] = value; // 마지막 방에 value 삽입
                        values = tempValues;
                    }
                    parameterMap.put(name, values); // String 배열 삽입
                } else { // 파일인 경우... (멀티파트가 아닌경우)
                	if(fileItem.getSize() > 0) { // 파일이 존재하는 경우...
                		fileItemMap.put(name, fileItem);
                	}
                }
            }
            addTo(); // 현재 객체를 속성값으로 설정한다.(래퍼클래스 여부 체크를 위해 설정) (kr.or....class에 래퍼클래스를 이름을 설정)
        } // 멀티파트 관련 로직 끝!
    }

    public boolean isMultipartContent() {
        return multipart;
    }

    public String getParameter(String name) {
        if (multipart) {
            String[] values = (String[])parameterMap.get(name);
            if (values == null) return null;
            return values[0];
        } else
            return super.getParameter(name);
    }

    public String[] getParameterValues(String name) {
        if (multipart)
            return (String[])parameterMap.get(name);
        else
            return super.getParameterValues(name);
    }

    public Enumeration<String> getParameterNames() {
        if (multipart) {
            return new Enumeration<String>() {
                Iterator<String> iter = parameterMap.keySet().iterator();

                public boolean hasMoreElements() {
                    return iter.hasNext();
                }
                public String nextElement() {
                    return iter.next();
                }
            };
        } else {
            return super.getParameterNames();
        }
    }

    public Map<String, String[]> getParameterMap() {
        if (multipart)
            return parameterMap;
        else
            return super.getParameterMap();
    }

    public Map<String, Object> getFileItemMap() {
    	 if (multipart)
             return fileItemMap;
         else
             return null;
    }

    public FileItem getFileItem(String name) {
        if (multipart)
            return (FileItem) fileItemMap.get(name);
        else
            return null;
    }

    /**
     * fileItemMap에 존재하는 FileItem을 삭제한다.
     */
    public void delete() {
        if (multipart) {
            Iterator<Object> fileItemIter = fileItemMap.values().iterator();
            while( fileItemIter.hasNext()) {
                FileItem fileItem = (FileItem)fileItemIter.next();
                fileItem.delete();
            }
        }
    }

    /**
     * request객체에 속성값으로 현재 래퍼객체(FileUploadRequestWrapper)를 등록함.
     * (추후 FileUploadRequestWrapper 객체 여부 체크하기 위해서 사용됨. 멀티파트인 경우에만 호출됨.)
     */
    public void addTo() {
      super.setAttribute(FileUploadRequestWrapper.class.getName(), this);
    }


    /**
     * FileUploadRequestWrapper 클래스 여부를 체크함.
     * @param request 요청객체
     * @return true 이면 래퍼클래스, false 이면 래퍼클래스 아님.
     */
    public static boolean hasWrapper(HttpServletRequest request) {
    	if (FileUploadRequestWrapper.getFrom(request) == null) {
    		return false;
    	} else {
    		return true;
    	}
    }

    /**
     * request객체에 존재하는 FileUploadRequestWrapper객체를 가져옴
     * @param request 객체
     * @return 존재하면 FileUploadRequestWrapper 객체, 없으면 null 리턴함.
     */
    public static FileUploadRequestWrapper
                  getFrom(HttpServletRequest request) {
        return (FileUploadRequestWrapper)
            request.getAttribute(FileUploadRequestWrapper.class.getName());
    }

}
